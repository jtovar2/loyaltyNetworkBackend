package javier.loyaltynetwork.model;

import java.io.Serializable;
import java.util.List;
@SuppressWarnings("serial")
public class EntityRefContainer implements Serializable
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