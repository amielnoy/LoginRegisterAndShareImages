package Model;

public class Image {
        private int id;

        private Image last_taken_image;
        private String userName;


        public Image(int id, Image last_taken_image, String userName) {
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

        public Image getLastTakenImage() {
        return last_taken_image;
    }

        public void setLastTakenImage(Image last_taken_image) {

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


