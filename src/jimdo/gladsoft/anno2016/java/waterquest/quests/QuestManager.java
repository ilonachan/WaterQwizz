package jimdo.gladsoft.anno2016.java.waterquest.quests;

import android.content.Context;
import android.util.Log;
import jimdo.gladsoft.anno2016.java.waterquest.QuestReader;
import jimdo.gladsoft.anno2016.java.waterquest.R;
import jimdo.gladsoft.anno2016.java.waterquest.android.MainActivity;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

/**
 * @author fgast34
 * @version ??? - 12.07.2016.
 */
public class QuestManager implements Serializable{

    private List<GeneralQuest> quests;
    private int mQuestIndex;
    private int mHighscore;
    private int mEloVal = 1000;

    public QuestManager() {
        reloadQuests();
    }

    private void reloadQuests() {
        quests = getAllQuests();

	    for (int i = 0; i < quests.size(); i++) {
		    if(quests.get(i) == null) {
		    	Log.e("QuestInit", "FATAL ERROR:\nFound null-type element between standard elements! Remember that quests is NOT nullable!");
			    quests.remove(i);
		    }
	    }

        quests = shuffleQuests(quests);
    }

    private List<GeneralQuest> getAllQuests() {
        ArrayList<GeneralQuest> quests = new ArrayList<>(/*Arrays.asList(new GeneralQuest[]{
                new MCQuest(new String[]{"10:00 Uhr", "10:30 Uhr", "11:00 Uhr"}, 2, "Um wie viel Uhr startet das SummerCamp?",null),
                new EstQuest(150,"Wieviele Minuten arbeiten wir hier nun schon?","Das stimmt jetzt natürlich nicht mehr!",10),
                new MCQuest(new String[]{"Deutschland", "Italien", "Frankreich", "Portugal"}, 4, "Wer ist Europameister 2016?",null),
                new MCQuest(new String[]{"Wirtschaftsinformatik", "Volkswirtschaftslehre", "Betriebswirtschaftslehre"}, 2, "Welches Fach kann man an der FHDW nicht studieren?",null)
        })*/);
        quests.addAll(loadQuestionsFromFile(MainActivity.a));
        return quests;
    }

    private List<GeneralQuest> shuffleQuests(List<GeneralQuest> quests) {
        Collections.shuffle(quests/*, new Comparator<GeneralQuest>() {
            Random r = new Random();
            @Override
            public int compare(GeneralQuest a1, GeneralQuest a2)
            {
                if(a1.getCategory().equals(a2.getCategory()))
                    return r.nextInt(3)-1;
                else
                    return a1.getCategory().compareTo(a2.getCategory());
            }
        }*/);
        Collections.sort(quests, new Comparator<GeneralQuest>() {
            @Override
            public int compare(GeneralQuest lhs, GeneralQuest rhs) {
                if(lhs.getCategory().equals(rhs.getCategory()))
                    return 0;
                else
                    return lhs.getCategory().compareTo(rhs.getCategory());
            }
        });
        return quests;
    }

    private ArrayList<GeneralQuest> loadQuestionsFromFile(Context c) {

        //this requires there to be a dictionary.csv file in the raw directory
        //in this case you can swap in whatever you want
        QuestReader reader = new QuestReader(c.getResources().openRawResource(R.raw.questions));

        ArrayList<GeneralQuest> listy = new ArrayList<>();

        try
        {
            GeneralQuest q;
	        q = reader.next();
            while (q != null) {
	            listy.add(q);
	            Log.d("QuestManager: lQFF():","Added new Quest of Non-null type");
	            q = reader.next();
            }

        }
        catch (IOException e) {
            // handle exception
            Log.e("QuestManager: lQFF()",e.getStackTrace().toString());
            try {
                reader.close();
            } catch (IOException ex) {
                Log.e("QuestManager: lQFF()",ex.getStackTrace().toString());
            }
        }
        return listy;
    }


    public GeneralQuest getQuest(int index) {
        return quests.get(index);
    }

    public int getNumberOfQuests() {
        return Math.min(quests.size(),15);
    }

    public void setHighscore(int s) {
        if(s > mHighscore) mHighscore = s;
    }

    public GeneralQuest getNext() {
        if(mQuestIndex == getNumberOfQuests()) {
            return null;
        }

        GeneralQuest q;
        q = getQuest(mQuestIndex);
        mQuestIndex++;


        return q;
    }

    public int getHighscore() {
        return mHighscore;
    }

    public int getElo() {
        return mEloVal;
    }

	/**
	 * Manages the score the player has after having played.
	 * TODO: Find an algorithm that suits the ELO Calculation System better
	 *
	 * @param correct
	 * @param total
	 * @param averageDifficulty
	 */
    public void manageElo(int correct, int total, int averageDifficulty) {
        double score = correct/total * averageDifficulty * 100;
        setHighscore((int)Math.ceil(score));

        double x = mEloVal;
        int corr = correct;
        for(int i = 0; i < total; i++) {
            if(corr > 0) {
                x += (20000/x);
                corr--;
            } else {
                x -= (x/200);
            }
        }

        mEloVal = (int) Math.round(x);

        this.mQuestIndex = 0;

        quests = shuffleQuests(quests);
    }

    public void skipQuest() {
        getNext();
    }
}
