package com.example.valentin.capteurmajeure;


import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Valentin on 13/12/2017.
 * Allows to make HTTP requests, gives the results of the request to the context.
 */

public class RoomContextHttpManager {

    private static  RequestQueue queue = null ;
    public String room ;


    public static void retrieveRoomContextState(String room, final ContextManagementActivity contextManagementActivity){

        String url = "https://mighty-plains-77473.herokuapp.com/api/rooms/" + room + "/content/";

        // on crée une queue si elle n'exite pas déjà pour pouvoir communiquer et on récupère le contexte de l'activity
        if(queue == null){
            queue = Volley.newRequestQueue(contextManagementActivity.getApplicationContext());
            queue.start();
        }

        //get room sensed context
        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("id");
                            // the example was : int lightLevel = Integer.parseInt(response.getJSONObject("light").get("level").toString());
                            // but this seems better :D
                            int lightLevel = response.getJSONObject("light").getInt("level");
                            String lightStatus = response.getJSONObject("light").getString("status");
                            int noiseLevel = response.getJSONObject("noise").getInt("level");
                            String noiseStatus = response.getJSONObject("noise").getString("status");

                            RoomContextState roomContextState = new RoomContextState(id, lightStatus, lightLevel, noiseStatus, noiseLevel);
                            contextManagementActivity.onUpdate(roomContextState);

                            // notify main activity for update

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(contextManagementActivity.getApplicationContext(), "ERROR accessing URL\nRoom may not exist\nInternet connection might be disabled", duration);
                        toast.show();
                    }
                });
        queue.add(contextRequest);
    };

    public static void switchLight(final ContextManagementActivity contextManagementActivity, final RoomContextState state, String room) {

        String url = "https://mighty-plains-77473.herokuapp.com/api/rooms/" + room + "/switchLightl/";

        /* No need to create the queue because the switch button is disabled if retrieveRoomContextState hasn't already been called
        if(queue == null){
            queue = Volley.newRequestQueue(contextManagementActivity.getApplicationContext());
            queue.start();
        }
        */

        // POST request, actualisation of context and getting of the response
        // You are supposed to use check button before switch button, otherwise, display could be wrong.
        StringRequest contextRequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                            if (state.getLightStatus().equals("ON")){
                                state.setLight("OFF");
                            }
                            else{
                                state.setLight("ON");
                            }
                            contextManagementActivity.onUpdate(state);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(contextManagementActivity.getApplicationContext(), "Unknown error\nHeroku?\nInternet?", duration);
                        toast.show();
                        // Some error to access URL : Room may not exists...
                    }
                });
        queue.add(contextRequest);
    };


    public static void switchRinger(final ContextManagementActivity contextManagementActivity, final RoomContextState state, String room) {

        String url = "https://mighty-plains-77473.herokuapp.com/api/rooms/" + room + "/switchRingerl/";

        // POST request, actualisation of context and getting of the response
        // You are supposed to use check button before switch button, otherwise, display could be wrong.
        StringRequest contextRequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (state.getNoiseStatus().equals("ON")){
                            state.setNoise("OFF");
                        }
                        else{
                            state.setNoise("ON");
                        }
                        contextManagementActivity.onUpdate(state);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(contextManagementActivity.getApplicationContext(), "Unknown error\nHeroku?\nInternet?", duration);
                        toast.show();
                        // Some error to access URL : Room may not exists...
                    }
                });
        queue.add(contextRequest);
    };
}
