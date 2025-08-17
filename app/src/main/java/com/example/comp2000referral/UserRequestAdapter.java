package com.example.comp2000referral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserRequestAdapter extends RecyclerView.Adapter<UserRequestAdapter.ViewHolder> {

    Context context;
    List<UserRequest> requestList;

    public UserRequestAdapter(Context context, List<UserRequest> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle;
        TextView requestStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.requestBooksUser);
            requestStatus = itemView.findViewById(R.id.requestStatus);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_items_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserRequest request = requestList.get(position);
        holder.bookTitle.setText(request.getBookTitle());
        holder.requestStatus.setText("Status: " + request.getStatus());
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}