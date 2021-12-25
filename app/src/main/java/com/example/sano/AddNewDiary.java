package com.example.sano;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.*;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNewDiary extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_diary);

        firestore = FirebaseFirestore.getInstance();

        EditText write_anything = findViewById(R.id.write_anything);
        TextView date_view = findViewById(R.id.date_view);
        Button save_diary_button = findViewById(R.id.save_diary_button);
        Button date_button = findViewById(R.id.date_button);

        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new com.example.sano.DatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        save_diary_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = write_anything.getText().toString().trim();
                String date = date_view.getText().toString().trim();

                if (content.isEmpty() || date.isEmpty()){
                    Toast.makeText(AddNewDiary.this, "Please fill in all required field.", Toast.LENGTH_SHORT).show();
                }

                //save data
                DocumentReference documentReference = firestore.collection("diaries").document();
                Map<String, Object> diary = new HashMap<>();
                diary.put("Content", content);
                diary.put("Date", date);
                documentReference.set(diary);

                documentReference.set(diary).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {
                        Toast.makeText(AddNewDiary.this, "Saved successfully.", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewDiary.this, "Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView date_view = findViewById(R.id.date_view);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date_view.setText(currentDate);
    }
}