package com.armend.android.oxygen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.armend.android.oxygen.offline.MainActivityOff;

import info.hoang8f.widget.FButton;


/**
 * Created by AH on 3/20/2015.
 */
public class StartGameActivity extends Activity{

    private FButton mLuajOnline;
    private FButton mOffline;
    private SharedPreferences myusername;
    public static final String PREF = "MyPrefs" ;
    public static final String USER = "user" ;
    private String testUser;
    private TextView mWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        mLuajOnline = (FButton)findViewById(R.id.online_butt);

        mWelcome= (TextView) findViewById(R.id.text_welcome);

        mOffline = (FButton) findViewById(R.id.offline_butt);

        myusername = getSharedPreferences(PREF, 0);

        testUser = myusername.getString(USER, null);

        if(testUser != null){
            Strings.myusername = testUser;
            mWelcome.setText("Mirë se vjen, \n"+ Strings.myusername);
            mWelcome.setVisibility(View.VISIBLE);
        }




        mOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent off = new Intent(getApplicationContext(), MainActivityOff.class);
                startActivity(off);
            }
        });


        mLuajOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent onl = new Intent(getApplicationContext(), StarGameOnline.class);
                startActivity(onl);
            }
        });


    }





}
