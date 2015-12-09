package com.copycat.model;

/**
 * Created by fanluo on 11/13/15.
 */

public class Post {

    private String postId;
    private String photoURI;
    private User user;
    private int likeCount;
    private String geoTag;
    private long timeStamp;

    public Post() {
        this.postId = "";
        this.photoURI = "";
        this.user = new User();
        this.likeCount = 0;
        this.geoTag = "Mountain View";
        this.timeStamp = 0;
    }

    public Post(String postId, String photoURI, User user, int likeCount, String geoTag, long timeStamp) {
        this.postId = postId;
        this.photoURI = photoURI;
        this.user = user;
        this.likeCount = likeCount;
        this.geoTag = geoTag;
        this.timeStamp = timeStamp;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPhotoURI() {
        return photoURI;
    }

    public void setPhotoURI(String photoURI) {
        this.photoURI = photoURI;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getGeoTag() {
        return geoTag;
    }

    public void setGeoTag(String geoTag) {
        this.geoTag = geoTag;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
