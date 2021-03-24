package database.objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Checkable {
    protected BooleanProperty ifChecked = new SimpleBooleanProperty(false);

    public boolean ifChecked(){
        return ifChecked.getValue();
    }

    public BooleanProperty getIfChecked() {
        return ifChecked;
    }

    public void setIfChecked(boolean ifChecked) {
        this.ifChecked.set(ifChecked);
    }
}
