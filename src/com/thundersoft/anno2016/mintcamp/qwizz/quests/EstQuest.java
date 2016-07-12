package com.thundersoft.anno2016.mintcamp.qwizz.quests;

/**
 * @author fgast34
 * @version 1.0 - 11.07.2016.
 */
public class EstQuest extends GeneralQuest {

    private int mCorrectAnswer;
    private int mTolerance;

    @Override
    public boolean answer(Object answer) throws InvalidAnswerTypeException, InvalidArgumentException {
        if (mAnswered)
            return isAnswerCorrect();
        if (answer instanceof Integer) {
            this.mUserAnswer = answer;
            this.mAnswered = true;
            int diff = Math.abs(mCorrectAnswer - (Integer)answer);
            this.mCorrect = (diff < mTolerance);
            return mCorrect;
        } else {
            throw new InvalidAnswerTypeException(answer, Integer.class);
        }
    }

    public EstQuest(int correctAnswer, String desc) {
        this(correctAnswer,desc, 5);
    }

    public EstQuest(int correctAnswer, String desc, int tol) {
        this.mCorrectAnswer = correctAnswer;
        this.mDesc = desc;
        this.mTolerance = tol;
    }

    public int getExactAnswer() {
        if(mAnswered)
            return mCorrectAnswer;
        return 0;
    }

    public int getTolerance(){
        return mTolerance;
    }

    @Override
    public String toString() {
        return "Estimation Quest: '"+mDesc+"'\n" +
                "Correct value: "+mCorrectAnswer+"\n" +
                (mAnswered?("Answer: "+mUserAnswer+", was "+(mCorrect?"":"in")+"correct"):"Not yet answered");
    }
}
