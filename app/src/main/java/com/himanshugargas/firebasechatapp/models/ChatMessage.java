package com.himanshugargas.firebasechatapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by himanshugargas on 5/8/16.
 */

public class ChatMessage implements Parcelable{
    private String message;
    private String sender;
    private Boolean self;


    public ChatMessage() {
    }

    public ChatMessage(String message, String sender, boolean chatType) {
        this.message = message;
        this.sender = sender;
        this.self = chatType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Boolean getSelf() {
        return self;
    }

    public void setSelf(Boolean self) {
        this.self = self;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                ", chatType=" + self +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected ChatMessage(Parcel in) {
        message = in.readString();
        sender = in.readString();
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(message);
        parcel.writeString(sender);
    }
}
