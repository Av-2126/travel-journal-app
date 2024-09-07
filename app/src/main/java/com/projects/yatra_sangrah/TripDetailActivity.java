package com.projects.yatra_sangrah;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TripDetailActivity extends AppCompatActivity {
    ArrayList<String> tripDetailList;
    ArrayList<Integer> tripDetailIdList;
    DatabaseHelper db;
    FloatingActionButton addDetailBtn;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trip_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Intent
        int id = getIntent().getIntExtra("id",-1);
        String tableName = "table_"+id;
        //SQLite Database section
        db = new DatabaseHelper(TripDetailActivity.this,tableName);
        db.createTable(tableName);

        tripDetailList = new ArrayList<>();
        tripDetailIdList = new ArrayList<>();
        addDetailBtn = findViewById(R.id.addDetailButton);

        //ListView section
        ListView tripDetails = findViewById(R.id.tripDetail);
        customAdapter = new CustomAdapter(TripDetailActivity.this,tripDetailList);
        tripDetails.setAdapter(customAdapter);
        loadTripDetails();

        addDetailBtn = findViewById(R.id.addDetailButton);

        addDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TripDetailActivity.this);
                builder.setTitle("Add Trip Detail");
                final View customLayout = getLayoutInflater().inflate(R.layout.add_trip,null);
                builder.setView(customLayout);
                builder.setPositiveButton("Done", (dialogInterface, i) -> {
                    EditText dialogText = customLayout.findViewById(R.id.dialog_text);
                    String detail = dialogText.getText().toString();
                    addTripDetail(detail);
                    dialogInterface.dismiss();
                });
                builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    private void addTripDetail(String trip) {
        if(!trip.isEmpty()){
            if(db.insertTrip(trip)) {
                Toast.makeText(this, "Trip added successfully", Toast.LENGTH_SHORT).show();
                loadTripDetails();
            }
            else Toast.makeText(TripDetailActivity.this, "Error adding trip !!", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(TripDetailActivity.this, "Trip can't be empty !!", Toast.LENGTH_SHORT).show();
    }

    private void loadTripDetails() {
        tripDetailList.clear();
        Cursor cursor = db.getAllTrips();
        if(cursor.moveToNext()){
            do{
                tripDetailIdList.add(cursor.getInt(0));
                tripDetailList.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        customAdapter.notifyDataSetChanged();
    }
    private void deleteTripDetail(int id){
        int tripId = tripDetailIdList.get(id);
        if(db.deleteTrip(tripId)){
            Toast.makeText(this, "Trip detail deleted successfully", Toast.LENGTH_SHORT).show();
            loadTripDetails();
        }
    }
    private String tableNameGenerator(String email){
        String[] emailSplit = email.split("@");
        return emailSplit[0];
    }
}
