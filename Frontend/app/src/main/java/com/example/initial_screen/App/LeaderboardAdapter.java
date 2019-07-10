package com.example.initial_screen.App;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.initial_screen.R;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private List<LeaderboardController> lbList;
    private Context context;

    /**
     *
     * @param lbList takes in a list of leaderboard controllers
     * @param context takes in a context
     */
    public LeaderboardAdapter(List<LeaderboardController> lbList, Context context) {
        this.lbList = lbList;
        this.context = context;
    }

    /**
     *
     * @param viewGroup takes in a viewgroup
     * @param i takes in an int
     * @return a viewholder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    /**
     *
     * @param viewHolder takes in a viewholder for the view
     * @param i takes in an int i
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        LeaderboardController lbItem = lbList.get(i);

        viewHolder.tvRank.setText(lbItem.getRank());
        viewHolder.tvUser.setText(lbItem.getUsername());
        viewHolder.tvScore.setText(lbItem.gethScore());

    }

    /**
     *
     * @return the list size
     */
    @Override
    public int getItemCount() {
        return lbList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvRank;
        public TextView tvUser;
        public TextView tvScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRank = (TextView) itemView.findViewById(R.id.tvLB_rank);
            tvUser = (TextView) itemView.findViewById(R.id.tvLB_username);
            tvScore = (TextView) itemView.findViewById(R.id.tvLB_score);

        }
    }
}
