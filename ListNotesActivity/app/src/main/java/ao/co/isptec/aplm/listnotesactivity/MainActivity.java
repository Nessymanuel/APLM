package ao.co.isptec.aplm.listnotesactivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> noteTitles = new ArrayList<>();
    private ArrayList<String> noteContents = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listViewNotes = findViewById(R.id.listViewNotes);
        Button btnNewNote = findViewById(R.id.btnNewNote);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteTitles);
        listViewNotes.setAdapter(adapter);

        btnNewNote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
            startActivityForResult(intent, 1);
        });

        listViewNotes.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, ReadNoteActivity.class);
            intent.putExtra("title", noteTitles.get(position));
            intent.putExtra("content", noteContents.get(position));
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            noteTitles.add(title);
            noteContents.add(content);
            adapter.notifyDataSetChanged();
        }
    }
}