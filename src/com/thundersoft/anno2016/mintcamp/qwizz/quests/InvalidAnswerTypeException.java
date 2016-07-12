package com.thundersoft.anno2016.mintcamp.qwizz.quests;

/**
 * @author fgast34
 * @version 1.0 - 11.07.2016.
 */
public class InvalidAnswerTypeException extends Exception {

    private Object param;
    private Class needed;

    public InvalidAnswerTypeException(Object givenType, Class neededType) {
        super("Got invalid Answer type: '"+givenType.toString()+"' is of type '"+givenType.getClass().getName()+"', expected "+neededType.getName());
    }

    public Object getParam(){
        return param;
    }

    public Class getParamType(){
        return param.getClass();
    }

    public Class getNeeded() {
        return needed;
    }
}
