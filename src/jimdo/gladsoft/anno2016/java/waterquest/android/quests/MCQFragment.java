package jimdo.gladsoft.anno2016.java.waterquest.android.quests;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import jimdo.gladsoft.anno2016.java.waterquest.R;
import jimdo.gladsoft.anno2016.java.waterquest.quests.MCQuest;

/**
 * @author Anton
 * @version ??? - 19.07.2016
 */
public class MCQFragment extends QuestFragment {

	LinearLayout mAnswers;
	Button[] mButtons;
	int selected = -1;
	MCQuest quest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mAnswers = (LinearLayout) inflater.inflate(R.layout.mcq_fragment, container, false);
		mButtons = new Button[4];
		mButtons[0] = (Button) mAnswers.findViewById(R.id.answer1);
		mButtons[1] = (Button) mAnswers.findViewById(R.id.answer2);
		mButtons[2] = (Button) mAnswers.findViewById(R.id.answer3);
		mButtons[3] = (Button) mAnswers.findViewById(R.id.answer4);

		return mAnswers;
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
		return selected;
	}




}