package com.something.ihfaz.hmu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PresetMessageAdapter extends RecyclerView.Adapter<PresetMessageAdapter.MyViewHolder> {

    //Preset messages
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    final String[] msgList = {"Yes", "No", "Time for meet up: ", "Day for meet up: ", "I want this back at: ", "I'm at the location", "I'm on my way",
                        "That sounds good", "Okay", "Can we negotiate the price", "Where are you?", "I'm interested in this item", "Is this item still available"
                        };
    Context context;

    PresetMessageAdapter(Context c) {
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_template, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {
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
                    //TODO send i to the database
                    Toast.makeText(v.getContext(), "Message selected", Toast.LENGTH_SHORT).show();
                    Timestamp ts = new Timestamp(0, 0);
                    ts = Timestamp.now();

                    Map<String, Object> message = new HashMap<>();
                    message.put("id", i);
                    message.put("timestamp", ts);
                    message.put("sender", user.getUid());

                    db.collection("new_trades").document("new_trade").collection("messages").document(i+""+user.getUid()).set(message);
                }
            });
        }

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
            presetMsg = (TextView) view.findViewById(R.id.msgTemplate);
            cView = (CardView) view.findViewById(R.id.cvMsg);
        }
    }
}
