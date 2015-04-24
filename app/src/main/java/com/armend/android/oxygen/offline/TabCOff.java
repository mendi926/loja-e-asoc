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


public class TabCOff extends Fragment implements View.OnClickListener {

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
                if (actionId == IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mButtonC.getWindowToken(), 0);

                    mButtonC.clearFocus();

                    if (MainActivityOff.compareStr(mButtonC.getText().toString(), MainActivityOff.c)) {
                        MainActivityOff.setLastMove("C");
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
        mButtonC1.setOnClickListener(this);
        mButtonC2.setOnClickListener(this);
        mButtonC3.setOnClickListener(this);
        mButtonC4.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {

                case R.id.c1:
                    // do code c1
                    mButtonC1.setText(MainActivityOff.c1);
                    mButtonC1.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;

                case R.id.c2:
                    // do code c2
                    mButtonC2.setText(MainActivityOff.c2);
                    mButtonC2.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;

                case R.id.c3:
                    // do code c3
                    mButtonC3.setText(MainActivityOff.c3);
                    mButtonC3.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;

                case R.id.c4:
                    // do code c4
                    mButtonC4.setText(MainActivityOff.c4);
                    mButtonC4.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;
            }

    }

}