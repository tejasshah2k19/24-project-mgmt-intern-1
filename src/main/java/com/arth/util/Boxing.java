package com.arth.util;

public class Boxing {

	public static void main(String[] args) {

		String data = "0123456789";
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= 6; i++) {
			int k = (int) (Math.random() * 100 % data.length());
			System.out.println(k);
			sb.append(data.charAt(k));
		}
//12%10 -> 2 
		//22%10 -> 2 
		//85%10 -> 5 
		
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
		System.out.println("OTP => " + sb);

	}
}
