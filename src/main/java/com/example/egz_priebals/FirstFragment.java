package com.example.egz_priebals;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class FirstFragment extends Fragment {

    TextView input;
    Button btnChart, btnChart2, btnNotification;
    int vowels, consonants, numbers;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container,false);

        //Id's
        input = (TextView) rootView.findViewById(R.id.inputText);
        btnChart = (Button) rootView.findViewById(R.id.buttonChart);
        btnChart2 = (Button) rootView.findViewById(R.id.buttonChart2);
        btnNotification = (Button) rootView.findViewById(R.id.buttonNotification);
        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String inputText = input.getText().toString();
                String result = Calculate(inputText);
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                String inputSend = inputText;

                ActivateSecondFragment(result);
            }
        });
        btnChart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = input.getText().toString();
                String result = Calculate(inputText);
                Notification(inputText);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    public void ActivateSecondFragment(String text){

        MainActivity mainActivity = (MainActivity) getActivity();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SecondFragment fragment = new SecondFragment();

        Bundle args = new Bundle();
        args.putString("data", text);

        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.graphPlaceHolder, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();

    }

    public void Notification(String text){

        String output = "Balsiai: " + vowels + " , Priebalsiai: " + consonants + " , Skaiciai: " +
                numbers;
        Intent intent=new Intent(getContext(),MainActivity.class);
        String CHANNEL_ID="MYCHANNEL";
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent=PendingIntent.getActivity(getContext(),1,intent,0);
        Notification notification=new Notification.Builder(getContext(),CHANNEL_ID)
                .setContentText("Rezultatas")
                .setContentTitle(output)
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat,"Title",pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .build();

        NotificationManager notificationManager=(NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);

    }

    public String Calculate(String text)
    {
        vowels = 0;
        consonants = 0;
        numbers = 0;

        String line = text.toLowerCase();
        for (int i = 0; i < line.length(); ++i) {
            char ch = line.charAt(i);

            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                ++vowels;
            }
            else if ((ch >= 'a' && ch <= 'z')) {
                ++consonants;
            }
            else if (Character.isDigit(ch)) {
                ++numbers;
            }
        }
        String result = vowels + "," + consonants + "," + numbers;
        return result;
    }
}
