package javier.loyaltynetwork.model;

import java.io.Serializable;

/**
 * Created by JXT0589 on 1/6/2016.
 */
@SuppressWarnings("serial")
public class BooleanBean implements Serializable{
    Boolean result;
    public BooleanBean(boolean newBoolean)
    {
        result = newBoolean;
    }
    public BooleanBean()
    {
    }

    public Boolean getBoolean()
    {
        return result;
    }
    public void setBoolean(Boolean newBoolean)
    {
        result = newBoolean;
    }
}
