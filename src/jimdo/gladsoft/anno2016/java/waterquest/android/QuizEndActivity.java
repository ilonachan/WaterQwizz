package jimdo.gladsoft.anno2016.java.waterquest.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import jimdo.gladsoft.anno2016.java.waterquest.R;
import jimdo.gladsoft.anno2016.java.waterquest.User;

/**
 * @author fgast34
 * @version ??? - 13.07.2016.
 */
public class QuizEndActivity extends Activity implements View.OnTouchListener{

    int score;
    int total;
    User gamer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        score = getIntent().getIntExtra("playerScore", 0);
        total = getIntent().getIntExtra("totalQuests", 10);
        gamer = (User) getIntent().getSerializableExtra("user");
        int gamerSc = gamer.getElo();
        setContentView(R.layout.finish_layout);

        double frac = (double)score / (double) total;
        int feedback = R.string.feedback2;
        if(frac <= 0.15) feedback = R.string.feedback1;
        if(frac >= 0.55) feedback = R.string.feedback3;
        if(frac >= 0.9) feedback = R.string.feedback4;
        TextView t = (TextView) findViewById(R.id.feedback);
        t.setText(feedback);

        t = (TextView) findViewById(R.id.evaluationString);
        String form = (String) t.getText();
        t.setText(String.format(form, score, total));

        t = (TextView) findViewById(R.id.gamerSc);
        form = (String) t.getText();
        t.setText(String.format(form, gamerSc));



        RelativeLayout lay = (RelativeLayout) findViewById(R.id.FinishLayout);
        lay.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        finish();
        return false;
    }
}
