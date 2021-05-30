package com.achilles.wild.server.other.thread;



public class JoinTest {
	

	public void test() throws Exception{
		Thread testA = new Thread( new Runnable() { 
			public void run() { 
				
				for(int m=0;m<10000;m++) {
					System.out.println("**************AAAA*****************  "+m);;
				}
			  } 
			} 
		); 
		
		testA.start();
		
		testA.join();
		
		System.out.println("------------------------------------------------------------------------------- ");;
		
		Thread testB = new Thread( new Runnable() { 
			public void run() { 
				
				for(int m=0;m<10000;m++) {
					System.out.println("*************BB******************  "+m);;
				}
			  } 
			} 
		); 
		
		testB.start();
		
		testB.join();
		
		System.out.println("over ============================================== ");;
	}

}
