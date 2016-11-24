package com.share.jack.friendcircledemo.main.bean;

import com.share.jack.friendcircledemo.login.model.UserProfile;

import java.util.List;

/**
 *
 */
public class DynamicData {

    private Integer id;
    private UserProfile userProfile;
    private String imageUrl;
    private Long time;
    private String content;
    private List<CommentData> commentDataList;
    private List<PraiseData> praiseDataList;
    private Integer praiseCount;
    private boolean isPriseByCurUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public List<CommentData> getCommentDataList() {
        return commentDataList;
    }

    public void setCommentDataList(List<CommentData> commentDataList) {
        this.commentDataList = commentDataList;
    }

    public List<PraiseData> getPraiseDataList() {
        return praiseDataList;
    }

    public void setPraiseDataList(List<PraiseData> praiseDataList) {
        this.praiseDataList = praiseDataList;
    }

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    public boolean isPriseByCurUser() {
        return isPriseByCurUser;
    }

    public void setIsPriseByCurUser(boolean isPriseByCurUser) {
        this.isPriseByCurUser = isPriseByCurUser;
    }

    public boolean hasPraise(){
        if(praiseDataList!=null && praiseDataList.size()>0){
            return true;
        }
        return false;
    }

    public boolean hasComment(){
        if(commentDataList!=null && commentDataList.size()>0){
            return true;
        }
        return false;
    }
}