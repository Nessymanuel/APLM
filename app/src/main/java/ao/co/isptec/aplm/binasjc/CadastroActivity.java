package ao.co.isptec.aplm.binasjc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextSenha;
    private Button buttonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextSenha.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Username", username);
                editor.putString("Password", password);
                editor.apply();

                Intent intent = new Intent(CadastroActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
