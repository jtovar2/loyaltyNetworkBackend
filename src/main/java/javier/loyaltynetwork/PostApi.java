package javier.loyaltynetwork;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.datastax.driver.core.utils.UUIDs;

import javier.loyaltynetwork.databaseApi.DatabaseApi;
import javier.loyaltynetwork.databaseApi.cassandra.Cassandra;
import javier.loyaltynetwork.model.EntityRefContainer;
import javier.loyaltynetwork.model.Post;
import javier.loyaltynetwork.model.PostContainer;
import javier.loyaltynetwork.model.UUIDBean;
@Path("/postapi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostApi
{
    DatabaseApi dbApi;
    public PostApi()
    {
        dbApi = new DatabaseApi(Cassandra.DB.getManager());
    }
    @Path("/getposts")
    @POST
    public PostContainer getPostApi(EntityRefContainer affiliations)
    {
        return dbApi.getPostsByCreatorId(affiliations);
    }
    @Path("/addpost")
    @POST
    public Post addPost(Post newPost)
    {
        newPost.setPostId(UUIDs.random());
        newPost.setCreationTime(UUIDs.timeBased());
        return dbApi.addPost(newPost);
    }
    
    @Path("/getpostbyid")
    @POST
    public Post getPost(UUIDBean bean)
    {
        return dbApi.getPostById(bean.getId());
    }
    
}