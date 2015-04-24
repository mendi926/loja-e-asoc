package com.armend.android.oxygen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

// Declaring Your View and Variables

    Toolbar toolbaron;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Z","A", "B", "C", "D"};
    int NumbOfTabs =5;
    public static TextView mTurn;
    public static TextView mLastMove;
    public static TextView mRadha;
    public static TextView mLevizja;
    public static CountDownTimer countDownCheck;
    public static CountDownTimer countDownGive;
    public static TextView mCountDown;

    public static Handler handler = new Handler();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

// Creating The Toolbar and setting it as the Toolbar for the activity

        toolbaron = (Toolbar) findViewById(R.id.tool_baron);
        setSupportActionBar(toolbaron);
        toolbaron.setVisibility(View.VISIBLE);

        getSupportActionBar().setDisplayShowTitleEnabled(false);



        mRadha= (TextView)findViewById(R.id.radha);
        mLevizja= (TextView)findViewById(R.id.levizja);
        mCountDown = (TextView)findViewById(R.id.countDown);



        mTurn= (TextView)findViewById(R.id.turnon);
        mLastMove= (TextView)findViewById(R.id.lastmoveon);
        mTurn.setText(Strings.turn);
        mLastMove.setText(Strings.lastmove);

        countDownGive = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                mCountDown.setText(Long.toString(millisUntilFinished / 1000));

            }

            public void onFinish() {
                mCountDown.setText("");
                Strings.turn = Strings.opponent;
                ServerFunctions.giveTurn(Strings.opponent);
                Log.d("MainActivity", "this countDownGive.onFinish");

            }
        };

        countDownCheck = new CountDownTimer(90000, 1000) {

            public void onTick(long millisUntilFinished) {
                mCountDown.setText(Long.toString(millisUntilFinished / 1000));
            }

            public void onFinish() {
                mCountDown.setText("");
                MainActivity.mRadha.setText("Lidhja me kundërshtarin ka humbur.");
                MainActivity.mLevizja.setVisibility(View.GONE);
                MainActivity.mTurn.setText("Loja mbaroi. Kthehu prapa për të luajtur prapë.");
                MainActivity.mLastMove.setVisibility(View.GONE);
                //end checkturn volley requests
                Strings.lastmove = "A1";
                Strings.lastmove2 = "Rez";
                Strings.gameinterrupted = true;
                Strings.gamefinished = true;
                handler.removeCallbacks(checkturn);
                ServerFunctions.giveTurn(Strings.opponent);

                Log.d("MainActivity", "this countDownCheck.onFinish "+"gamefinished:"+Strings.gamefinished+"gameinterrupted: "+Strings.gameinterrupted);

            }

        };


        ServerFunctions.setTurn();



// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),Titles,NumbOfTabs);

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




    public static void updateToolbar(){
        mTurn.setText(Strings.turn);
        if(Strings.lastmove2.equalsIgnoreCase("No move yet")) {
            mLastMove.setText(Strings.lastmove);
        }else {
            mLastMove.setText(Strings.lastmove +", "+Strings.lastmove2);
        }
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



    public static Runnable checkturn = new Runnable() {
        @Override
        public void run() {
      /* do what you need to do */
            ServerFunctions.checkTurn();

        }
    };

    public static Runnable giveturn = new Runnable() {
        @Override
        public void run() {
      /* do what you need to do */
            ServerFunctions.giveTurn(Strings.opponent);

        }
    };


    public static void setLastMove(String thelastmove){

        switch(thelastmove){

            case "A": TabA.mButtonA.setText(Strings.a);
                TabRez.mButtonAA.setText(Strings.a);
                TabA.mButtonA.setFocusable(false);
                TabA.mButtonA1.setText(Strings.a1);
                TabA.mButtonA1.setClickable(false);
                TabA.mButtonA2.setText(Strings.a2);
                TabA.mButtonA2.setClickable(false);
                TabA.mButtonA3.setText(Strings.a3);
                TabA.mButtonA3.setClickable(false);
                TabA.mButtonA4.setText(Strings.a4);
                TabA.mButtonA4.setClickable(false);
                break;


            case "B": TabB.mButtonB.setText(Strings.b);
                TabB.mButtonB.setFocusable(false);
                TabRez.mButtonBB.setText(Strings.b);

                TabB.mButtonB1.setText(Strings.b1);
                TabB.mButtonB1.setClickable(false);
                TabB.mButtonB2.setText(Strings.b2);
                TabB.mButtonB2.setClickable(false);
                TabB.mButtonB3.setText(Strings.b3);
                TabB.mButtonB3.setClickable(false);
                TabB.mButtonB4.setText(Strings.b4);
                TabB.mButtonB4.setClickable(false);
                break;


            case "C": TabC.mButtonC.setText(Strings.c);
                TabRez.mButtonCC.setText(Strings.c);
                TabC.mButtonC.setFocusable(false);

                TabC.mButtonC1.setText(Strings.c1);
                TabC.mButtonC1.setClickable(false);
                TabC.mButtonC2.setText(Strings.c2);
                TabC.mButtonC2.setClickable(false);
                TabC.mButtonC3.setText(Strings.c3);
                TabC.mButtonC3.setClickable(false);
                TabC.mButtonC4.setText(Strings.c4);
                TabC.mButtonC4.setClickable(false);

                break;



            case "D": TabD.mButtonD.setText(Strings.d);
                TabRez.mButtonDD.setText( Strings.d);
                TabD.mButtonD.setFocusable(false);

                TabD.mButtonD1.setText( Strings.d1);
                TabD.mButtonD1.setClickable(false);
                TabD.mButtonD2.setText( Strings.d2);
                TabD.mButtonD2.setClickable(false);
                TabD.mButtonD3.setText( Strings.d3);
                TabD.mButtonD3.setClickable(false);
                TabD.mButtonD4.setText( Strings.d4);
                TabD.mButtonD4.setClickable(false);

                break;


            case "Rez": TabRez.mButtonRez.setText( Strings.rez);
                TabRez.mButtonRez.setFocusable(false);
                TabA.mButtonA1.setText(Strings.a1);
                TabA.mButtonA1.setClickable(false);
                TabA.mButtonA2.setText(Strings.a2);
                TabA.mButtonA2.setClickable(false);
                TabA.mButtonA3.setText(Strings.a3);
                TabA.mButtonA3.setClickable(false);
                TabA.mButtonA4.setText(Strings.a4);
                TabA.mButtonA4.setClickable(false);
                TabB.mButtonB1.setText(Strings.b1);
                TabB.mButtonB1.setClickable(false);
                TabB.mButtonB2.setText(Strings.b2);
                TabB.mButtonB2.setClickable(false);
                TabB.mButtonB3.setText(Strings.b3);
                TabB.mButtonB3.setClickable(false);
                TabB.mButtonB4.setText(Strings.b4);
                TabB.mButtonB4.setClickable(false);
                TabC.mButtonC1.setText(Strings.c1);
                TabC.mButtonC1.setClickable(false);
                TabC.mButtonC2.setText(Strings.c2);
                TabC.mButtonC2.setClickable(false);
                TabC.mButtonC3.setText(Strings.c3);
                TabC.mButtonC3.setClickable(false);
                TabC.mButtonC4.setText(Strings.c4);
                TabC.mButtonC4.setClickable(false);
                TabD.mButtonD1.setText(Strings.d1);
                TabD.mButtonD1.setClickable(false);
                TabD.mButtonD2.setText(Strings.d2);
                TabD.mButtonD2.setClickable(false);
                TabD.mButtonD3.setText(Strings.d3);
                TabD.mButtonD3.setClickable(false);
                TabD.mButtonD4.setText(Strings.d4);
                TabD.mButtonD4.setClickable(false);

                MainActivity.setLastMove("A");
                MainActivity.setLastMove("B");
                MainActivity.setLastMove("C");
                MainActivity.setLastMove("D");






                //MainActivity.gamefinished = true;
                break;

            default: break;


        }

    }



}
