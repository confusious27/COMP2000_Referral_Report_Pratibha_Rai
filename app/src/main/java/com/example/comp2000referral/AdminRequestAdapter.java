package com.example.comp2000referral;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminRequestAdapter extends RecyclerView.Adapter<AdminRequestAdapter.ViewHolder> {

    // connects to book.java
    Context context;
    List<Request> requestList;

    public AdminRequestAdapter(Context context, List<Request> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle;
        TextView requester;

        public ViewHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.requestBooks);
            requester = itemView.findViewById(R.id.requestedBy);
        }
    }

    @Override
    public AdminRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_items, parent, false);
        return new AdminRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdminRequestAdapter.ViewHolder holder, int position) {
        final Request request = requestList.get(position);
        holder.bookTitle.setText(request.getBookTitle());
        holder.requester.setText("Requested by: " + request.getRequesterName());
        // so requested by text appears

        // for the details page
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RequestDetailActivity.class);
            intent.putExtra("bookTitle", request.getBookTitle());
            intent.putExtra("requesterName", request.getRequesterName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}