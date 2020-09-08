package col106.bigassignment.classifier;

import java.util.ArrayList;
import java.util.Collection;

import col106.bigassignment.index.DistanceFunction;
import col106.bigassignment.index.VPTreeImpl;

public class OneNNClassifier<MNISTImage> implements Classifier<MNISTImage> {

	VPTreeImpl<MNISTImage> classifier;
	DistanceFunction d;

	public OneNNClassifier(DistanceFunction d) {
		this.d = d;
		this.classifier = null;
	}

	@Override
	public void train(Collection<MNISTImage> trainingSet) {
		classifier = new VPTreeImpl(trainingSet, this.d);
		classifier.Build_VPTree();
	}

	@Override
	public void test(Collection<MNISTImage> testSet) {

	}

	@Override
	public void printAccuracy() {

	}

	public Collection<MNISTImage> predict(Collection<MNISTImage> images) {
		Collection<MNISTImage> prediction = new ArrayList();
		for (MNISTImage query : images) {
			prediction.add(classifier.findOneNN(query));
		}
		return prediction;
	}

}
