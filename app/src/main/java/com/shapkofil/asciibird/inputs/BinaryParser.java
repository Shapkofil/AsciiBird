package com.shapkofil.asciibird.inputs;

import android.util.Log;
import android.widget.Toast;

import com.shapkofil.asciibird.MainActivity;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Timer;
import java.util.TimerTask;

import static android.widget.Toast.makeText;

public class BinaryParser {

    private static final int bitCap = 7;

    private BitSet bitInfo = new BitSet(bitCap);

    private int bitCount = -1;

    private Timer timer = new Timer("Timer");
    private long timerDelay;
    private TimerTask task;


    private ArrayList<BitReceivers> receivers = new ArrayList<>();
    private ArrayList<BitPositionReceivers> bpReceivers = new ArrayList<>();


    public BinaryParser(long delay) {
        timerDelay = delay;
    }


    public void addReceiver(BitReceivers receiver) {
        receivers.add(receiver);
    }

    public void addbpReceiver(BitPositionReceivers receiver) {
        bpReceivers.add(receiver);
    }

    private void sendMessage() {


        //Console message send
        Log.d("message send:", bitInfo.toString());

        //invoking all of the receivers
        for (BitReceivers f : receivers) {
            f.messageArrived(bitInfo);
        }

        bitInfo.clear();
        bitCount = -1;
        timer.cancel();
    }

    public void openChannel() {
        if (bitCount == -1) {
            bitCount++;
            resetTimer();
            resetDisplays();
            return;
        }

        bitInfo.set(bitCount);

    }

    private void resetTimer() {
        task = new TimerTask() {
            @Override
            public void run() {
                updateDisplays();
                if (bitCount >= bitCap - 1) {
                    sendMessage();
                    return;
                }
                bitCount++;
                Log.d("tick", "tack");
            }
        };
        timer = new Timer("Timer");
        timer.scheduleAtFixedRate(task, timerDelay, timerDelay);
    }

    private void updateDisplays() {
        for (BitPositionReceivers f : bpReceivers) {
            f.onBitArrived(bitCount, bitInfo.get(bitCount));
        }
    }

    private void resetDisplays() {
        for (BitPositionReceivers f : bpReceivers) {
            f.onReset();
        }
    }
}
