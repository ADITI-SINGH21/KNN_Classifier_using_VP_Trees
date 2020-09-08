package col106.bigassignment.classifier;

import java.util.Collection;

import col106.bigassignment.index.DistanceFunction;
import col106.bigassignment.index.VPTreeImpl;

public interface Classifier<MNISTImage>{
	public void train (Collection<MNISTImage> trainingSet);
	public void test (Collection<MNISTImage> testSet);
	public void printAccuracy (); 
}
