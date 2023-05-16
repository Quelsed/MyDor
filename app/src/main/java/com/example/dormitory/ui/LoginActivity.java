package com.example.dormitory.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dormitory.MainActivity;
import com.example.dormitory.R;
import com.example.dormitory.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LoginActivity extends AppCompatActivity {

    private String USER_KEY = "USERS";

    private DatabaseReference databaseReference;
    private List<User> users;

    private EditText email;
    private EditText password;
    private Button signInOrUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signInOrUp = findViewById(R.id.signInOrUp);

        signInOrUp.setEnabled(false);

        databaseReference = FirebaseDatabase.getInstance().getReference(USER_KEY);
        users = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    users.add(child.getValue(User.class));
                }
                signInOrUp.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        signInOrUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                Optional<User> found = users.stream().filter(user -> user.getEmail().equals(emailString)).findFirst();
                if(!found.isPresent()) {
                    User newUser = new User(User.Role.USER, emailString, passwordString.hashCode());
                    databaseReference.push().setValue(newUser);
                    signInOrUp.setEnabled(false);
                } else {
                    if(found.get().getEmail().equals(emailString) && found.get().getHashPassword() == passwordString.hashCode()) {
                        if (found.get().getRole() == User.Role.USER) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}