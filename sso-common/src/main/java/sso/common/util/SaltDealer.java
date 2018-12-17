package sso.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Random;

public class SaltDealer {
	
	private static final String[] UPPER_CASE = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "=", "-", "%", "~", "#", "$", "^", "*"};
	private static final String[] LOWER_CASE = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "=", "-", "%", "~", "#", "$", "^", "*"};
	private static final int[] NUMBERS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
	private static final int RAN_CHARACTER_LENGTH = UPPER_CASE.length;
	private static final int RAN_NUMBER_LENGTH = NUMBERS.length;
	private static final int[] INDEXS = {2, 3, 7};
	
	private static final Random RAN_SELECT = new Random();
	
	public static final String getSaltedString(String string) {
		String f = UPPER_CASE[RAN_SELECT.nextInt(RAN_CHARACTER_LENGTH)] + LOWER_CASE[RAN_SELECT.nextInt(RAN_CHARACTER_LENGTH)] + NUMBERS[RAN_SELECT.nextInt(RAN_NUMBER_LENGTH)];
		String s = UPPER_CASE[RAN_SELECT.nextInt(RAN_CHARACTER_LENGTH)] + LOWER_CASE[RAN_SELECT.nextInt(RAN_CHARACTER_LENGTH)] + NUMBERS[RAN_SELECT.nextInt(RAN_NUMBER_LENGTH)];
		String t = UPPER_CASE[RAN_SELECT.nextInt(RAN_CHARACTER_LENGTH)] + LOWER_CASE[RAN_SELECT.nextInt(RAN_CHARACTER_LENGTH)] + NUMBERS[RAN_SELECT.nextInt(RAN_NUMBER_LENGTH)];
		String fs = string.substring(0, INDEXS[0]);
		String ss = string.substring(INDEXS[0], INDEXS[1]);
		String ts = string.substring(INDEXS[1], INDEXS[2]);
		String o = string.substring(INDEXS[2]);
		return fs + f + ss + s + ts + t + o;
	}
	
	public static final String getOriginalString(String saltedString) {
		StringBuilder builder = new StringBuilder();
		builder.append(saltedString.substring(0, 2))
		.append(saltedString.substring(5, 6))
		.append(saltedString.substring(9, 13))
		.append(saltedString.substring(16));
		return builder.toString();
	}
	
	public static final String base64encrypt(String string) {
		return Base64.getEncoder().encodeToString(string.getBytes());
	}
	
	public static final String base64decrypt(String encrypted) throws UnsupportedEncodingException {
		return new String(Base64.getDecoder().decode(encrypted), "UTF-8");
	}
	
}
