package javier.loyaltynetwork;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.datastax.driver.core.utils.UUIDs;

import javier.loyaltynetwork.databaseApi.DatabaseApi;
import javier.loyaltynetwork.databaseApi.cassandra.Cassandra;
import javier.loyaltynetwork.model.BooleanBean;
import javier.loyaltynetwork.model.EntityRef;
import javier.loyaltynetwork.model.User;
import javier.loyaltynetwork.model.Group;
@Path("/groupapi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupApi
{
    DatabaseApi dbApi;
    
    public GroupApi()
    {
        dbApi = new DatabaseApi(Cassandra.DB.getManager());
    }
    
    //Adders for group and user relationships
    @POST
    @Path("/addfollower")
    public void addGroupFollower(User user, EntityRef groupRef)
    {
        dbApi.addGroupFollower(user, groupRef);
    }
    @POST
    @Path("/addmember")
    public void addGroupMember(User user, EntityRef groupRef)
    {
        dbApi.addGroupMember(user, groupRef);
    }
    
    @POST
    @Path("/setleader")
    public void setGroupLeader(User user, EntityRef groupRef)
    {
        dbApi.setGroupLeader(user, groupRef);
    }
    
    //removers for group and user relationships
    @POST
    @Path("/removemember")
    public void removeGroupMember(User user, EntityRef groupRef)
    {
        dbApi.removeGroupMember(user, groupRef);
    }
    @POST
    @Path("/removefollower")
    public void removeGroupFollower(User user, EntityRef groupRef)
    {
        dbApi.removeGroupFollower(user, groupRef);
        
    }
    @POST
    @Path("/addgroup")
    public EntityRef addGroup(Group newGroup)
    {
        newGroup.setId(UUIDs.random());
        return dbApi.addGroup(newGroup);
    }
    @POST
    @Path("/changename")
    public EntityRef changeGroupName(EntityRef groupRef, String newName)
    {
        return dbApi.changeGroupName(groupRef, newName);
        
    }
    @POST
    @Path("/changemission")
    public void chageGroupMission(EntityRef groupRef, String newMission)
    {
        dbApi.changeGroupMission(groupRef, newMission);
    }
    
    @POST
    @Path("/getgroup")
    public Group getGroup(EntityReg groupRef)
    {
        return dbApi.getGroup(groupRef);
    }
    
}