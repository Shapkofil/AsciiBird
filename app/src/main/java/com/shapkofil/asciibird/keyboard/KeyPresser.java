package com.shapkofil.asciibird.keyboard;

import android.app.Instrumentation;
import android.os.DeadObjectException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.BaseInputConnection;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.shapkofil.asciibird.encoding.KeyCodeReceivers;

public class KeyPresser implements KeyCodeReceivers {

    private TextInputLayout textInputLayout;

    public KeyPresser(TextInputLayout textInputLayout)
    {
        this.textInputLayout = textInputLayout;
    }

    @Override
    public void keyCodeSent(int keyCode) {
        try {
            BaseInputConnection inputConnection = new BaseInputConnection(textInputLayout,true);
            inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,keyCode));

            //sending a debug message
            Log.d("last mark","keyCode pressed:" + keyCode);
        }catch (Exception exp){
        }
    }
}
