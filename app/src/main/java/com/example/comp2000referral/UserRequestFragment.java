package com.example.comp2000referral;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserRequestFragment extends Fragment {
    RecyclerView recyclerView;
    UserRequestAdapter adapter;
    List<UserRequest> requestList;

    public UserRequestFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_request, container, false);

        recyclerView = view.findViewById(R.id.requestsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data
        requestList = new ArrayList<>();
        requestList.add(new UserRequest("The Hobbit", "Accepted"));
        requestList.add(new UserRequest("1984", "Declined"));
        requestList.add(new UserRequest("Brave New World", "Pending"));

        adapter = new UserRequestAdapter(getContext(), requestList);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
