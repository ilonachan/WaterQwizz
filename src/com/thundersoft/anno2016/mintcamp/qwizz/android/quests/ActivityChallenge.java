package com.thundersoft.anno2016.mintcamp.qwizz.android.quests;

import android.app.Activity;
import android.os.Bundle;
import com.thundersoft.anno2016.mintcamp.qwizz.android.R;

/**
 * @author fgast34
 * @version ??? - 12.07.2016.
 */
public class ActivityChallenge extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.MCQLayout);
    }
}
