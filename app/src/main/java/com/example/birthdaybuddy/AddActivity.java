package com.example.birthdaybuddy;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
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
    Uri selectedImageUri;
    int count;
    private static final int pickimage = 1;
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
        count = getIntent().getIntExtra("count",0);
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
        btimage.setOnClickListener(view -> opengallery());
        btsave.setOnClickListener(view -> savedata());
    }
    private void opengallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,pickimage);
    }
    protected void onActivityResult(int requestcode, int resultcode, @Nullable Intent data){
        super.onActivityResult(requestcode,resultcode,data);
        if (requestcode == pickimage && resultcode == RESULT_OK && data!=null){
            getContentResolver().takePersistableUriPermission(data.getData(),Intent.FLAG_GRANT_READ_URI_PERMISSION);
            selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);
            imageview.setVisibility(View.VISIBLE);
            btimage.setVisibility(View.GONE);
        }
    }
    private void savedata(){
        SharedPreferences sharedPreferences = getSharedPreferences("BirthdayData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (etname.getText().toString().isEmpty() || Dob==null ||selectedImageUri==null)
            Toast.makeText(this,"ENTER ALL THE DATA",Toast.LENGTH_SHORT).show();
        else {
            editor.putInt("count",++count);
            editor.putString("name"+count, etname.getText().toString());
            editor.putString("birthday"+count, Dob);
            editor.putString("imagePath"+count, selectedImageUri.toString());
            editor.apply();
            switchActivity();
        }
    }
    private void switchActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("count", count);
        startActivity(intent);
        finish();
    }
    public void onBackPressed(){
        super.onBackPressed();
        switchActivity();
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