package com.thundersoft.anno2016.mintcamp.qwizz;

import com.thundersoft.anno2016.mintcamp.qwizz.quests.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author fgast34
 * @version 1.0 - 11.07.2016.
 */
public final class Main {

    private static List<GeneralQuest> quests = Arrays.asList(new GeneralQuest[]{
        new MCQuest(new String[]{"10:00 Uhr", "10:30 Uhr", "11:00 Uhr"},2,"Um wie viel Uhr startet das SummerCamp?"),
        new EstQuest(150,"Wieviele Minuten arbeiten wir hier nun schon?",10),
        new MCQuest(new String[]{"Deutschland","Italien","Frankreich","Portugal"},4,"Wer ist Europameister 2016?"),
        new MCQuest(new String[]{"Wirtschaftsinformatik","Volkswirtschaftslehre","Betriebswirtschaftslehre"},2,"Welches Fach kann man an der FHDW nicht studieren?")
    });

    private Main(){}

    public static void playMCQuest(MCQuest quest){
        System.out.println(quest.getDescription()+"\n");
        for(int i = 0; i < quest.getAnswers().length; i++) {
            System.out.println((i + 1) + ". " + quest.getAnswers()[i]);
        }
        int answerVal = 2;
        try {
            quest.answer(answerVal);
        } catch (InvalidAnswerTypeException e) {
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            return;
        }

        if(quest.isAnswerCorrect()){
            System.out.println("\nYes! That was the correct answer!\n");
        } else {
            System.out.println("\nNope, that´s wrong.\n");
        }
    }

    public static void playEstQuest(EstQuest quest){
        System.out.println(quest.getDescription()+"\n");

        int answerVal = 2;
        try {
            quest.answer(answerVal);
        } catch (InvalidAnswerTypeException e) {
        } catch (InvalidArgumentException e2) {}

        if(quest.isAnswerCorrect()){
            System.out.println("\nYou´re good, the total value was "+quest.getExactAnswer()+"\n");
        } else {
            System.out.println("\nYou´re too far, the total value was "+quest.getExactAnswer()+"\n");
        }
    }


    public static void main(String[] args) {
        for(GeneralQuest q : quests) {
            if(q instanceof MCQuest) {
                playMCQuest((MCQuest)q);
            }
            if(q instanceof EstQuest) {
                playEstQuest((EstQuest)q);
            }
            System.out.println(q.toString());
        }
    }
}
