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

}
