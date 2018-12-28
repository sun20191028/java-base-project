package com.base.Unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class CollectionApp {
    private static sun.misc.Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
        	
        }
    }


    public static void main(String[] args) {
        try {
            User instance = (User) unsafe.allocateInstance(User.class);

            instance.setName("google");
            System.err.println("instance:" + instance);
            instance.test();

            Field name = instance.getClass().getDeclaredField("name");
            unsafe.putObject(instance, unsafe.objectFieldOffset(name), "Facebook");
            System.err.println("instance:" + instance);
            instance.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class User {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void test() {
        System.err.println("hello,world" + name);
    }
}
