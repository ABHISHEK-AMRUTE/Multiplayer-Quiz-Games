package com.amrute_studio.game_multiplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class playerNameAdapter extends RecyclerView.Adapter<playerNameAdapter.ViewHolder> {

    private ArrayList<playerData> list;
    private playerNameAdapter.OnitemClickListner mlistner;

    public playerNameAdapter(ArrayList<playerData> list) {
        this.list = list;
    }

    public interface OnitemClickListner {
        void onItemClick(int position);
    }

    public void setonitemclickedlistner(playerNameAdapter.OnitemClickListner listner)
    {
        mlistner=listner;
    }


    @NonNull
    @Override
    public playerNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_data_view,parent,false);
        playerNameAdapter.ViewHolder evh = new playerNameAdapter.ViewHolder(v,mlistner);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull playerNameAdapter.ViewHolder holder, int position) {
          playerData obj = list.get(position);
          holder.name.setText(obj.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ViewHolder(@NonNull View itemView, final OnitemClickListner mlistner) {
            super(itemView);
            name = itemView.findViewById(R.id.name);

        }
    }


}
