package javier.loyaltynetwork.model;

import java.io.Serializable;
import java.util.ArrayList;
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
    public void addRef(EntityRef ref)
    {
        if(refs == null)
        {
            refs = new ArrayList<EntityRef>();
        }
        refs.add(ref);
    }
}