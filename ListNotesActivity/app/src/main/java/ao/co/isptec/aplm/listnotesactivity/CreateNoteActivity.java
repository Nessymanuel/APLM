package ao.co.isptec.aplm.listnotesactivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);


        EditText editTextTitle = findViewById(R.id.editTextTitle);
        EditText editTextContent = findViewById(R.id.editTextContent);
        Button btnOk = findViewById(R.id.btnOk);
        Button btnCancel = findViewById(R.id.btnCancel);

            btnOk.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString().trim();
            String content = editTextContent.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) {
                // Mostrar uma mensagem de erro ou aviso
                Toast.makeText(CreateNoteActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}