package com.example.valentin.capteurmajeure;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.valentin.capteurmajeure.R.id.editText44;


public class Building extends AppCompatActivity {
    // Use of AppCompat to display the title

    private String building;
    BuildingContextState buildingContextState ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        findViewById(R.id.button44).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                building = ((EditText) findViewById(editText44))
                        .getText().toString();
                retrieveBuildingContextState(building);

            }
        });

        /*findViewById(R.id.buttonHue).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                building = ((EditText) findViewById(editText44))
                        .getText().toString();
                switchHue(buildingContextState, building);
            }
        });*/

    }

    public void onUpdate(BuildingContextState context) {
        buildingContextState = context ;

        // On affiche les noms et le nombre de rooms
        ((TextView) findViewById(R.id.textViewBuildingName)).setText(context.getNom()+ ", "+context.getNbrooms()+" rooms");
    }

    protected void retrieveBuildingContextState(String building){
        BuildingContextHttpManager.retrieveBuildingContextState(building, this);
    }

    protected void switchHue(BuildingContextState state, String room){
        BuildingContextHttpManager.switchHue(this, state, room);
    }

    public void room(View view) {
        // Pour retourner à l'activité précédente
        Intent intent2 = new Intent(this, ContextManagementActivity.class);
        startActivity(intent2);
    }

    public void switchVibrator(View view) {

        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }


        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int mode = audioManager.getRingerMode();
        if (mode == AudioManager.RINGER_MODE_SILENT)
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        else {
            if (mode == AudioManager.RINGER_MODE_NORMAL)
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            else {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
        }
    }

    public void Vibrate(View view) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
        // make a sound for 500 ms
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 500);
    }
}
