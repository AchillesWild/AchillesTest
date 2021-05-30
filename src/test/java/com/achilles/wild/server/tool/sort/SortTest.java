package com.achilles.wild.server.tool.sort;

import java.util.Arrays;

public class SortTest {

	public static void main(String[] args) {

	      int arr[] = new int[]{1,6,4,3,2,2,5};
//	        BubbleSort.bubbleSort(arr);
	  
	        
	        for (int i = 0; i < arr.length-1; i++) {
				for (int j = 0; j < arr.length-1-i; j++) {
					if (arr[j+1]<arr[j]) {
						int temp = arr[j+1];
						arr[j+1] = arr[j];
						arr[j] = temp;
					}
					
				}
			}
	        
	        System.out.println(Arrays.toString(arr));
	}

}
