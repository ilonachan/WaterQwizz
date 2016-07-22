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

	    String Desc = getArrSafe(parts,1);
	    String Extra = getArrSafe(parts,2);
	    if(Extra == "") Extra = null;
        switch(getArrSafe(parts,0)){
            case "mcq":
                String[] ans = Arrays.copyOfRange(parts,3,7);
                if(ans[3] == null) ans = Arrays.copyOfRange(ans,0,3);
                return new MCQuest(ans,1,Desc, Extra, mCategory);
            case "estq":
                double val = Double.parseDouble(parts[3]);
                double tol = Double.parseDouble(parts[4]);
                return new EstQuest(val,Desc,Extra,tol, mCategory);
	        case "cat":
		        if(parts.length < 2) return null;
		        mCategory = getArrSafe(parts,1);
		        return null;
            default:
                return null;
        }
    }

	private String getArrSafe(String[] list, int index) {
		try{
			return list[index];
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			return null;
		}
	}

    public void close() throws IOException {
        mStream.close();
    }
}
