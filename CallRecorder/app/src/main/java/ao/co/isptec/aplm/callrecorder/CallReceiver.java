package ao.co.isptec.aplm.callrecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class CallReceiver extends BroadcastReceiver {
    private MediaRecorder recorder;
    private boolean isRecording = false;
    private File audiofile;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                // Telefone está tocando
            } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
                // Telefone está em uma chamada, começa a gravar
                startRecording(context);
            } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                // Telefone voltou ao estado inativo, para a gravação
                stopRecording();
            }
        }
    }

    private void startRecording(Context context) {
        try {
            File sampleDir = new File(context.getExternalFilesDir(null), "/CallRecordings");
            if (!sampleDir.exists()) {
                sampleDir.mkdirs();
            }

            String fileName = "Recording_" + System.currentTimeMillis() + ".3gp";
            audiofile = new File(sampleDir, fileName);

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(audiofile.getAbsolutePath());
            recorder.prepare();
            recorder.start();
            isRecording = true;

            Toast.makeText(context, "Gravação iniciada", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (isRecording && recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            isRecording = false;
        }
    }
}
