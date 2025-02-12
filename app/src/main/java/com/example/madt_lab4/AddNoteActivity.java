package com.example.madt_lab4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;
import java.util.Set;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTextNoteName;
    private EditText editTextNoteContent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextNoteName = findViewById(R.id.editTextNoteName);
        editTextNoteContent = findViewById(R.id.editTextNoteContent);
        Button buttonSaveNote = findViewById(R.id.buttonSaveNote);
        sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);

        buttonSaveNote.setOnClickListener(v -> saveNote());
    }

    private boolean isNoteNameExists(String noteName) {
        Set<String> noteSet = sharedPreferences.getStringSet("notes", new HashSet<>());
        for (String noteStr : noteSet) {
            String[] parts = noteStr.split("\\|\\|\\|");
            if (parts.length == 2 && parts[0].equals(noteName)) {
                return true;
            }
        }
        return false;
    }

    private void saveNote() {
        String noteName = editTextNoteName.getText().toString().trim();
        String noteContent = editTextNoteContent.getText().toString().trim();

        if (TextUtils.isEmpty(noteName) || TextUtils.isEmpty(noteContent)) {
            Toast.makeText(this, R.string.empty_field_warning, Toast.LENGTH_SHORT).show();
            return;
        }

        if (isNoteNameExists(noteName)) {
            Toast.makeText(this, "A note with this name already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        Set<String> noteSet = sharedPreferences.getStringSet("notes", new HashSet<>());
        Set<String> newNoteSet = new HashSet<>(noteSet);
        newNoteSet.add(noteName + "|||" + noteContent);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("notes", newNoteSet);
        editor.apply();

        Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show();
        finish();
    }
}