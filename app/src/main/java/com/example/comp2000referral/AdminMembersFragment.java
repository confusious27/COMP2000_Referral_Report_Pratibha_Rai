package com.example.comp2000referral;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AdminMembersFragment extends Fragment {

    RecyclerView recyclerView;
    MemberAdapter adapter;
    List<Member> memberList;
    FloatingActionButton addMember;


    //tells android to use the xml for the ui
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_members, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.adminMembersView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // help fragment get member list and adapter
        memberList = new ArrayList<>();
        memberList.add(new Member("Alice Snow", "https://images.unsplash.com/photo-1568230044329-399ffe29b6d1?q=80&w=1175&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        memberList.add(new Member("Nawahang Altai", "https://images.unsplash.com/photo-1742201473141-07daabc7a327?q=80&w=1364&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));


        // passes the click listener to the adapter
        adapter = new MemberAdapter(getContext(), memberList, member -> {
            Intent intent = new Intent(getContext(), AdminBookDetailFragment.class);
            intent.putExtra("Name", member.getName());
            intent.putExtra("Profile Picture", member.getProfileUrl());
        });
        recyclerView.setAdapter(adapter);

        addMember = view.findViewById(R.id.addMember);
        addMember.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddBookFragment.class);
            startActivity(intent);
        });

    }
}
