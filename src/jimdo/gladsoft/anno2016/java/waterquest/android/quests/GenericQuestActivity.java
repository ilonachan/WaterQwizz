package jimdo.gladsoft.anno2016.java.waterquest.android.quests;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import jimdo.gladsoft.anno2016.java.waterquest.R;
import jimdo.gladsoft.anno2016.java.waterquest.quests.GeneralQuest;
import jimdo.gladsoft.anno2016.java.waterquest.quests.InvalidAnswerTypeException;
import jimdo.gladsoft.anno2016.java.waterquest.quests.InvalidArgumentException;
import jimdo.gladsoft.anno2016.java.waterquest.quests.MCQuest;

/**
 * @author Anton
 * @version ??? - 19.07.2016
 */
public class GenericQuestActivity extends Activity implements View.OnClickListener {

	GeneralQuest quest;
	TextView mDesc;
	LinearLayout mFragmentContainer;
	Button Submit;
	QuestFragment mFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		quest = (GeneralQuest) getIntent().getSerializableExtra("givenQuest");
		initGUI();
	}

	public void initGUI() {
		setContentView(R.layout.mcq_layout);

		mDesc = (TextView) findViewById(R.id.desc);
		mFragmentContainer = (LinearLayout) findViewById(R.id.fragmentContainer);

		Submit = (Button) findViewById(R.id.SubmitButton);
		Submit.setOnClickListener(this);

		applyQuestToGUI();
	}

	protected void applyQuestToGUI() {
		mDesc.setText(quest.getDescription());

		if (quest instanceof MCQuest) {
			// Add Fragment for MCQuests
			mFragment = new MCQFragment();
		}

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragmentContainer, mFragment);
		transaction.commit();
	}

	@Override
	public void onClick(View view) {
		if (view == Submit) {
			Object value = mFragment.getUserSelection(); // TODO: Work out if this type of line can work
														 // Well, I´m quite sure.
			try {
				quest.answer(value);
			} catch (InvalidAnswerTypeException | InvalidArgumentException e) {
				Log.e("QuestActivity: onClick",e.getMessage());
			}

			if (quest.isAnswerCorrect()) {
				// Make the fragment evaluate the win
				// And write the returned specific
				// message into the answer pad
				mDesc.setText(mFragment.handleWin());
			} else {
				// Make the fragment evaluate the loss
				// And write the returned specific
				// message into the answer pad
				mDesc.setText(mFragment.handleLoss());
			}

			mDesc.setOnClickListener(v -> startActivity(new Intent(this, AnswerDescActivity.class).putExtra("desc", quest.getExtra())));

			Submit.setText(R.string.continueButton);
			Submit.setOnClickListener(v -> {
				setResult(RESULT_OK, new Intent().putExtra("quest", quest));
				finish();
			});
		}
	}

	@Override
	public void onBackPressed() {
	}
}