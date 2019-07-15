
public class MatrixPower {
	
	//PROBLEM 1
	
	 //Optimized version of power method 
	static int[][] power(int [][] array , int n) {
		int [][] result;
		//base case: if power is 0, we multiply by the identity matrix
		if( n == 0) { 
			result = new int [array.length][array[0].length];
			for (int i=0; i<array.length; i++) {
				result[i][i]=1;
				for (int j=0; j<array[0].length; j++) {
					if (j != i) {
						result[i][j]=0;
					}
				}
			} 
			return result;
		}   
		
		//recursive case: recursively calculate the partial down to the base case
		else {
			int [][] partial = power(array, n/2);
			result = multiply(partial, partial);
			//if the power is odd, need to multiply one more time
			if( n%2 != 0 )
				result = multiply(result, array);
			}
		return result;
	    }
	
	//helper method that multiplies two matrices
	static int [][] multiply(int a[][], int b[][]) {
	int mul[][] = new int[a.length][b[0].length]; 
    for (int i = 0; i < a.length; i++) { 
        for (int j = 0; j < b[0].length; j++) { 
            mul[i][j] = 0; 
            for (int k = 0; k < b[0].length; k++) 
                mul[i][j] += a[i][k] * b[k][j]; 
        } 
    } 
    return mul;
	}
	public static void main (String [] args) {

    	int[][] arr= {{1,2,-1}, {4,-3,6},{3,-5,2}};

    	int [][] n = power(arr, 2);

    	
        System.out.println ("matrix power result:");
    	for (int i =0; i< n.length; i++) {

    		for (int j =0; j<n[0].length; j++) {

    			System.out.print(n[i][j] + " ");

    		}

    		System.out.println();

    	}
}
}
   
	
   


