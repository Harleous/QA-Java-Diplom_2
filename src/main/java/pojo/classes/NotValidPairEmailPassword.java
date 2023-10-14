package pojo.classes;

import org.apache.commons.lang3.RandomStringUtils;

public class NotValidPairEmailPassword {

    private String email;
    private String password;

    public NotValidPairEmailPassword
            (String email, String password) {
        this.email = email;
        this.password = password;
    }
    public static NotValidPairEmailPassword fromCreateUserData(CreateUser createUser) {
        return new NotValidPairEmailPassword(createUser.getEmail(), createUser.getPassword());
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password = RandomStringUtils.randomAlphabetic(6);
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
