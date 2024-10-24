package ao.co.isptec.aplm.callrecorder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CALL_LOG_PERMISSION = 1;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 2;
    private ListView callListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> callList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText("Lista de chamadas do telefone da Gracieth");

        callListView = findViewById(R.id.callListView);
        callList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, callList);
        callListView.setAdapter(adapter);

        callListView.setOnItemClickListener((parent, view, position, id) -> {
            String callDetail = callList.get(position);
            String[] lines = callDetail.split("\n");
            String number = lines[0].split(": ")[1];
            String callType = lines[1].split(": ")[1];

            String recordingFilePath = getExternalFilesDir(null).getAbsolutePath() + "/" + number + "_" + callType + ".3gp";

            playRecording(recordingFilePath);
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_CALL_LOG_PERMISSION);
        } else {
            loadCallLogs();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }

    private void loadCallLogs() {
        String sortOrder = CallLog.Calls.DATE + " DESC"; // Ordenar por data (últimas chamadas primeiro)

        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, sortOrder);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                @SuppressLint("Range") long dateMillis = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                @SuppressLint("Range") int durationSeconds = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = dateFormat.format(new Date(dateMillis));
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String formattedTime = timeFormat.format(new Date(dateMillis));

                String durationFormatted = formatDuration(durationSeconds);

                String callType;
                switch (Integer.parseInt(type)) {
                    case CallLog.Calls.INCOMING_TYPE:
                        callType = "Recebida";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        callType = "Efetuada";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callType = "Perdida";
                        break;
                    default:
                        callType = "Desconhecida";
                        break;
                }

                String callDetails = "Nª de telefone: " + number + "\nTipo: " + callType + "\nData: " + formattedDate + "\nHora: " + formattedTime + "\nDuração: " + durationFormatted;
                callList.add(callDetails);
            }
            cursor.close();
            adapter.notifyDataSetChanged();
        }
    }

    private String formatDuration(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return minutes + "m " + remainingSeconds + "s";
    }

    private void playRecording(String filePath) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(mp -> {
                mp.release();
            });
        } catch (IOException e) {
            Toast.makeText(this, "Erro ao reproduzir gravação: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL_LOG_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadCallLogs();
            } else {
                Toast.makeText(this, "Permissão negada para ler os logs de chamadas", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão para gravação de áudio concedida
            } else {
                Toast.makeText(this, "Permissão negada para gravar áudio", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
