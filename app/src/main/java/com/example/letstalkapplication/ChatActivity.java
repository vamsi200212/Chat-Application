package com.example.letstalkapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    RecyclerView messagesRecView;
    EditText messageBox;
    TextView toMessageUser;
    ImageView sendBtn,backBtn;
    MessageAdapter adapter;
    ArrayList<com.example.letstalkapplication.Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageBox = findViewById(R.id.message_box);
        messagesRecView = findViewById(R.id.messages_recview);
        sendBtn = findViewById(R.id.send_btn);
        toMessageUser = findViewById(R.id.name_to_send);
        backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String name = getIntent().getStringExtra("Name");
        String UID = getIntent().getStringExtra("UID");

        toMessageUser.setText(name);

        messages = new ArrayList<>();

        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String receiverUid = UID;

        String senderRoom = receiverUid + senderUid;
        String receiverRoom = senderUid + receiverUid;

        DatabaseReference DBref = FirebaseDatabase.getInstance().getReference().child("chats");

        DBref.child(senderRoom).child("messages")
                        .addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                messages.clear();
                                for (DataSnapshot snapshot1: snapshot.getChildren()){
                                    Message messOb = snapshot1.getValue(Message.class);
                                    messages.add(messOb);
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mess = messageBox.getText().toString();
                Message ob = new Message(senderUid,mess);

                DBref.child(senderRoom).child("messages").push()
                        .setValue(ob).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                DBref.child(receiverRoom).child("messages").push()
                                        .setValue(ob);
                            }
                        });
                messageBox.setText("");
            }
        });
        adapter = new MessageAdapter(this,messages);

        messagesRecView.setLayoutManager(new LinearLayoutManager(this));
        messagesRecView.setAdapter(adapter);
        messagesRecView.setItemAnimator(null);

    }
}