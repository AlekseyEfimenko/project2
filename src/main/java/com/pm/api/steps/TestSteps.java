package com.pm.api.steps;

import static com.pm.api.StatusCode.FORBIDDEN;
import static com.pm.utils.ApiManager.getInstance;
import static org.assertj.core.api.Assertions.assertThat;
import static com.pm.api.StatusCode.SUCCESS;
import static com.pm.api.StatusCode.BAD_REQUEST;
import static com.pm.temp.Context.TOKEN;
import static com.pm.temp.ScenarioContext.setContext;

import com.google.gson.JsonObject;
import com.pm.api.Keys;
import com.pm.api.pojo.NewUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestSteps {
    private static final Logger LOG = LogManager.getRootLogger();
    private static final String SUCCESS_MESSAGE = String.format("%1$s  SUCCESS  %1$s%n%n", "=".repeat(50));

    public void registerNewUser(NewUser user, String target) {
        LOG.info("Registering new user");
        getInstance().postRequest(target, user);
    }

    @SuppressWarnings("all")
    public void assertSuccessStatusCode(int statusCode) {
        LOG.info("Checking if status code of the request is equals to {}", SUCCESS.getValue());

        assertThat(getInstance().getStatusCode() == statusCode)
                .as(String.format("Expected status code of request is: %1$s, but was found: %2$s",
                        SUCCESS.getValue(),
                        getInstance().getStatusCode()))
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void assertBodyIsNotEmpty(String empty) {
        LOG.info("Checking if body response is not empty");

        assertThat(getInstance().getBody().equals(empty))
                .as(String.format("The body is empty: %1$s", getInstance().getBody()))
                .isFalse();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void assertTokenIsGenerated(String key) {
        LOG.info("Checking if user token is generated and field \"token\" is not empty");
        String token = getInstance().getValue(key);
        setContext(TOKEN, token);

        assertThat(token)
                .as(String.format("The field %1$s is not found in the response or it has an empty value: %2$s",
                        key, token))
                .isNotEmpty();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void changePassword(String oldPassword, String newPassword, String target, String token) {
        LOG.info("Changing current password");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.OLD_PASSWORD.getValue(), oldPassword);
        jsonObject.addProperty(Keys.NEW_PASSWORD.getValue(), newPassword);
        String json = jsonObject.toString();

        getInstance().postRequest(target, json, token);
    }

    public void assertBadRequestStatusCode(int statusCode) {
        LOG.info("Checking if status code of the request is equals to {}", BAD_REQUEST.getValue());

        assertThat(getInstance().getStatusCode() == statusCode)
                .as(String.format("Expected status code of request is: %1$s, but was found: %2$s",
                        BAD_REQUEST.getValue(),
                        getInstance().getStatusCode()))
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void assertErrorMessage(String message) {
        LOG.info("Checking if response body contains description message: \"{}\"", message);

        assertThat(getInstance().getBody())
                .as(String.format("Response body dosn't contain description message: \"%1$s\"", message))
                .contains(message);
        LOG.info(SUCCESS_MESSAGE);
    }

    public void login(String userLogin, String userPassword, String target){
        LOG.info("Login user");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.LOGIN.getValue(), userLogin);
        jsonObject.addProperty(Keys.PASSWORD.getValue(), userPassword);
        String body = jsonObject.toString();
        getInstance().postRequest(target, body);
    }

    public void assertForbiddenStatusCode(int statusCode) {
        LOG.info("Checking if status code of the request is equals to {}", FORBIDDEN.getValue());

        assertThat(getInstance().getStatusCode() == statusCode)
            .as(String.format("Expected status code of request is: %1$s, but was found: %2$s",
                FORBIDDEN.getValue(),
                getInstance().getStatusCode()))
            .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }
}
