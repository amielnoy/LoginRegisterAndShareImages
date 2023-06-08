package Model;

public class UserImage {
        private int id;

        private byte [] userImageByteArray;
        private String userName;


        public UserImage(int id,byte [] userImageByteArray,  String userName) {
            this.id = id;
            this.userImageByteArray=userImageByteArray;
            this.userName = userName;

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

        public byte [] getLastTakenUserImage() {
        return userImageByteArray;
    }

        public void setUserImage(byte[] imageBytesArray) {
            userImageByteArray= imageBytesArray;
    }





        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", userName='" + userName + '\'' +
                    '}';
        }
}


