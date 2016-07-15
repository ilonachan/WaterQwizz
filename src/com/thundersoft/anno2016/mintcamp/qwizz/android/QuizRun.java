package com.thundersoft.anno2016.mintcamp.qwizz.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.thundersoft.anno2016.mintcamp.qwizz.User;
import com.thundersoft.anno2016.mintcamp.qwizz.android.quests.EstQActivity;
import com.thundersoft.anno2016.mintcamp.qwizz.android.quests.MCQActivity;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.QuestManager;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.EstQuest;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.GeneralQuest;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.MCQuest;

/**
 * @author fgast34
 * @version ??? - 14.07.2016.
 */
public class QuizRun extends Activity {

    private QuestManager mManager;
    private GeneralQuest quest;
    private int score;
    private User user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getSerializableExtra("user");


        runQuiz(user);
    }


    public void runQuiz(User user) {
        mManager = user.getManager();
        proceedQuiz();
    }

    public void proceedQuiz() {
        quest = mManager.getNext();

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
        i.putExtra("playerScore", score);
        i.putExtra("totalQuests", mManager.getNumberOfQuests());
        i.putExtra("user", user);
        this.startActivity(i);

        mManager.manageElo(score,mManager.getNumberOfQuests(),1);
        setResult(RESULT_OK, new Intent().putExtra("user",user));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 29303) {
            GeneralQuest qs = (GeneralQuest)data.getSerializableExtra("quest");

            if(!qs.hasUserAnswered()) {
                mManager.skipQuest();
                proceedQuiz();
                return;
            }

            if(qs.isAnswerCorrect()) {
                score++;
            }
            quest.setAnswered(qs.isAnswerCorrect());
            proceedQuiz();
        }
    }
}
