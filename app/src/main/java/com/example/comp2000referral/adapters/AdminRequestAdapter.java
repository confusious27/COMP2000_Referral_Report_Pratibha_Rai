package com.example.comp2000referral.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000referral.MainActivity;
import com.example.comp2000referral.R;
import com.example.comp2000referral.Request;
import com.example.comp2000referral.RequestDetailFragment;

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
        TextView statusAdmin;

        public ViewHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.requestBooks);
            requester = itemView.findViewById(R.id.requestedBy);
            statusAdmin = itemView.findViewById(R.id.statusAdmin);
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
        holder.statusAdmin.setText(request.getStatus()); // will show the status of the request

        // will ensure you can't go back into a request you already passed decision on
        if (request.getStatus().equals("Pending")) {
            holder.itemView.setClickable(true);

            holder.itemView.setOnClickListener(v -> {
                RequestDetailFragment fragment = new RequestDetailFragment();
                Bundle args = new Bundle();
                args.putSerializable("request", request);
                fragment.setArguments(args);

                // assuming context is MainActivity
                ((MainActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            });
        } else {
            holder.itemView.setClickable(false);
            holder.itemView.setOnClickListener(null); // removes listener from triggering
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}