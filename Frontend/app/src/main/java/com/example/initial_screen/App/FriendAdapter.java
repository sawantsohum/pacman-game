package com.example.initial_screen.App;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.initial_screen.Activities.MessageFriend;
import com.example.initial_screen.R;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private List<FriendController> fList;
    private Context context;
    private String curUser;

    /**
     * Creates an adapter for friend messages
     * @param fList takes in a list of controllers for Friend
     * @param context takes in a context
     * @param curUser takes in a username
     */
    public FriendAdapter(List<FriendController> fList, Context context, String curUser) {
        this.fList = fList;
        this.context = context;
        this.curUser = curUser;
    }

    /**
     * sets the viewholder for the activity
     * @param viewGroup takes in a viewgroup
     * @param i takes in an int
     * @return a new viewholder for friend adapter
     */
    @NonNull
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from((viewGroup.getContext()))
                .inflate(R.layout.username_friends_list_item, viewGroup, false);
        return new FriendAdapter.ViewHolder(v);
    }

    /**
     *
     * @param viewHolder binds the view
     * @param i takes in an int to get the specific friendcontroller item
     */
    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.ViewHolder viewHolder, int i) {
        final FriendController fItem = fList.get(i);

        viewHolder.tvUsername.setText(fItem.getUsername());

        viewHolder.msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, fItem.getUsername(), Toast.LENGTH_SHORT).show();
                Intent goToMessageFriend = new Intent(context, MessageFriend.class); //switch this to messages for demo 4
                goToMessageFriend.putExtra("usename", curUser);
                goToMessageFriend.putExtra("friendname", fItem.getUsername());
                context.startActivity(goToMessageFriend);
            }
        });
    }

    /**
     *
     * @return returns the item count of the list
     */
    @Override
    public int getItemCount() { return fList.size(); }

    /**
     * Class for viewholder using Recycler View
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvUsername;
        public Button msgBtn;

        /**
         * Going to create a new viewholder
         * @param itemView takes in a view
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = (TextView) itemView.findViewById(R.id.tv_username_msg);
            msgBtn = (Button) itemView.findViewById(R.id.btnMsgFriend);
        }
    }
}
