package com.example.letstalkapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView recview;
    UserAdapter usersAdapter;
    LinearLayoutManager nestedLayoutManager;
    FirebaseRecyclerOptions<User> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recview = (RecyclerView)findViewById(R.id.rec_view);
        nestedLayoutManager = new LinearLayoutManager(this);
        nestedLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recview.setLayoutManager(nestedLayoutManager);
        ArrayList<User> usersList = new ArrayList<>();

        if(getSupportActionBar()!=null) getSupportActionBar().hide();

        FirebaseDatabase.getInstance().getReference().child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                int val = snapshot.child("val").getValue(Integer.class);
//                ArrayList<DataSnapshot> val = (ArrayList<DataSnapshot>) snapshot.getChild("");
//                Toast.makeText(MainActivity.this,""+val,Toast.LENGTH_SHORT).show();
                usersList.clear();

                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    User userModel = new User();
                    userModel.setName(snapshot1.child("name").getValue(String.class));
                    userModel.setUid(snapshot1.child("uid").getValue(String.class));
                    userModel.setEmail(snapshot1.child("email").getValue(String.class));
                    Toast.makeText(MainActivity.this,""+userModel.getName(),Toast.LENGTH_SHORT).show();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if(!uid.equals(userModel.getUid())) usersList.add(userModel);
//                    usersList.add(userModel);

                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        usersAdapter = new UserAdapter(usersList,this);
        recview.setAdapter(usersAdapter);
//        recview.setNestedScrollingEnabled(false);
        recview.setItemAnimator(null);

        ImageView logout_button = (ImageView) findViewById(R.id.settings);

        // Setting onClick behavior to the button
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, logout_button);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("Logout")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Confirm Exit");
                            builder.setMessage("Are you sure you want to logout?");
                            builder.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (FirebaseAuth.getInstance().getCurrentUser() != null)
                                        FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    SharedPreferences preferences =getSharedPreferences("login", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.clear();
                                    editor.apply();
                                    startActivity(intent);
                                    finishAffinity();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}