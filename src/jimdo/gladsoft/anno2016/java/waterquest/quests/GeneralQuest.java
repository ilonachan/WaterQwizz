package jimdo.gladsoft.anno2016.java.waterquest.quests;

import java.io.Serializable;

/**
 * The <code>GeneralQuest</code> class represents what is a question in a quiz. It is held abstract due to the
 * fact that there are incredibly many possible types of Questions, only to mention the Multiple-Choice
 * and the estimation.
 *
 * @author Anton Schlömer
 * @version 1.0 - 11.07.2016.
 * @see MCQuest
 */
public abstract class GeneralQuest implements Serializable{

    protected boolean mCorrect;
    protected boolean mAnswered;
    protected Object mUserAnswer;
    protected String mDesc;
    protected String mExtra;
    protected String mCategory;

    /**
     * The method handles the request of the user to answer the question.
     *
     * @param answer:Object The answer our user is giving. It is an object to be casted/evaluated in the function.
     * @return If the answer was correctly given (can be done later with <code>isAnswerCorrect()</code>)
     * @throws InvalidAnswerTypeException Is thrown if the answer does not match the expected type
     * @throws InvalidArgumentException Thrown if the answer, though of the correct type, cannot be evaluated
     */
    public abstract boolean answer(Object answer) throws InvalidAnswerTypeException, InvalidArgumentException;

    public Object getUserAnswer(){
        return mUserAnswer;
    }
    public boolean isAnswerCorrect(){
        return mCorrect;
    }
    public boolean hasUserAnswered(){
        return mAnswered;
    }
    public String getDescription() {
        return mDesc;
    }
    public void setAnswered(boolean c){
        mAnswered = true;
        mCorrect = c;
    }

    public void retry(){
        mAnswered = false;
        mCorrect = false;
        mUserAnswer = null;
    }

    public String getExtra() {
        return mExtra;
    }

    public GeneralQuest(String desc, String extra) {
        this(desc, extra, null);
    }

    public GeneralQuest(String desc, String extra, String category) {
        this.mDesc = desc;
        this.mExtra = extra;
        this.mCategory = category;
    }

    public String getCategory() {
        if(mCategory == null) return "";
        return this.mCategory;
    }
}
