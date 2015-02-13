package com.mulodo.miniblog.test.junit;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

public class CommentsTest {
	//root url
	static final String ROOT_URL = "http://localhost:8080/MiniBlog/";
	// Test Add Comments
	@Test
	public void testAddComments() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL
					+ "{id_posts}/add/");
			request.accept("application/json");
			request.pathParameter("id_posts", 1);
			request.header("token", "5O0cYcWjzUN1n98XeO17hVAuYtKpvzFKF4UB2Wxa");
			request.formParameter("comment", "Arsenal win!!!chaizo chaizo....");

			ClientResponse<String> response = request.post(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[Test Add Comments] Add Comment \n");
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
	// Test Edit Comments
	@Test
	public void testEditComments() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL
					+ "{id_posts}/edit/{id}/");
			request.accept("application/json");
			request.pathParameter("id_posts", 1);
			request.pathParameter("id", 2);
			request.header("token", "5O0cYcWjzUN1n98XeO17hVAuYtKpvzFKF4UB2Wxa");
			request.formParameter("comment", "Arsenal losts!!!yeah yeah");

			ClientResponse<String> response = request.put(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[Test Edit Comments] Edit Comments \n");
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
	// Test Delete Comments
	@Test
	public void testDeleteComments() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL
					+ "{id_posts}/delete/{id}/");
			request.accept("application/json");
			request.pathParameter("id_posts", 1);
			request.pathParameter("id", 2);
			request.header("token", "5O0cYcWjzUN1n98XeO17hVAuYtKpvzFKF4UB2Wxa");

			ClientResponse<String> response = request.delete(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("[Test Delete Comments] Delete Comemnts \n");
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
	// Test GetAllCommentsForUser
	@Test
	public void testGetAllCommentsForUser() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL
					+ "getcommentsofuser/");
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
					.println("[Test Get All Comments For User] Get All Comments For User \n");
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
	// Test GetAllCommentsForPosts
	@Test
	public void testGetAllCommentsForPosts() throws Exception {
		try {
			ClientRequest request = new ClientRequest(ROOT_URL
					+ "/{id_posts}/getcommentsofposts/");
			request.accept("application/json");
			request.pathParameter("id_posts", 1);
			ClientResponse<String> response = request.get(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out
					.println("[Test Get All Comments For Posts] Get All Comments For Posts \n");
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
