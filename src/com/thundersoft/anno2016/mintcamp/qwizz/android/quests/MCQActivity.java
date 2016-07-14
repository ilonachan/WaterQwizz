package com.thundersoft.anno2016.mintcamp.qwizz.android.quests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.thundersoft.anno2016.mintcamp.qwizz.android.R;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.InvalidAnswerTypeException;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.InvalidArgumentException;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.MCQuest;

/**
 * @author fgast34
 * @version ??? - 12.07.2016.
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
        mAnswers = (LinearLayout) findViewById(R.id.answers);

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
            if (view == mButtons[i]) {
                Log.println(Log.ASSERT, "LogTag", "The " + (i + 1) + "th Button was pressed");
                mButtons[selected].setBackgroundColor(getResources().getColor(R.color.mcactivity_answertextbg));
                mButtons[i].setBackgroundColor(getResources().getColor(R.color.mcactivity_answertextbgc));
                selected = i;
            }
        }
        if(view == Submit && selected > -1) {
            try {  quest.answer(selected+1);
            } catch (InvalidAnswerTypeException e) {
            } catch (InvalidArgumentException e2) {}

            if (quest.isAnswerCorrect()){
                Toast.makeText(this,"Correct!!!",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"Wrong :´(",Toast.LENGTH_LONG).show();
            }
            setResult(RESULT_OK, new Intent().putExtra("quest", quest));
            finish();

        }
    }
}