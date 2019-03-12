package com.base.basic.base64;


public class Base64Encode {
	public static void main(String[] args) {
		String base64String = "whuang123";
		/*
		 * commons-net包下的不是公认的base64 要用commons-codec下的base64
		 */
		// byte[] result = Base64.encodeBase64(base64String.getBytes());

		byte[] result = org.apache.commons.codec.binary.Base64.encodeBase64(base64String.getBytes());
		System.out.println(result);
		result = org.apache.commons.codec.binary.Base64.decodeBase64(result);
		System.out.println(new String(result));

	}
}
