package com.example.walabotsmartsecuritydefense.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walabotsmartsecuritydefense.R;
import com.example.walabotsmartsecuritydefense.table.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<Notification> mNotificationList;

    public List<Notification> getNotificationList() {
        return mNotificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.mNotificationList = notificationList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationAdapter.ViewHolder holder, final int position) {
        Notification notification = mNotificationList.get(position);
        holder.createTime.setText(notification.getCreateTime());
        holder.Content.setText(notification.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NotificationAdapter", "Notification onClick");
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                Log.d("NotificationAdapter", "Notification onLongClick");

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
        return mNotificationList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView createTime;
        TextView Content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            createTime = itemView.findViewById(R.id.create_date);
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
