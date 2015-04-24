package com.armend.android.oxygen;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.devspark.appmsg.AppMsg;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;


public class TabB extends Fragment implements View.OnClickListener {



    public static Button  mButtonB1, mButtonB2, mButtonB3, mButtonB4;
    public static EditText mButtonB;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tabb,container,false);

        mButtonB = (EditText)v.findViewById(R.id.b);
        mButtonB1 = (Button)v.findViewById(R.id.b1);
        mButtonB2 = (Button)v.findViewById(R.id.b2);
        mButtonB3 = (Button)v.findViewById(R.id.b3);
        mButtonB4 = (Button)v.findViewById(R.id.b4);



        mButtonB.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(Strings.editTextOn) {
                if (actionId == IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mButtonB.getWindowToken(), 0);
                    mButtonB.clearFocus();

                    if (MainActivity.compareStr(mButtonB.getText().toString(), Strings.b)) {

                        MainActivity.setLastMove("B");

                        AppMsg.makeText(getActivity(), "Correct", AppMsg.STYLE_INFO).setLayoutGravity(Gravity.BOTTOM).show();
                        Strings.lastmove2 = "B";

                    } else {

                        AppMsg.makeText(getActivity(), "Incorrect", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.BOTTOM).show();
                        Strings.lastmove2 = "No move yet";
                    }

                    Strings.turn = Strings.opponent;

                    MainActivity.countDownGive.cancel();
                    MainActivity.mCountDown.setText("");
                    ServerFunctions.giveTurn(Strings.opponent);

                    handled = true;


                }
            }else{
                hideDefaultKeyboard(mButtonB);
                mButtonB.clearFocus();
                AppMsg.makeText(getActivity(), "Not your turn", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.BOTTOM).show();
            }
                return handled;
            }
        });

        mButtonB1.setOnClickListener(this);
        mButtonB2.setOnClickListener(this);
        mButtonB3.setOnClickListener(this);
        mButtonB4.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if(Strings.buttonsOn) {
            switch (v.getId()) {


                case R.id.b1:
                    // do code b1
                    mButtonB1.setText(Strings.b1);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "B1";
                    break;


                case R.id.b2:
                    // do code b2
                    mButtonB2.setText(Strings.b2);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "B2";
                    break;


                case R.id.b3:
                    // do code b3
                    mButtonB3.setText(Strings.b3);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "B3";
                    break;


                case R.id.b4:
                    // do code b4
                    mButtonB4.setText(Strings.b4);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "B4";
                    break;

            }
        }

    }
    private void hideDefaultKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);

    }

}