package com.example.comp2000referral;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000referral.adapters.MemberAdapter;
import com.example.comp2000referral.models.Member;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        // passes the click listener to the adapter
        adapter = new MemberAdapter(getContext(), memberList, member -> {
            MemberDetailFragment fragment = MemberDetailFragment.newInstance(
                    member.getUsername(),
                    member.getFirstname(),
                    member.getLastname(),
                    member.getEmail(),
                    member.getContact(),
                    member.getMembershipEndDate()
            );

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });
        recyclerView.setAdapter(adapter);

        addMember = view.findViewById(R.id.addMember);
        addMember.setOnClickListener(v -> {
            AddMemberFragment fragment = new AddMemberFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // calls API to load
        loadMembersFromAPI();
    }

    public void loadMembersFromAPI() {
        APIClient.getMembers(new APIClient.ApiCallback() {
            @Override
            public void onSuccess(String result) {

                Log.d("AdminMembers", "API response: " + result);

                try {
                    JSONArray array = new JSONArray(result);
                    memberList.clear();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);

                        Member member = new Member(
                                obj.getString("username"),
                                obj.getString("firstname"),
                                obj.getString("lastname"),
                                obj.getString("email"),
                                obj.getString("contact"),
                                obj.getString("membership_end_date")
                        );
                        memberList.add(member);
                    }

                    requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("AdminMembers", "Error loading members", e);
                Toast.makeText(getContext(), "Failed to load members", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
