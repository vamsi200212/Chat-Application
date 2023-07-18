package com.example.letstalkapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<Message> messages;

    MessageAdapter(Context context, ArrayList<Message> messages){
        this.messages = messages;
        this.context = context;
    }

    int ITEM_RECEIVE = 1;
    int ITEM_SENT = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_layout,parent,false);
            return new MessageAdapter.receiveHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_layout,parent,false);
            return new MessageAdapter.sentHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message currMessage = messages.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(currMessage.getSenderId())){
            return ITEM_SENT;
        }else{
            return ITEM_RECEIVE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message messageModel = messages.get(position);
        if(holder.getClass()==sentHolder.class){
            RecyclerView.ViewHolder viewHolder = (sentHolder) holder;
            ((sentHolder) holder).sentMessage.setText(messageModel.getMessage());
        }else if(holder.getClass()==receiveHolder.class){
            RecyclerView.ViewHolder viewHolder = (receiveHolder) holder;
            ((receiveHolder) holder).receivedMessage.setText(messageModel.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class sentHolder extends RecyclerView.ViewHolder {

        TextView sentMessage;

        public sentHolder(@NonNull View itemView) {
            super(itemView);
            sentMessage = itemView.findViewById(R.id.sent_message);
        }
    }

    public class receiveHolder extends RecyclerView.ViewHolder {

        TextView receivedMessage;

        public receiveHolder(@NonNull View itemView) {
            super(itemView);
            receivedMessage = itemView.findViewById(R.id.receive_message);

        }
    }
}
