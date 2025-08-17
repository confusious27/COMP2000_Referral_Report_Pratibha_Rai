package com.example.comp2000referral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    // connects to member.java
    Context context;
    List<Member> memberList;
    MemberAdapter.MemberClickListener listener;

    public MemberAdapter(Context context, List<Member> memberList, MemberClickListener listener) {
        this.context = context;
        this.memberList = memberList;
        this.listener = listener;
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePicture;
        TextView memberName;

        public MemberViewHolder(View itemView) {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.profilePicture);
            memberName = itemView.findViewById(R.id.memberName);
        }
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_items, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        final Member member = memberList.get(position);

        holder.memberName.setText(member.getName());

        // for the profile picture
        Glide.with(context)
                .load(member.getProfileUrl())
                .placeholder(R.drawable.profile_placeholder)
                .into(holder.profilePicture);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMemberClick(member);
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public interface MemberClickListener {
        void onMemberClick(Member member);
    }
}
