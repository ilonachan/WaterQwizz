package jimdo.gladsoft.anno2016.java.waterquest;

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
