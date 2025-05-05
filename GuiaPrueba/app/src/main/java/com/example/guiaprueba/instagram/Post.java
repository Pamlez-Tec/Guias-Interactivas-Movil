package com.example.guiaprueba.instagram;

public class Post {

        private int id;
        private String username;
        private String image;
        private String caption;

        public Post(int id, String username, String image, String caption) {
            this.id = id;
            this.username = username;
            this.image = image;
            this.caption = caption;
        }

        public int getId() { return id; }
        public String getUsername() { return username; }
        public String getImage() { return image; }
        public String getCaption() { return caption; }

        public void setUsername(String username) { this.username = username; }
        public void setImage(String image) { this.image = image; }
        public void setCaption(String caption) { this.caption = caption; }


}
