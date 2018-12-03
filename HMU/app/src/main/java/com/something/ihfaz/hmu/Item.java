package com.something.ihfaz.hmu;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Item implements Parcelable {   // Parcelable so can be passed through intents
    String name, description, sellerNETID, condition, location, picture;
    boolean status;    // sell or buy
    double price;
    // int picture; //TODO COME BACK TO THIS IT NEEDS TO BE STRING TO WORK WITH DB
    // ID
    ArrayList<String> keywords;
    // IF ADDING MORE MEMBERS NEED TO UPDATE PARCELABLE METHODS

    public Item(String name, String description, String picture, String sellerNETID, String condition, String location, boolean status, double price) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.sellerNETID = sellerNETID;
        this.condition = condition;
        this.location = location;
        this.status = status;
        this.price = price;
    }

    public String toString() {
        return name + "\t" + condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }
                                                                            //TODO to work with db needs to change picture tos ring latr
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSellerNETID() {
        return sellerNETID;
    }

    public void setSellerNETID(String sellerNETID) {
        this.sellerNETID = sellerNETID;
    }

    public String getCondition() { return condition; }

    public void setCondition(String condition) { this.condition = condition; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<String> getKeywords() { return keywords; }

    public void setKeywords(ArrayList<String> keywords) { this.keywords = keywords; }

    protected Item(Parcel in) {
        name = in.readString();
        description = in.readString();
        sellerNETID = in.readString();
        status = in.readByte() != 0x00;
        price = in.readDouble();
        picture = in.readString();
        condition = in.readString();
        location = in.readString();
        if (in.readByte() == 0x01) {
            keywords = new ArrayList<String>();
            in.readList(keywords, String.class.getClassLoader());
        } else {
            keywords = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(sellerNETID);
        dest.writeByte((byte) (status ? 0x01 : 0x00));
        dest.writeDouble(price);
        dest.writeString(picture);
        dest.writeString(condition);
        dest.writeString(location);
        if (keywords == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(keywords);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}

//package com.something.ihfaz.hmu;
//
//
//import android.os.Parcel;
//import android.os.Parcelable;
//import android.support.annotation.NonNull;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//
//
//public class Item implements Parcelable, Comparable {   // Parcelable so can be passed through intents
//    String name, description, sellerNETID, condition, location, lastMessage;
//    boolean status;    // sell or buy
//    double price;
//    int picture;    // ID
//    ArrayList<String> keywords;
//    Timestamp timePosted;
//    // IF ADDING MORE MEMBERS NEED TO UPDATE PARCELABLE METHODS
//
//    // ADDED LAST MESSAGE add to parceable
//
//    public Item(String name, String description, int picture, String sellerNETID, String condition, String location, boolean status, double price, Timestamp timePosted) {
//        this.name = name;
//        this.description = description;
//        this.picture = picture;
//        this.sellerNETID = sellerNETID;
//        this.condition = condition;
//        this.location = location;
//        this.status = status;
//        this.price = price;
//        this.timePosted = timePosted;
//        this.lastMessage = "";   // empty by default
//    }
//
//    @Override
//    public int compareTo(Object obj) {
//        Item other = (Item)obj;
//        return name.compareTo(other.getName());
//    }
//
//    public String toString() {
//        return name + "\t" + condition;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public int getPicture() {
//        return picture;
//    }
//
//    public void setPicture(int picture) {
//        this.picture = picture;
//    }
//
//    public String getSellerNETID() {
//        return sellerNETID;
//    }
//
//    public void setSellerNETID(String sellerNETID) {
//        this.sellerNETID = sellerNETID;
//    }
//
//    public String getCondition() { return condition; }
//
//    public void setCondition(String condition) { this.condition = condition; }
//
//    public String getLocation() { return location; }
//
//    public void setLocation(String location) { this.location = location; }
//
//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public ArrayList<String> getKeywords() { return keywords; }
//
//    public void setKeywords(ArrayList<String> keywords) { this.keywords = keywords; }
//
//    public Timestamp getTimePosted() { return timePosted; }
//
//    public String printTimePosted() {
//        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm");
//        Date resultdate = new Date(timePosted.getTime());
//        return sdf.format(resultdate);
//    }
//
//    public void setTimePosted(Timestamp timePosted) { this.timePosted = timePosted; }
//
//    protected Item(Parcel in) {
//
//        name = in.readString();
//
//        description = in.readString();
//
//        sellerNETID = in.readString();
//
//        status = in.readByte() != 0x00;
//
//        price = in.readDouble();
//
//        picture = in.readInt();
//
//        condition = in.readString();
//
//        location = in.readString();
//
//        if (in.readByte() == 0x01) {
//
//            keywords = new ArrayList<String>();
//
//            in.readList(keywords, String.class.getClassLoader());
//
//        } else {
//
//            keywords = null;
//
//        }
//
//        timePosted = new Timestamp(in.readLong());
//
//        lastMessage = in.readString();
//
//    }
//
//
//
//    @Override
//
//    public int describeContents() {
//
//        return 0;
//
//    }
//
//
//
//    @Override
//
//    public void writeToParcel(Parcel dest, int flags) {
//
//        dest.writeString(name);
//
//        dest.writeString(description);
//
//        dest.writeString(sellerNETID);
//
//        dest.writeByte((byte) (status ? 0x01 : 0x00));
//
//        dest.writeDouble(price);
//
//        dest.writeInt(picture);
//
//        dest.writeString(condition);
//
//        dest.writeString(location);
//
//        if (keywords == null) {
//
//            dest.writeByte((byte) (0x00));
//
//        } else {
//
//            dest.writeByte((byte) (0x01));
//
//            dest.writeList(keywords);
//
//        }
//
//        dest.writeLong(timePosted.getTime());
//
//        dest.writeString(lastMessage);
//
//    }
//
//
//
//    @SuppressWarnings("unused")
//
//    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
//
//        @Override
//
//        public Item createFromParcel(Parcel in) {
//
//            return new Item(in);
//
//        }
//
//
//
//        @Override
//
//        public Item[] newArray(int size) {
//
//            return new Item[size];
//
//        }
//
//    };
//
//
//
//}

