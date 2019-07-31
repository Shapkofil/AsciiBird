package com.shapkofil.asciibird.encoding;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

import com.shapkofil.asciibird.inputs.BitReceivers;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

public class Encoder implements BitReceivers {

    private ArrayList<KeyCodeReceivers> receivers = new ArrayList<>();
    private HashMap<Character, Integer> keyCodeMap = new HashMap<>();

    public static LambdaEncode ASCII = (bitSet) -> {
        char label = (char) bitSet.toLongArray()[0];
        return label;
    };


    private LambdaEncode currentMethod = ASCII;

    public Encoder(LambdaEncode method) {
        currentMethod = method;
        mapKeyCodes();
    }

    public void addReceiver(KeyCodeReceivers receiver) {
        receivers.add(receiver);
    }

    @Override
    public void messageArrived(BitSet bitset) {
        char label = currentMethod.perform(bitset);
        Log.d("char label", "" + label + "->" + bitset.toLongArray()[0]);
        for (KeyCodeReceivers f : receivers) {
            if (keyCodeMap.containsKey(label)) f.keyCodeSent(keyCodeMap.get(label));
        }
    }

    private void mapKeyCodes() {
        KeyCharacterMap characterMap = KeyCharacterMap.load(KeyCharacterMap.ALPHA);
        if (Build.VERSION.SDK_INT >= 11)
            characterMap = KeyCharacterMap.load(KeyCharacterMap.VIRTUAL_KEYBOARD);

        for (int i = 0; i < 258; i++) {
            if (characterMap.isPrintingKey(i))
                keyCodeMap.put(characterMap.getDisplayLabel(i), i);
        }

        //Special cases
        keyCodeMap.put(' ',KeyEvent.KEYCODE_SPACE);
        keyCodeMap.put('\b',KeyEvent.KEYCODE_DEL);
    }
}
