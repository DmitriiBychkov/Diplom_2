package org.example.models;

public class CredentialsWithWrongEmail {
    private String email;
    private String password;

    public CredentialsWithWrongEmail(String email, String password) {
        this.email = "@";
        this.password = password;
    }

    public static CredentialsWithWrongEmail from(User user) {
        return new CredentialsWithWrongEmail(user.getEmail(), user.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}