package com.himanshugargas.firebasechatapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.himanshugargas.firebasechatapp.R;
import com.himanshugargas.firebasechatapp.models.ChatMessage;

/**
 * Created by himanshugargas on 5/8/16.
 */

public class ChatAdapter extends FirebaseRecyclerAdapter<ChatMessage,ChatAdapter.CustomViewHolder> {


    public ChatAdapter(Class<ChatMessage> modelClass, int modelLayout, Class<CustomViewHolder> viewHolderClass, DatabaseReference ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

     @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_other_layout,parent,false);
            return new CustomViewHolder(itemView);
        }


    @Override
    protected void populateViewHolder(CustomViewHolder holder, ChatMessage chatMessage, int position) {
        if (chatMessage!=null  && !chatMessage.getSender().equalsIgnoreCase("nexus 6p")){
            chatMessage.setSelf(false);
        }
        if (chatMessage != null) {
            if (!chatMessage.getSelf()) {
                holder.selfLayout.setVisibility(View.GONE);
                holder.otherLayout.setVisibility(View.VISIBLE);
                holder.otherMessageTV.setText(chatMessage.getMessage());
                holder.otherNameTV.setText(chatMessage.getSender());

            }else {
                holder.otherLayout.setVisibility(View.GONE);
                holder.selfLayout.setVisibility(View.VISIBLE);
                holder.selfMessageTV.setText(chatMessage.getMessage());
                holder.selfNameTV.setText(chatMessage.getSender());
            }
        }
    }



   public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView selfNameTV;
        private TextView otherNameTV;
        private TextView otherMessageTV;
        private TextView selfMessageTV;
        private LinearLayout otherLayout;
        private LinearLayout selfLayout;


        public CustomViewHolder(View itemView) {
            super(itemView);
            selfNameTV = (TextView)itemView.findViewById(R.id.selfNameTV);
            selfMessageTV = (TextView)itemView.findViewById(R.id.selfMessageTV);
            otherNameTV = (TextView)itemView.findViewById(R.id.otherNameTV);
            otherMessageTV = (TextView)itemView.findViewById(R.id.otherMessageTV);
            otherLayout = (LinearLayout)itemView.findViewById(R.id.chat_content_other);
            selfLayout = (LinearLayout) itemView.findViewById(R.id.chat_content_self);
        }
    }
}
