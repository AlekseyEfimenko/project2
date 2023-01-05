package com.pm.api.pojo;

import lombok.Data;

@Data
public class NewUser {
    private String formName = "REGISTRATIONBYPHONE";
    private String phone;
    private String email;
    private String password;
    private String defaultCurrency = "XTS";
    private String selectedLanguage = "en";
    private String isPlayerAgree = "true";

    public NewUser(String phone, String email, String password) {
        this.phone = phone;
        this.email = email;
        this.password = password;
    }
}
