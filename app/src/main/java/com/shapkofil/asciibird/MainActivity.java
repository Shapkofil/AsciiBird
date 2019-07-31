package com.shapkofil.asciibird;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.shapkofil.asciibird.displays.DisplayController;
import com.shapkofil.asciibird.encoding.Encoder;
import com.shapkofil.asciibird.guides.UserGuideActivity;
import com.shapkofil.asciibird.inputs.BinaryParser;
import com.shapkofil.asciibird.keyboard.KeyPresser;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout canvas;

    private BinaryParser binaryParser = new BinaryParser(1000L);
    private Encoder encoder = new Encoder(Encoder.ASCII);
    private KeyPresser keyPresser;
    private DisplayController currentBitController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //setting up the display
        final String PREFS_NAME = "vault";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("first_contact", true)) {
            startGuide();
            settings.edit().putBoolean("first_contact", false).commit();
        }


        //getting the canvas
        canvas = findViewById(R.id.telegraph_canvas);
        keyPresser = new KeyPresser(canvas);




        currentBitController = new DisplayController(findViewById(R.id.current_message_display), this);

        //managing the observer model
        binaryParser.addReceiver(encoder);
        encoder.addReceiver(keyPresser);
        binaryParser.addbpReceiver(currentBitController);

        FloatingActionButton fab = findViewById(R.id.ui_input);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binaryParser.openChannel();
            }
        });


    }

    private void startGuide()
    {
        startActivity(new Intent(this, UserGuideActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startGuide();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK) {
            binaryParser.openChannel();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setText(TextView textView, String value) {
        runOnUiThread(() -> textView.setText(value));
    }
}
