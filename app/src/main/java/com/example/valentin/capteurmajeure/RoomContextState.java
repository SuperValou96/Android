package com.example.valentin.capteurmajeure;

/**
 * Created by Valentin on 04/11/2017.
 */

public class RoomContextState {

    private String room;
    private String lightstatus;
    private String noisestatus;
    private int light;
    private int noise;

    public RoomContextState(String room, String lightstatus, int light, String noisestatus, int noise) {
        super();
        this.room = room;
        this.lightstatus = lightstatus;
        this.light = light;
        this.noisestatus = noisestatus;
        this.noise = noise;
    }

    public String getRoom() {
        return this.room;
    }

    public String getLightStatus() {
        return this.lightstatus;
    }

    public int getLight() {
        return this.light;
    }

    public String getNoiseStatus() {
        return this.noisestatus;
    }

    public int getNoise() {
        return this.noise;
    }

    public void setLight(String lightstatus){ this.lightstatus = lightstatus;}

    public void setNoise(String noisestatus) { this.noisestatus = noisestatus;}
}