package com.something.ihfaz.hmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<ChatMessage> chats;
    //private List<String> chats;
    private Context mContext;

    public MessageAdapter(Context mContext, List<ChatMessage> chats) {
        this.chats = chats;
        this.mContext = mContext;
    }

    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg, parent, false);
        return new MessageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, int position) {
        Log.d("Chats", chats.get(position).getMessage());
        if(chats.get(position).getVendor()) //TODO if vendor is true then that is the seller
        {
            holder.sellerMsg.setText(chats.get(position).getMessage());
            holder.buyer.setVisibility(View.GONE);
        }

        else {
            holder.buyerMsg.setText(chats.get(position).getMessage());
            holder.seller.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sellerMsg, buyerMsg;
        CardView seller, buyer;

        public MyViewHolder(View view) {
            super(view);
            sellerMsg = (TextView) view.findViewById(R.id.u2Text);
            buyerMsg = (TextView) view.findViewById(R.id.u1Text);
            seller = (CardView) view.findViewById(R.id.user2cv);
            buyer = (CardView) view.findViewById(R.id.user1cv);

        }
    }

}
