package jimdo.gladsoft.anno2016.java.waterquest.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.widget.*;
import jimdo.gladsoft.anno2016.java.waterquest.R;
import jimdo.gladsoft.anno2016.java.waterquest.User;
import jimdo.gladsoft.anno2016.java.waterquest.UserManager;
import jimdo.gladsoft.anno2016.java.waterquest.android.quests.GenericQuestActivity;
import jimdo.gladsoft.anno2016.java.waterquest.quests.QuestManager;


/**
 * @author fgast34
 * @version ??? - 13.07.2016.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    UserManager userManager;
    Button[] mMenuButtons;
    QuestManager q;
    public static Activity a;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a=this;
        setContentView(R.layout.main_layout);
        mMenuButtons = new Button[3];
        mMenuButtons[0] = (Button) findViewById(R.id.startButton);
        mMenuButtons[1] = (Button) findViewById(R.id.manualButton);
        mMenuButtons[2] = (Button) findViewById(R.id.creditsButton);
//        mMenuButtons[3] = (Button) findViewById(R.id.settingsButton);

        for (Button mMenuButton : mMenuButtons) {
            mMenuButton.setOnClickListener(this);
        }

        userManager = readUsersFromConfig();
    }

    private UserManager readUsersFromConfig() {

        /*try (FileInputStream fis = openFileInput("userdata.obj")){
        } catch (IOException e) {
            Log.e("LogTag", e.getMessage());
        }*/
        return new UserManager("Test");
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
                        this.startActivity(new Intent(this, CreditsActivity.class));
                        break;
                }
            }
        }
    }

    public void startQuiz(boolean isTimeRace) {
        this.startActivityForResult(new Intent(this, GenericQuestActivity.class).putExtra("user", userManager.getCurrent()),0x28bca406);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode != 0x28bca406) return;
        this.userManager.editCurrent((User) data.getSerializableExtra("user"));
    }
}