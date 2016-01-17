package javier.loyaltynetwork.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class UserAndGroupRefContainer implements Serializable
{
    private User user;
    private EntityRef groupRef;
    public UserAndGroupRefContainer()
    {}
    public User getUser()
    {
        return user;
    }
    public EntityRef getGroupRef()
    {
        return groupRef;
    }
    
    public void setUser(User newUser)
    {
        user = newUser;
    }
    public void setGroupRef(EntityRef newGroupRef)
    {
        groupRef = newGroupRef;
    }
}