package com.example.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.Activities.MessageActivity;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.Model.User;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> users;
    private boolean ischat;
    String lastMessage;

    public UserAdapter(Context mContext, List<User> mUsers, boolean ischat) {
        this.context = mContext;
        this.users = mUsers;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);

        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = users.get(position);
        holder.username.setText(user.getUsername());
        if (user.getImageURL().equals("default")) {
            holder.userimage.setImageResource(R.mipmap.icon);
        } else {
            Glide.with(context).load(user.getImageURL()).into(holder.userimage);
        }
        if (ischat) {
            lastMessage(user.getId(), holder.last_mes);
        } else {
            holder.last_mes.setVisibility(View.GONE);
        }

        if (ischat) {
            if (user.getStatus().equals("online")) {
                holder.image_on.setVisibility(View.VISIBLE);
                holder.image_off.setVisibility(View.GONE);
            } else {
                holder.image_off.setVisibility(View.VISIBLE);
                holder.image_on.setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, last_mes;
        ImageView userimage, image_on, image_off;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userimage = itemView.findViewById(R.id.user_profile_image);
            username = itemView.findViewById(R.id.user_profile_name);
            image_on = itemView.findViewById(R.id.image_on);
            image_off = itemView.findViewById(R.id.image_off);
            last_mes = itemView.findViewById(R.id.last_mes);
        }
    }

    private void lastMessage(final String userid, final TextView last_mes) {
        lastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (firebaseUser != null) {
                        if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                                chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                            lastMessage = chat.getMessage();
                        }
                    }
                }
                switch (lastMessage) {
                    case "default":
                        last_mes.setText("No Message");
                        break;
                    default:
                        last_mes.setText(lastMessage);
                        break;
                }
                lastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
