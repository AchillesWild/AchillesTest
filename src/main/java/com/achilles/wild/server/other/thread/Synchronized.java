package com.achilles.wild.server.other.thread;

public class Synchronized {

	 public synchronized void objectA(){
		 System.out.println("objectA start  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  "+Thread.currentThread().getName());
		 try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 System.out.println("objectA  end   *****************************"+Thread.currentThread().getName());
	 } 
	 
	 public synchronized void objectB(){
		 System.out.println("objectB start  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+Thread.currentThread().getName());
//		 try {
//			Thread.sleep(3000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		 System.out.println("objectB  end   *****************************"+Thread.currentThread().getName());
	 } 
	 
	 public static synchronized void staticC() {
		 System.out.println("staticC start  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+Thread.currentThread().getName());
		 try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 System.out.println("staticC  end   *****************************"+Thread.currentThread().getName());
	 } 
	 
	 public static synchronized void staticD(){
		 System.out.println("staticD start  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+Thread.currentThread().getName());
//		 try {
//			Thread.sleep(3000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		 System.out.println("staticD  end   *****************************"+Thread.currentThread().getName());
	 } 
	
}
