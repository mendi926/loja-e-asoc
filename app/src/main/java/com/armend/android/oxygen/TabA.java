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


public class TabA extends Fragment implements View.OnClickListener {

    public static Button  mButtonA1, mButtonA2, mButtonA3, mButtonA4;
    public static EditText mButtonA;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.taba,container,false);

        mButtonA = (EditText)v.findViewById(R.id.a);
        mButtonA1 = (Button)v.findViewById(R.id.a1);
        mButtonA2 = (Button)v.findViewById(R.id.a2);
        mButtonA3 = (Button)v.findViewById(R.id.a3);
        mButtonA4 = (Button)v.findViewById(R.id.a4);



        mButtonA.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(Strings.editTextOn) {
                if (actionId == IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mButtonA.getWindowToken(), 0);
                    mButtonA.clearFocus();

                    if (MainActivity.compareStr(mButtonA.getText().toString(), Strings.a)) {

                        MainActivity.setLastMove("A");

                        AppMsg.makeText(getActivity(), "Correct", AppMsg.STYLE_INFO).setLayoutGravity(Gravity.BOTTOM).show();

                        Strings.lastmove2 = "A";

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
                hideDefaultKeyboard(mButtonA);
                mButtonA.clearFocus();
                AppMsg.makeText(getActivity(), "Not your turn", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.BOTTOM).show();
            }
                return handled;
            }
        });

        mButtonA1.setOnClickListener(this);
        mButtonA2.setOnClickListener(this);
        mButtonA3.setOnClickListener(this);
        mButtonA4.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if(Strings.buttonsOn) {
            switch (v.getId()) {
                case R.id.a1:
                    //do code a1

                    mButtonA1.setText(Strings.a1);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "A1";
                    break;
                case R.id.a2:

                    //do code a2
                    mButtonA2.setText(Strings.a2);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "A2";

                    break;
                case R.id.a3:
                    // do code a3

                    mButtonA3.setText(Strings.a3);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "A3";

                    break;
                case R.id.a4:
                    // do code a4

                    mButtonA4.setText(Strings.a4);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "A4";

                    break;
            }
        }

    }

    private void hideDefaultKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);

    }

}