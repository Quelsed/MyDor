package com.example.dormitory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.dormitory.Adapter.NotesListAdapter;
import com.example.dormitory.DataBase.RoomDB;
import com.example.dormitory.Models.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MyNotesActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    RoomDB database;
    Button fab_add;
    List <Notes> notes = new ArrayList<>();

    Notes selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        Button buttongoback = (Button) findViewById(R.id.buttongoback);
        fab_add = findViewById(R.id.buttonadd);
        recyclerView = findViewById(R.id.recycler_home);
        database = RoomDB.getInstance(this);
        notes = database.mainDAO().getAll();

        View.OnClickListener buttonaddd =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MyNotesActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        };

        buttongoback.setOnClickListener(buttonaddd);

        updateRecycle (notes);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyNotesActivity.this, NotesTaker.class);
                startActivityForResult(intent, 101);
            }
        });






}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101){
            if (resultCode== Activity.RESULT_OK){
                Notes new_notes =(Notes) data.getSerializableExtra("note");
                database.mainDAO().insert(new_notes);
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode==102){
            if (resultCode== Activity.RESULT_OK){
                Notes new_notes =(Notes) data.getSerializableExtra("note");
                database.mainDAO().update(new_notes.getID(), new_notes.getFio(), new_notes.getRoomnumber(), new_notes.getPlace(), new_notes.getNotes(), new_notes.getDate());
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();

            }
        }
    }

    private void updateRecycle(List<Notes> notes) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        notesListAdapter = new NotesListAdapter(MyNotesActivity.this, notes, notesClickListener);
        recyclerView.setAdapter(notesListAdapter);
    }
    private final NotesClickListener notesClickListener= new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(MyNotesActivity.this, NotesTaker.class);
            intent.putExtra("old_notes", notes);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selectedNote = new Notes();
            selectedNote = notes;
            showPopUp (cardView);

        }
    };

    private void showPopUp(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                database.mainDAO().delete(selectedNote);
                notes.remove(selectedNote);
                notesListAdapter.notifyDataSetChanged();

                Toast.makeText(MyNotesActivity.this, "Поломка удалена", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;

        }

    }
}