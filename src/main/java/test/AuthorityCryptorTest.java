package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import vn.anvui.enums.Permission;
import vn.anvui.utility.AuthorityCryptor;

public class AuthorityCryptorTest {
	
	@Test
	public void testEncode() {
		List<String> permission = new ArrayList<String>();
		permission.add(Permission.CREATE_ROLE.name());
		permission.add(Permission.UPDATE_ROLE.name());
		permission.add(Permission.DELETE_ROLE.name());
		permission.add(Permission.GET_ROLE.name());

		String result = AuthorityCryptor.encodeToHex(permission);
		System.out.println("result:" + result);
		
//		assertEquals(, result,"Encode result mustbme same");
	}

}
