package ao.co.isptec.aplm.binasjc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verificar se o usuário já está logado
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String storedUsername = sharedPreferences.getString("Username", "");
        String storedPassword = sharedPreferences.getString("Password", "");

        // Se as credenciais estiverem armazenadas, redirecionar para a tela de chat
        if (!storedUsername.isEmpty() && !storedPassword.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Caso contrário, redirecionar para a tela de login
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
    }
}
