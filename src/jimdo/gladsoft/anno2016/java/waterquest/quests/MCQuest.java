package jimdo.gladsoft.anno2016.java.waterquest.quests;

/**
 * @author Anton Schlömer
 * @version 1.0 - 11.07.2016.
 */
public class MCQuest extends GeneralQuest {

    private int mCorrectAnswer;
    private String[] mAnswers;


    public int getCorrect() {
        if(hasUserAnswered())
            return mCorrectAnswer;
        return 0;
    }

    @Override
    public boolean answer(Object answer) throws InvalidAnswerTypeException, InvalidArgumentException {
        if (mAnswered)
            return isAnswerCorrect();
        if (answer instanceof Integer) {
            if((Integer)answer <= 0 || (Integer)answer > mAnswers.length)
                throw new InvalidArgumentException("Answer must be in range of 1 and " + mAnswers.length, answer);
            this.mUserAnswer = answer;
            this.mAnswered = true;
            this.mCorrect = (mCorrectAnswer == (Integer) answer);
            return mCorrect;
        } else {
            throw new InvalidAnswerTypeException(answer, Integer.class);
        }
    }

    public MCQuest(String[] answers, int correctAnswer, String desc, String extra) {
        this(answers, correctAnswer, desc, extra, null);
    }

    public MCQuest(String[] answers, int correctAnswer, String desc, String extra, String category){
        super(desc, extra, category);
        this.mAnswers = answers;
        this.mCorrectAnswer = correctAnswer;
        this.mDesc = desc;

        shuffle();
    }

    public String[] getAnswers() {
        return mAnswers;
    }

    @Override
    public String toString() {
        String str = "Multiple-Choice Quest: '"+mDesc+"'\n"/* +
                "Answers:\n";
        for(int i = 0; i < mAnswers.length; i++) {
            str += (i+1)+". "+mAnswers[i]+"\n";
        }
        str += "Correct answer: "+mCorrectAnswer+"\n" +
                (mAnswered?("Answer: "+mUserAnswer+", was "+(mCorrect?"":"in")+"correct"):"Not yet answered")*/;
        return str;
    }

    protected void shuffle(){
        for(int x = mAnswers.length - 1; x >= 0; x--) {
            for(int y = 0; y < x; y++) {
                if(Math.random() < 0.5) {
                    String h = mAnswers[y];
                    mAnswers[y] = mAnswers[y+1];
                    mAnswers[y+1] = h;

                    if(mCorrectAnswer == y+1) mCorrectAnswer = y+2;
                    else if(mCorrectAnswer == y+2) mCorrectAnswer = y+1;
                }
            }
        }
    }
}
