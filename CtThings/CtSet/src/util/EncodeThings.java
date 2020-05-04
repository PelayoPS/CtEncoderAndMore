package util;

import java.util.ArrayList;
import java.util.List;

public class EncodeThings {

	public static String encodeRegisters(char charAt, char charAt2) {
		int left = Integer.valueOf(charAt);
		int left1 = Integer.valueOf(charAt2);

		if (left < 48 || left > 55) {
			throw new IllegalArgumentException("Invalid register: R" + left);
		}
		if (left1 < 48 || left1 > 55) {
			throw new IllegalArgumentException("Invalid register: R" + charAt2);
		}
		String aux = Integer.toBinaryString(left & 0xFF);
		String aux1 = Integer.toBinaryString(left1 & 0xFF);
		return String.format("%s %s",aux.substring(3),aux1.substring(3));
	}
	
	public static String encodeRegister(char charAt) {
		int left = Integer.valueOf(charAt);
		String aux = Integer.toBinaryString(left & 0xFF);
		return aux.substring(3);
	}
	
	public static String encodeNumber(String number) {
		String aux = Integer.toBinaryString(Integer.valueOf(number) & 0xFF);
		String zero = "";
		int counter = 8 - aux.length();
		while (counter > 0) {
			zero += "0";
			counter--;
		}
		aux = zero + aux;
		return aux;
	}
	
	public static String encodeHex(String internalEncoding) {
		List<String> aux = getParts(internalEncoding);
		String hexStr = new String();
		for (String s : aux) {
			int decimal = Integer.parseInt(s,2);
			hexStr += Integer.toHexString(decimal).toUpperCase();
		}
		return hexStr;
	}
	
	public static List<String> getParts(String code) {
		code = code.replaceAll("\\s","");
		List<String> aux = new ArrayList<String>();
		aux.add(code.substring(0, 4));
		aux.add(code.substring(4,8));
		aux.add(code.substring(8, 12));
		aux.add(code.substring(12, 16));
		return aux;
		
	}
}
