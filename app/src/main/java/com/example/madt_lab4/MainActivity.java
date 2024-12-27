package com.example.madt_lab4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ListView listViewNotes;
    private ArrayList<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewNotes = findViewById(R.id.listViewNotes);
        refreshNotesList();
        setupListViewClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshNotesList();
    }

    private void refreshNotesList() {
        SharedPreferences sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);
        Set<String> noteSet = sharedPreferences.getStringSet("notes", new HashSet<>());
        notesList = new ArrayList<>();

        for (String noteStr : noteSet) {
            String[] parts = noteStr.split("\\|\\|\\|");
            if (parts.length == 2) {
                notesList.add(new Note(parts[0], parts[1]));
            }
        }

        ArrayAdapter<Note> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(adapter);
    }

    private void setupListViewClickListener() {
        listViewNotes.setOnItemClickListener((parent, view, position, id) -> {
            Note selectedNote = notesList.get(position);
            Intent intent = new Intent(MainActivity.this, ViewNoteActivity.class);
            intent.putExtra("note_name", selectedNote.getName());
            intent.putExtra("note_content", selectedNote.getContent());
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_note) {
            Intent addIntent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(addIntent);
            return true;
        } else if (id == R.id.action_delete_note) {
            Intent deleteIntent = new Intent(MainActivity.this, DeleteNoteActivity.class);
            startActivity(deleteIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}