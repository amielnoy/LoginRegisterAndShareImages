package Model;

public class User{
        private int id;

        private String Role;

        private String userName;
        private String password;
        private String email ;

        public User(int id, String userName, String password, String email,String Role) {
            this.id = id;
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.Role=Role;
        }

        public User() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole(){return Role;}
        public void setRole(String Role){this.Role=Role;}

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", userName='" + userName + '\'' +
                    ", password='" + password + '\'' +
                    ", email='" + email + '\'' +
                    ",Role='" +Role+ '\''+
                    '}';
        }
}


