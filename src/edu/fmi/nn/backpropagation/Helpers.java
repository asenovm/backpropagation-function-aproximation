package edu.fmi.nn.backpropagation;

public class Helpers {
	public static double[][] MakeMatrix(int rows, int cols) {
		double[][] result = new double[rows][];
		for (int i = 0; i < rows; ++i)
			result[i] = new double[cols];
		return result;
	}

	public static void ShowVector(double[] vector, int decimals, boolean blankLine) {
		for (int i = 0; i < vector.length; ++i) {
			if (i > 0 && i % 12 == 0) // max of 12 values per row
				System.out.println(" ");
			if (vector[i] >= 0.0)
				System.out.println(" ");
			System.out.println(vector[i]);
		}
		if (blankLine)
			System.out.println("\n");
	}

	public static void ShowMatrix(double[][] matrix, int numRows, int decimals)
    {
      int ct = 0;
      if (numRows == -1) numRows = Integer.MAX_VALUE; // if numRows == -1, show all rows
      for (int i = 0; i < matrix.length && ct < numRows; ++i)
      {
        for (int j = 0; j < matrix[0].length; ++j)
        {
          if (matrix[i][j] >= 0.0) {
        	  System.out.println(" ");
          }
          System.out.println(matrix[i][j]);
        }
        System.out.println("");
        ++ct;
      }
      System.out.println("");
    }
}
