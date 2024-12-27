package com.example.madt_lab4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DeleteNoteActivity extends AppCompatActivity {
    private ListView listViewNotesToDelete;
    private SharedPreferences sharedPreferences;
    private ArrayList<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        listViewNotesToDelete = findViewById(R.id.listViewNotesToDelete);
        sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);

        loadNotes();
        setupListViewClickListener();
    }

    private void loadNotes() {
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
        listViewNotesToDelete.setAdapter(adapter);
    }

    private void setupListViewClickListener() {
        listViewNotesToDelete.setOnItemClickListener((parent, view, position, id) -> {
            Note selectedNote = notesList.get(position);
            deleteNote(selectedNote);
        });
    }

    private void deleteNote(Note note) {
        Set<String> noteSet = sharedPreferences.getStringSet("notes", new HashSet<>());
        Set<String> newNoteSet = new HashSet<>();

        for (String noteStr : noteSet) {
            String[] parts = noteStr.split("\\|\\|\\|");
            if (parts.length == 2 && !parts[0].equals(note.getName())) {
                newNoteSet.add(noteStr);
            }
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("notes", newNoteSet);
        editor.apply();

        Toast.makeText(this, R.string.note_deleted, Toast.LENGTH_SHORT).show();
        finish();
    }
}