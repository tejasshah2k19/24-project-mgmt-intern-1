package com.arth.util;

public class Boxing {

	public static void main(String[] args) {

		int a = 10;
		int b = 10;
		System.out.println(a == b);

		Integer i1 = 10; // 6968
		Integer i2 = 10; // 6968
		System.out.println(i1 == i2);

		Integer k1 = new Integer(10);// 55555
		Integer k2 = new Integer(10);// 66666
		System.out.println(k1 == k2);
		System.out.println(k1.intValue() == k2.intValue());

		int x = 123; // decimal
		int y = 0123;// octal
		int z = 0x1234; // hexadecimal
		int aa = 0b1111; // binary
		System.out.println(x);
		System.out.println(y);

		System.out.println(Math.random() * 1000000);
		System.out.println(Math.random() * 1000000);
		System.out.println(Math.random() * 1000000);
		System.out.println(Math.random() * 1000000);

	}
}
