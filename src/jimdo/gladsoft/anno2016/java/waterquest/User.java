package jimdo.gladsoft.anno2016.java.waterquest;

import jimdo.gladsoft.anno2016.java.waterquest.quests.QuestManager;

import java.io.Serializable;

/**
 * @author fgast34
 * @version ??? - 14.07.2016.
 */
public class User implements Serializable{

    private QuestManager mManager;
    private String mName;

    public User(String name) {
        this.mName = name;
        this.mManager = new QuestManager();
    }

    public int getElo() {
        return mManager.getElo();
    }

    public int getHighscore() {
        return mManager.getHighscore();
    }

    public String getName() {
        return this.mName;
    }

    public QuestManager getManager(){
        return mManager;
    }
}
