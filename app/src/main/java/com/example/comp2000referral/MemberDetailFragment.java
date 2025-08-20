package com.example.comp2000referral;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

public class MemberDetailFragment extends Fragment {

    TextView memberName;
    TextView firstNameView;
    TextView lastNameView;
    TextView emailView;
    TextView contactView;
    TextView membershipView;
    Button editButton;
    TextView bookIssueList;

    public static MemberDetailFragment newInstance(String username, String firstname, String lastname,
                                                     String email, String contact, String membershipEndDate) {
        MemberDetailFragment fragment = new MemberDetailFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        args.putString("firstname", firstname);
        args.putString("lastname", lastname);
        args.putString("email", email);
        args.putString("contact", contact);
        args.putString("membershipEndDate", membershipEndDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        memberName = view.findViewById(R.id.memberName);
        firstNameView = view.findViewById(R.id.firstNameView);
        lastNameView = view.findViewById(R.id.lastNameView);
        emailView = view.findViewById(R.id.emailView);
        contactView = view.findViewById(R.id.contactView);
        membershipView = view.findViewById(R.id.membershipView);
        editButton = view.findViewById(R.id.editMemButton);
        bookIssueList = view.findViewById(R.id.bookIssueList);

        Bundle args = getArguments();
        if (args != null) {
            memberName.setText("Username: " + args.getString("username"));
            firstNameView.setText("First Name: " + args.getString("firstname"));
            lastNameView.setText("Last Name: " + args.getString("lastname"));
            emailView.setText("Email: " + args.getString("email"));
            contactView.setText("Contact: " + args.getString("contact"));
            membershipView.setText("Membership ends on: " + args.getString("membershipEndDate"));

            String username = args.getString("username");

            // bring the books in!
            loadIssuedBooks(username);
        }

        editButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Edit Member clicked!", Toast.LENGTH_SHORT).show();

            if (args != null) {
                EditMembersFragment fragment = EditMembersFragment.newInstance(
                        args.getString("username"),
                        args.getString("firstname"),
                        args.getString("lastname"),
                        args.getString("email"),
                        args.getString("contact"),
                        args.getString("membershipEndDate")
                );
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

        private void loadIssuedBooks(String username) {
            Log.d("MemberDetailFragment", "Loading issued books for user: " + username);
            APIClient.issuedBooks(username, new APIClient.ApiCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d("MemberDetailFragment", "Issued books API response: " + result);

                    try {
                        JSONArray array = new JSONArray(result);
                        StringBuilder booksText = new StringBuilder();

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject book = array.getJSONObject(i);
                            booksText.append(book.getString("book_title"))
                                    .append(" (Issued: ").append(book.getString("issue_date"))
                                    .append(", Return: ").append(book.getString("return_date")).append(")\n");
                        }

                        requireActivity().runOnUiThread(() -> {
                            bookIssueList.setText(booksText.toString());
                        });

                    } catch (Exception e) {
                        Log.e("MemberDetailFragment", "Failed to parse issued books JSON", e);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.e("MemberDetailFragment", "Failed to load issued books", e);
                    e.printStackTrace();
                }

            });
        }
    }