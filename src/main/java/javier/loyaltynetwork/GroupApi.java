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
import javier.loyaltynetwork.model.UserAndGroupRefContainer;
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
    public void addGroupFollower(UserAndGroupRefContainer container)
    {
        dbApi.addGroupFollower(container.getUser(), container.getGroupRef());
    }
    @POST
    @Path("/addmember")
    public void addGroupMember(UserAndGroupRefContainer container)
    {
        dbApi.addGroupMember(container.getUser(), container.getGroupRef());
    }
    
    @POST
    @Path("/setleader")
    public void setGroupLeader(UserAndGroupRefContainer container)
    {
        dbApi.setGroupLeader(container.getUser(), container.getGroupRef());
    }
    
    //removers for group and user relationships
    @POST
    @Path("/removemember")
    public void removeGroupMember(UserAndGroupRefContainer container)
    {
        dbApi.removeGroupMember(container.getUser(), container.getGroupRef());
    }
    @POST
    @Path("/removefollower")
    public void removeGroupFollower(UserAndGroupRefContainer container)
    {
        dbApi.removeGroupFollower(container.getUser(), container.getGroupRef());
        
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
    public Group getGroup(EntityRef groupRef)
    {
        return dbApi.getGroup(groupRef);
    }
    
}