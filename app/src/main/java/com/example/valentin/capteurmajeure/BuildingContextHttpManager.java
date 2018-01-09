package com.example.valentin.capteurmajeure;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Valentin on 06/01/2018.
 */

public class BuildingContextHttpManager {

    private static RequestQueue queue = null ;
    private static int count = 0 ;
    public String building ;

    public static void retrieveBuildingContextState(String building, final Building buildingManagementActivity){

        String url = "https://mighty-plains-77473.herokuapp.com/api/buildings/" + building + "/content/";

        // on crée une queue si elle n'exite pas déjà pour pouvoir communiquer et on récupère le contexte de l'activity
        if(queue == null){
            queue = Volley.newRequestQueue(buildingManagementActivity.getApplicationContext());
            queue.start();
        }

        //get building sensed context
        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String nom = response.getString("nom");
                            //List<RoomContextState> rooms = response.getJSONObject("rooms");
                            count = response.getJSONArray("rooms").length();

                            BuildingContextState buildingContextState = new BuildingContextState(nom, count);

                            buildingManagementActivity.onUpdate(buildingContextState);

                            // notify main activity for update

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(buildingManagementActivity.getApplicationContext(), "ERROR accessing URL\nInternet?", duration);
                        toast.show();
                    }
                });
        queue.add(contextRequest);
    };

    // still not working
    public static void switchHue(final Building contextActivity, final BuildingContextState state, String room) {

        String url = "'http://192.168.220.52/api/FZT80cdHuI7jWvfubWNkGX9u18IC0QG-qjOXsI73/lights/1/state";

        // create the queue
        if(queue == null){
            queue = Volley.newRequestQueue(contextActivity.getApplicationContext());
            queue.start();
        }

        JsonObjectRequest request = null;
        JSONObject json = new JSONObject();
        try {
            json.put("on", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }


            request = new JsonObjectRequest(
            Request.Method.POST, url, json,
            new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                //...
            }
        },
        new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(contextActivity.getApplicationContext(), "Unknown error", duration);
                toast.show();
                // Some error to access URL
            }
        })
        {
            /*@Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("\"on\"", "false");// something to do here ??
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("\"on\"", "false");// "\"on\"", "false" or "{\"on\" : false}", "false" ??
                return params;
            }*/
        };

        queue.add(request);
    };
}
