package pojoClasses;

public class LogOutUser {


    private String refreshToken;

public LogOutUser(){}
        public LogOutUser(String refreshToken) {
            this.refreshToken =  refreshToken;

                   }
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    }

