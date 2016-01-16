package javier.loyaltynetwork.model;

import java.io.Serializable;

/**
 * Created by JXT0589 on 1/6/2016.
 */
@SuppressWarnings("serial")
public class BeanLong implements Serializable
{
    Long id;

    public BeanLong(Long newId)
    {
        id = newId;
    }

    public BeanLong()
    {
    }

    public Long getId()
    {
        return id;
    }
    public void setId(Long newId)
    {
        id = newId;
    }
}
