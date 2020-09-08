package col106.bigassignment;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import col106.bigassignment.classifier.OneNNClassifier;
import col106.bigassignment.index.DistanceL2;
import col106.bigassignment.index.MNISTImage;
import col106.bigassignment.index.DistanceL1;
import col106.bigassignment.index.DistanceLinf;
import col106.bigassignment.index.VPTreeImpl;
 // javac ./col106/bigassignment/classifier/OneNNClassifier.java
// import col106.bigassignment.classifier.*;
//driver code
public class Main {
	public static int ChangeDataType(byte[] bytes) {
		return (int) (((bytes[0] & 0xff) << 24) | ((bytes[1] & 0xff) << 16) | ((bytes[2] & 0xff) << 8)
				| (bytes[3] & 0xff));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(System.getProperty("user.dir"));
		try (	FileInputStream train_images = new FileInputStream("/mnt/c/Users/ADITI SINGH/Desktop/2019-20_SemII/COL106/Big_Assignment/Aditi/Data/Data/train_images");
				FileInputStream train_labels = new FileInputStream("/mnt/c/Users/ADITI SINGH/Desktop/2019-20_SemII/COL106/Big_Assignment/Aditi/Data/Data/train_labels");
				String test_images = args[0];
				String test_labels = args[1];
				FileInputStream test_images = new FileInputStream(test_images);
				FileInputStream test_labels = new FileInputStream(test_labels);

		) {
			// Reading the train images
			train_images.skip(4);
			byte[] arr = new byte[4];
			train_images.read(arr);
			train_images.skip(8);
			train_labels.skip(8);
			int total = ChangeDataType(arr);
			int[][] images = new int[total][28 * 28];
			int[][] labels = new int[total][1];
			byte[] image = new byte[28 * 28];
			byte[] label = new byte[1];
			int i = 0;
			int j = 0;
			while (i < total) {
				train_images.read(image);
				train_labels.read(label);
				while (j < 28 * 28) {
					images[i][j] = ((int) image[j] & 0xFF);
					j++;
				}
				labels[i][0] = ((int) label[0] & 0xFF);
				i++;
			}
			ArrayList<MNISTImage> train_Data_Label = new ArrayList<>();
			for (int k = 0; k < total; k++) {
				int[] arrFinal = new int[784];
				for (int m = 0; m < 784; m++) {
					arrFinal[m] = images[k][m];
					// System.out.println(arrFinal[m]);
				}
				MNISTImage imageClass = new MNISTImage(arrFinal, labels[k][0], k);
				train_Data_Label.add(imageClass);
			}
			// ArrayList<MNISTImage> test_Data_Label = new ArrayList<>();
			// for (int k=9999; k>8999 ; k-- ) {
			// 	test_Data_Label.add(train_Data_Label.get(k));
			// }
			// OneNNClassifier<MNISTImage> classifier = new OneNNClassifier(new DistanceL2());
			// classifier.train(train_Data_Label);
			// Collection<MNISTImage> prediction = classifier.predict(test_Data_Label);
			// for (MNISTImage result : prediction) {
			// 	System.out.print(" " + result.getIndexFromTrainingSet()+" ");
			// }
			VPTreeImpl<MNISTImage> VPTreeEuclidean = new VPTreeImpl(ArrayList<MNISTImage> train_Data_Label,DistanceL2());//implements Euclidean Distance
			VPTreeImpl<MNISTImage> VPTreeL1 = new VPTreeImpl(ArrayList<MNISTImage> train_Data_Label,DistanceL1());//implements L1
			VPTreeImpl<MNISTImage> VPTreeLinf = new VPTreeImpl(ArrayList<MNISTImage> train_Data_Label,DistanceLinf());//implements L2
			System.out.println("VP Tree print corresponding to Euclidean");
			VPTreeEuclidean.Build_VPTree();
			VPTreeEuclidean.printTree();
			System.out.println("VP Tree print corresponding to L1");
			VPTreeL1.Build_VPTree();
			VPTreeL1.printTree();
			System.out.println("VP Tree print corresponding to Linf");
			VPTreeLinf.Build_VPTree();
			VPTreeLinf.printTree(); 
			// Reading the test_images
			test_images.skip(4);
			byte[] testarr = new byte[4];
			test_images.read(arr);
			test_images.skip(8);
			test_labels.skip(8);
			int testtotal = ChangeDataType(arr);
			int[][] testimages = new int[testtotal][28 * 28];
			int[][] testlabels = new int[testtotal][1];
			byte[] testimage = new byte[28 * 28];
			byte[] testlabel = new byte[1];
			i = 0;
			j = 0;
			while (i < testtotal) {
				test_images.read(image);
				test_labels.read(label);
				while (j < 28 * 28) {
					testimages[i][j] = ((int) testimage[j] & 0xFF);
					j++;
				}
				testlabels[i][0] = ((int) testlabel[0] & 0xFF);
				i++;
			}
			Collection<MNISTImage> test_Data_Label = new ArrayList<>();
			for (int k = 0; k < total; k++) {
				int[] arrFinal = new int[784];
				for (int m = 0; m < 784; m++) {
					arrFinal[m] = testimages[k][m];
				}
				MNISTImage testimageClass = new MNISTImage(arrFinal, labels[k][0], -1);
				test_Data_Label.add(testimageClass);
			}
			OneNNClassifier<MNISTImage> classifier = new OneNNClassifier(new DistanceL2<MNISTImage>());
			classifier.train(train_Data_Label);
			Collection<MNISTImage> prediction = classifier.predict(test_Data_Label);
			for (MNISTImage result : prediction) {
				System.out.println("Corresponding Image Index = " + result.getIndexFromTrainingSet());
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
