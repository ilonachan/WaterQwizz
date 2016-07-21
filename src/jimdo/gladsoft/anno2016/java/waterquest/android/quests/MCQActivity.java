package jimdo.gladsoft.anno2016.java.waterquest.android.quests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import jimdo.gladsoft.anno2016.java.waterquest.R;
import jimdo.gladsoft.anno2016.java.waterquest.quests.InvalidAnswerTypeException;
import jimdo.gladsoft.anno2016.java.waterquest.quests.InvalidArgumentException;
import jimdo.gladsoft.anno2016.java.waterquest.quests.MCQuest;

/**
 * @author fgast34
 * @version 0.1b1 - 12.07.2016.
 * @deprecated as of Version 0.2b1, this class is no longer used; See {@link MCQFragment} instead
 */
public class MCQActivity extends Activity implements View.OnClickListener {

    MCQuest quest;
    TextView mDesc;
    LinearLayout mAnswers;
    Button[] mButtons;
    Button Skip;
    Button Submit;
    int selected = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quest = (MCQuest) getIntent().getSerializableExtra("givenQuest");
        initGUI();
    }

    public void initGUI(){
        setContentView(R.layout.mcq_layout);

        mDesc = (TextView) findViewById(R.id.desc);
        mAnswers = (LinearLayout) findViewById(R.id.fragmentContainer);

        Skip = (Button) findViewById(R.id.SkipButton);
        Submit = (Button) findViewById(R.id.SubmitButton);

        mButtons = new Button[quest.getAnswers().length];

        mButtons[0] = (Button) findViewById(R.id.answer1);
        mButtons[1] = (Button) findViewById(R.id.answer2);
        mButtons[2] = (Button) findViewById(R.id.answer3);
        if(mButtons.length == 4)
            mButtons[3] = (Button) findViewById(R.id.answer4);
        else {
            View v = findViewById(R.id.answer4);
            v.setVisibility(View.GONE);
        }

        Skip.setOnClickListener(this);
        Submit.setOnClickListener(this);

        for (Button mButton : mButtons) {
            mButton.setOnClickListener(this);
        }
        applyQuestToGUI();
    }

    protected void applyQuestToGUI() {
        mDesc.setText(quest.getDescription());
        String[] ans = quest.getAnswers();
        for(int i = 0; i < mButtons.length; i++) {
            mButtons[i].setText(ans[i]);
        }
    }

    @Override
    public void onClick(View view) {
        for(int i = 0; i < mButtons.length; i++) {
            if (view == mButtons[i] && !quest.hasUserAnswered()) {
                Log.println(Log.ASSERT, "LogTag", "The " + (i + 1) + "th Button was pressed");
                if(selected != -1) mButtons[selected].setBackgroundColor(getResources().getColor(R.color.mcactivity_answertextbg));
                mButtons[i].setBackgroundColor(getResources().getColor(R.color.mcactivity_answertextbgc));
                selected = i;
            }
        }

        Activity c = this;

        if(view == Submit && selected > -1) {
            try {  quest.answer(selected+1);
            } catch (InvalidAnswerTypeException e) {
            } catch (InvalidArgumentException e2) {}

            if (quest.isAnswerCorrect()){
                mButtons[selected].setBackgroundColor(getResources().getColor(R.color.green));
                mDesc.setText("Correct!!!");
            } else {
                mButtons[selected].setBackgroundColor(getResources().getColor(R.color.red));
                mButtons[quest.getCorrect()-1].setBackgroundColor(getResources().getColor(R.color.green));
                mDesc.setText("Wrong :´(");
            }

            mDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(c, AnswerDescActivity.class).putExtra("desc",quest.getExtra()));
                }
            });

            Submit.setText(R.string.continueButton);
            Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c.setResult(RESULT_OK, new Intent().putExtra("quest", quest));
                    c.finish();
                }
            });
        }
        if(view == Skip) {
            c.setResult(RESULT_OK, new Intent().putExtra("quest", quest));
            c.finish();
        }
    }

    @Override
    public void onBackPressed() {
    }
}