package com.armend.android.oxygen.offline;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.armend.android.oxygen.MyDatabase;
import com.armend.android.oxygen.R;
import com.armend.android.oxygen.SlidingTabLayout;
import info.hoang8f.widget.FButton;

public class MainActivityOff extends ActionBarActivity {

// Declaring Your View and Variables

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapterOff adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Z","A", "B", "C", "D"};
    int NumbOfTabs =5;
    public static TextView mRound;
    public static TextView mPoints;
    public static FButton mNext;
    public static FButton mPrev;
    public static FButton mDots;
    public static int round;
    public static int points;
    public static String  a1, a2, a3, a4, a, b1, b2, b3, b4, b, c1, c2, c3, c4, c, d1, d2, d3, d4, d, rez;
    public static Resources res;
    private MyDatabase db;

    private NumberPicker mNumPicker;
    private AlertDialog mAlertDiag;
    private ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);



// Creating The Toolbar and setting it as the Toolbar for the activity

        points = 200 ;
        round = 1;

        db = new MyDatabase(this);

        getQuestions(round);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mRound= (TextView)findViewById(R.id.round);
        mPoints= (TextView)findViewById(R.id.points);
        mRound.setText(Integer.toString(round));
        mPoints.setText(Integer.toString(points));


        mPrev = (FButton)findViewById(R.id.prev);
        mNext = (FButton)findViewById(R.id.next);
        mDots = (FButton)findViewById(R.id.selectquest);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivityOff.round != 32){
                MainActivityOff.round = MainActivityOff.round + 1;
                MainActivityOff.points = 200;
                resetAll();
                resetFocus();
                getQuestions(MainActivityOff.round);
                MainActivityOff.updateToolbar();
                }
            }
        });

        mPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivityOff.round != 1){
                    MainActivityOff.round= MainActivityOff.round - 1;
                    MainActivityOff.points = 200;
                    resetAll();
                    resetFocus();
                    getQuestions(MainActivityOff.round);
                    MainActivityOff.updateToolbar();
                }

            }
        });

        mDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v1 = inflater.inflate(R.layout.numberpicker, null);
                mNumPicker = (NumberPicker) v1.findViewById(R.id.number);

                mNumPicker.setMaxValue(32);
                mNumPicker.setMinValue(1);
                mNumPicker.setValue(round);
                mNumPicker.setWrapSelectorWheel(false);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityOff.this);

                builder.setView(v1);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivityOff.round= mNumPicker.getValue();
                        MainActivityOff.points = 200;
                        resetAll();
                        resetFocus();
                        getQuestions(MainActivityOff.round);
                        MainActivityOff.updateToolbar();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDiag.dismiss();

                    }
                });

                mAlertDiag = builder.create();
                mAlertDiag.show();
            }
        });


// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapterOff(getSupportFragmentManager(),Titles,NumbOfTabs);

// Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(5);
        pager.setAdapter(adapter);

// Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

// Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

// Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MAINACTIVITYOFF LOG:", "This is onDestroy()");
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.zgjidhja_a:
                setLastMove("A");
                MainActivityOff.points-=20;
                MainActivityOff.updateToolbar();
                return true;
            case R.id.zgjidhja_b:
                setLastMove("B");
                MainActivityOff.points-=20;
                MainActivityOff.updateToolbar();
                return true;
            case R.id.zgjidhja_c:
                setLastMove("C");
                MainActivityOff.points-=20;
                MainActivityOff.updateToolbar();
                return true;
            case R.id.zgjidhja_d:
                setLastMove("D");
                MainActivityOff.points-=20;
                MainActivityOff.updateToolbar();
                return true;
            case R.id.zgjidhja_rez:
                setLastMove("Rez");
                MainActivityOff.points-=20;
                MainActivityOff.updateToolbar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static void updateToolbar(){
        mRound.setText(Integer.toString(round));
        mPoints.setText(Integer.toString(points));
    }


    private void getQuestions(int number){


        Cursor questions = db.getQuestions(number);


        a = questions.getString(1).toUpperCase();
        a1 = questions.getString(2);
        a2 = questions.getString(3);
        a3 = questions.getString(4);
        a4 = questions.getString(5);

        b = questions.getString(6).toUpperCase();
        b1 = questions.getString(7);
        b2 = questions.getString(8);
        b3 = questions.getString(9);
        b4 = questions.getString(10);

        c = questions.getString(11).toUpperCase();
        c1 = questions.getString(12);
        c2 = questions.getString(13);
        c3 = questions.getString(14);
        c4 = questions.getString(15);

        d = questions.getString(16).toUpperCase();
        d1 = questions.getString(17);
        d2 = questions.getString(18);
        d3 = questions.getString(19);
        d4 = questions.getString(20);


        rez = questions.getString(21).toUpperCase();

        questions.close();

    }

    public static void setLastMove(String thelastmove){

        switch(thelastmove){

            case "A": TabAOff.mButtonA.setText(MainActivityOff.a);
                TabRezOff.mButtonAA.setText(MainActivityOff.a);
                TabAOff.mButtonA.setFocusable(false);
                TabAOff.mButtonA1.setText(MainActivityOff.a1);
                TabAOff.mButtonA1.setClickable(false);
                TabAOff.mButtonA2.setText(MainActivityOff.a2);
                TabAOff.mButtonA2.setClickable(false);
                TabAOff.mButtonA3.setText(MainActivityOff.a3);
                TabAOff.mButtonA3.setClickable(false);
                TabAOff.mButtonA4.setText(MainActivityOff.a4);
                TabAOff.mButtonA4.setClickable(false);
                break;


            case "B": TabBOff.mButtonB.setText(MainActivityOff.b);
                TabBOff.mButtonB.setFocusable(false);
                TabRezOff.mButtonBB.setText(MainActivityOff.b);

                TabBOff.mButtonB1.setText(MainActivityOff.b1);
                TabBOff.mButtonB1.setClickable(false);
                TabBOff.mButtonB2.setText(MainActivityOff.b2);
                TabBOff.mButtonB2.setClickable(false);
                TabBOff.mButtonB3.setText(MainActivityOff.b3);
                TabBOff.mButtonB3.setClickable(false);
                TabBOff.mButtonB4.setText(MainActivityOff.b4);
                TabBOff.mButtonB4.setClickable(false);
                break;


            case "C": TabCOff.mButtonC.setText(MainActivityOff.c);
                TabRezOff.mButtonCC.setText(MainActivityOff.c);
                TabCOff.mButtonC.setFocusable(false);

                TabCOff.mButtonC1.setText(MainActivityOff.c1);
                TabCOff.mButtonC1.setClickable(false);
                TabCOff.mButtonC2.setText(MainActivityOff.c2);
                TabCOff.mButtonC2.setClickable(false);
                TabCOff.mButtonC3.setText(MainActivityOff.c3);
                TabCOff.mButtonC3.setClickable(false);
                TabCOff.mButtonC4.setText(MainActivityOff.c4);
                TabCOff.mButtonC4.setClickable(false);

                break;



            case "D": TabDOff.mButtonD.setText(MainActivityOff.d);
                TabRezOff.mButtonDD.setText(MainActivityOff.d);
                TabDOff.mButtonD.setFocusable(false);

                TabDOff.mButtonD1.setText(MainActivityOff.d1);
                TabDOff.mButtonD1.setClickable(false);
                TabDOff.mButtonD2.setText(MainActivityOff.d2);
                TabDOff.mButtonD2.setClickable(false);
                TabDOff.mButtonD3.setText(MainActivityOff.d3);
                TabDOff.mButtonD3.setClickable(false);
                TabDOff.mButtonD4.setText(MainActivityOff.d4);
                TabDOff.mButtonD4.setClickable(false);

                break;


            case "Rez": TabRezOff.mButtonRez.setText(MainActivityOff.rez);
                TabRezOff.mButtonRez.setFocusable(false);
                TabAOff.mButtonA1.setText(MainActivityOff.a1);
                TabAOff.mButtonA1.setClickable(false);
                TabAOff.mButtonA2.setText(MainActivityOff.a2);
                TabAOff.mButtonA2.setClickable(false);
                TabAOff.mButtonA3.setText(MainActivityOff.a3);
                TabAOff.mButtonA3.setClickable(false);
                TabAOff.mButtonA4.setText(MainActivityOff.a4);
                TabAOff.mButtonA4.setClickable(false);
                TabBOff.mButtonB1.setText(MainActivityOff.b1);
                TabBOff.mButtonB1.setClickable(false);
                TabBOff.mButtonB2.setText(MainActivityOff.b2);
                TabBOff.mButtonB2.setClickable(false);
                TabBOff.mButtonB3.setText(MainActivityOff.b3);
                TabBOff.mButtonB3.setClickable(false);
                TabBOff.mButtonB4.setText(MainActivityOff.b4);
                TabBOff.mButtonB4.setClickable(false);
                TabCOff.mButtonC1.setText(MainActivityOff.c1);
                TabCOff.mButtonC1.setClickable(false);
                TabCOff.mButtonC2.setText(MainActivityOff.c2);
                TabCOff.mButtonC2.setClickable(false);
                TabCOff.mButtonC3.setText(MainActivityOff.c3);
                TabCOff.mButtonC3.setClickable(false);
                TabCOff.mButtonC4.setText(MainActivityOff.c4);
                TabCOff.mButtonC4.setClickable(false);
                TabDOff.mButtonD1.setText(MainActivityOff.d1);
                TabDOff.mButtonD1.setClickable(false);
                TabDOff.mButtonD2.setText(MainActivityOff.d2);
                TabDOff.mButtonD2.setClickable(false);
                TabDOff.mButtonD3.setText(MainActivityOff.d3);
                TabDOff.mButtonD3.setClickable(false);
                TabDOff.mButtonD4.setText(MainActivityOff.d4);
                TabDOff.mButtonD4.setClickable(false);

                MainActivityOff.setLastMove("A");
                MainActivityOff.setLastMove("B");
                MainActivityOff.setLastMove("C");
                MainActivityOff.setLastMove("D");






                //MainActivityOff.gamefinished = true;
                break;

            default: break;


        }

    }

    private void resetAll(){
        TabAOff.mButtonA1.setText("A1");
        TabAOff.mButtonA2.setText("A2");
        TabAOff.mButtonA3.setText("A3");
        TabAOff.mButtonA4.setText("A4");
        TabBOff.mButtonB1.setText("B1");
        TabBOff.mButtonB2.setText("B2");
        TabBOff.mButtonB3.setText("B3");
        TabBOff.mButtonB4.setText("B4");
        TabCOff.mButtonC1.setText("C1");
        TabCOff.mButtonC2.setText("C2");
        TabCOff.mButtonC3.setText("C3");
        TabCOff.mButtonC4.setText("C4");
        TabDOff.mButtonD1.setText("D1");
        TabDOff.mButtonD2.setText("D2");
        TabDOff.mButtonD3.setText("D3");
        TabDOff.mButtonD4.setText("D4");
        TabDOff.mButtonD.setText("");
        TabCOff.mButtonC.setText("");
        TabBOff.mButtonB.setText("");
        TabAOff.mButtonA.setText("");
        TabRezOff.mButtonRez.setText("");

        TabAOff.mButtonA1.setClickable(true);
        TabAOff.mButtonA2.setClickable(true);
        TabAOff.mButtonA3.setClickable(true);
        TabAOff.mButtonA4.setClickable(true);
        TabBOff.mButtonB1.setClickable(true);
        TabBOff.mButtonB2.setClickable(true);
        TabBOff.mButtonB3.setClickable(true);
        TabBOff.mButtonB4.setClickable(true);
        TabCOff.mButtonC1.setClickable(true);
        TabCOff.mButtonC2.setClickable(true);
        TabCOff.mButtonC3.setClickable(true);
        TabCOff.mButtonC4.setClickable(true);
        TabDOff.mButtonD1.setClickable(true);
        TabDOff.mButtonD2.setClickable(true);
        TabDOff.mButtonD3.setClickable(true);
        TabDOff.mButtonD4.setClickable(true);


        TabRezOff.mButtonDD.setText("D");
        TabRezOff.mButtonCC.setText("C");
        TabRezOff.mButtonBB.setText("B");
        TabRezOff.mButtonAA.setText("A");

    }

    private void resetFocus(){

        TabDOff.mButtonD.setFocusable(true);
        TabDOff.mButtonD.setFocusableInTouchMode(true);
        TabCOff.mButtonC.setFocusable(true);
        TabCOff.mButtonC.setFocusableInTouchMode(true);
        TabBOff.mButtonB.setFocusable(true);
        TabBOff.mButtonB.setFocusableInTouchMode(true);
        TabAOff.mButtonA.setFocusable(true);
        TabAOff.mButtonA.setFocusableInTouchMode(true);
        TabRezOff.mButtonRez.setFocusable(true);
        TabRezOff.mButtonRez.setFocusableInTouchMode(true);
    }

    public static void showDiag(Context context, String string){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(string).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });


        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public static boolean compareStr(String string1, String string2){

        string1 = string1.toLowerCase();
        string2 = string2.toLowerCase();

        int str1 = Math.round(string1.length()/2);
        String string1sub = string1.substring(0, str1);
        if(string2.contains(string1sub)){
            return true;
        }
        else{
            return false;
        }

    }




}
