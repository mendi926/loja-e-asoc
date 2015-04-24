package com.armend.android.oxygen.offline;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.armend.android.oxygen.R;
import com.devspark.appmsg.AppMsg;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;


public class TabRezOff extends Fragment {


    public static Button mButtonAA, mButtonBB, mButtonCC, mButtonDD;
    public static EditText mButtonRez;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabrez, container, false);


        mButtonAA = (Button) v.findViewById(R.id.aa);
        mButtonBB = (Button) v.findViewById(R.id.bb);
        mButtonCC = (Button) v.findViewById(R.id.cc);
        mButtonDD = (Button) v.findViewById(R.id.dd);
        mButtonRez = (EditText) v.findViewById(R.id.rez);




        mButtonRez.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean handled = false;
                    if (actionId == IME_ACTION_DONE) {
                        hideDefaultKeyboard(mButtonRez);
                        mButtonRez.clearFocus();

                        if (MainActivityOff.compareStr(mButtonRez.getText().toString(), MainActivityOff.rez)) {

                            MainActivityOff.setLastMove("Rez");

                            //AppMsg.makeText(getActivity(), "Correct", AppMsg.STYLE_INFO).setLayoutGravity(Gravity.BOTTOM).show();

                            MainActivityOff.showDiag(getActivity(), "Ju keni gjetur zgjidhjen përfundimtare");

                        } else {

                            AppMsg.makeText(getActivity(), "Incorrect", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.BOTTOM).show();
                        }

                        handled = true;
                    }

                return handled;
            }
        });







        return v;

    }

    public void hideDefaultKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);

    }


}