package com.example.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context context;
    private List<Chat> mchat;
    private String imageurl;
    FirebaseUser firebaseUser;

    public MessageAdapter(Context mContext, List<Chat> chats, String imageurl) {
        this.context = mContext;
        this.mchat = chats;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat = mchat.get(position);
        holder.showMessage.setText(chat.getMessage());
        if (imageurl.equals("default")) {
            holder.userimage.setImageResource(R.mipmap.icon);
        } else {
            Glide.with(context).load(imageurl).into(holder.userimage);
        }

        if (position == mchat.size() - 1) {
            if (chat.isIsseen()){
                holder.txt_seen.setText("Seen");
            }else {
                holder.txt_seen.setText("Delivered");
            }
        }else {
            holder.txt_seen.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return mchat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView showMessage, txt_seen;
        ImageView userimage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userimage = itemView.findViewById(R.id.user_image);
            showMessage = itemView.findViewById(R.id.show_message);
            txt_seen = itemView.findViewById(R.id.txt_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mchat.get(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
