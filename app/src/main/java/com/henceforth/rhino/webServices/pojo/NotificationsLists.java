package com.henceforth.rhino.webServices.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationsLists {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id_1")
    @Expose
    private Object userId1;
    @SerializedName("user_id_1_name")
    @Expose
    private Object userId1Name;
    @SerializedName("user_id_1_image")
    @Expose
    private Object userId1Image;
    @SerializedName("user_id_2")
    @Expose
    private Integer userId2;
    @SerializedName("notif_type")
    @Expose
    private Integer notifType;
    @SerializedName("notif_msg")
    @Expose
    private String notifMsg;
    @SerializedName("is_read")
    @Expose
    private Integer isRead;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getUserId1() {
        return userId1;
    }

    public void setUserId1(Object userId1) {
        this.userId1 = userId1;
    }

    public Object getUserId1Name() {
        return userId1Name;
    }

    public void setUserId1Name(Object userId1Name) {
        this.userId1Name = userId1Name;
    }

    public Object getUserId1Image() {
        return userId1Image;
    }

    public void setUserId1Image(Object userId1Image) {
        this.userId1Image = userId1Image;
    }

    public Integer getUserId2() {
        return userId2;
    }

    public void setUserId2(Integer userId2) {
        this.userId2 = userId2;
    }

    public Integer getNotifType() {
        return notifType;
    }

    public void setNotifType(Integer notifType) {
        this.notifType = notifType;
    }

    public String getNotifMsg() {
        return notifMsg;
    }

    public void setNotifMsg(String notifMsg) {
        this.notifMsg = notifMsg;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}