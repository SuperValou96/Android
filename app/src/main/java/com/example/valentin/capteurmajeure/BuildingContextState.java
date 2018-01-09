package com.example.valentin.capteurmajeure;

import java.util.List;

/**
 * Created by Valentin on 06/01/2018.
 * Pour l'instant, un building est juste un nom et son nombre de rooms mais je trouve déjà ça plutôt cool ! :D
 */

public class BuildingContextState {

    private String nom;
    private int nbrooms;
    //private List<RoomContextState> rooms;

    public BuildingContextState(String nom, int nbrooms){//, List<RoomContextState> rooms) {
        super();
        //this.rooms = rooms;
        this.nom = nom;
        this.nbrooms = nbrooms;
    }

    public String getNom() { return nom;}
    public void setNom(String nom) { this.nom = nom;}
    public int getNbrooms() { return nbrooms;}

    //public List<RoomContextState> getRooms() { return rooms;}
    //public void setRooms(List<RoomContextState> rooms) { this.rooms = rooms;}

}
