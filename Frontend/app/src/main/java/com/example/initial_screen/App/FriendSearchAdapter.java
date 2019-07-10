package com.example.initial_screen.App;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.initial_screen.Activities.AddFriend;
import com.example.initial_screen.R;

import java.util.List;

public class FriendSearchAdapter extends RecyclerView.Adapter<FriendSearchAdapter.ViewHolder> {
    private List<FriendSearchController> fsList;
    private Context context;
    private String curUser;

    /**
     *
     * @param fsList friendsearch controller list
     * @param context a context of hte app
     * @param curUser the username of the user
     */
    public FriendSearchAdapter(List<FriendSearchController> fsList, Context context, String curUser) {
        this.fsList = fsList;
        this.context = context;
        this.curUser = curUser;
    }

    /**
     *
     * @param viewGroup takes in a viewgroup
     * @param i takes an int i
     * @return a new freindsearchadapter view
     */
    @NonNull
    @Override
    public FriendSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.friend_search_item, viewGroup, false);
        return new FriendSearchAdapter.ViewHolder(v);
    }

    /**
     * bind the view
     * @param viewHolder takes in a friend search adapter view
     * @param i takes in an int i
     */
    @Override
    public void onBindViewHolder(@NonNull FriendSearchAdapter.ViewHolder viewHolder, int i) {
        final FriendSearchController fsItem = fsList.get(i);

        viewHolder.tvRank.setText(fsItem.getRank());
        viewHolder.tvUser.setText(fsItem.getUsername());
        viewHolder.tvScore.setText(fsItem.getHscore());

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, fsItem.getUsername(), Toast.LENGTH_SHORT).show();
                Intent goToAddFriend = new Intent(context, AddFriend.class);
                goToAddFriend.putExtra("username", curUser);
                goToAddFriend.putExtra("friendname", fsItem.getUsername());
                goToAddFriend.putExtra("rank", fsItem.getRank());
                goToAddFriend.putExtra("hscore", fsItem.getHscore());
                context.startActivity(goToAddFriend);
            }
        });

    }

    /**
     *
     * @return gets the item count of the freind search list
     */
    @Override
    public int getItemCount() {
        return fsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvRank;
        public TextView tvUser;
        public TextView tvScore;
        public LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = (LinearLayout) itemView.findViewById(R.id.FSitemLL);
            tvRank = (TextView) itemView.findViewById(R.id.tvFS_rank);
            tvUser = (TextView) itemView.findViewById(R.id.tvFS_username);
            tvScore = (TextView) itemView.findViewById(R.id.tvFS_hscore);

        }
    }
}

