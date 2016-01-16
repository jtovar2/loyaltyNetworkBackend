package javier.loyaltynetwork.model;



import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Table;
import javier.loyaltynetwork.databaseApi.cassandra.model.PostByIdAndType;

import java.util.Date;
import java.util.UUID;

/**
 * Created by JXT0589 on 12/31/2015.
 */
@Table(keyspace = "loyalty_network", name = "posts")
public class Post
{
    private String title;
    private String body;
    private @Column(name = "entity_type") String entityType;
    @Frozen
    private EntityRef creator;

    @Column(name = "creator_id")
    private  UUID creatorId;

    @ClusteringColumn(0) @Column(name = "post_id")
    private UUID postId;
    @PartitionKey(0) @Column(name = "creation_time") private UUID creationTime;


    public Post()
    {}
	//setters for cassandra
    public void setPostId(UUID newId)
    {
        postId = newId;
    }
    public void setCreationTime(UUID newTime)
    {
        creationTime = newTime;
    }
	public void setCreator(EntityRef newCreator)
    	{
	       	creator = newCreator;
	        creatorId = newCreator.getId();
        	entityType = newCreator.getType();
    	}
	public void setTitle(String newTitle)
	{
		title = newTitle;
	}
	public void setBody(String newBody)
	{
		body = newBody;
	}
	public void setEntityType(String newEntityType)
	{
		entityType = newEntityType;
	}
	
	
	//getters for casandra
    public String getTitle()
    {
        return title;
    }
    public String getBody()
    {
        return body;
    }
    public UUID getPostId()
    {
        return postId;
    }

    public EntityRef getCreator()
    {
        return creator;
    }
    public UUID getCreationTime()
    {
    	return creationTime;
    }
    public UUID getCreatorId()
    {
    	return creatorId;
    }
    public String getEntityType()
    {
    	return entityType;
    }
    
    public Date getTime()
    {
        return new Date(creationTime.timestamp());
    }
    

    public Post(UUID newPostId, EntityRef newCreator, UUID newCreationTime, String newTitle, String newBody)
    {
        postId = newPostId;
        creator = newCreator;
        body = newBody;
        title = newTitle;
        creationTime = newCreationTime;
        creatorId = newCreator.getId();
        entityType = newCreator.getType();
    }

    public PostByIdAndType toPostByIdAndType()
    {
        return new PostByIdAndType(postId, creator, creationTime, title, body);
    }
}
