package com.example.madt_lab4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ListView listViewNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewNotes = findViewById(R.id.listViewNotes);
        SharedPreferences sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);
        Set<String> noteSet = sharedPreferences.getStringSet("notes", new HashSet<>());
        ArrayList<Note> notesList = new ArrayList<>();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_note) {
            startActivity(new Intent(this, AddNoteActivity.class));
            return true;
        } else if (id == R.id.action_delete_note) {
            startActivity(new Intent(this, DeleteNoteActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning to MainActivity
        SharedPreferences sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);
        Set<String> noteSet = sharedPreferences.getStringSet("notes", new HashSet<>());
        ArrayList<Note> notesList = new ArrayList<>();

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
}