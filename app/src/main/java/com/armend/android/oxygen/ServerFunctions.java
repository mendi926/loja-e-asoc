package com.armend.android.oxygen;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


public class ServerFunctions {
    private static final String TAG = ServerFunctions.class.getSimpleName();


    public static void setTurn(){


      if(Strings.turn.equalsIgnoreCase(Strings.myusername)){
          // do code
          Strings.buttonsOn = true;
          Strings.editTextOn = true;
          if(!Strings.gamefinished){
              MainActivity.countDownGive.start();
          }
      }

        else if (Strings.turn.equalsIgnoreCase(Strings.opponent)){
          // do code
          Strings.buttonsOn = false;
          Strings.editTextOn = false;

          if(!Strings.gamefinished){
              MainActivity.countDownCheck.start();
          }

          ServerFunctions.checkTurn();

      }

    }

    public static void setOpponent(){
        if(Strings.myusername.equalsIgnoreCase(Strings.user1)){
            Strings.opponent = Strings.user2;
        }
        else {
            Strings.opponent = Strings.user1;
        }
    }

    public static void giveTurn(final String opponent) {
        // Tag used to cancel the request
        String tag_string_req = "req_changeturn";


        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());



                    if(Strings.gamefinished==false){
                        Log.d("ServerFunctions", "this is !Strings.gamefinished");
                        MainActivity.updateToolbar();
                        ServerFunctions.setTurn();
                    }
                    else{
                        if(!Strings.gameinterrupted) {
                            Log.d("ServerFunctions", "this is !Strings.gameinterrupted");
                            MainActivity.mRadha.setText("Loja mbaroi.");
                            MainActivity.mLevizja.setVisibility(View.GONE);
                            MainActivity.mTurn.setText("Kthehu prapa për të luajtur prapë.");
                            MainActivity.mLastMove.setVisibility(View.GONE);
                        }

                    }

                Log.d(TAG, "Strings.gamefinished: " + Strings.gamefinished);
                Log.d(TAG, "Strings.gameinterrupted: " + Strings.gamefinished);


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());

                Strings.editTextOn = false;

                MainActivity.handler.postDelayed(MainActivity.giveturn, 6000);

            }


        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "changeturn");
                params.put("idroom", Strings.idroom);
                params.put("turn", opponent);
                params.put("lastmove",Strings.lastmove);
                params.put("lastmove2", Strings.lastmove2);

                return params;
            }



        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }

    public static void checkTurn() {
        // Tag used to cancel the request
        String tag_string_req = "req_checkturn";


        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    String turn = jObj.getString("turn");

                    if(turn.equalsIgnoreCase(Strings.myusername)){
                        MainActivity.countDownCheck.cancel();
                        Strings.turn = Strings.myusername;
                        Strings.lastmove = jObj.getString("lastmove");
                        Strings.lastmove2 = jObj.getString("lastmove2");

                        if(!Strings.lastmove2.equalsIgnoreCase("rez")){
                            MainActivity.updateToolbar();

                        }else {

                            MainActivity.mRadha.setText("Kundërshtari ka gjetur zgjidhjen.");
                            MainActivity.mLevizja.setVisibility(View.GONE);
                            MainActivity.mTurn.setText("Loja mbaroi. Kthehu prapa për të luajtur prapë.");
                            MainActivity.mLastMove.setVisibility(View.GONE);
                            Strings.gamefinished = true;

                        }
                        ServerFunctions.setLastMove(Strings.lastmove.toUpperCase());
                        ServerFunctions.setLastMove(Strings.lastmove2.toUpperCase());

                        ServerFunctions.setTurn();


                    } else {

                        //ServerFunctions.checkTurn();

                        MainActivity.handler.postDelayed(MainActivity.checkturn, 6000);

                    }

                    // do stuff on response

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());

                MainActivity.handler.postDelayed(MainActivity.checkturn, 6000);
            }


        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "checkturn");
                params.put("idroom", Strings.idroom);
                return params;
            }



        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }

    public static void setLastMove(String thelastmove){

        switch(thelastmove){

            case "A": TabA.mButtonA.setText(Strings.a);
                      TabRez.mButtonAA.setText(Strings.a);
                        TabA.mButtonA.setFocusable(false);

                ServerFunctions.setLastMove("A1");
                ServerFunctions.setLastMove("A2");
                ServerFunctions.setLastMove("A3");
                ServerFunctions.setLastMove("A4");
                        break;
            case "A1": TabA.mButtonA1.setText(Strings.a1);
                        TabA.mButtonA1.setClickable(false);
                break;
            case "A2": TabA.mButtonA2.setText(Strings.a2);
                        TabA.mButtonA.setClickable(false);
                break;
            case "A3": TabA.mButtonA3.setText(Strings.a3);
                        TabA.mButtonA3.setClickable(false);
                break;
            case "A4": TabA.mButtonA4.setText(Strings.a4);
                        TabA.mButtonA4.setClickable(false);
                break;

            case "B": TabB.mButtonB.setText(Strings.b);
                        TabB.mButtonB.setFocusable(false);
                        TabRez.mButtonBB.setText(Strings.b);


                ServerFunctions.setLastMove("B1");
                ServerFunctions.setLastMove("B2");
                ServerFunctions.setLastMove("B3");
                ServerFunctions.setLastMove("B4");
                break;

            case "B1": TabB.mButtonB1.setText(Strings.b1);
                        TabB.mButtonB1.setClickable(false);
                break;
            case "B2": TabB.mButtonB2.setText(Strings.b2);
                        TabB.mButtonB2.setClickable(false);
                break;
            case "B3": TabB.mButtonB3.setText(Strings.b3);
                        TabB.mButtonB3.setClickable(false);
                break;
            case "B4": TabB.mButtonB4.setText(Strings.b4);
                        TabB.mButtonB4.setClickable(false);
                break;

            case "C": TabC.mButtonC.setText(Strings.c);
                        TabRez.mButtonCC.setText(Strings.c);
                        TabC.mButtonC.setFocusable(false);


                ServerFunctions.setLastMove("C1");
                ServerFunctions.setLastMove("C2");
                ServerFunctions.setLastMove("C3");
                ServerFunctions.setLastMove("C4");
                break;

            case "C1": TabC.mButtonC1.setText(Strings.c1);
                        TabC.mButtonC1.setClickable(false);
                break;

            case "C2": TabC.mButtonC2.setText(Strings.c2);
                        TabC.mButtonC2.setClickable(false);
                break;

            case "C3": TabC.mButtonC3.setText(Strings.c3);
                        TabC.mButtonC3.setClickable(false);
                break;

            case "C4": TabC.mButtonC4.setText(Strings.c4);
                        TabC.mButtonC4.setClickable(false);
                break;


            case "D": TabD.mButtonD.setText(Strings.d);
                        TabRez.mButtonDD.setText(Strings.d);
                        TabD.mButtonD.setFocusable(false);


                ServerFunctions.setLastMove("D1");
                ServerFunctions.setLastMove("D2");
                ServerFunctions.setLastMove("D3");
                ServerFunctions.setLastMove("D4");

                break;

            case "D1": TabD.mButtonD1.setText(Strings.d1);
                        TabD.mButtonD2.setClickable(false);
                break;

            case "D2": TabD.mButtonD2.setText(Strings.d2);
                        TabD.mButtonD2.setClickable(false);
                break;

            case "D3": TabD.mButtonD3.setText(Strings.d3);
                        TabD.mButtonD3.setClickable(false);
                break;

            case "D4": TabD.mButtonD4.setText(Strings.d4);
                        TabD.mButtonD4.setClickable(false);
                break;

            case "REZ": TabRez.mButtonRez.setText(Strings.rez);
                        TabRez.mButtonRez.setFocusable(false);

                ServerFunctions.setLastMove("A");
                ServerFunctions.setLastMove("A1");
                ServerFunctions.setLastMove("A2");
                ServerFunctions.setLastMove("A3");
                ServerFunctions.setLastMove("A4");
                ServerFunctions.setLastMove("B");
                ServerFunctions.setLastMove("B1");
                ServerFunctions.setLastMove("B2");
                ServerFunctions.setLastMove("B3");
                ServerFunctions.setLastMove("B4");
                ServerFunctions.setLastMove("C");
                ServerFunctions.setLastMove("C1");
                ServerFunctions.setLastMove("C2");
                ServerFunctions.setLastMove("C3");
                ServerFunctions.setLastMove("C4");
                ServerFunctions.setLastMove("D");
                ServerFunctions.setLastMove("D1");
                ServerFunctions.setLastMove("D2");
                ServerFunctions.setLastMove("D3");
                ServerFunctions.setLastMove("D4");


                //Strings.gamefinished = true;
                break;

            default:
                break;


        }

    }



}