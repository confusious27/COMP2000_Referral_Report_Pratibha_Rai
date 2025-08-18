package com.example.comp2000referral;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

// ADMIN
public class RequestDetailFragment extends Fragment {

    TextView bookTitleView;
    TextView requesterView;
    Button acceptButton;
    Button declineButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_detail, container, false);

        bookTitleView = view.findViewById(R.id.bookTitleAdmin);
        requesterView = view.findViewById(R.id.requesterName);
        acceptButton = view.findViewById(R.id.acceptButton);
        declineButton = view.findViewById(R.id.declineButton);

        // Dummy data for now
        bookTitleView.setText("Book Title: The Hobbit");
        requesterView.setText("Requested by: John Doe");

        // notification
//        createNotificationChannel();
//
//        acceptButton.setOnClickListener(v -> {
//            updateRequestStatus(requestId, "accepted");
//            sendNotification("Request Accepted", "You accepted the request for The Hobbit.");
//        });
//
//        declineButton.setOnClickListener(v -> {
//            updateRequestStatus(requestId, "declined");
//            sendNotification("Request Declined", "You declined the request for The Hobbit.");
//        });
//
        return view;
    }
}
//
//    private void updateRequestStatus(String requestId, String status) {
//        // TODO: Update your request status in your database/backend here
//        // This could be a Firebase update, API call, SQLite update, etc.
//        // Example (pseudo-code):
//        // database.updateRequestStatus(requestId, status);
//        System.out.println("Request " + requestId + " updated to " + status);
//    }
//
//    private void sendNotification(String title, String message) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_notification) // Replace with your notification icon
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
//        notificationManager.notify(1, builder.build());
//    }
//
//    private void createNotificationChannel() {
//        // Required for API 26+
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Request Status";
//            String description = "Notifications about request status updates";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//
//            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//}