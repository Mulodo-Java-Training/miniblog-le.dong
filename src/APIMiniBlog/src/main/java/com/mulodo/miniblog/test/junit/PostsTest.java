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

public class PostsTest {
	
	static final String ROOT_URL = "http://localhost:8080/MiniBlog/posts/";
	// Test Create Posts
	@Test
	public void testCreate() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL + "create/");
			request.accept("application/json");
			request.header("token", "5O0cYcWjzUN1n98XeO17hVAuYtKpvzFKF4UB2Wxa");
			request.formParameter("title", "[Champion League] Round of 16");
			request.formParameter("content", "PSG - Chelsea");

			ClientResponse<String> response = request.post(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[Test Create Posts] Create Posts \n");
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
	// Test Active Posts
	@Test
	public void testActive() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL + "active/{id}");
			request.accept("application/json");
			request.header("token", "5O0cYcWjzUN1n98XeO17hVAuYtKpvzFKF4UB2Wxa");
			request.pathParameter("id", 2);

			ClientResponse<String> response = request.put(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[Test Active Posts] Active Posts \n");
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
	// Test Deactive Posts
	@Test
	public void testDeactive() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL
					+ "deactive/{id}");
			request.accept("application/json");
			request.header("token", "5O0cYcWjzUN1n98XeO17hVAuYtKpvzFKF4UB2Wxa");
			request.pathParameter("id", 2);

			ClientResponse<String> response = request.put(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[Test Deactive Posts] Deactive Posts \n");
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
	// Test Update Posts
	@Test
	public void testUpdate() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL + "update/");
			request.accept("application/json");
			request.header("token", "5O0cYcWjzUN1n98XeO17hVAuYtKpvzFKF4UB2Wxa");
			request.formParameter("title", "[FA Cup]Round of 5");
			request.formParameter("content", "MU 3-0 Cambridge");

			ClientResponse<String> response = request.put(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[Test Update Posts] Update Posts \n");
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
	// Test Delete Posts
	@Test
	public void testDelete() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL + "delete/{id}");
			request.accept("application/json");
			request.header("token", "5O0cYcWjzUN1n98XeO17hVAuYtKpvzFKF4UB2Wxa");
			request.pathParameter("id", 2);

			ClientResponse<String> response = request.delete(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[Test Delete Posts] Delete Posts \n");
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
	// Test Get Posts
	@Test
	public void testGet() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL + "get/{id}");
			request.accept("application/json");
			request.pathParameter("id", 1);

			ClientResponse<String> response = request.get(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[Test Get Posts] Get Posts \n");
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
	// Test Get All Posts Active or Deactive
	@Test
	public void testGetAll() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL
					+ "getallactive/");
			// ClientRequest request = new ClientRequest(ROOT_URL +
			// "getalldeactive/");
			request.accept("application/json");

			ClientResponse<String> response = request.get(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[Test Get All Posts] Get All Posts \n");
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
	// Test GetAllPostsForUser
	@Test
	public void testGetAllPostsForUser() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL
					+ "getpostsofuser/");
			request.accept("application/json");
			request.header("token", "5O0cYcWjzUN1n98XeO17hVAuYtKpvzFKF4UB2Wxa");
			ClientResponse<String> response = request.get(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out
					.println("[Test Get All Posts For User] Get All Posts For User \n");
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
