package com.hamzaamin.i180550_i170298;


public class Message {
    String senderId;
    String receiverId;
    String text;
    Long timestamp;
    String imgSrc;

    public Message(String senderId, String receiverId, String text,String imgSrc) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.timestamp=System.currentTimeMillis()/1000;
        this.imgSrc=imgSrc;
    }

    public Message(String senderId, String receiverId, String text, String timestamp ,String imgSrc) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        System.out.println("HEYYYYYYYYYYYYYYYYYYYYYYYYYY"+timestamp);
        this.timestamp= Long.parseLong(timestamp, 10);
        this.imgSrc=imgSrc;
    }

    public Message(){

    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }


    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}