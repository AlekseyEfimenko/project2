package com.pm.utils;

import com.pm.api.StatusCode;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ApiManager {
    private static final String BASE_PATH = FileManager.getData().basePath();
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String AUTHORISATION = "Authorization";
    private static ApiManager instance;
    private final RequestSpecification specification = new RequestSpecBuilder()
            .setBaseUri(BASE_PATH)
            .build();
    private Response response;

    private ApiManager() {
    }

    /**
     * Create an instance to get access to class methods
     *
     * @return Class instance
     */
    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
            RestAssured.filters(new MyRequestFilter());
        }
        return instance;
    }

    /**
     * Send GET request to API
     *
     * @param target URL of request
     */
    public void getRequest(String target) {
        response = RestAssured
                .given()
                .spec(specification)
                .get(target);
    }

    /**
     * Send data to the API server to create new resource
     *
     * @param target URL to post request
     * @param obj    T object to be sent
     */
    public <T> void postRequest(String target, T obj) {
        response = RestAssured.given()
                .header(CONTENT_TYPE, ContentType.JSON)
                .body(obj)
                .post(BASE_PATH + target);
    }

    /**
     * Send data to the API server to create new resource
     *
     * @param target URL to post request
     * @param body    String to put in the request body
     */
    public <T> void postRequestMobile(String target, T body) {
        response = RestAssured.given()
                .header(CONTENT_TYPE, ContentType.JSON)
                .body(body)
                .post(target);
    }

    /**
     * Send data to the API server to create new resource
     *
     * @param target URL to post request
     * @param obj    T object to be sent
     * @param header The value of header in the request
     * @param <T>    The type of object
     */
    public <T> void postRequest(String target, T obj, String header) {
        response = RestAssured.given()
                .header(CONTENT_TYPE, ContentType.JSON)
                .header(AUTHORISATION, header)
                .body(obj)
                .post(BASE_PATH + target);
    }

    /**
     * Get the status code of request:
     * 1xx - information;
     * 2xx - success;
     * 3xx - redirect;
     * 4xx - client error;
     * 5xx - server error
     *
     * @return Status code
     */
    public int getStatusCode() {
        return response.statusCode();
    }

    /**
     * Get the Content type of the request
     *
     * @return String representation of Content type
     */
    public String getContentType() {
        return response.contentType().split(";")[0];
    }

    /**
     * Getting value from the request by the key
     *
     * @param key The key to get value of
     * @param <T> The type of the key and return value
     * @return The value by the key
     */
    public <T> T getValue(T key) {
        return response.jsonPath().get(key.toString());
    }

    /**
     * Get the request body from the API server
     *
     * @return The String, that represents the request body
     */
    public String getBody() {
        return response.getBody().asString();
    }

    /**
     * Get the list of some value from API request
     *
     * @param target The key to get list of values
     * @param <T>    The type of value
     * @return List of values by key "target"
     */
    public <T extends Comparable<T>> List<T> getList(String target) {
        return response.jsonPath().getList(target);
    }

    /**
     * Converting request from API server to list of T objects
     *
     * @return List of T objects
     */
    public <T> List<T> getListOfObjects(Class<T> cl, String target) {
        return response.jsonPath().getList(target, cl);
    }

    /**
     * Filter request command and add logging
     */
    static class MyRequestFilter implements Filter {
        private static final Logger LOG = LogManager.getRootLogger();

        @Override
        public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
            Response response = ctx.next(requestSpec, responseSpec);
            if (requestSpec.getMethod().equals("GET")) {
                LOG.info("Getting request from {}", requestSpec.getURI());
            } else if (requestSpec.getMethod().equals("POST")) {
                LOG.info("Post request to {}", requestSpec.getURI());
            }
            LOG.info("Status code of request is: {}", response.statusCode());
            if (response.statusCode() >= StatusCode.BAD_REQUEST.getValue()) {
                LOG.error("{} => {}%n{}", requestSpec.getURI(), response.getStatusLine(), requestSpec.getBody());
            }
            return response;
        }
    }
}
