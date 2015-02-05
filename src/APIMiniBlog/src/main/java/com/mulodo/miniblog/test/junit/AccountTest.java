package com.mulodo.miniblog.test.junit;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.util.MD5Hash;

public class AccountTest {
	static final String ROOT_URL = "http://localhost:8080/MiniBlog/account/";
/*
	@Test
	public void testRegister() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL + "register/");
			request.accept("application/json");
			request.formParameter("username", "vykhuong");
			request.formParameter("password", "vykhuong");
			request.formParameter("email", "vykhuong@gmail.com");

			ClientResponse<String> response = request.post(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[TestRegister] Register \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	@Test
	public void testLogin() throws Exception {
		try {

			ClientRequest request = new ClientRequest(ROOT_URL + "login/");
			request.accept("application/json");
			request.formParameter("u", "admin");
			request.formParameter("p", "123456");

			ClientResponse<String> response = request.post(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[TestLogin] Login \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	@Test
	public void testUpdate() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL + "update/{id}");
			request.accept("application/json");
			request.pathParameter("id", 4);
			request.formParameter("username", "vykhuong");
			request.formParameter("email", "vykhuong@gmail.com");
			request.formParameter("lastname", "vy khuong");
			request.formParameter("firstname", "truong");

			ClientResponse<String> response = request.put(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[TestUpdate] Update \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	@Test
	public void testGetInfo() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL + "get/{id}");
			request.accept("application/json");
			request.pathParameter("id", 4);

			ClientResponse<String> response = request.get(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[TestGetInfo] Get Info \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	} 
	
	
	@Test
	public void testChangePass() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL + "changepass/{id}");
			request.accept("application/json");
			request.pathParameter("id", 4);
			request.formParameter("oldpass", "vykhuong");
			request.formParameter("newpass", "password");

			ClientResponse<String> response = request.put(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[TestChangepass] Change Password \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	@Test
	public void testSearch() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL + "search/{name}");
			request.accept("application/json");
			request.pathParameter("name", "hae");

			ClientResponse<String> response = request.get(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[TestSearch] Search \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	*/
	@Test
	public void testLogout() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL + "logout/");
			request.accept("application/json");

			ClientResponse<String> response = request.post(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[TestLogout] Logout \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
}
