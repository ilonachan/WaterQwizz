package com.thundersoft.anno2016.mintcamp.qwizz.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.thundersoft.anno2016.mintcamp.qwizz.android.quests.EstQActivity;
import com.thundersoft.anno2016.mintcamp.qwizz.android.quests.MCQActivity;
import com.thundersoft.anno2016.mintcamp.qwizz.android.quests.QuestManager;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.EstQuest;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.GeneralQuest;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.MCQuest;

/**
 * @author fgast34
 * @version ??? - 13.07.2016.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    Button[] mMenuButtons;
    QuestManager q;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        mMenuButtons = new Button[4];
        mMenuButtons[0] = (Button) findViewById(R.id.startButton);
        mMenuButtons[1] = (Button) findViewById(R.id.manualButton);
        mMenuButtons[2] = (Button) findViewById(R.id.scoretButton);
        mMenuButtons[3] = (Button) findViewById(R.id.settingsButton);

        for (Button mMenuButton : mMenuButtons) {
            mMenuButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        for(int i = 0; i < mMenuButtons.length; i++) {
            if(v == mMenuButtons[i]) {
                switch(i){
                    case 0:
                        startQuiz(false);
                        break;
                    case 1:
                        this.startActivity(new Intent(this, InstructActivity.class));
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
        }
    }

    public void startQuiz(boolean isTimeRace) {
        q = new QuestManager();
        proceedQuiz();
    }

    public void proceedQuiz() {
        GeneralQuest quest = q.getNext();
        if (quest  != null) {
            Intent i = new Intent();
            if(quest instanceof MCQuest) {
                i = new Intent(this, MCQActivity.class);
            }
            if(quest instanceof EstQuest) {
                i = new Intent(this, EstQActivity.class);
            }

            i.putExtra("givenQuest", quest);
            this.startActivityForResult(i, 29303);
            return;
        }
        Intent i = new Intent(this, QuizEndActivity.class);
        i.putExtra("playerScore", q.getScore());
        i.putExtra("totalQuests", q.getNumberOfQuests());
        this.startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 29303) {
            GeneralQuest qs = (GeneralQuest)data.getSerializableExtra("quest");
            if(qs.isAnswerCorrect()) {
                q.addScore();
                q.putActual(qs);
            }
            proceedQuiz();
        }
    }
}