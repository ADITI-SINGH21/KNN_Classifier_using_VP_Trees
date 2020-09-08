package col106.bigassignment.index;
import java.io.*;
import java.util.*;
import java.lang.*;
public class DistanceL1 implements DistanceFunction{
	public double distance(MNISTImage one,MNISTImage two){
		int[] a = (int[])(Object) one.getImage();
		int[] b = (int[])(Object) two.getImage(); 
		double L1 = 0.0;
		for (int i=0; i<a.length ; i++) {
			double a1 = (double) a[i];
			double b1 = (double) b[i];
		 	L1+= Math.abs(a1-b1);
		 } 
		return L1;
	}
}