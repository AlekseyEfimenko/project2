package com.pm.api.test;

import static com.pm.api.StatusCode.SUCCESS;
import static com.pm.api.StatusCode.BAD_REQUEST;
import static com.pm.api.StatusCode.UNAUTHORIZED;
import static com.pm.temp.Context.TOKEN;
import static com.pm.temp.ScenarioContext.getContext;

import com.pm.api.EndPoints;
import com.pm.api.StatusCode;
import com.pm.api.pojo.NewUser;
import com.pm.api.steps.TestSteps;
import com.pm.utils.DataManager;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

public class ApiTest {
    private static final String REGISTER_NEW_USER = EndPoints.REGISTRATION.getValue();
    private static final String CHANGE_PASSWORD = EndPoints.UPDATE_PASSWORD.getValue();
    private static final String USER_PHONE = String.format("+38067%1$s", DataManager.getRandomNumber(7));
    private static final String USER_EMAIL = String.format("%1$s@ukr.net", DataManager.getRandomString(10));
    private static final String USER_PASSWORD = DataManager.generatePassword(10);
    private static final String NEW_PASSWORD = DataManager.generatePassword(10);
    private static final String EMPTY_BODY = "{}";
    private static final String TOKEN_KEY = "token";
    private static final String DESCRIPTION_MESSAGE = "Email and Currency pair is already used.";
    protected TestSteps steps = new TestSteps();
    private static final String LOGIN = EndPoints.LOGIN.getValue();
    private static final String NOT_MATCHING_PASSWORD = DataManager.generatePassword(10);
    private static final String INVALID_TOKEN = DataManager.getRandomString(15);
    private static final String INVALID_PHONE_NUMBER = DataManager.getRandomString(7);
    private static final String INVALID_USER_EMAIL =DataManager.getRandomString(10);

    @Feature("API")
    @Description("Registering new user")
    @Test
    public void registerNewUser() {
        steps.registerNewUser(new NewUser(USER_PHONE, USER_EMAIL, USER_PASSWORD), REGISTER_NEW_USER);
        steps.assertSuccessStatusCode(SUCCESS.getValue());
        steps.assertBodyIsNotEmpty(EMPTY_BODY);
        steps.assertTokenIsGenerated(TOKEN_KEY);
    }

    @Feature("API")
    @Description("Trying to register user, that has already registered")
    @Test(dependsOnMethods = {"registerNewUser"})
    public void registerSameUser() {
        steps.registerNewUser(new NewUser(USER_PHONE, USER_EMAIL, USER_PASSWORD), REGISTER_NEW_USER);
        steps.assertBadRequestStatusCode(BAD_REQUEST.getValue());
        steps.assertErrorMessage(DESCRIPTION_MESSAGE);
    }

    @Feature("API")
    @Description("Registration of a new user with an invalid phone number")
    @Test()
    public void registerNewUserUsingInvalidPhoneNumber(){
        steps.registerNewUser(new NewUser(INVALID_PHONE_NUMBER, USER_EMAIL, USER_PASSWORD), REGISTER_NEW_USER);
        steps.assertBadRequestStatusCode(BAD_REQUEST.getValue());
    }

    @Feature("API")
    @Description("Registration of a new user with an invalid email")
    @Test()
    public void registerNewUserUsingInvalidEmail(){
        steps.registerNewUser(new NewUser(USER_PHONE, INVALID_USER_EMAIL, USER_PASSWORD), REGISTER_NEW_USER);
        steps.assertBadRequestStatusCode(BAD_REQUEST.getValue());
    }
    @Feature("API")
    @Description("Changing current password to new password")
    @Test(dependsOnMethods = {"registerSameUser"})
    public void changePassword() {
        steps.changePassword(USER_PASSWORD, NEW_PASSWORD, CHANGE_PASSWORD, (String) getContext(TOKEN));
        steps.assertSuccessStatusCode(SUCCESS.getValue());
    }
    @Feature("API")
    @Description("Change user password using not matching old password")
    @Test(dependsOnMethods = {"registerNewUser"})
    public void changePasswordUsingNotMatchingOldPassword(){
        steps.changePassword(NOT_MATCHING_PASSWORD, NEW_PASSWORD, CHANGE_PASSWORD, (String) getContext(TOKEN));
        steps.assertBadRequestStatusCode(BAD_REQUEST.getValue());
        steps.assertBodyIsNotEmpty(EMPTY_BODY);
    }

    @Feature("API")
    @Description("Change user password using invalid token")
    @Test(dependsOnMethods = {"registerNewUser"})
    public void changePasswordUsingInvalidToken(){
        steps.changePassword(USER_PASSWORD, NEW_PASSWORD, CHANGE_PASSWORD,INVALID_TOKEN);
        steps.assertUnauthorizedStatusCode(UNAUTHORIZED.getValue());
    }

    @Feature("API")
    @Description("Changing a user's current password using an invalid new password")
    @Test(dependsOnMethods = {"registerNewUser"})
    public void changePasswordUsingInvalidNewPassword(){
        steps.changePassword(USER_PASSWORD, EMPTY_BODY, CHANGE_PASSWORD, (String) getContext(TOKEN));
        steps.assertBadRequestStatusCode(BAD_REQUEST.getValue());
    }

    @Feature("API")
    @Description("Login user using Email")
    @Test(dependsOnMethods = "registerNewUser")
    public void loginUsingEmail(){
        steps.login(USER_EMAIL, USER_PASSWORD, LOGIN);
        steps.assertSuccessForbiddenStatusCode();
        steps.assertBodyIsNotEmpty(EMPTY_BODY);
    }
}
