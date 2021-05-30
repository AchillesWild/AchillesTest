package com.achilles.wild.server.other.thread;

import org.junit.Test;

public class SynchronizedTest {

	@Test
	public void testA() {
		 final Synchronized synch = new Synchronized(); 
		 Thread testA = new Thread( new Runnable() { public void run() { synch.objectA(); } } ); 
		 Thread testB = new Thread( new Runnable() { public void run() { synch.objectB(); } } ); 
		 testA.start(); 
		 testB.start(); 
		 
		 try {
			Thread.sleep(6000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAB() {
		 final Synchronized synch1 = new Synchronized(); 
		 Thread test1 = new Thread( new Runnable() { public void run() { synch1.objectA(); } } ); 
		 final Synchronized synch2 = new Synchronized();
		 Thread test2 = new Thread( new Runnable() { public void run() { synch2.objectB(); } } ); 
		 test1.start(); 
		 test2.start(); 
		 
		 try {
			Thread.sleep(6000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testABC() {
		 final Synchronized synch = new Synchronized(); 
		 Thread testA = new Thread( new Runnable() { public void run() { synch.objectA(); } } ); 
		 Thread testB = new Thread( new Runnable() { public void run() { synch.objectB(); } } ); 
		 Thread testC = new Thread( new Runnable() { public void run() { synch.staticC(); } } );
		
		 testC.start();
		 testA.start(); 
		 testB.start(); 
		
		 try {
			Thread.sleep(6000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCD() {
		 final Synchronized synch = new Synchronized(); 
		 Thread testC = new Thread( new Runnable() { public void run() { synch.staticC(); } } );
		 Thread testD = new Thread( new Runnable() { public void run() { synch.staticD(); } } );
		
		 testC.start();
		 testD.start(); 
		
		 try {
			Thread.sleep(6000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCD2() {
		 final Synchronized synch = new Synchronized(); 
		 Thread testC = new Thread( new Runnable() { public void run() { synch.staticC(); } } );
		 final Synchronized synch2 = new Synchronized();
		 Thread testD = new Thread( new Runnable() { public void run() { synch2.staticD(); } } );
		
		 testC.start(); 
		 testD.start();
		
		 try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
