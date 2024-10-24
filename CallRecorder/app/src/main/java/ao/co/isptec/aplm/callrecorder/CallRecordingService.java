package ao.co.isptec.aplm.callrecorder;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class CallRecordingService extends Service {

    private MediaRecorder recorder;
    private boolean isRecording = false;
    private String outputFilePath;

    @Override
    public void onCreate() {
        super.onCreate();

        // Registrar o listener para eventos de chamadas
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        // Começar a gravar quando a chamada for atendida
                        startRecording(incomingNumber);
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        // Parar de gravar quando a chamada for finalizada
                        if (isRecording) {
                            stopRecording();
                        }
                        break;
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void startRecording(String phoneNumber) {
        if (recorder != null) {
            recorder.release();
        }

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        outputFilePath = getExternalFilesDir(null).getAbsolutePath() + "/" + phoneNumber + "_Recebida.3gp";
        recorder.setOutputFile(outputFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
            isRecording = true;
            Toast.makeText(this, "Gravação iniciada.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Erro ao iniciar gravação: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        isRecording = false;
        Toast.makeText(this, "Gravação finalizada.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
