package com.example.birthdaybuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    int count = 0;
    List<DataClass> list;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

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
        count = getIntent().getIntExtra("count", 0);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this,AddActivity.class);
            intent.putExtra("count",count);
            startActivity(intent);
            finish();
        });
        list=new ArrayList<>();
        retrieveData();
        setAdapter();
    }
    private void setAdapter(){
        adapter = new Adapter(this,list);
        recyclerView.setAdapter(adapter);
    }

    private void retrieveData()
    {
        list.clear();

        SharedPreferences sharedPreferences= getSharedPreferences("BirthdayData",MODE_PRIVATE);
        count = sharedPreferences.getInt("count",0);

        for(int i=0;i<=count;i++)
        {
            String name=sharedPreferences.getString("name"+i,null);
            String birthday=sharedPreferences.getString("birthday"+i,null);
            String imagepath=sharedPreferences.getString("imagePath"+i,null);

            if(name != null && birthday != null)
            {
                list.add(new DataClass(name,birthday,imagepath));
            }
        }
    }
}