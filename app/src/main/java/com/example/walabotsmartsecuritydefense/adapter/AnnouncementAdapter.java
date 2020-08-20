package com.example.walabotsmartsecuritydefense.adapter;

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

    private OnRecyclerViewClickListener listener;
    public void setItemClickListener(OnRecyclerViewClickListener itemClickListener) {
        listener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        if(listener != null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(v);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClickListener(v);
                    return true;
                }
            });
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.ViewHolder holder, int position) {
        Announcement announcement = mAnnouncementList.get(position);
        holder.createDate.setText(announcement.getCreateDate());
        holder.Content.setText(announcement.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "onclick" + position, Toast.LENGTH_LONG).show();
//                addData(holder.getLayoutPosition());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
//                Toast.makeText(MainActivity.this, "on long click" + position, Toast.LENGTH_LONG).show();
//                removeData(holder.getLayoutPosition());
                return true;
            }
        });

//        if(onItemClickListener != null) {
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListener.onItemClick(holder.itemView, position);
//                }
//            });
//        }

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

    public interface OnRecyclerViewClickListener {
        void onItemClickListener(View view);
        void onItemLongClickListener(View view);
    }


//    private OnItemClickListener onItemClickListener;
//    public interface OnItemClickListener{
//        void onItemClick(View view , int pos);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
}
