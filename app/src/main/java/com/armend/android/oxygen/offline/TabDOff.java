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

import com.armend.android.oxygen.R;
import com.devspark.appmsg.AppMsg;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;


public class TabDOff extends Fragment implements View.OnClickListener {

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

                    if (actionId == IME_ACTION_DONE) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mButtonD.getWindowToken(), 0);
                        mButtonD.clearFocus();


                        if (MainActivityOff.compareStr(mButtonD.getText().toString(), MainActivityOff.d)) {
                            MainActivityOff.setLastMove("D");
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

        mButtonD1.setOnClickListener(this);
        mButtonD2.setOnClickListener(this);
        mButtonD3.setOnClickListener(this);
        mButtonD4.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

            switch (v.getId()) {

                case R.id.d1:
                    // do code d1
                    mButtonD1.setText(MainActivityOff.d1);
                    mButtonD1.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;

                case R.id.d2:
                    // do code d2
                    mButtonD2.setText(MainActivityOff.d2);
                    mButtonD2.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();

                    break;


                case R.id.d3:
                    // do code d3
                    mButtonD3.setText(MainActivityOff.d3);
                    mButtonD3.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;


                case R.id.d4:
                    // do code d4
                    mButtonD4.setText(MainActivityOff.d4);
                    mButtonD4.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;


            }


    }

}