package pojoClasses;

public class LogOutUser {
        private String email;
        private String password;

        public LogOutUser(String email, String password) {
            this.email = email;
            this.password = password;
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




        public static LogOutUser fromLoginUserData (LoginUser loginUser){

            return new LogOutUser(loginUser.getEmail(), loginUser.getPassword());
        }
    }

