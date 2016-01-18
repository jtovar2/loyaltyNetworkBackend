package javier.loyaltynetwork.databaseApi.cassandra.model;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import javier.loyaltynetwork.model.EntityRef;
import javier.loyaltynetwork.model.Post;

import java.util.UUID;

/**
 * Created by JXT0589 on 1/9/2016.
 */
@Table(keyspace = "loyalty_network", name = "posts_by_creator_id_and_type_and_creation_time")
public class PostByIdAndType
{
	//TODO change entity_type column to creator_type, also change class variable entityType to creatorType
    @PartitionKey(0)
    @Column(name = "creator_id")
    UUID creatorId;

    @PartitionKey(1)
    @Column(name ="entity_type")
    String entityType;


    @ClusteringColumn(0)
    @Column(name = "creation_time")
    UUID creationTime;

    String body;
    String title;
    @Frozen
    EntityRef creator;
    @Column(name = "post_id") UUID postId;

    public PostByIdAndType(UUID newPostId, EntityRef newCreator, UUID newCreatorId, String newCreatorType, UUID newCreationTime, String newTitle, String newBody)
    {
        postId = newPostId;
        creator = newCreator;
        body = newBody;
        title = newTitle;
        creationTime = newCreationTime;
        creatorId = newCreatorId;
        entityType = newCreatorType;
    }
    public PostByIdAndType()
    {}
    public Post toPost()
    {
        return new Post(postId, creator, creatorId, entityType, creationTime, title, body);
    }
    
    //setters for casssandra
    public void setCreatorId(UUID newCreatorId)
    {
    	creatorId = newCreatorId;
    }
    public void setEntityType(String newEntityType)
    {
    	entityType = newEntityType;
    }
    public void setCreationTime(UUID newCreationTime)
    {
    	creationTime = newCreationTime;
    }
    public void setBody(String newBody)
    {
    	body = newBody;
    }
    public void setTitle(String newTitle)
    {
    	title = newTitle;
    }
    public void setCreator(EntityRef newCreator)
    {
    	creator = newCreator;
    	creatorId = newCreator.getId();
    	entityType = newCreator.getType();
    }
    public void setPostId(UUID newPostId)
    {
    	postId = newPostId;
    }
    
    //getters for cassandra
    public UUID getCreatorId()
    {
    	return creatorId;
    }
    public String getEntityType()
    {
    	return entityType;
    }
    public UUID getCreationTime()
    {
    	return creationTime;
    }
    public String getBody()
    {
    	return body;
    }
    public String getTitle()
    {
    	return title;
    }
    public EntityRef getCreator()
    {
    	return creator;
    }
    public UUID getPostId()
    {
    	return postId;
    }
    

}
