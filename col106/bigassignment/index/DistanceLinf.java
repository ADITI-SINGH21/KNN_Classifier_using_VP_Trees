package col106.bigassignment.index;
import java.io.*;
import java.util.*;
import java.lang.*;
public class DistanceLinf implements DistanceFunction{
	public double distance(MNISTImage one,MNISTImage two){
		int[] a = (int[])(Object) one.getImage();
		int[] b = (int[])(Object) two.getImage(); 
		double Linf = 0.0;
		double[] arr = new double[a.length];
		for (int i=0; i<a.length ; i++) {
			double a1 = (double) a[i];
			double b1 = (double) b[i];
			arr[i] = Math.abs(a1-b1);
		}
		for (int i=0; i<a.length ; i++) {
			if (arr[i]>Linf) {
				Linf = arr[i];
			}
		}
		return Linf;
	}
}