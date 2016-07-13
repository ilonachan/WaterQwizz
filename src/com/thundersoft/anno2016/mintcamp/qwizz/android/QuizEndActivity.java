package com.thundersoft.anno2016.mintcamp.qwizz.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author fgast34
 * @version ??? - 13.07.2016.
 */
public class QuizEndActivity extends Activity implements View.OnTouchListener{

    int score;
    int total;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        score = getIntent().getIntExtra("playerScore", 0);
        total = getIntent().getIntExtra("totalQuests", 10);
        setContentView(R.layout.finish_layout);

        double frac = (double)score / (double) total;
        String feedback = "Good!";
        if(frac <= 0.15) feedback = "Oh, No!";
        if(frac >= 0.55) feedback = "Great!";
        if(frac >= 0.9) feedback = "Fantastic!";
        TextView t = (TextView) findViewById(R.id.feedback);
        t.setText(feedback);

        t = (TextView) findViewById(R.id.evaluationString);
        String form = (String) t.getText();
        t.setText(String.format(form, score, total));



        RelativeLayout lay = (RelativeLayout) findViewById(R.id.FinishLayout);
        lay.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        finish();
        return false;
    }
}
