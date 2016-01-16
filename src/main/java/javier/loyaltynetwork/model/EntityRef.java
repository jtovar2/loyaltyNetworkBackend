package javier.loyaltynetwork.model;
import com.datastax.driver.mapping.annotations.UDT;
import com.google.common.base.Objects;
import javier.loyaltynetwork.databaseApi.cassandra.model.*;
import com.datastax.driver.mapping.annotations.Field;

import java.util.UUID;

/**
 * Created by JXT0589 on 1/8/2016.
 */
@UDT(keyspace = "loyalty_network", name = "entity_ref")
public class EntityRef
{
    UUID id;
    String name;
	@Field(name = "entity_type")
    String type;

    public EntityRef()
    {
    }

    public EntityRef(UUID newId, String newName, String newType)
	{
        id = newId;
        name = newName;
        type = newType;
    }

    public UUID getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

	//setters
	public void setId(UUID newId)
	{
		id = newId;
	}

	public void setName(String newName)
	{
		name = newName;
	}

	public void setType(String newType)
	{
		type = newType;
	}
    @Override
    public boolean equals(Object object)
    {
        if(object instanceof EntityRef)
        {
            EntityRef otherReference = (EntityRef) object;
            return Objects.equal(id, otherReference.getId()) && Objects.equal(type, otherReference.getType());
        }
        else return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(id, type);
    }

    public EntityRefByName toEntityRefByName()
    {
        return new EntityRefByName(this);
    }
}
