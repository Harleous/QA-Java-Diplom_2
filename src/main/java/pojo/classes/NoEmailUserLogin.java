package pojo.classes;

public class NoEmailUserLogin {
    private String email;
    private String password;

    public NoEmailUserLogin
            (String email, String password) {
        this.email = email;
        this.password = password;
    }
    public static String getEmail() {
        return null;
    }
    public static NoEmailUserLogin fromNoEmailUserData(CreateUser createUser) {
        return new NoEmailUserLogin(createUser.getEmail(), createUser.getPassword());
    }
    public void setLogin(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
