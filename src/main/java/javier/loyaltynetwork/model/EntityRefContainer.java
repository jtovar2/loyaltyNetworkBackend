package javier.loyaltynetwork.model;

import java.util.List;

public class EntityRefContainer
{
    List<EntityRef> refs;
    
    public EntityRefContainer()
    {}
    
    public void setEntityReferences(List<EntityRef> newRefs)
    {
        refs = newRefs;
    }
    public List<EntityRef> getEntityReferences()
    {
        return refs;
    }
}