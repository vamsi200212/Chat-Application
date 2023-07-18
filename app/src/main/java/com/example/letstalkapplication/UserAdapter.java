package com.example.letstalkapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

//public class UserAdapter extends RecyclerView.Adapter<UserAdapter.myviewholder> {
//    User model1;
//    Context context;

//    public UserAdapter, Context context) {
//        super(options);
//        this.context = context;
////        this.firebaseRecyclerOptions = options;
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull User model) {
////        Glide.with(holder.img.getContext()).load(model.getImg()).into(holder.img);
////        holder.name.setText(model.getName());
////        holder.location.setText(model.getLocation());
////        Glide.with(holder.pic.getContext()).load(model.getImg()).into(holder.pic);
//        this.model1 = model;
//        holder.name.setText(model.getName());
//        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
////                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
////                startActivity(intent);
////                Bundle bundle = new Bundle();
////                bundle.putString("latKey",model.getLat());
////                bundle.putString("lngKey",model.getLng());
////                MapsFragment mapsFragment = new MapsFragment();
////                mapsFragment.setArguments(bundle);
////                AppCompatActivity activity = (AppCompatActivity)view.getContext();
////                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame,mapsFragment,"MapsFragment").addToBackStack("MapsFragment").commit();
////                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
////                progressDialog.setMessage("Maps Loading...");
////                progressDialog.setTitle();
////                progressDialog.setCanceledOnTouchOutside(false);
////                progressDialog.show();
////                Handler handler = new Handler();
////                handler.postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        progressDialog.dismiss();
////                    }
////                },2000);
//
//                Toast.makeText(view.getContext(),""+model.getName(),Toast.LENGTH_LONG).show();
//                context.startActivity(new Intent(context,ChatActivity.class));
//            }
//        });
//    }
//
//
//
//    @NonNull
//    @Override
//    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout,parent,false);
//        return new myviewholder(view);
//    }
//
//    class myviewholder extends RecyclerView.ViewHolder{
//        TextView name;
//        ConstraintLayout constraintLayout;
//        public myviewholder(@NonNull View itemView) {
//            super(itemView);
////            pic = itemView.findViewById(R.id.bunk_img);
////            name = itemView.findViewById(R.id.bunk_name);
////            location = itemView.findViewById(R.id.location1);
////            card = itemView.findViewById(R.id.card);
////                air = itemView.findViewById(R.id.air1);
////            this.onNoteListener = onNoteListener;
//            name = itemView.findViewById(R.id.user_name);
//            constraintLayout = itemView.findViewById(R.id.const_mess);
//        }
//    }
//}

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.myViewHolder>{

    Context context;
    ArrayList<User> options;

    public UserAdapter(ArrayList<User> options, Context context) {
        this.options = options;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.myViewHolder holder, int position) {
//        Glide.with(holder.img.getContext()).load(model.getImg()).into(holder.img);
//        holder.name.setText(model.getName());
//        holder.location.setText(model.getLocation());
//        Glide.with(holder.pic.getContext()).load(model.getImg()).into(holder.pic);
        User model = options.get(holder.getAdapterPosition());
        holder.name.setText(model.getName());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ChatActivity.class);
                intent.putExtra("Name",model.getName());
                intent.putExtra("UID",model.getUid());
                Toast.makeText(view.getContext(),""+model.getName(),Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ConstraintLayout constraintLayout;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
//            pic = itemView.findViewById(R.id.bunk_img);
//            name = itemView.findViewById(R.id.bunk_name);
//            location = itemView.findViewById(R.id.location1);
//            card = itemView.findViewById(R.id.card);
//                air = itemView.findViewById(R.id.air1);
//            this.onNoteListener = onNoteListener;
            name = itemView.findViewById(R.id.user_name);
            constraintLayout = itemView.findViewById(R.id.const_mess);
        }
    }
}