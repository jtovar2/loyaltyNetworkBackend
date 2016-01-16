package javier.loyaltynetwork.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.FrozenValue;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.google.common.base.Objects;
import javier.loyaltynetwork.databaseApi.cassandra.model.*;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by JXT0589 on 12/31/2015.
 */
@Table(keyspace = "loyalty_network", name = "users")
public class User
{
    @PartitionKey private UUID id;
    private String name;
    @Column(name = "hashed_password")
    private String hashedPassword;
    private String mission;
    @FrozenValue
    private Set<EntityRef> affiliations;
    @Frozen
    private EntityRef reference;

    public User()
    {

    }
    public User(UUID newId, String newName, String newHashedPassword, String newMission, Set<EntityRef> newAffiliations)
    {
        id = newId;
        name = newName;
        hashedPassword = newHashedPassword;
        mission = newMission;
        affiliations = newAffiliations;
        reference = new EntityRef(id, name, "user");
    }

    //Getters
    public UUID getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public String getHashedPassword()
    {
        return hashedPassword;
    }
    public Set<EntityRef> getAffiliations()
    {
        return affiliations;
    }
    public String getMission()
    {
        return mission;
    }
    public EntityRef getReference()
    {
        return reference;
    }

    //Setters
    public void setName(String newName)
    {
        name = newName;
        reference = new EntityRef(id, name, "user");
    }
    public void setMission(String newMission)
    {
        mission = newMission;
    }
    public void setHashedPassword(String newPassword)
    {
        hashedPassword = newPassword;
    }
	public void setId(UUID newId)
	{
		id = newId;
	}
	public void setAffiliations(Set<EntityRef> newAffiliations)
	{
		affiliations = newAffiliations;
	}

	public void setReference(EntityRef newRef)
	{
		reference = newRef;
	}

	public void generateEntityRef()
	{
		reference = new EntityRef(id, name, "user");
	}
    //Affiliations modifications
    public void addAffilitation(EntityRef newReference)
    {
        if(affiliations == null)
        {
            affiliations = (Set<EntityRef>) new HashSet<EntityRef>();
        }
        affiliations.add(newReference);
    }
    public void removeAffiliation(EntityRef reference)
    {
        if(affiliations != null)
        {
            affiliations.remove(reference);
        }
    }

    @Override
    public boolean equals(Object object)
    {
        if( object instanceof User)
        {
            User otherUser = (User) object;
            return Objects.equal(id, otherUser.getId());
        }
        else return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(id);
    }

    public UserByNameAndHashedPassword toUserByNameAndHashedPassword()
    {
        return new UserByNameAndHashedPassword(id, name, hashedPassword, mission, affiliations);
    }
}
