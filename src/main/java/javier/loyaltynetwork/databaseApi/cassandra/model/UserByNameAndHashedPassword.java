package javier.loyaltynetwork.databaseApi.cassandra.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.FrozenValue;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import javier.loyaltynetwork.model.EntityRef;
import javier.loyaltynetwork.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by JXT0589 on 1/9/2016.
 */
@Table(keyspace = "loyalty_network", name = "users_by_name_and_hashed_password")
public class UserByNameAndHashedPassword
{
    @PartitionKey(0)
    String name;
    @PartitionKey(1) @Column(name = "hashed_password")
    String hashedPassword;

    UUID id;
    String mission;
    @FrozenValue
    Set<EntityRef> affiliations;
    @Frozen EntityRef reference;

    public UserByNameAndHashedPassword()
    {}

    public UserByNameAndHashedPassword(UUID newId, String newName, String newHashedPassword, String newMission, Set<EntityRef> newAffiliations)
    {
        id = newId;
        name = newName;
        hashedPassword = newHashedPassword;
        mission = newMission;
        affiliations = newAffiliations;
        reference = new EntityRef(id, name, "user");
    }

    public User toUser()
    {
        return new User(id, name, hashedPassword, mission, affiliations);
    }
    ///getters for cassandra
    public String getName()
    {
    	return name;
    }
    public String getHashedPassword()
    {
    	return hashedPassword;
    }
    public UUID getId()
    {
    	return id;
    }
    public String getMission()
    {
    	return mission;
    }
    public Set<EntityRef> getAffiliations()
    {
    	return affiliations;
    }
    public EntityRef getReference()
    {
    	return reference;
    }
    
    //setters for cassadnra
    public void setName(String newName)
    {
    	name = newName;
    }
    public void setHashedPassword(String newPassword)
    {
    	hashedPassword = newPassword;
    }
    public void setId(UUID newId)
    {
    	id = newId;
    }
    public void setMission(String newMission)
    {
    	mission = newMission;
    }
    public void setAffiliations(Set<EntityRef> newAffiliations)
    {
    	affiliations = newAffiliations;
    }
    public void setReference(EntityRef newRef)
    {
    	reference = newRef;
    }
}

