package com.thundersoft.anno2016.mintcamp.qwizz;

import java.io.Serializable;

/**
 * @author fgast34
 * @version ??? - 15.07.2016.
 */
public class UserManager implements Serializable {

    User current;

    public UserManager(String name) {
        current = new User(name);
    }

    public User getCurrent() {
        return current;
    }

    public void editCurrent(User user) {
        this.current = user;
    }
}
