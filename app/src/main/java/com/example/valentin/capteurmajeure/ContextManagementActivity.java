package com.example.valentin.capteurmajeure;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.valentin.capteurmajeure.R.id.editText1;

public class ContextManagementActivity extends AppCompatActivity {

    private String room;
    RoomContextState roomContextState ;
    ArrayList<RoomContextRule> rules = new ArrayList<RoomContextRule>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.buttonCheck)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                room = ((EditText) findViewById(editText1))
                        .getText().toString();
                retrieveRoomContextState(room);
            }
        });

        // For following copy paste, I deleted redundant castings
        findViewById(R.id.buttonLight).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                room = ((EditText) findViewById(editText1))
                        .getText().toString();
                switchLight(roomContextState, room);
                retrieveRoomContextState(room);

            }
        });

        findViewById(R.id.buttonRinger).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                room = ((EditText) findViewById(editText1))
                        .getText().toString();
                switchRinger(roomContextState, room);
                retrieveRoomContextState(room);

            }
        });

        rules.add(new RoomContextRule() {
            @Override
            public void apply(RoomContextState roomContextState) {
                super.apply(roomContextState);
                if (condition(roomContextState)) action();
                    //doesn't work : display(this + " applies: silent mode switched on!");
            }

            @Override
            protected boolean condition(RoomContextState roomContextState) {
                return roomContextState.getLight() > 100
                        && roomContextState.getNoise() > 1.0;
            }

            @Override
            protected void action() {
                ((AudioManager) getApplicationContext().getSystemService(
                        Context.AUDIO_SERVICE))
                        .setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }

            public String toString() {
                return "Rule 1";
            }
        });
    }

    public void onUpdate(RoomContextState context) {
        roomContextState = context ;

        // apply the first rule
        rules.get(0).apply(context);

        // maintenant que tu as récupéré ta room, tu vas pouvoir les utiliser tes boutons
        findViewById(R.id.buttonLight).setEnabled(true);
        findViewById(R.id.buttonRinger).setEnabled(true);

        // On affiche l'icône qui va bien pour la lumière
        if (context.getLightStatus().equals("ON")) {
            ((ImageView) findViewById(R.id.imageViewLightOn)).setVisibility(View.VISIBLE);
            findViewById(R.id.imageViewLightOff).setVisibility(View.INVISIBLE);
        }
        else {
            findViewById(R.id.imageViewLightOn).setVisibility(View.INVISIBLE);
            findViewById(R.id.imageViewLightOff).setVisibility(View.VISIBLE);
        }

        // Pareil pour le son
        if (context.getNoiseStatus().equals("ON")) {
            findViewById(R.id.imageViewRingerOn).setVisibility(View.VISIBLE);
            findViewById(R.id.imageViewRingerOff).setVisibility(View.INVISIBLE);
        }
        else {
            findViewById(R.id.imageViewRingerOn).setVisibility(View.INVISIBLE);
            findViewById(R.id.imageViewRingerOff).setVisibility(View.VISIBLE);
        }

        // On affiche les valeurs numériques, on caste bizarrement psk ça permet d'avoir une espace après les deux points
        // le textView est ici obligé pour utiliser setText apparemment
        ((TextView) findViewById(R.id.textViewLightValue)).setText(" " + context.getLight());
        ((TextView) findViewById(R.id.textViewNoiseValue)).setText(" " + context.getNoise());

    }


    // Functions calling results from HTTP Requests
    protected void retrieveRoomContextState(String room){
        RoomContextHttpManager.retrieveRoomContextState(room, this);
    }

    protected void switchLight(RoomContextState state, String room){
        RoomContextHttpManager.switchLight(this, state, room);
    }

    protected void switchRinger(RoomContextState state, String room){
        RoomContextHttpManager.switchRinger(this, state, room);
    }

    // Function in order to change activity
    public void building(View view) {
        Intent intent = new Intent(this, Building.class);
        startActivity(intent);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/
}
