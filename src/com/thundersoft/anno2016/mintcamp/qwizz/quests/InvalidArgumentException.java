package com.thundersoft.anno2016.mintcamp.qwizz.quests;

/**
 * @author fgast34
 * @version ??? - 12.07.2016.
 */
public class InvalidArgumentException extends Exception {

    private String misactDesc;
    private Object paramValue;

    public InvalidArgumentException(String mis, Object val) {
        this.misactDesc = mis;
        this.paramValue = val;
    }

    public Object getParam() {
        return paramValue;
    }

    public String getDesc() {
        return misactDesc;
    }
}
