package lab10;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * 
 * COMP 3021
 * 
This is a class that prints the maximum value of a given array of 90 elements

This is a single threaded version.

Create a multi-thread version with 3 threads:

one thread finds the max among the cells [0,29] 
another thread the max among the cells [30,59] 
another thread the max among the cells [60,89]

Compare the results of the three threads and print at console the max value.

 * 
 * @author valerio
 *
 */
public class FindMax {
	// this is an array of 90 elements
	// the max value of this array is 9999
	static int[] array = { 1, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2, 3, 4543,
			234, 3, 454, 1, 2, 3, 1, 9999, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3, 1, 34, 5, 6, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3 };

	public static void main(String[] args) {
		new FindMax().printMax();
	}

	public void printMax() {
		// this is a single threaded version
		/*int max = findMax(0, array.length - 1);
		System.out.println("the max value is " + max);*/
		//mulit thread version
		findMaxTask first = new findMaxTask(0, 29);
		findMaxTask second = new findMaxTask(30, 59);
		findMaxTask third = new findMaxTask(60, 89);
		Thread one = new Thread(first);
		Thread two = new Thread(second);
		Thread three = new Thread(third);
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.execute(one);
		pool.execute(two);
		pool.execute(three);
		pool.shutdown();
		while(!pool.isTerminated());
		int[] anotherarray = {first.localMax,second.localMax,third.localMax};
		int max = first.localMax;
		for(int i=1; i<3;i++){
			if(max<anotherarray[i]){
				max = anotherarray[i];
			}
		}
		System.out.println("the max value is " + max);
		
		
		
	}
	class findMaxTask implements Runnable {
		int localMax;
		int start,end;
		public findMaxTask(int start,int end){
			this.start = start;
			this.end = end;
			
		}
		
		public void run() {
			localMax = findMax(start, end);
		}
	}

	/**
	 * returns the max value in the array within a give range [begin,range]
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private int findMax(int begin, int end) {
		// you should NOT change this function
		int max = array[begin];
		for (int i = begin + 1; i <= end; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}
}
