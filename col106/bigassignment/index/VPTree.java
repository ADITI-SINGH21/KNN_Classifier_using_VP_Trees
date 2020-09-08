package col106.bigassignment.index;

import java.util.Collection;
import java.io.*;
import java.util.*;
import java.lang.*;
public abstract class VPTree<T> {
	// Create VPTree from collection based on the DistanceFunction d
    public VPTree (Collection <T> collection, DistanceFunction d) {}
    public abstract String toString();
    public abstract DistanceFunction getDistanceFunc (); //return the distance function being used in this index
    public abstract T findOneNN (T q); //should print root-leaf pivots
    public abstract void printTree(); //print four lines: pivot, root, left subtree collection, right subtree collection
}
