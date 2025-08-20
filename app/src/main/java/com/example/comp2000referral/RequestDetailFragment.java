package com.example.comp2000referral;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// ADMIN
public class RequestDetailFragment extends Fragment {

    TextView bookTitleView;
    TextView requesterView;
    Button acceptButton;
    Button declineButton;

    private Request currentRequest; // request is passed from AdminRequestsFragment

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_detail, container, false);

        bookTitleView = view.findViewById(R.id.bookTitleAdmin);
        requesterView = view.findViewById(R.id.requesterName);
        acceptButton = view.findViewById(R.id.acceptButton);
        declineButton = view.findViewById(R.id.declineButton);

        if (getArguments() != null) {
            currentRequest = (Request) getArguments().getSerializable("request");
            bookTitleView.setText("Book Title: " + currentRequest.getBookTitle());
            requesterView.setText("Requested by: " + currentRequest.getRequesterName());
        }

        // buttons update status
        acceptButton.setOnClickListener(v -> {
            issueBookToMember(currentRequest); // calling API to issue the book
            updateRequestStatus("Accepted"); // updates local manager too
        });
        declineButton.setOnClickListener(v -> updateRequestStatus("Declined"));

        return view;
    }

    private void updateRequestStatus(String status) {
        UserRequestManager manager = new UserRequestManager(requireContext());
        List<UserRequest> allRequests = manager.getRequests();

        for (UserRequest ur : allRequests) {
            if (ur.getBookTitle().equals(currentRequest.getBookTitle())
                    && ur.getRequestedBy().equals(currentRequest.getRequesterName())) {
                ur.setStatus(status);
                break;
            }
        }

        manager.updateRequests(allRequests);

        // for the different actions for the buttons
        if (status.equals("Accepted")) {
            issueBookToMember(currentRequest);
        } else if (status.equals("Declined")) {
            removeIssuedBook(currentRequest.getRequesterName(), currentRequest.getBookTitle());
        }

        // sends notification
        sendNotification("Request " + status, "You " + status.toLowerCase() + " the request for " + currentRequest.getBookTitle());

        // goes back to previous page
        getParentFragmentManager().popBackStack();

    }

    // notifications
    private void sendNotification(String title, String message) {
        String CHANNEL_ID = "request_channel";

        // creates notif channel for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Request Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_box) // NEEDED or it will crash T^T
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // this is for the runtime check that Android 13+ has
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                requireContext().checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                        == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
            notificationManager.notify((int) System.currentTimeMillis(), builder.build()); // makes the ID unique for the notif
        }
    }

    // for da books in the API part
    private void issueBookToMember(Request request) {
        try {

            // helps to format to the API's version or it will crash D:
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // date when added AKA today's date
            LocalDate today = LocalDate.now();
            // can set the amount of dates to put the return date in... neat :D
            LocalDate returnDate = today.plusDays(14);

            JSONObject body = new JSONObject();
            body.put("username", request.getRequesterName());
            body.put("book_title", request.getBookTitle());
            body.put("issue_date", today.format(formatter));
            body.put("return_date", returnDate.format(formatter));

            APIClient.issueBook(body, new APIClient.ApiCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d("API_SUCCESS", "Book issued successfully: " + result);
                }
                @Override
                public void onError(Exception e) {
                    Log.e("API_ERROR", "Failed to issue book", e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // for deleting the book from API, not from the BookManager
    private void removeIssuedBook(String username, String bookTitle) {
        try {
            JSONObject body = new JSONObject();
            body.put("username", username);
            body.put("book_title", bookTitle);

            APIClient.deleteBook(body, new APIClient.ApiCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d("API_SUCCESS", "Book removed successfully: " + result);
                }

                @Override
                public void onError(Exception e) {
                    Log.e("API_ERROR", "Failed to remove book", e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}