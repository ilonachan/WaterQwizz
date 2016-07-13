package com.thundersoft.anno2016.mintcamp.qwizz.android.quests;

import com.thundersoft.anno2016.mintcamp.qwizz.quests.EstQuest;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.GeneralQuest;
import com.thundersoft.anno2016.mintcamp.qwizz.quests.MCQuest;

import java.util.*;

/**
 * @author fgast34
 * @version ??? - 12.07.2016.
 */
public class QuestManager {

    private List<GeneralQuest> quests;
    private int score;
    private int mQuestIndex;

    public QuestManager() {
        quests = Arrays.asList(new GeneralQuest[]{
                new MCQuest(new String[]{"10:00 Uhr", "10:30 Uhr", "11:00 Uhr"}, 2, "Um wie viel Uhr startet das SummerCamp?"),
                new EstQuest(150,"Wieviele Minuten arbeiten wir hier nun schon?",10),
                new MCQuest(new String[]{"Deutschland", "Italien", "Frankreich", "Portugal"}, 4, "Wer ist Europameister 2016?"),
                new MCQuest(new String[]{"Wirtschaftsinformatik", "Volkswirtschaftslehre", "Betriebswirtschaftslehre"}, 2, "Welches Fach kann man an der FHDW nicht studieren?")
        });





        Collections.sort(quests, new Comparator<GeneralQuest>() {
            Random r = new Random();
            @Override
            public int compare(GeneralQuest a1, GeneralQuest a2)
            {

                return r.nextInt(3)-1;
            }
        });
    }

    public GeneralQuest getQuest(int index) {
        return quests.get(index);
    }

    public int getNumberOfQuests() {
        return quests.size();
    }

    public int getScore() {
        return score;
    }

    public int addScore() {
        score++;
        return score;
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

}
