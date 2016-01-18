package javier.loyaltynetwork;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import javier.loyaltynetwork.databaseApi.DatabaseApi;
import javier.loyaltynetwork.databaseApi.cassandra.Cassandra;
import javier.loyaltynetwork.model.EntityRefContainer;
import javier.loyaltynetwork.model.Post;
import javier.loyaltynetwork.model.PostContainer;
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
        return dbApi.addPost(newPost);
    }
}