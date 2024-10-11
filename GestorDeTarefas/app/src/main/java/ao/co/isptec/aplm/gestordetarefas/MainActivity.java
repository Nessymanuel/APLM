package ao.co.isptec.aplm.gestordetarefas;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declaração dos componentes
    private EditText editTextTask;
    private ListView listViewTasks;
    private ArrayList<String> taskList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa as views
        editTextTask = findViewById(R.id.editTextTask);
        listViewTasks = findViewById(R.id.listViewTasks);

        // Inicializa a lista de tarefas e o ArrayAdapter
        taskList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);

        // Conecta o ArrayAdapter ao ListView
        listViewTasks.setAdapter(arrayAdapter);

        // Captura o evento de pressionar o "Enter" no EditText
        editTextTask.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Verifica se a tecla "Enter" foi pressionada
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String task = editTextTask.getText().toString().trim();
                    if (!task.isEmpty()) {
                        // Adiciona a nova tarefa no topo da lista
                        taskList.add(0, task);
                        arrayAdapter.notifyDataSetChanged();
                        editTextTask.setText(""); // Limpa o campo de texto
                    }
                    return true;
                }
                return false;
            }
        });
    }
}