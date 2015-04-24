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


public class TabD extends Fragment implements View.OnClickListener {

    public static Button mButtonD1, mButtonD2, mButtonD3, mButtonD4;
    public static EditText mButtonD;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tabd,container,false);

        mButtonD = (EditText)v.findViewById(R.id.d);
        mButtonD1 = (Button)v.findViewById(R.id.d1);
        mButtonD2 = (Button)v.findViewById(R.id.d2);
        mButtonD3 = (Button)v.findViewById(R.id.d3);
        mButtonD4 = (Button)v.findViewById(R.id.d4);


            mButtonD.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if(Strings.editTextOn) {
                    if (actionId == IME_ACTION_DONE) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mButtonD.getWindowToken(), 0);
                        mButtonD.clearFocus();


                        if (MainActivity.compareStr(mButtonD.getText().toString(), Strings.d)) {
                            MainActivity.setLastMove("D");
                            AppMsg.makeText(getActivity(), "Correct", AppMsg.STYLE_INFO).setLayoutGravity(Gravity.BOTTOM).show();
                            Strings.lastmove2 = "D";


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
                    hideDefaultKeyboard(mButtonD);
                    mButtonD.clearFocus();
                    AppMsg.makeText(getActivity(), "Not your turn", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.BOTTOM).show();

                }
                    return handled;
                }
            });

        mButtonD1.setOnClickListener(this);
        mButtonD2.setOnClickListener(this);
        mButtonD3.setOnClickListener(this);
        mButtonD4.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        if(Strings.buttonsOn) {
            switch (v.getId()) {

                case R.id.d1:
                    // do code d1
                    mButtonD1.setText(Strings.d1);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "D1";
                    break;

                case R.id.d2:
                    // do code d2
                    mButtonD2.setText(Strings.d2);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "D2";

                    break;


                case R.id.d3:
                    // do code d3
                    mButtonD3.setText(Strings.d3);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "D3";
                    break;


                case R.id.d4:
                    // do code d4
                    mButtonD4.setText(Strings.d4);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "D4";
                    break;


            }
        }

    }
    private void hideDefaultKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);

    }

}