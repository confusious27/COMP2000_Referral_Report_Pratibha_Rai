package com.example.comp2000referral;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000referral.adapters.AdminRequestAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdminRequestsFragment extends Fragment {

    RecyclerView recyclerView;
    AdminRequestAdapter adapter;
    List<Request> requestList;

    public AdminRequestsFragment() {
        //empty; purpose is so android can restore fragments
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_requests, container, false);

        recyclerView = view.findViewById(R.id.adminRequestsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        requestList = new ArrayList<>();
        requestList.add(new Request("The Hobbit", "John Doe"));
        requestList.add(new Request("1984", "Jane Smith"));
        requestList.add(new Request("Brave New World", "Alice Johnson"));

        adapter = new AdminRequestAdapter(getContext(), requestList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}