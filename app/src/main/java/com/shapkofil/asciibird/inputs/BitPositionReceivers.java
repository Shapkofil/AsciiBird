package com.shapkofil.asciibird.inputs;

public interface BitPositionReceivers {
    void onBitArrived(int position,boolean value);
    void onReset();
}
