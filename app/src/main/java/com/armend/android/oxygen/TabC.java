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


public class TabC extends Fragment implements View.OnClickListener {

    public static Button mButtonC1, mButtonC2, mButtonC3, mButtonC4;
    public static EditText mButtonC;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tabc,container,false);

        mButtonC = (EditText)v.findViewById(R.id.c);
        mButtonC1 = (Button)v.findViewById(R.id.c1);
        mButtonC2 = (Button)v.findViewById(R.id.c2);
        mButtonC3 = (Button)v.findViewById(R.id.c3);
        mButtonC4 = (Button)v.findViewById(R.id.c4);



        mButtonC.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(Strings.editTextOn) {
                if (actionId == IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mButtonC.getWindowToken(), 0);

                    mButtonC.clearFocus();

                    if (MainActivity.compareStr(mButtonC.getText().toString(), Strings.c)) {
                        MainActivity.setLastMove("C");
                        AppMsg.makeText(getActivity(), "Correct", AppMsg.STYLE_INFO).setLayoutGravity(Gravity.BOTTOM).show();
                        Strings.lastmove = "C";

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
                    hideDefaultKeyboard(mButtonC);
                    mButtonC.clearFocus();
                    AppMsg.makeText(getActivity(), "Not your turn", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.BOTTOM).show();
                }
                return handled;
            }
        });
        mButtonC1.setOnClickListener(this);
        mButtonC2.setOnClickListener(this);
        mButtonC3.setOnClickListener(this);
        mButtonC4.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if(Strings.buttonsOn) {
            switch (v.getId()) {

                case R.id.c1:
                    // do code c1
                    mButtonC1.setText(Strings.c1);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "C1";
                    break;

                case R.id.c2:
                    // do code c2
                    mButtonC2.setText(Strings.c2);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "C2";
                    break;

                case R.id.c3:
                    // do code c3
                    mButtonC3.setText(Strings.c3);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "C3";
                    break;

                case R.id.c4:
                    // do code c4
                    mButtonC4.setText(Strings.c4);
                    Strings.buttonsOn = false;
                    Strings.lastmove = "C4";
                    break;
            }
        }

    }
    private void hideDefaultKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);

    }

}