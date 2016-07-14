package com.thundersoft.anno2016.mintcamp.qwizz.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.thundersoft.anno2016.mintcamp.qwizz.R;
import com.thundersoft.anno2016.mintcamp.qwizz.User;
import com.thundersoft.anno2016.mintcamp.qwizz.android.quests.QuestManager;


/**
 * @author fgast34
 * @version ??? - 13.07.2016.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    Button[] mMenuButtons;
    QuestManager q;
    public static Activity a;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a=this;
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
        this.startActivity(new Intent(this, QuizRun.class).putExtra("user", new User("Testy")/*UserManager.getCurrent()*/));
    }
}