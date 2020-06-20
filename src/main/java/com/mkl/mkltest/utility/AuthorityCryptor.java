package com.mkl.mkltest.utility;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import com.mkl.mkltest.enums.Permission;

public class AuthorityCryptor {
	public static List<String> decodeFromHex(String tokenAuthority) {
		BigInteger bigInt = new BigInteger(tokenAuthority, 16);
		String tokenBinary = bigInt.toString(2);
		
		char[] cA = tokenBinary.toCharArray();
		List<String> results = new ArrayList<String>();
		
		for (int i = cA.length - 1; i >= 0; i--) {
			if (cA[i] == '1') {
				results.add(Permission.indexOf(cA.length - i - 1).toString());
			}
		}
		
		return results;
	}

	/* First solution */
	public static String encodeToHex(List<String> permissions) {
		BitSet bits = new BitSet(Permission.PermissionDirectory.size());

		for (int i = 0; i < permissions.size(); i++) {
			int index = Permission.valueOf(permissions.get(i)).getIndex();
			bits.set(index);
		}

		BigInteger bi = new BigInteger(bits.toByteArray());
		
		// to bit
		return bi.toString(16);
	}

	public static String encodeToHex(int b) {
		BitSet bits = new BitSet(b);
		bits.set(b);
		BigInteger bi = new BigInteger(bits.toByteArray());
		// to bit
		return bi.toString(16);
	}
	
	
}
