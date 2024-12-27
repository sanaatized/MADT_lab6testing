package com.example.madt_lab4;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ViewNoteActivity extends AppCompatActivity {
    private TextView textViewNoteName;
    private TextView textViewNoteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        textViewNoteName = findViewById(R.id.textViewNoteName);
        textViewNoteContent = findViewById(R.id.textViewNoteContent);

        String noteName = getIntent().getStringExtra("note_name");
        String noteContent = getIntent().getStringExtra("note_content");

        textViewNoteName.setText(noteName);
        textViewNoteContent.setText(noteContent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}