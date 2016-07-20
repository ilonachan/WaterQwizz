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
public abstract class QuestFragment extends Fragment {

	/**
	 * Callback hopefully called when the answer evaluated by the quest was correct.
	 *
	 * @return The string to be displayed as some sort of win message
	 */
	public abstract String handleWin();

	/**
	 * Callback hopefully called when the answer evaluated by the quest was wrong.
	 *
	 * @return The string to be displayed as some sort of win message
	 */
	public abstract String handleLoss();

	public abstract Object getUserSelection();

}