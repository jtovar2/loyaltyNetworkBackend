package javier.loyaltynetwork.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.HashSet;
import java.util.UUID;

/**
 * Created by Shannor on 12/25/2015.
 */
@Table(keyspace = "loyalty_network", name = "groups")
public class Group{

    @PartitionKey
    @Column(name = "group_id")
    UUID id;
    String name;
    String mission;
    @Frozen
    EntityRef reference;
    @Frozen
    EntityRef leader;
    @Frozen
    HashSet<EntityRef> admins;
    @Frozen
    HashSet<EntityRef> members;
    @Frozen
    HashSet<EntityRef> followers;

    public Group()
    {}

    public Group(UUID newId, String newName, String newMission, EntityRef newLeader)
    {
        id = newId;
        name = newName;
        mission = newMission;
        reference = new EntityRef(id, name, "group");
        leader = newLeader;

        admins = new HashSet<EntityRef>();
        members = new HashSet<EntityRef>();
        followers = new HashSet<EntityRef>();
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

    public String getMission()
    {
        return mission;
    }

    public EntityRef getReference()
    {
        return reference;
    }

    public EntityRef getLeader()
    {
        return leader;
    }

    public HashSet<EntityRef> getAdmins()
    {
        return admins;
    }

    public HashSet<EntityRef> getMembers()
    {
        return members;
    }

    public HashSet<EntityRef> getFollowers()
    {
        return followers;
    }


    //Setters
	public void setReference(EntityRef newRef)
	{
		reference = newRef;
	}
	public void setFollowers(Set<EntityRef> newFollowers)
	{
		followers = newFollowers;
	}
	public void setAdmins(Set<EntityRef> newAdmins)
	{
		admins = newAdmins;
	}
	public void setMembers(Set<EntityRef> newMembers)
	{
		members = newMembers;
	}

    public void setName(String newName)
    {
        name = newName;
        reference = new EntityRef(id, name, "group");
    }

    public void setMission(String newMission)
    {
        mission = newMission;
    }

    public void setLeader(User newLeader)
    {
        leader = newLeader.getReference();
    }

    public void setLeader(EntityRef newLeader)
    {
        leader = newLeader;
    }

    ///User and group relationships
	public void setId(UUID newId)
	{
		id = newId;
	}

    public void addFollower(EntityRef newFollower)
    {
        if(members != null)
        {
            members.remove(newFollower);
        }
        if(admins != null)
        {
            admins.remove(newFollower);
        }
        if(followers == null)
        {
            followers = new HashSet<EntityRef>();
        }
        followers.add(newFollower);
    }

    public void addMember(EntityRef newMember)
    {
        if(admins != null) {
            admins.remove(newMember);
        }
        if(followers != null)
        {
            followers.remove(newMember);
        }


        if(members == null)
        {
            members = new HashSet<EntityRef>();
        }
        members.add(newMember);
    }

    public void addAdmin(EntityRef newAdmin) {
        if (followers != null) {
            followers.remove(newAdmin);
        }
        if (members != null){

            members.remove(newAdmin);
        }
        if(admins == null)
        {
            admins = new HashSet<EntityRef>();
        }
        admins.add(newAdmin);
    }

    public void removeFollower(EntityRef follower)
    {
        followers.remove(follower);
    }

    public void removeMember(EntityRef member)
    {
        members.remove(member);
    }

    public void removeAdmin(EntityRef admin)
    {
        admins.remove(admin);
    }

}
