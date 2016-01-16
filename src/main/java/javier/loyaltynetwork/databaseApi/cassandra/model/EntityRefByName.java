package javier.loyaltynetwork.databaseApi.cassandra.model;

import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import javier.loyaltynetwork.model.EntityRef;

/**
 * Created by JXT0589 on 1/9/2016.
 */
@Table(keyspace = "loyalty_network", name = "entity_references_by_name")
public class EntityRefByName
{
    @PartitionKey String name;
    @Frozen
    EntityRef reference;

    public EntityRefByName()
    {
    }
    
    
    public EntityRefByName(EntityRef newRef)
    {
        reference = newRef;
        name = newRef.getName();
    }
    ///Getters for cassandra
    public String getName()
    {
        return name;
    }

    public EntityRef getReference()
    {
        return reference;
    }
    
    //setters
    public void setName(String newName)
    {
    	name = newName;
    }
    public void setReference(EntityRef newReference)
    {
    	reference = newReference;
    	name = newReference.getName();
    }


}
