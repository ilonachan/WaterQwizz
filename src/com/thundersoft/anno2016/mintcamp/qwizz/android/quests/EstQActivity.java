package com.thundersoft.anno2016.mintcamp.qwizz.android.quests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.thundersoft.anno2016.mintcamp.qwizz.R;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.EstQuest;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.InvalidAnswerTypeException;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.InvalidArgumentException;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.MCQuest;

/**
 * @author fgast34
 * @version ??? - 12.07.2016.
 */
public class EstQActivity extends Activity implements View.OnClickListener {

    EstQuest quest;
    TextView mDesc;
    EditText input;
    LinearLayout mAnswers;
    Button Skip;
    Button Submit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quest = (EstQuest) getIntent().getSerializableExtra("givenQuest");
        initGUI();
    }

    public void initGUI(){
        setContentView(R.layout.estq_layout);

        mDesc = (TextView) findViewById(R.id.desc);
        input = (EditText) findViewById(R.id.inputValue);

        Skip = (Button) findViewById(R.id.SkipButton);
        Submit = (Button) findViewById(R.id.SubmitButton);

        Skip.setOnClickListener(this);
        Submit.setOnClickListener(this);

        applyQuestToGUI();
    }

    protected void applyQuestToGUI() {
        mDesc.setText(quest.getDescription());
    }

    @Override
    public void onClick(View view) {

        Activity c = this;

        if(view == Submit) {
            try {  quest.answer(Integer.parseInt(input.getText().toString()));
            } catch (InvalidAnswerTypeException e) {
                Toast.makeText(this,"Please enter a number!",Toast.LENGTH_LONG).show();
                return;
            } catch (InvalidArgumentException e2) {}

            if (quest.isAnswerCorrect()){
                mDesc.setText("Very good!\nThe value was: "+ quest.getExactAnswer());
            } else {
                mDesc.setText("Too bad...\nThe value was: "+ quest.getExactAnswer());
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