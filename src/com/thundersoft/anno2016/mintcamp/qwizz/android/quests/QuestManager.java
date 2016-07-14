package com.thundersoft.anno2016.mintcamp.qwizz.android.quests;

import android.content.Context;
import android.util.Log;
import com.thundersoft.anno2016.mintcamp.qwizz.R;
import com.thundersoft.anno2016.mintcamp.qwizz.android.MainActivity;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.EstQuest;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.GeneralQuest;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.MCQuest;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author fgast34
 * @version ??? - 12.07.2016.
 */
public class QuestManager implements Serializable{

    private List<GeneralQuest> quests;
    private int mQuestIndex;
    private GeneralQuest actual;
    private int mHighscore;
    private int mEloVal;

    public QuestManager() {
        reloadQuests();
    }

    public void reloadQuests() {
        quests = getAllQuests();

        quests = shuffleQuests(quests);
    }

    private List<GeneralQuest> getAllQuests() {
        List<GeneralQuest> quests = Arrays.asList(new GeneralQuest[]{
                new MCQuest(new String[]{"10:00 Uhr", "10:30 Uhr", "11:00 Uhr"}, 2, "Um wie viel Uhr startet das SummerCamp?"),
                new EstQuest(150,"Wieviele Minuten arbeiten wir hier nun schon?",10),
                new MCQuest(new String[]{"Deutschland", "Italien", "Frankreich", "Portugal"}, 4, "Wer ist Europameister 2016?"),
                new MCQuest(new String[]{"Wirtschaftsinformatik", "Volkswirtschaftslehre", "Betriebswirtschaftslehre"}, 2, "Welches Fach kann man an der FHDW nicht studieren?")
        });
        quests.addAll(loadQuestionsFromFile(MainActivity.a));
        return quests;
    }

    protected List<GeneralQuest> shuffleQuests(List<GeneralQuest> quests) {
        Collections.sort(quests, new Comparator<GeneralQuest>() {
            Random r = new Random();
            @Override
            public int compare(GeneralQuest a1, GeneralQuest a2)
            {

                return r.nextInt(3)-1;
            }
        });
        return quests;
    }

    private ArrayList<GeneralQuest> loadQuestionsFromFile(Context c) {

        //this requires there to be a dictionary.csv file in the raw directory
        //in this case you can swap in whatever you want
        InputStream inputStream = c.getResources().openRawResource(R.raw.questions);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        ArrayList<GeneralQuest> listy = new ArrayList<>();

        try
        {
            String word;
            while ((word = reader.readLine()) != null)
            {
                String[] parts = word.split(";");
                String Desc = parts[1];
                switch(parts[0]){
                    case "mcq":
                        String[] ans = Arrays.copyOfRange(parts,2,6);
                        listy.add(new MCQuest(ans,1,Desc));
                        break;
                    case "estq":
                        int val = Integer.parseInt(parts[2]);
                        int tol = Integer.parseInt(parts[3]);
                        listy.add(new EstQuest(val,Desc,tol));
                        break;
                }

            }

        }
        catch (Exception ex) {
            // handle exception
            Log.v(ex.getMessage(), "message");
        }
        finally
        {
            try
            {
                inputStream.close();

            }
            catch (IOException e) {
                // handle exception
                Log.v(e.getMessage(), "message");
            }
        }
        return listy;
    }







    public GeneralQuest getQuest(int index) {
        return quests.get(index);
    }

    public int getNumberOfQuests() {
        return quests.size();
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

    public void manageElo(int correct, int total, int averageDifficulty) {
        double score = correct/total * averageDifficulty * 100;
        setHighscore((int)Math.ceil(score));

    }
}
