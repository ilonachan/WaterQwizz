package jimdo.gladsoft.anno2016.java.waterquest.android.quests;

import android.app.Fragment;
import jimdo.gladsoft.anno2016.java.waterquest.quests.GeneralQuest;

/**
 * The superclass of all quest GUI types. It doesn´t add any code, yet requires important functionality for any subclasses.
 *
 * @since 0.2b1
 * @author gladSoft Productions
 * @version 0.2b1 - 19.07.2016
 */
public abstract class QuestFragment extends Fragment {

	/**
	 * Callback called when the answer evaluated by the quest was correct.
	 *
	 * @return The string to be displayed as some sort of win message
	 */
	public abstract String handleWin();

	/**
	 * Callback called when the answer evaluated by the quest was wrong.
	 *
	 * @return The string to be displayed as some sort of win message
	 */
	public abstract String handleLoss();

	/**
	 * The method returns the value of the user answer. It could be of any type, so we just return an {@code Object}.
	 *
	 * @return The user answer as the most unified type
	 */
	public abstract Object getUserSelection();

	/**
	 * Callback function to be called when the Fragment is created, giving thus access to the used quest.<br/>
	 *
	 * @param quest
	 */
	public abstract void setQuest(GeneralQuest quest);

}