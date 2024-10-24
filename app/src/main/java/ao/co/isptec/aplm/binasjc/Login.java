package ao.co.isptec.aplm.binasjc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ALogin extends AppCompatActivity {


        private EditText editTextTelefoneLogin, editTextSenhaLogin;
        private Button buttonLogin;
        private TextView textViewCadastrar;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            editTextTelefoneLogin = findViewById(R.id.editTextTelefoneLogin);
            editTextSenhaLogin = findViewById(R.id.editTextSenhaLogin);
            buttonLogin = findViewById(R.id.buttonLogin);
            textViewCadastrar = findViewById(R.id.textViewCadastrar);

            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String inputUsername = editTextTelefoneLogin.getText().toString();
                    String inputPassword = editTextSenhaLogin.getText().toString();

                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    String storedUsername = sharedPreferences.getString("Username", "");
                    String storedPassword = sharedPreferences.getString("Password", "");

                    if (inputUsername.equals(storedUsername) && inputPassword.equals(storedPassword)) {
                        Intent intent = new Intent(Login.this, ChatActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Credenciais incorretas", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            textViewCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Login.this, CadastroActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

