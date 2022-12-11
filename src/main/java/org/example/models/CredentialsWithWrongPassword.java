package org.example.models;

public class CredentialsWithWrongPassword {
    private String email;
    private String password;

    public CredentialsWithWrongPassword(String email, String password) {
        this.email = email;
        this.password = "-";
    }

    public static CredentialsWithWrongPassword from(User user) {
        return new CredentialsWithWrongPassword(user.getEmail(), user.getPassword());
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