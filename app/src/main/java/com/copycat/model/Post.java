package com.copycat.model;

import com.copycat.util.remote.UserUtil;

/**
 * Created by fanluo on 11/13/15.
 */
public class Post {
    private int postId;
    private String photoURI;
    private User user;
    private int likeCount;
    private int pinCount;
    private boolean liked;

    private String geoTag;
    private long timeStamp;

    public Post() {
        this.postId = this.hashCode();
        this.photoURI = "";
        this.user = new User("Admin","Admin");
        this.likeCount = 0;
        this.geoTag = "Mountain View";
        this.timeStamp = System.currentTimeMillis();
    }

    public Post(String photoURI, User user, int likeCount, String geoTag) {
        this.postId = this.hashCode();
        this.photoURI = photoURI;
        this.user = user;
        this.likeCount = likeCount;
        this.geoTag = geoTag;
        this.timeStamp = System.currentTimeMillis();
    }

    public boolean isLiked() {
        return liked;
    }

    public void flipLiked() {
        this.liked = !this.liked;
    }


    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
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

    public int incrementLikeCount(){
        return ++this.likeCount;
    }

    public int decreaseLikeCount(){
        return --this.likeCount;
    }

    public int incrementPinCount(){
        return ++this.pinCount;
    }

    public int decreasePinCount(){
        return --this.pinCount;
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

