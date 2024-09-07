package com.projects.yatra_sangrah;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {

    ArrayList<String> trips;
    Context context;

    public CustomAdapter(Context context, ArrayList<String> trips) {
        super(context,0,trips);
        this.context = context;
        this.trips = trips;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(trips.get(position));
        return convertView;
    }
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list,parent,false);
//        MyViewHolder vh = new MyViewHolder(v);
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {
//        MyViewHolder holder_new = null;
//        if(holder instanceof MyViewHolder){
//            holder_new = (MyViewHolder) holder;
//        }
//        if(holder_new == null) return;
//        holder_new.name.setText(tripNames.get(position));
//        holder_new.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "Clicked "+tripNames.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context,MainActivity.class);
//                view.getContext().startActivity(intent);
//            }
//        });
//        holder_new.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Toast.makeText(context, "Long Clicked"+tripNames.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return tripNames.size();
//    }
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView name;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            name = itemView.findViewById(R.id.tripName);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context,TripDetailActivity.class);
//                    String text = name.getText().toString();
//                    intent.putExtra("tripName",text);
//                    startActivity(context,intent,null);
//                }
//            });
//        }
//    }
}
