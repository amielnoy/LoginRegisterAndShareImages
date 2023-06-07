package Model;

public class UserImage {
        private int id;

        private UserImage last_taken_image;
        private byte [] userImageByteArray;
        private String userName;


        public UserImage(int id, UserImage last_taken_image, String userName) {
            this.id = id;
            this.last_taken_image=last_taken_image;
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
        public void setLastTakenImage(UserImage last_taken_image) {

            this.last_taken_image = last_taken_image;
        }




        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", userName='" + userName + '\'' +
                    '}';
        }
}


