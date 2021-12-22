package com.example.sano;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNewDiary extends AppCompatActivity {

    public static final String TAG = "tag";
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_diary);

        EditText write_anything = findViewById(R.id.write_anything);
        Button save_diary_button = findViewById(R.id.save_diary_button);

        firestore = FirebaseFirestore.getInstance();

        save_diary_button.setOnClickListener(new View.OnClickListener() {
            String user_input = write_anything.getText().toString().trim();

            @Override
            public void onClick(View view) {
                DocumentReference documentReference = firestore.collection("input").document(user_input);
                Map<String, Object> input = new HashMap<>();
                input.put("User Input", user_input);
                documentReference.set(input);
                documentReference.set(input).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {
                        Log.d(TAG, "Saved Successfully.");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Please try again.");
                    }
                });

            }
        });
    }

}