package jimdo.gladsoft.anno2016.java.waterquest.android.quests;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import jimdo.gladsoft.anno2016.java.waterquest.R;
import jimdo.gladsoft.anno2016.java.waterquest.quests.EstQuest;
import jimdo.gladsoft.anno2016.java.waterquest.quests.GeneralQuest;

/**
 * @author Anton
 * @version ??? - 20.07.2016
 */
public class EstQFragment extends QuestFragment {

	LinearLayout mView;
	EditText mAnswer;
	int selected = -1;
	EstQuest quest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = (LinearLayout) inflater.inflate(R.layout.estq_fragment, container, false);
		mAnswer = (EditText) mView.findViewById(R.id.inputValue);
		return mView;
	}

	@Override
	public void setQuest(GeneralQuest q) {
		quest = (EstQuest) q;
	}

	public String handleWin() {
		mAnswer.setBackgroundColor(getResources().getColor(R.color.green));
		return getResources().getString(R.string.estcorrect, quest.getExactAnswer());
	}

	public String handleLoss() {
		mAnswer.setBackgroundColor(getResources().getColor(R.color.red));
		return getResources().getString(R.string.estwrong, quest.getExactAnswer());
	}

	public Object getUserSelection() {
		try {
			return Float.parseFloat(mAnswer.getText().toString());
		}catch(NumberFormatException e) {
			return null;
		}
	}
}