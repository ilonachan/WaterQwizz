package jimdo.gladsoft.anno2016.java.waterquest.android.quests;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import jimdo.gladsoft.anno2016.java.waterquest.R;
import jimdo.gladsoft.anno2016.java.waterquest.User;
import jimdo.gladsoft.anno2016.java.waterquest.android.QuizEndActivity;
import jimdo.gladsoft.anno2016.java.waterquest.quests.*;

/**
 * @author Anton
 * @version ??? - 19.07.2016
 */
public class GenericQuestActivity extends Activity implements View.OnClickListener {

	private QuestManager mManager;
	private int score;
	private User user;
	private GeneralQuest quest;
	private TextView mDesc;
	private Button Submit;
	private QuestFragment mFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		user = (User) getIntent().getSerializableExtra("user");
		mManager = user.getManager();

		proceedQuiz();
	}




	public void proceedQuiz() {
		quest = mManager.getNext();

		if (quest  != null) {
			initGUI();
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




	public void initGUI() {
		setContentView(R.layout.quest_layout);

		mDesc = (TextView) findViewById(R.id.desc);

		Submit = (Button) findViewById(R.id.SubmitButton);
		Submit.setOnClickListener(this);

		applyQuestToGUI();
	}

	protected void applyQuestToGUI() {
		mDesc.setText(quest.getDescription());

		mFragment = null;

		if (quest instanceof MCQuest) {
			// Add Fragment for MCQuests
			mFragment = new MCQFragment();
		}
		if (quest instanceof EstQuest) {
			// Add Fragment for MCQuests
			mFragment = new EstQFragment();
		}

		if(mFragment == null) throw new Error("Unsupported Quest Type detected; tried to initialize fragment, but couldn´t. Please repair your database.");

		mFragment.setQuest(quest);

		FragmentManager man = getFragmentManager();
		FragmentTransaction transaction = man.beginTransaction();
		transaction.replace(R.id.fragmentContainer, mFragment);
		transaction.commit();
//		mFragment = (QuestFragment) getFragmentManager().findFragmentById(R.id.fragmentContainer);
	}

	@Override
	public void onClick(View view) {
		if (view == Submit) {
			Object value = mFragment.getUserSelection(); // Works perfectly; obviously I can interact with my fragment
														 // just as with any other kind of object.

			if(value == null)
				return;

			try {
				quest.answer(value);
			} catch (InvalidAnswerTypeException | InvalidArgumentException e) {
				Log.e("QuestActivity: onClick","Error");
			}

			if (quest.isAnswerCorrect()) {
				// Make the fragment evaluate the win
				// And write the returned specific
				// message into the answer pad
				mDesc.setText(mFragment.handleWin());
				score++;
			} else {
				// Make the fragment evaluate the loss
				// And write the returned specific
				// message into the answer pad
				mDesc.setText(mFragment.handleLoss());
			}

			mDesc.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					GenericQuestActivity.this.startActivity(new Intent(GenericQuestActivity.this, AnswerDescActivity.class).putExtra("desc", quest.getExtra()));
				}
			});

			Submit.setText(R.string.continueButton);
			Submit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Submit.setText(R.string.submit);
					GenericQuestActivity.this.proceedQuiz();
				}
			});
		}
	}

	@Override
	public void onBackPressed() {
	}
}