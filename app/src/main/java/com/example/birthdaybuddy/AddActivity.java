package com.example.birthdaybuddy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    EditText etname;
    TextView tvbirthday;
    ImageButton btimage;
    ImageView imageview;
    AppCompatButton btsave,btpick;
    String Dob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etname = findViewById(R.id.etname);
        tvbirthday = findViewById(R.id.tvbirthday);
        btimage = findViewById(R.id.btimage);
        imageview = findViewById(R.id.imageview);
        btsave = findViewById(R.id.btsave);
        btpick = findViewById(R.id.button1);

        btpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendarDialog();
            }
        });
    }


    private void openCalendarDialog()
    {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,(view,Year,Month,dayOfMonth)->{

            calendar.set(Calendar.YEAR,Year);
            calendar.set(Calendar.MONTH,Month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            Date date = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Dob=dateFormat.format(date);
            tvbirthday.setText(Dob);

        },year,month,day);
        datePickerDialog.show();
    }
}