package com.armend.android.oxygen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.armend.android.oxygen.offline.MainActivityOff;
import com.devspark.appmsg.AppMsg;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import info.hoang8f.widget.FButton;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;


/**
 * Created by AH on 3/20/2015.
 */
public class StarGameOnline extends Activity{

    private static final String TAG = StarGameOnline.class.getSimpleName();
    private EditText mInputName;
    private EditText mRoomCode;

    private RadioButton mRadioRan;
    private RadioButton mRadioCak;

    private FButton mLuaj;  //mOnline
    private TextView mTextProgress;
    private SmoothProgressBar mProgressBar;
    private SharedPreferences myusername;
    public static final String PREF = "MyPrefs" ;
    public static final String USER = "user" ;
    private String testUser;
    private RadioGroup radioGroup;
    private String mCode;
    private boolean mSpecific;
    private int questrandom;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startonline);

        mInputName = (EditText)findViewById(R.id.name_input);
        mRoomCode = (EditText) findViewById(R.id.room_code);

        mRadioRan = (RadioButton) findViewById(R.id.loj_ran);
        mRadioCak = (RadioButton) findViewById(R.id.loj_cak);
        radioGroup = (RadioGroup) findViewById(R.id.loj_radio);



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.loj_ran) {
                    mRoomCode.setVisibility(View.INVISIBLE);
                    mSpecific = false;
                } else {
                    mRoomCode.setVisibility(View.VISIBLE);
                    mSpecific = true;
                }

            }
        });



        mLuaj = (FButton)findViewById(R.id.luaj_butt);
        mTextProgress= (TextView) findViewById(R.id.text_progress);
        mProgressBar = (SmoothProgressBar) findViewById(R.id.progress_bar);

        myusername = getSharedPreferences(PREF, 0);

        testUser = myusername.getString(USER, null);

        if(testUser != null){
            mInputName.setVisibility(View.INVISIBLE);
        }

        mInputName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean handled = false;
                if (actionId == IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mInputName.getWindowToken(), 0);
                    mInputName.clearFocus();
                    if(mInputName.getText().toString().length() > 3) {
                        Strings.myusername = mInputName.getText().toString();
                        SharedPreferences.Editor editor = myusername.edit();
                        editor.putString(USER, Strings.myusername).commit();
                        mInputName.setVisibility(View.INVISIBLE);

                    }else {
                        AppMsg.makeText(StarGameOnline.this, "Emri është i shkurtër. Provo prapë.", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.BOTTOM).show();
                    }


                    handled = true;
                }

                return handled;
            }
        });

        mRoomCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean handled = false;
                if (actionId == IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mRoomCode.getWindowToken(), 0);
                    mRoomCode.clearFocus();
                    if (mRoomCode.getText().toString().length() > 4) {
                        mCode = mRoomCode.getText().toString();
                    } else {
                        AppMsg.makeText(StarGameOnline.this, "Parulla është e shkurtër. Provo prapë.", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.BOTTOM).show();
                    }


                    handled = true;
                }

                return handled;
            }
        });





        mLuaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                questrandom = (int) Math.ceil(Math.random()*32);
                Strings.questionnr = Integer.toString(Math.round(questrandom));

                if(mSpecific) {
                    if(mCode != null && Strings.myusername !=null) {
                        updateProgress("Duke përgatitur lojën");
                        mProgressBar.setVisibility(View.VISIBLE);
                        checkRoomOppo(Strings.myusername, Strings.questionnr, mCode);
                    }else {
                        AppMsg.makeText(StarGameOnline.this, "Parulla ose emri është zbrazët", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.BOTTOM).show();
                    }

                }else{
                    if(Strings.myusername !=null) {
                        updateProgress("Duke përgatitur lojën");
                        mProgressBar.setVisibility(View.VISIBLE);
                        checkRoom(Strings.myusername, Strings.questionnr);
                    }
                    else {
                        AppMsg.makeText(StarGameOnline.this, "Shkruaj emrin tuaj", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.BOTTOM).show();
                    }
                }
            }
        });


    }

    public void updateProgress(String string){

        mTextProgress.setText(string);

    }

    public void startGame(){
        ServerFunctions.setOpponent();
        mProgressBar.setVisibility(View.INVISIBLE);
        updateProgress("");
        Intent game = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(game);
    }

    public void checkRoom(final String myusername, final String questionid) {
        // Tag used to cancel the request
        String tag_string_req = "req_question";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    // User successfully stored in MySQL
                    // Now store the user in sqlite
                    //String id = jObj.getString("id");

                    String status = jObj.getString("roomis");

                    switch(status){

                        case "created": // do code

                            updateProgress("Presim");
                            handler.postDelayed(checkroom, 6000);


                            break;

                        case "joined": case "oppojoined": // do code

                            updateProgress("Loja është gati");

                            Strings.idroom = jObj.getString("id");
                            Strings.user1 = jObj.getString("user1");
                            Strings.user2 = jObj.getString("user2");
                            Strings.turn = jObj.getString("turn");
                            Strings.lastmove = jObj.getString("lastmove");
                            Strings.lastmove2 = jObj.getString("lastmove2");
                            Strings.questionnr = jObj.getString("questionnr");


                            getQuestion(Strings.questionnr);


                            break;

                        case "stillopen": //do code

                            updateProgress("Presim pak");

                            handler.postDelayed(checkroom, 6000);

                            break;

                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());

                updateProgress("Presim veç edhe pak");
                handler.postDelayed(checkroom, 6000);
            }


        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "checkroom");
                params.put("username", myusername);
                params.put("questionnr", questionid);

                return params;
            }



        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }

    public void checkRoomOppo(final String myusername, final String questionid, final String code) {
        // Tag used to cancel the request
        String tag_string_req = "req_question";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    // User successfully stored in MySQL
                    // Now store the user in sqlite
                    //String id = jObj.getString("id");

                    String status = jObj.getString("roomis");

                    switch(status){

                        case "created": // do code

                            updateProgress("Presim");
                            handler.postDelayed(checkroomoppo, 6000);


                            break;

                        case "joined": case "oppojoined": // do code

                            updateProgress("Loja është gati");

                            Strings.idroom = jObj.getString("id");
                            Strings.user1 = jObj.getString("user1");
                            Strings.user2 = jObj.getString("user2");
                            Strings.turn = jObj.getString("turn");
                            Strings.lastmove = jObj.getString("lastmove");
                            Strings.lastmove2 = jObj.getString("lastmove2");
                            Strings.questionnr = jObj.getString("questionnr");


                            getQuestion(Strings.questionnr);


                            break;

                        case "stillopen": //do code

                            updateProgress("Presim pak");

                            handler.postDelayed(checkroomoppo, 6000);

                            break;

                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());

                updateProgress("Presim veç edhe pak");
                handler.postDelayed(checkroomoppo, 6000);
            }


        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "checkroomoppo");
                params.put("username", myusername);
                params.put("questionnr", questionid);
                params.put("code", code);

                return params;
            }



        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }


    public void getQuestion(final String questionid) {
        // Tag used to cancel the request
        String tag_string_req = "req_question";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        //String id = jObj.getString("id");



                        Strings.a = jObj.getString("a").toUpperCase();
                        Strings.a1 = jObj.getString("a1");
                        Strings.a2 = jObj.getString("a2");
                        Strings.a3 = jObj.getString("a3");
                        Strings.a4 = jObj.getString("a4");
                        Strings.b = jObj.getString("b").toUpperCase();
                        Strings.b1 = jObj.getString("b1");
                        Strings.b2 = jObj.getString("b2");
                        Strings.b3 = jObj.getString("b3");
                        Strings.b4 = jObj.getString("b4");
                        Strings.c = jObj.getString("c").toUpperCase();
                        Strings.c1 = jObj.getString("c1");
                        Strings.c2 = jObj.getString("c2");
                        Strings.c3 = jObj.getString("c3");
                        Strings.c4 = jObj.getString("c4");
                        Strings.d = jObj.getString("d").toUpperCase();
                        Strings.d1 = jObj.getString("d1");
                        Strings.d2 = jObj.getString("d2");
                        Strings.d3 = jObj.getString("d3");
                        Strings.d4 = jObj.getString("d4");
                        Strings.id = jObj.getString("id");
                        Strings.rez = jObj.getString("rez").toUpperCase();

                        startGame();




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.e(TAG, "Error: " + error.getMessage());
                updateProgress("Duke shkarkuar pyetjet");
                handler.postDelayed(getquestion, 6000);

            }


        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "question");
                params.put("id", questionid);

                return params;
            }



        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private Runnable checkroom = new Runnable() {
        @Override
        public void run() {
      /* do what you need to do */
            checkRoom(Strings.myusername, Strings.questionnr);

        }
    };

    private Runnable checkroomoppo = new Runnable() {
        @Override
        public void run() {
      /* do what you need to do */
            checkRoomOppo(Strings.myusername, Strings.questionnr, mCode);

        }
    };

    private Runnable getquestion = new Runnable() {
        @Override
        public void run() {
      /* do what you need to do */
            getQuestion(Strings.questionnr);

        }
    };



}
