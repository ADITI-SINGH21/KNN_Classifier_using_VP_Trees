package col106.bigassignment.index;
import java.io.*;
import java.util.*;
import java.lang.*;
public final class MNISTImage{
	public int[] image_data;
	public int label;
	public int index;
	public MNISTImage(){}
	public MNISTImage(int[] image_data,int label,int index){
		this.image_data = image_data;
		this.label = label;
		this.index = index;
	}

	public int[] getImage(){
		return this.image_data;
	}

	public int getLabel(){
		return this.label;
	}

	public int getIndexFromTrainingSet(){
		return this.index;
	}
}