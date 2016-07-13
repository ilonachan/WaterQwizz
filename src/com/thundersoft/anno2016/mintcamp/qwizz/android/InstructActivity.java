package com.thundersoft.anno2016.mintcamp.qwizz.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author fgast34
 * @version ??? - 13.07.2016.
 */
public class InstructActivity extends Activity implements View.OnClickListener {

    Button b;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instr_layout);
        b = (Button) findViewById(R.id.dialogCloseButton);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}