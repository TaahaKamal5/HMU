package com.something.ihfaz.hmu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.sql.Timestamp;


public class PresetMessageAdapter extends RecyclerView.Adapter<PresetMessageAdapter.MyViewHolder> {

    final long YEARS49INSECONDS = 1545000000000L;

    public static int itemsC;
    FirebaseAuth user = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Preset messages
    final static String[] msgList = {"Yes", "No", "Time for meet up: ", "Day for meet up: ", "I want this back at: ", "I'm at the location", "I'm on my way",
            "That sounds good", "Okay", "Can we negotiate the price", "Where are you?", "I'm interested in this item", "Is this item still available"
    };
    Context context;
    String itemId;

    PresetMessageAdapter(Context c, String a) {
        this.context = c;
        this.itemId = a;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_template, parent, false);
        return new MyViewHolder(v);
    }

    //A method that takes a MyViewHolder and an int as arguments.
    //  Initially the method checks if the user chose an option to send a point in time.
    //This is done with the first if and the else if. Assuming both of these are false the method then creates a hashmap to store
    //the message's data in. It writes the message value and sender to the hashmap then creates a new document representing the message
    //in the message collection of the item that is being messaged about. Lastly it returns the user to the regular chat screen.
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final int msgType = i;
        holder.presetMsg.setText(msgList[i]);
        if(i == 2 || i == 4) {
            holder.cView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO send i to the database
                    Toast.makeText(v.getContext(), "Show time selection screen", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(i == 3) {
            holder.cView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO send i to the database
                    Toast.makeText(v.getContext(), "Show date selection screen", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            holder.cView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Sending message to db..",  msgType+"");
                    Map<String, Object> message = new HashMap<>();
                    message.put("message", msgType + "");
                    message.put("sender", user.getUid());
                    message.put("timestamp", getMessageTimestamp());
                    db.collection("items").document(itemId).collection("messages").document(itemId + "" + itemsC + "" +user.getUid() + Math.random() * 49 + 1).set(message);
                    Toast.makeText(v.getContext(), "Message selected", Toast.LENGTH_SHORT).show();

                    toChat();
                }
            });
        }
    }

    public int getMessageTimestamp(){
        int secondsSince2019 = 0;
        Timestamp timeSince2019 = new Timestamp(System.currentTimeMillis());

        timeSince2019.setTime(timeSince2019.getTime());
        Log.d("Timestamp Test:","Timestamp initially set to " + timeSince2019.getTime());
        timeSince2019.setTime(timeSince2019.getTime()/1000);  //Converting to seconds

        timeSince2019.setTime(timeSince2019.getTime() - YEARS49INSECONDS);  //Now it is seconds since 2019

        return (int)timeSince2019.getTime();
    }


    @Override
    public int getItemCount() {
        return msgList.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView presetMsg;
        CardView cView;

        public MyViewHolder(View view) {
            super(view);
            presetMsg = view.findViewById(R.id.msgTemplate);
            cView = view.findViewById(R.id.cvMsg);
        }
    }

    public static String getMsg(int i){
        return msgList[i];
    }

    public void toChat(){
        Intent intentA = new Intent(context, Chat.class);
        context.startActivity(intentA);
    }
}

