package com.something.ihfaz.hmu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ActiveItemAdapter extends RecyclerView.Adapter<ActiveItemAdapter.MyViewHolder> {

    //Sample data for last message sent
    final String[] msg = {"Where do you want to meet?", "What condition is it in?", "I'm here", "I'm here", "I'm here", "I'm here"};

    List<Item> itemsList;
    Context cont;

    public ActiveItemAdapter(Context cont, List<Item> itemsList) {
        this.cont = cont;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_sales_items, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final Item item = itemsList.get(i);
        holder.itemName.setText(item.getName());
        holder.chat.setText(msg[i]); // Add last message field in the item class
        //holder.picture.setImageResource(item.getPicture());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                toChat(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView chat;
        ImageView picture;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.heading);
            chat = (TextView) view.findViewById(R.id.message);
            picture = (ImageView) view.findViewById(R.id.itemImage);
            cardView = (CardView) view.findViewById(R.id.button_cv);
        }
    }

    public void toChat(final Item item){
        Intent intent = new Intent(cont, Chat.class);
        intent.putExtra("itemObj", item);
        cont.startActivity(intent);
    }
}
