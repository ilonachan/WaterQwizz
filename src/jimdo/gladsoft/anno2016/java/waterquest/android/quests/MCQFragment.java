package jimdo.gladsoft.anno2016.java.waterquest.android.quests;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import jimdo.gladsoft.anno2016.java.waterquest.R;
import jimdo.gladsoft.anno2016.java.waterquest.quests.GeneralQuest;
import jimdo.gladsoft.anno2016.java.waterquest.quests.MCQuest;

/**
 * @author Anton
 * @version ??? - 19.07.2016
 */
public class MCQFragment extends QuestFragment implements View.OnClickListener {

	LinearLayout mAnswers;
	Button[] mButtons;
	int selected = -1;
	MCQuest quest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mAnswers = (LinearLayout) inflater.inflate(R.layout.mcq_fragment, container, false);
		mButtons = new Button[quest.getAnswers().length];
		mButtons[0] = (Button) mAnswers.findViewById(R.id.answer1);
		mButtons[1] = (Button) mAnswers.findViewById(R.id.answer2);
		mButtons[2] = (Button) mAnswers.findViewById(R.id.answer3);
		if(mButtons.length == 4)
			mButtons[3] = (Button) mAnswers.findViewById(R.id.answer4);
		else {
			mAnswers.findViewById(R.id.answer4).setVisibility(View.GONE);
		}


		String[] ans = quest.getAnswers();
		for(int i = 0; i < mButtons.length; i++) {
			mButtons[i].setText(ans[i]);
		}



		for (Button mButton : mButtons) {
			mButton.setOnClickListener(this);
		}

		return mAnswers;
	}

	@Override
	public void setQuest(GeneralQuest q) {
		quest = (MCQuest) q;
	}

	public String handleWin() {
		mButtons[selected].setBackgroundColor(getResources().getColor(R.color.green));
		return getResources().getString(R.string.correct);
	}

	public String handleLoss() {
		mButtons[selected].setBackgroundColor(getResources().getColor(R.color.red));
		mButtons[quest.getCorrect()-1].setBackgroundColor(getResources().getColor(R.color.green));
		return getResources().getString(R.string.wrong);
	}

	public Object getUserSelection() {
		if(selected == -1)
			return null;
		return selected+1;
	}


	@Override
	public void onClick(View v) {
		for(int i = 0; i < mButtons.length; i++) {
			if (v == mButtons[i] && !quest.hasUserAnswered()) {
				Log.println(Log.ASSERT, "LogTag", "The " + (i + 1) + "th Button was pressed");
				if(selected != -1) mButtons[selected].setBackgroundColor(getResources().getColor(R.color.mcactivity_answertextbg));
				mButtons[i].setBackgroundColor(getResources().getColor(R.color.mcactivity_answertextbgc));
				selected = i;
			}
		}
	}
}