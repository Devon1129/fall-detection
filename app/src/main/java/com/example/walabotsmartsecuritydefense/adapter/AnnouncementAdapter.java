package com.example.walabotsmartsecuritydefense.adapter;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walabotsmartsecuritydefense.R;
import com.example.walabotsmartsecuritydefense.table.Announcement;

import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

    private List<Announcement> mAnnouncementList;

    public List<Announcement> getAnnouncementList() {
        return mAnnouncementList;
    }

    public void setAnnouncementListList(List<Announcement> announcementList) {
        this.mAnnouncementList = announcementList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AnnouncementAdapter.ViewHolder holder, final int position) {
        Announcement announcement = mAnnouncementList.get(position);
        holder.createDate.setText(announcement.getCreateDate());
        holder.Content.setText(announcement.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AnnouncementAdapter", "Announcement onClick");
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                Log.d("AnnouncementAdapter", "Announcement onLongClick");

                return true;
            }
        });

        if(onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mAnnouncementList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView createDate;
        TextView Content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            createDate = itemView.findViewById(R.id.create_date);
            Content = itemView.findViewById(R.id.content);
        }
    }

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(View view , int pos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
