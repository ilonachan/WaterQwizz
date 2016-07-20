package jimdo.gladsoft.anno2016.java.waterquest;

import android.util.Log;
import jimdo.gladsoft.anno2016.java.waterquest.quests.EstQuest;
import jimdo.gladsoft.anno2016.java.waterquest.quests.GeneralQuest;
import jimdo.gladsoft.anno2016.java.waterquest.quests.MCQuest;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Arrays;

/**
 * @author Anton
 * @version ??? - 17.07.2016
 */
public class QuestReader {

    private BufferedReader mReader;
    private InputStream mStream;
    private String mCategory;

    public QuestReader(InputStream i){
        mStream = i;
        mReader = new BufferedReader(new InputStreamReader(mStream));
    }

    public GeneralQuest next() throws IOException {
        try {
            String word;
            GeneralQuest q;
            do {
                word = mReader.readLine();


                Log.v("QuestReader: next()", "Has read new line of questions.csv:\n"+word);
                if (word == null) {
                    Log.d("QuestReader: next()", "End of given Stream has been reached");
                    this.close();
                    return null;
                }
                q = decodeQuestFromString(word);
            } while (q == null);
            return q;
        } catch (Exception e) {
            this.close();
            throw e;
        }
    }

    @Nullable
    private GeneralQuest decodeQuestFromString(String word) {
        String[] parts = word.split(";");
        if(parts[0] == "rem") return null;
        if(parts.length < 2) return null;
        if(parts[0] == "cat" && parts.length >= 2) {
            mCategory = parts[1];
            return null;
        }
        String Desc = parts[1];
        String Extra = null;
        if (parts.length >= 7) Extra = parts[6];
        switch(parts[0]){
            case "mcq":
                String[] ans = Arrays.copyOfRange(parts,2,6);
                if(ans[3] == null) ans = Arrays.copyOfRange(ans,0,3);
                return new MCQuest(ans,1,Desc, Extra, mCategory);
            case "estq":
                float val = Float.parseFloat(parts[2]);
                float tol = Float.parseFloat(parts[3]);
                return /*new EstQuest(val,Desc,Extra,tol, mCategory)*/ null;
            default:
                return null;
        }
    }

    public void close() throws IOException {
        mStream.close();
    }
}
