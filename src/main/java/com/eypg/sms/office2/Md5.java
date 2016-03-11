package com.eypg.sms.office2;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Md5 {

	private static MessageDigest md = null;

	public static String md5Encrypt(String input) {
		try {
			if (md == null) {
				md = MessageDigest.getInstance("MD5");
			}
			byte buffer[] = input.getBytes();
			md.update(buffer);
			byte bDigest[] = md.digest();
			md.reset();
			BigInteger bi = new BigInteger(1, bDigest);
			return bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
