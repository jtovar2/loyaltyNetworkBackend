package javier.loyaltynetwork;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.datastax.driver.core.utils.UUIDs;
import java.util.HashSet;

import javier.loyaltynetwork.databaseApi.DatabaseApi;
import javier.loyaltynetwork.databaseApi.cassandra.Cassandra;
import javier.loyaltynetwork.model.BooleanBean;
import javier.loyaltynetwork.model.EntityRef;
import javier.loyaltynetwork.model.EntityRefContainer;
import javier.loyaltynetwork.model.User;


@Path("/userapi")
@Produces(MediaType.APPLICATION_JSON)
public class UserApi
{
	
	DatabaseApi dbApi;
	
	public UserApi()
	{
		dbApi = new DatabaseApi(Cassandra.DB.getManager());
	}
	
	@GET
	@Path("/authuser/{username}/{password}")
	public BooleanBean authenticateUser(@PathParam("username") String username, @PathParam("password") String hashedPassword)
	{
		return new BooleanBean(dbApi.authenticateUser(username, hashedPassword));
		
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/adduser")
	public EntityRef addUser(User newUser)
	{
		newUser.setId(UUIDs.random());
		newUser.generateEntityRef();
		newUser.setAffiliations(new HashSet<EntityRef>());
		return dbApi.addUser(newUser);
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getuser")
	public User getUser(EntityRef userRef)
	{
		return dbApi.getUser(userRef);
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/lookupuser/{username}")
	public EntityRefContainer lookupUsername(@PathParam("username") String username)
	{
	    EntityRefContainer container = new EntityRefContainer();
	    
		container.addRef(dbApi.getEntityRef(username));
		return container;
	}
}
