package jimdo.gladsoft.anno2016.java.waterquest.quests;

/**
 * @author fgast34
 * @version 1.0 - 11.07.2016.
 */
public class EstQuest extends GeneralQuest{

    private double mCorrectAnswer;
    private double mTolerance;

    @Override
    public boolean answer(Object answer) throws InvalidAnswerTypeException, InvalidArgumentException {
        if (mAnswered)
            return isAnswerCorrect();
        if (answer instanceof Double) {
            this.mUserAnswer = answer;
            this.mAnswered = true;
            double diff = Math.abs(mCorrectAnswer - (double)answer);
            this.mCorrect = (diff <= mTolerance);
            return mCorrect;
        } else {
            throw new InvalidAnswerTypeException(answer, Double.class);
        }
    }

    public EstQuest(float correctAnswer, String desc, String extra) {
        this(correctAnswer,desc, extra, null);
    }

    public EstQuest(float correctAnswer, String desc, String extra, String category) {
        this(correctAnswer,desc,extra,5,category);
    }

    public EstQuest(float correctAnswer, String desc, String extra, float tol) {
        this(correctAnswer,desc,extra,tol,null);
    }

    public EstQuest(float correctAnswer, String desc, String extra, float tol, String category) {
        super(desc, extra, category);
        this.mCorrectAnswer = correctAnswer;
        this.mTolerance = tol;
    }

    public double getExactAnswer() {
        if(mAnswered)
            return mCorrectAnswer;
        return 0;
    }

    public double getTolerance(){
        return mTolerance;
    }

    @Override
    public String toString() {
        return "Estimation Quest: '"+mDesc+"'\n" +
                "Correct value: "+mCorrectAnswer+"\n" +
                (mAnswered?("Answer: "+mUserAnswer+", was "+(mCorrect?"":"in")+"correct"):"Not yet answered");
    }
}
