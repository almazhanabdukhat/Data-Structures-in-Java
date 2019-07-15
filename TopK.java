import java.util.Random;
public class TopK {
	
	
		static int topKelement(int array[], int k) {
			//call the recursive method quickselect
			return quickSelect(array, 0, array.length - 1, k);
		}
		
		//helper method for partition 
		private static  void swap(int [] array, int i, int j) {
		    int temp = array[i];
		    array[i] = array[j];
		    array[j] = temp;
		}
		//method stores elements that are greater than pivot to the left side
		//and elements that are less than pivot to the right side 
		public static int partition(int [] array, int  low, int high, int pivot_index ){
			int pivot = array[pivot_index];
			swap (array, pivot_index, high);
			int i = low;
			for (int j=low; j < high; j++) {
				//if the element is greater than pivot, it is stored to the left side of the array
				//the elements are stored in decreasing order (reverse) since we are searching for the top-k element
				if (array[j] > pivot) {
					swap(array, i,j);
					i++;
				  }
			    }
			    //after finishing the sorting, insert the pivot element (stored at the last position) at the correct index
			    swap(array,i,high);
			    return i;
			}
			

	    //quickSelect method finds the top-k element by recursively searching through the relevant part of the array
		private static int quickSelect(int array[], int low, int high, int k){
		    // top-k element cannot be negative
		    if(k <= 0) {
		        return Integer.MAX_VALUE;
		    }
		    
		    // top-k element must be positive but less than the number of elements in the array
		    int size = high - low;
		    if (k <= size + 1) {
		        // generate a random index to select a random pivot element for the partition
		    	// get the position of the pivot element in the array
		        Random rand = new Random();
		        int random_index = low + rand.nextInt(high - low + 1);
		        int pivot_index = partition(array, low, high,random_index);

		        // if pivot_index is same as k, return the element at the pivot index
		        if (k-1 == pivot_index - low) {
		        	return array[pivot_index];
		        }

		        // If k is less than size of the left partition, then search in the left part recursively
		        if(k - 1 < pivot_index - low) {
		        	return quickSelect(array, low, pivot_index - 1, k);
		        }

		        // Otherwise, search in the right part recursively
		        //note: k-1 since we exclude pivot element; k-pivot_index since we 
		        //exclude elements to the left side of the pivot; k+low since the method can be called in a subarray
		        return quickSelect(array, pivot_index + 1, high, k - pivot_index + low - 1);
		    }
		    
		    // If k is more than number of elements in array, no solution
		    return Integer.MIN_VALUE;
		}
		public static void main (String [] args) {

	    	
	    	//Testing topK
	    	int[] test = {7, 4, 8, 3, 9, 2, 10, 1};
	    	int second_smallest = quickSelect(test, 0, test.length - 1, 3);
	    	System.out.println("Second Largest Element: " + second_smallest + "\n");

	    	

	    }

	}

