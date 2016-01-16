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
@Table(keyspace = "loyalty_network", name = "posts_creator_id_and_entity_type_and_creation")
public class PostByIdAndType
{
    @PartitionKey(0)
    @Column(name = "creator_id")
    UUID creatorId;

    @PartitionKey(1)
    @Column(name ="entity_type")
    String entityType;


    @ClusteringColumn(2)
    @Column(name = "creation_time")
    UUID creationTime;

    String body;
    String title;
    @Frozen
    EntityRef creator;
    @Column(name = "post_id") UUID postId;

    public PostByIdAndType(UUID newPostId, EntityRef newCreator, UUID newCreationTime, String newTitle, String newBody)
    {
        postId = newPostId;
        creator = newCreator;
        body = newBody;
        title = newTitle;
        creationTime = newCreationTime;
        creatorId = newCreator.getId();
        entityType = newCreator.getType();
    }

    public Post toPost()
    {
        return new Post(postId, creator, creationTime, title, body);
    }

}
