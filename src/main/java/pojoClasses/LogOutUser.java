package pojoClasses;

public class LogOutUser {


    private String token;

public LogOutUser(){}
        public LogOutUser(String token) {
            this.token =  token;

                   }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    }

