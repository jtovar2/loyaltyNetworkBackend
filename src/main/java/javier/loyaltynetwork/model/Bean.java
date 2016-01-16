package javier.loyaltynetwork.model;

/**
 * Created by Javier on 12/31/2015.
 */
public class Bean {

    String name;

    public Bean() {
        name = null;
    }

    public Bean(String newName) {
        name = newName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
