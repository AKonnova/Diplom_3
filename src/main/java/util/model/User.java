package util.model;

import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private String email;
    private String password;
    private String name;
    private String accessToken;
    private String refreshToken;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String token) { this.accessToken = token; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String token) { this.refreshToken = token; }

    public static User random() {
        String email = "user" + RandomStringUtils.randomAlphabetic(6) + "@test.com";
        String name = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphanumeric(6);
        return new User(email, password, name);
    }
}