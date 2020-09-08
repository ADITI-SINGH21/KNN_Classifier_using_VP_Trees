package col106.bigassignment.index;
import java.io.*;
import java.util.*;
import java.lang.*;
public class DistanceL2 implements DistanceFunction{
	public double distance(MNISTImage one,MNISTImage two){
		int[] a = (int[])(Object) one.getImage();
		int[] b = (int[])(Object) two.getImage(); 
		double L2 = 0.0;
		for (int i=0; i<a.length ; i++) {
			double a1 = (double) a[i];
			double b1 = (double) b[i];
			// System.out.println("a1 " +a1+"b1 "+b1);
		 	L2+= Math.pow(Math.abs(a1-b1),2.0);
		 } 
		return Math.sqrt(L2);
	}
}