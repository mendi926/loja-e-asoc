package com.armend.android.oxygen.offline;
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

import com.armend.android.oxygen.MainActivity;
import com.armend.android.oxygen.R;
import com.devspark.appmsg.AppMsg;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;


public class TabAOff extends Fragment implements View.OnClickListener {

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
                if (actionId == IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mButtonA.getWindowToken(), 0);
                    mButtonA.clearFocus();

                    if (MainActivityOff.compareStr(mButtonA.getText().toString(), MainActivityOff.a)) {

                        MainActivityOff.setLastMove("A");
                        MainActivityOff.points= MainActivityOff.points-5;
                        MainActivityOff.updateToolbar();
                        AppMsg.makeText(getActivity(), "Correct", AppMsg.STYLE_INFO).setLayoutGravity(Gravity.BOTTOM).show();

                    } else {

                        AppMsg.makeText(getActivity(), "Incorrect", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.BOTTOM).show();
                    }
                    handled = true;
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
            switch (v.getId()) {
                case R.id.a1:

                    mButtonA1.setText(MainActivityOff.a1);
                    mButtonA1.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;
                case R.id.a2:

                    mButtonA2.setText(MainActivityOff.a2);
                    mButtonA2.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;
                case R.id.a3:

                    mButtonA3.setText(MainActivityOff.a3);
                    mButtonA3.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;
                case R.id.a4:

                    mButtonA4.setText(MainActivityOff.a4);
                    mButtonA4.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;
            }

    }



}