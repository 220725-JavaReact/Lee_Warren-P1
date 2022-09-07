package com.revature.app.utils;

import java.util.concurrent.Future;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.reactor.IOReactorStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Make requests to the REST API.
 * @author Warren Lee
 */
public class RESTFactory {
	private static RESTFactory restFactory = new RESTFactory();
	private static Logger logger = LogManager.getLogger(RESTFactory.class);
	private static String restHost = PropertiesFactory.getInstance().loadProperties().getProperty("rest_host");
	/**
	 * Returns the RestFactory instance.
	 * @return the RestFactory instance
	 */
	public static RESTFactory getInstance() {
		logger.info("Getting RestFactory instance...");
		return restFactory;
	}
	/**
	 * Performs a GET request to the specified REST API endpoint and returns the response body as a string.
	 * @param uri the REST API endpoint
	 * @return the response body as a string
	 */
	public String sendGet(String uri) {
		SimpleHttpRequest getRequest = SimpleRequestBuilder.get(restHost + uri).build();
		SimpleHttpResponse getResponse = null;
		try (CloseableHttpAsyncClient httpClient = HttpAsyncClientBuilder.create().build()) {
			httpClient.start();
			Future<SimpleHttpResponse> future = httpClient.execute(getRequest, new FutureCallback<SimpleHttpResponse>() {
				@Override
				public void completed(SimpleHttpResponse result) {
					logger.info("GET request successful.");
				}
				@Override
				public void failed(Exception ex) {
					logger.warn("GET request failed.", ex);
				}
				@Override
				public void cancelled() {
					logger.warn("GET request cancelled.");
				}
			});
			getResponse = future.get();
		} catch (Exception e) {
			logger.warn("Could not retrieve GET request results.", e);
		}
		return getResponse == null ? "GET request failed." : getResponse.getBodyText();
	}
	/**
	 * Performs a POST request to the specified REST API endpoint and returns the response body as a string.
	 * @param uri the REST API endpoint
	 * @param body the request body
	 * @return the response body as a string
	 */
	public String sendPost(String uri, String body) {
		SimpleHttpRequest postRequest = SimpleRequestBuilder.post(restHost + uri).build();
		postRequest.setBody(body, ContentType.APPLICATION_JSON);
		SimpleHttpResponse postResponse = null;
		try (CloseableHttpAsyncClient httpClient = HttpAsyncClientBuilder.create().build()) {
			httpClient.start();
			Future<SimpleHttpResponse> future = httpClient.execute(postRequest, new FutureCallback<SimpleHttpResponse>() {
				@Override
				public void completed(SimpleHttpResponse result) {
					logger.info("POST request successful.");
				}
				@Override
				public void failed(Exception ex) {
					logger.warn("POST request failed.", ex);
				}
				@Override
				public void cancelled() {
					logger.warn("POST request cancelled.");
				}
			});
			postResponse = future.get();
		} catch (Exception e) {
			logger.warn("Could not retrieve POST request results.", e);
		}
		return postResponse == null ? "POST request failed." : postResponse.getBodyText();
	}
}
