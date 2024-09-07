package com.projects.yatra_sangrah;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> trips;
    ArrayList<Integer> tripIds;
    DatabaseHelper db;
    FloatingActionButton add;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //SQLite Database section
        String email = getIntent().getStringExtra("email");
        db = new DatabaseHelper(this,tableNameGenerator(email));
        trips = new ArrayList<>();
        tripIds = new ArrayList<>();
        //ListView section
        ListView tripList = findViewById(R.id.tripList);
        customAdapter = new CustomAdapter(MainActivity.this,trips);
        tripList.setAdapter(customAdapter);
        loadTrips();

        add = findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add Trip");
                final View customLayout = getLayoutInflater().inflate(R.layout.add_trip,null);
                builder.setView(customLayout);
                builder.setPositiveButton("Done", (dialogInterface, i) -> {
                    EditText dialogText = customLayout.findViewById(R.id.dialog_text);
                    String trip = dialogText.getText().toString();
                    addTrip(trip);
                    dialogInterface.dismiss();
                });
                builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        tripList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int tripId = tripIds.get(position);
                System.out.println("TripId is "+tripId);
                Intent intent = new Intent(MainActivity.this,TripDetailActivity.class);
                intent.putExtra("id",tripId);
                startActivity(intent);
            }
        });
    }
    private void addTrip(String trip) {
        if(!trip.isEmpty()){
            if(db.insertTrip(trip)) {
                Toast.makeText(this, "Trip added successfully", Toast.LENGTH_SHORT).show();
                loadTrips();
            }
            else Toast.makeText(MainActivity.this, "Error adding trip !!", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(MainActivity.this, "Trip can't be empty !!", Toast.LENGTH_SHORT).show();
    }

    private void loadTrips() {
        trips.clear();
        tripIds.clear();
        Cursor cursor = db.getAllTrips();
        if(cursor.moveToNext()){
            do{
                tripIds.add(cursor.getInt(0));
                trips.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        customAdapter.notifyDataSetChanged();
    }
    private void deleteTrip(int id){
        int tripId = tripIds.get(id);
        if(db.deleteTrip(tripId)){
            Toast.makeText(this, "Trip deleted successfully", Toast.LENGTH_SHORT).show();
            loadTrips();
        }
    }
    private String tableNameGenerator(String email){
        String[] emailSplit = email.split("@");
        return emailSplit[0];
    }

}