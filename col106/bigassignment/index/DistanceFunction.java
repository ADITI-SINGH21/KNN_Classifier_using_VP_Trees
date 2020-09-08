package col106.bigassignment.index;
//
// Implement this interface on specific distance functions
// such as L1, L2, Linf distances
// 
import java.io.*;
import java.util.*;
import java.lang.*;
public interface DistanceFunction  {
    double distance(MNISTImage one, MNISTImage two); // returns L1, L2 or L∞ distance
}

