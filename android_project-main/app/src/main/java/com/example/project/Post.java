package com.example.project;

public class Post {
    private String userName;
    private String postContent;
    private long timestamp;

    // Firestore 사용을 위한 기본 생성자
    public Post() {}

    // 매개변수를 받는 생성자
    public Post(String userName, String postContent, long timestamp) {
        this.userName = userName;
        this.postContent = postContent;
        this.timestamp = timestamp;
    }

    // Getter와 Setter
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
