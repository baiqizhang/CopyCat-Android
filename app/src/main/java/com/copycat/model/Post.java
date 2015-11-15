package com.copycat.model;

/**
 * Created by fanluo on 11/13/15.
 */

public class Post {

    private String postId;
    private int likeNumber;
    private String photoId;
    private String userId;
    private long timeStamp;

    public Post(String postId, int likeNumber, String photoId, String userId, long timeStamp) {
        this.postId = postId;
        this.likeNumber = likeNumber;
        this.photoId = photoId;
        this.userId = userId;
        this.timeStamp = timeStamp;
    }

    //Common Getter and Setter for every attributes
    public String getPostId() {
        return postId;
    }
    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getLikeNumber() {
        return likeNumber;
    }
    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getPhotoId() {
        return photoId;
    }
    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

}
