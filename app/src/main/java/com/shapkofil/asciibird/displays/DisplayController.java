package com.shapkofil.asciibird.displays;

import android.widget.TableRow;
import android.widget.TextView;

import com.shapkofil.asciibird.MainActivity;
import com.shapkofil.asciibird.inputs.BitPositionReceivers;

import java.util.ArrayList;
import java.util.Collections;

public class DisplayController implements BitPositionReceivers {

    private ArrayList<TextView> miniDisplays = new ArrayList<>();
    private MainActivity mainActivity;

    public DisplayController(TableRow tableRow, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        int lenght = tableRow.getVirtualChildCount();
        for (int i = 0; i < lenght; i++) {
            miniDisplays.add((TextView) tableRow.getChildAt(i));
        }
        Collections.reverse(miniDisplays);
    }

    @Override
    public void onBitArrived(int position, boolean value) {
        mainActivity.setText(miniDisplays.get(position),value?"1":"0");
    }

    @Override
    public void onReset() {
        for(TextView f:miniDisplays) {
            mainActivity.setText(f,"X");
        }
    }
}
