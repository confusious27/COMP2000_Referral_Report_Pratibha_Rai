package com.example.comp2000referral;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    Button logout;

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

        // loads requests from SharedPreferences
        UserRequestManager manager = new UserRequestManager(requireContext());
        List<UserRequest> allRequests = manager.getRequests();

        // changes user request to request list
        requestList = new ArrayList<>();
        for (UserRequest ur : allRequests) {
            requestList.add(new Request(ur.getBookTitle(), ur.getRequestedBy(), ur.getStatus()));
        }

        adapter = new AdminRequestAdapter(getContext(), requestList);
        recyclerView.setAdapter(adapter);

        // log out button
        logout = view.findViewById(R.id.logOutAdmin);
        logout.setOnClickListener(v -> {
            // clears saved login info
            requireActivity().getSharedPreferences("test_users", getContext().MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();

            // navigates back to login
            ((MainActivity) requireActivity()).logout();
        });

        return view;
    }

    // refreshes list after performing action
    @Override
    public void onResume() {
        super.onResume();
        reloadAdminRequests();
    }

    public void reloadAdminRequests() {
        UserRequestManager manager = new UserRequestManager(requireContext());
        List<UserRequest> allRequests = manager.getRequests();

        requestList.clear(); // clears old data
        for (UserRequest ur : allRequests) {
            requestList.add(new Request(ur.getBookTitle(), ur.getRequestedBy(), ur.getStatus()));
        }

        adapter.notifyDataSetChanged(); // refreshes UI
    }

}