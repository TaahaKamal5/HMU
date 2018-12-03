package com.something.ihfaz.hmu;

public class ChatMessage {
    boolean vendor;
    String message;

    public ChatMessage(boolean vend, String msg){
        vendor = vend;
        message = msg;
    }

    public boolean getVendor(){
        return vendor;
    }

    public String getMessage(){
        return message;
    }
}
