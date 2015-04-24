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


public class TabBOff extends Fragment implements View.OnClickListener {



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
                if (actionId == IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mButtonB.getWindowToken(), 0);
                    mButtonB.clearFocus();

                    if (MainActivityOff.compareStr(mButtonB.getText().toString(), MainActivityOff.b)) {

                        MainActivityOff.setLastMove("B");
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

        mButtonB1.setOnClickListener(this);
        mButtonB2.setOnClickListener(this);
        mButtonB3.setOnClickListener(this);
        mButtonB4.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

            switch (v.getId()) {

                case R.id.b1:

                    mButtonB1.setText(MainActivityOff.b1);
                    mButtonB1.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;


                case R.id.b2:

                    mButtonB2.setText(MainActivityOff.b2);
                    mButtonB2.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;


                case R.id.b3:

                    mButtonB3.setText(MainActivityOff.b3);
                    mButtonB3.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;


                case R.id.b4:

                    mButtonB4.setText(MainActivityOff.b4);
                    mButtonB4.setClickable(false);
                    MainActivityOff.points= MainActivityOff.points-5;
                    MainActivityOff.updateToolbar();
                    break;

            }

    }

}