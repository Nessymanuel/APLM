package ao.co.isptec.aplm.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Chave usada para passar a mensagem para a próxima atividade
    public static final String EXTRA_MESSAGE = "com.example.helloworldtwoactivities.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referências para os componentes da UI
        EditText editTextMessage = findViewById(R.id.editTextMessage);
        Button buttonSend = findViewById(R.id.buttonSend);

        // Definir o comportamento do botão "Enviar"
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Captura o texto digitado
                String message = editTextMessage.getText().toString();

                // Cria uma Intent para iniciar a nova atividade
                Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                intent.putExtra(EXTRA_MESSAGE, message); // Passa a mensagem para a nova atividade
                startActivity(intent); // Inicia a nova atividade
            }
        });
    }
}