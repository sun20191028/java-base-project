package com.base.classT.baseClass.randomT.t1;

import java.util.concurrent.ThreadLocalRandom;

public class RandomT {
	public static void main(String[] args) {
		ThreadLocalRandom localRandom = ThreadLocalRandom.current();
		
		for (int i = 0; i < 10; i++) {
			System.out.println(localRandom.nextInt());
		}
		
	}
}
