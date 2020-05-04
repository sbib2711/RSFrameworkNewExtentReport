package com.test.automation.selenium.businesscomponents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

public class CredentialManager {

	private final LinkedList<String> loginList = new LinkedList();
    private static CredentialManager manager;
    public static CredentialManager getInstance() {
    	return manager;
    }
    static {
    	manager = new CredentialManager();
    }
	public void initialise() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("credentials.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] users = prop.getProperty("userList").split(":");
		String[] passwords = prop.getProperty("passwordList").split(":");
		for (int i = 0; i < users.length; i++) {

			loginList.add(users[i] + ":" + passwords[i]);

		}

	}

public  String getCredentials() {
	String credentials = loginList.getFirst();
	loginList.removeFirst();
	return credentials;
}

public void releaseCredentials(String credentials) {
	loginList.add(credentials);
}

}
