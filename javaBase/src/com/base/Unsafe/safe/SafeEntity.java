package com.base.Unsafe.safe;

import sun.misc.Unsafe;
import com.base.Unsafe.UnsafeUtil;
/**
 * 利用cas算法 将 SafeEntity 设计成 一个 非阻塞 线性安全的类
 * 并非完全的非阻塞，而是通过原子级操作，避免了加锁和释放锁。
 * unsafe.compareAndSwapInt(this, nextOffset, now, after); 就是一个原子操作。一次只能一个线程通过。
 * cas算法：全称 compare and set ，就是set之前先对比，是否等于预期值，等于预期值就set一个新的值，返回ture，
 * 																否则返回false
 * compareAndSet 中有一个递归调用，保证了每一次 改变 都能够成功。
 * @author liangpro
 *
 */
public class SafeEntity {
	private int num;
	private int sum;
	
	public void numAddOne() throws Exception {
		final Unsafe unsafe = UnsafeUtil.getUnsafeInstance();
		long nextOffset = 0l;
		long nextOffsetSum = 0l;
		try {
			nextOffset = unsafe.objectFieldOffset(SafeEntity.class.getDeclaredField("num"));//获取SafeEntity类的num属性的 内存偏移量。
			nextOffsetSum = unsafe.objectFieldOffset(SafeEntity.class.getDeclaredField("sum"));
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		compareAndSet(unsafe, nextOffset);
		//doSomeThing();
		compareAndSetSum(unsafe, nextOffsetSum);
	}
	
	public boolean compareAndSet(Unsafe unsafe,long nextOffset){
		int now = this.num;
		int after = now +1;
		boolean flag =  unsafe.compareAndSwapInt(this, nextOffset, now, after);//若this的内存偏移 nextOffset处的值为 now，则更新为 after
		if(!flag){
			compareAndSet(unsafe, nextOffset);//如果不成功，则再尝试修改一次。
		}
		return flag;
	}
	public boolean compareAndSetSum(Unsafe unsafe,long nextOffset){//可以为每一个线性不安全的操作，引入原子级修改操作。使整个类达到线程安全。
		int now = this.sum;
		int after = now + 2;
		boolean flag =  unsafe.compareAndSwapInt(this, nextOffset, now, after);
		if(!flag){
			compareAndSetSum(unsafe, nextOffset);
		}
		return flag;
	}

	public int getNum() {
		return num;
	}
	public int getSum() {
		return sum;
	}
}
