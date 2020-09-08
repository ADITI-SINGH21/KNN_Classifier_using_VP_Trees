package col106.bigassignment.index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public final class VPTreeImpl<T> extends VPTree<T> {
	DistanceFunction d;
	Collection<T> collection;
	ArrayList<Item> list;
	VPNode root;
	Queue<VPNode> queue;
	double tau;

	public VPTreeImpl(Collection<T> collection, DistanceFunction d) {
		super(collection, d);
		// TODO Auto-generated constructor stub
		this.d = d;
		this.collection = collection;
		this.list = new ArrayList();
		this.root = null;
		this.queue = new LinkedList<VPNode>();
		this.tau = Double.MAX_VALUE;
		;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findOneNN(T q) {
		// TODO Auto-generated method stub
		return SearchQuery(q, root);
	}

	public T SearchQuery(T q, VPNode node) {
		if (node == null) {
			return null;
		}
		T desired = null;
		Item item = node.getCorr();
		double x = d.distance((MNISTImage)(Object) q, (MNISTImage)(Object)item.getId());
		double[] Il = new double[2];
		double[] Ir = new double[2];
		if (x < this.tau) {
			this.tau = x;
			desired = (T) item.getId();
		}
		Il[0] = (double) node.getLeft().getbnds().get(0) - this.tau;
		Il[1] = (double) node.getLeft().getbnds().get(node.getLeft().getbnds().size() - 1) + this.tau;
		Ir[0] = (double) node.getRight().getbnds().get(0) - this.tau;
		Ir[1] = (double) node.getRight().getbnds().get(node.getRight().getbnds().size() - 1) + this.tau;
		double middle = ((double) node.getLeft().getbnds().get(0)
				+ (double) node.getRight().getbnds().get(node.getRight().getbnds().size() - 1)) / 2;
		middle /= 2;
		if (x < middle) {
			if (x < Il[1] && x > Il[0]) {
				SearchQuery(q, node.getLeft());
			}
			if (x < Ir[1] && x > Ir[0]) {
				SearchQuery(q, node.getRight());
			}
		} else {
			if (x < Ir[1] && x > Ir[0]) {
				SearchQuery(q, node.getLeft());
			}
			if (x < Il[1] && x > Il[0]) {
				SearchQuery(q, node.getRight());
			}
		}
		return desired;
	}

	@Override
	public void printTree() {
		// TODO Auto-generated method stub
		printPreorder(this.root);
	}

	public void printPreorder(VPNode node) {
		MNISTImage n = (MNISTImage) (Object) node.getVantagePoint();
		if (n == null) {
			return;
		}
		System.out.println((int) n.getIndexFromTrainingSet());
		printPreorder(node.getLeft());
		printPreorder(node.getRight());
	}

	public VPNode getRoot() {
		return this.root;
	}

	public void Build_VPTree() {
		for (T imageList : collection) {
			Item<T> item = new Item(imageList);
			list.add(item);
		}
		this.root = Recursive_VPTree(list);
	}

	public VPNode Recursive_VPTree(ArrayList<Item> list1) {
		if (list1 == null || list1.size()==0) {
			return null;
		}
		VPNode<T> node = new VPNode();
		Item_Node x = Select_VantagePoint(list1);
		T p = (T) x.getPoint();
		node.setVantagePoint(p);
		Item item_p = (Item) x.getItem();
		node.setCorr(item_p);
		list1.remove(item_p);
		int n = list1.size();
		for (int i=0;i<list1.size();i++) {
			Item item = list1.get(i);
			double dist = d.distance((MNISTImage)(Object)p, (MNISTImage)(Object)item.getId());
			item.addTohist(d.distance((MNISTImage)(Object)p, (MNISTImage)(Object)item.getId()));
		}
		ArrayList<Double> median = new ArrayList();
		for (Item item : list1) {
			ArrayList<Double> history = item.getHist();
			median.add(history.get(history.size() - 1));
		}

		double mu = getMedian(median);
		ArrayList<Item> L = new ArrayList();
		ArrayList<Item> R = new ArrayList();
		for (int i = 0 ; i<list1.size() ; i++ ) {
			Item item10 = (Item)list1.get(i);
			ArrayList<Double> history = item10.getHist();
			if ((double)history.get(history.size() - 1) < mu) {
				L.add(item10);
				list1.remove(item10);
				i--;
			} else {
				R.add(item10);
				list1.remove(item10);
				i--;
			}
		}
		node.setLeft(Recursive_VPTree(L));
		node.setRight(Recursive_VPTree(R));
		T p2 = node.getVantagePoint();
		Item item_node = (Item) node.getCorr();
		if (node.getLeft() == null && node.getRight() == null) {
			ArrayList<Double> mergeS = item_node.getHist();
			Collections.sort(mergeS);
			node.setbnds(mergeS);
			return node;
		} else if (node.getLeft() != null && node.getRight() == null) {
			ArrayList<Double> mergeL = new ArrayList();
			mergeL.addAll(item_node.getHist());
			mergeL.addAll(node.getLeft().getbnds());
			Collections.sort(mergeL);
			node.setbnds(mergeL);
			return node;
		} else if (node.getLeft() == null && node.getRight() != null) {
			ArrayList<Double> mergeR = new ArrayList();
			mergeR.addAll(item_node.getHist());
			mergeR.addAll(node.getRight().getbnds());
			Collections.sort(mergeR);
			node.setbnds(mergeR);
			return node;
		}
		node.setbnds(Merge(node.getLeft().getbnds(), node.getRight().getbnds(), item_node.getHist()));
		return node;

	}

	public ArrayList<Double> Merge(ArrayList<Double> range_list1, ArrayList<Double> range_list2,
			ArrayList<Double> value_list) {
		ArrayList<Double> merge = new ArrayList();
		merge.addAll(range_list1);
		merge.addAll(range_list2);
		if (value_list != null)
			merge.addAll(value_list);
		Collections.sort(merge);
		return merge;
	}

	public Item_Node Select_VantagePoint(ArrayList<Item> S) {
		Collections.shuffle(S);
		T best_P = null;
		Item_Node x = new Item_Node();
		double best_spread = 0.0;
		int n = 0;
		if (S.size() > 5) {
			n = S.size() / 5;
		} else {
			n = S.size();
		}
		Collection<Item> P = new ArrayList();
		int count = 0;
		for (Item it : S) {
			if (count == n) {
				break;
			}
			P.add(it);
			count++;
		}
		for (Item iter : P) {
			Collections.shuffle(S);
			Collection<Item> D = new ArrayList();
			count = 0;
			for (Item it : S) {
				if (count == n) {
					break;
				}
				D.add(it);
				count++;
			}
			ArrayList<Double> median = new ArrayList();
			for (Item it1 : D) {
				median.add(d.distance((MNISTImage)(Object) it1.getId(), (MNISTImage)(Object) iter.getId()));
			}
			double mu = getMedian(median);
			ArrayList<Double> variance = new ArrayList();
			for (Item it1 : D) {
				variance.add(d.distance((MNISTImage)(Object) it1.getId(), (MNISTImage)(Object)iter.getId()));
			}
			double spread = getVariance(variance);
			if (spread > best_spread) {
				best_spread = spread;
				best_P = (T) iter.getId();
				x.setPoint(best_P);
				x.setItem(iter);
			}
		}
		return x;
	}

	public double getMedian(ArrayList<Double> median) {
		if (median.size() == 1) {
			return median.get(0);
		}
		Collections.sort(median);
		double mu = 0.0;
		int n = median.size();
		if (n % 2 != 0) {
			mu = median.get(n / 2);
		} else {
			mu = ((median.get((n - 1) / 2) + median.get(n / 2)) / 2);
		}
		return mu;
	}

	public double getVariance(ArrayList<Double> variance) {
		double mean = 0.0;
		for (int i = 0; i < variance.size(); i++) {
			mean += variance.get(i);
		}
		mean = mean / variance.size();
		double moment2 = 0.0;
		for (int i = 0; i < variance.size(); i++) {
			moment2 += Math.pow(variance.get(i) - mean, 2);
		}
		moment2 = moment2 / variance.size();
		return moment2;
	}

	@Override
	public DistanceFunction getDistanceFunc() {
		// TODO Auto-generated method stub
		return (DistanceFunction) (Object) d;
	}

}

class VPNode<T> {
	public T vantagePoint;
	public VPNode left;
	public VPNode right;
	public ArrayList<Double> bnds;
	public Item corr;

	public VPNode() {
		this.vantagePoint = null;
		this.left = null;
		this.right = null;
		this.bnds = new ArrayList();
		this.corr = null;
	}

	public void setVantagePoint(T vantagePoint) {
		this.vantagePoint = vantagePoint;
	}

	public T getVantagePoint() {
		return this.vantagePoint;
	}

	public VPNode getLeft() {
		return this.left;
	}

	public VPNode getRight() {
		return this.right;
	}

	public Item getCorr() {
		return this.corr;
	}

	public void setCorr(Item corr) {
		this.corr = corr;
	}

	public void setLeft(VPNode left) {
		this.left = left;
	}

	public void setRight(VPNode right) {
		this.right = right;
	}

	public void setbnds(ArrayList<Double> bounds) {
		this.bnds = bounds;
	}

	public ArrayList<Double> getbnds() {
		return this.bnds;
	}

}

class Item<T> {
	public T id;
	public ArrayList<Double> hist;

	public Item(T id) {
		this.id = id;
		this.hist = new ArrayList();
	}

	public void addTohist(double item) {
		hist.add(item);
	}

	public T getId() {
		return this.id;
	}

	public ArrayList<Double> getHist() {
		return this.hist;
	}
}

class Item_Node<T> {
	T point;
	Item item;

	public Item_Node() {
		this.item = null;
		this.point = null;
	}

	public void setPoint(T point) {
		this.point = point;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public T getPoint() {
		return this.point;
	}

	public Item getItem() {
		return this.item;
	}
}
