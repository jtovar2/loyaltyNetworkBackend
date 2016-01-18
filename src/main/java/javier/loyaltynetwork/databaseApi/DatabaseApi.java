package javier.loyaltynetwork.databaseApi;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Mapper;
//import com.datastax.driver.mapping.UDTMapper;
import com.datastax.driver.mapping.MappingManager;
import javier.loyaltynetwork.model.*;
import javier.loyaltynetwork.databaseApi.cassandra.Cassandra;
import javier.loyaltynetwork.databaseApi.cassandra.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
/**
 * Created by JXT0589 on 1/4/2016.
 */
public class DatabaseApi
{
    public static Logger logger;
    public static String POSTTABLE = "posts_by_creator_id_and_type_and_creation_time";
    MappingManager manager;
    Mapper<Group> groupMapper;
    Mapper<User> userMapper;
    Mapper<Post> postMapper;
    Mapper<EntityRefByName> refMapper;
    Mapper<PostByIdAndType> postByIdAndTypeMapper;
    Mapper<UserByNameAndHashedPassword> userByNameAndHashedPasswordMapper;
	//UDTMapper<EntityRef> refUDTMapper;
    //TODO make this class not static and to allow saving and reading from cassandra

    //This constant is used to determine the length of time a post is relevant
    static Long TIME_WINDOW = new Long(12*60*60*1000);

    public DatabaseApi(MappingManager newManager)
    {
        manager = newManager;
        manager.udtCodec(EntityRef.class);
        groupMapper = manager.mapper(Group.class);
        userMapper = manager.mapper(User.class);
        postMapper = manager.mapper(Post.class);
        refMapper = manager.mapper(EntityRefByName.class);
        postByIdAndTypeMapper = manager.mapper(PostByIdAndType.class);
        userByNameAndHashedPasswordMapper = manager.mapper(UserByNameAndHashedPassword.class);
        logger = Logger.getLogger("database api");

	//refUDTMapper = manager.udtMapper(EntityRef.class);

    }
    ///Methods for saving a user
    private void saveUser(User user)
    {
        userMapper.save(user);
        userByNameAndHashedPasswordMapper.save(user.toUserByNameAndHashedPassword());
    }
    private EntityRef saveUserWithNameChange(User user)
    {
        userMapper.save(user);
        userByNameAndHashedPasswordMapper.save(user.toUserByNameAndHashedPassword());
        refMapper.save(user.getReference().toEntityRefByName());
        return user.getReference();
    }

    private void saveGroup(Group group)
    {
        groupMapper.save(group);
    }
    private void saveGroupWithNameChange(Group group)
    {
        groupMapper.save(group);
        refMapper.save(group.getReference().toEntityRefByName());
    }

    private void savePost(Post newPost)
    {
        postMapper.save(newPost);
        postByIdAndTypeMapper.save(newPost.toPostByIdAndType());
    }


    //For relationships between a group and a user
    public void addGroupFollower(User user, EntityRef groupRef )
    {
        Group group = groupMapper.get(groupRef.getId());
        //Get user and group from database using the ids
        EntityRef userRef = user.getReference();

        user.addAffilitation(groupRef);
        group.addFollower(userRef);

        saveUser(user);
        saveGroup(group);
    }

    public void removeGroupFollower(User user, EntityRef groupRef)
    {
        EntityRef userRef = user.getReference();
        Group group = groupMapper.get(groupRef.getId());

        user.removeAffiliation(groupRef);
        group.removeFollower(userRef);
        //save entitiesosts_by_creator_id_and_type_and_creation_time
        saveGroup(group);
        saveUser(user);
    }

    public void addGroupMember(User user, EntityRef groupRef)
    {
        EntityRef userRef = user.getReference();
        Group group = groupMapper.get(groupRef.getId());

        user.addAffilitation(groupRef);
        group.addMember(userRef);

        saveGroup(group);
        saveUser(user);
    }
    public void removeGroupMember(User user, EntityRef groupRef)
    {
        Group group = groupMapper.get(groupRef.getId());
        EntityRef userRef = user.getReference();

        user.removeAffiliation(groupRef);
        group.removeMember(userRef);

        saveGroup(group);
        saveUser(user);
    }

    public void addGroupAdmin(User user, EntityRef groupRef)
    {
        Group group = groupMapper.get(groupRef.getId());
        EntityRef userRef = user.getReference();


        user.addAffilitation(groupRef);
        group.removeAdmin(userRef);

        saveGroup(group);
        saveUser(user);
    }

    public void removeGroupAdmin(User user, EntityRef groupRef)
    {
        Group group = groupMapper.get(groupRef.getId());
        EntityRef userRef = user.getReference();


        user.removeAffiliation(groupRef);
        group.removeAdmin(userRef);

        saveGroup(group);
        saveUser(user);
    }

    public void setGroupLeader(User user, EntityRef groupRef)
    {
        Group group = groupMapper.get(groupRef.getId());
        EntityRef userRef = user.getReference();

        user.addAffilitation(groupRef);
        group.setLeader(userRef);

        saveGroup(group);
        saveUser(user);
    }

    //Post related stuff

    public Post addPost(Post newPost)
    {
        newPost.setCreator(newPost.getCreator());
        newPost.setPostId(UUID.randomUUID());
        newPost.setCreationTime(UUIDs.timeBased());
        savePost(newPost);
        return newPost;
    }
    
    public PostContainer getPostsByCreatorId(EntityRefContainer affiliationsContainer)
    {
        PostContainer postsContainer = new PostContainer();
        ArrayList<EntityRef> affiliations = (ArrayList<EntityRef>) affiliationsContainer.getEntityReferences();
        ArrayList<Post> posts = new ArrayList<Post>();
        
        for( EntityRef ref : affiliations)
        {
            Statement postQueryStatement = QueryBuilder.select()
                    .from(Cassandra.DB.KEYSPACE, POSTTABLE)
                    .where(QueryBuilder.eq("creator_id", ref.getId()))
                    .and(QueryBuilder.eq("entity_type", ref.getType()))
                    .and(QueryBuilder.gt("creation_time", UUIDs.startOf(System.currentTimeMillis() - TIME_WINDOW)));
            
            List<PostByIdAndType> resultsByIdAndType = postByIdAndTypeMapper.map(Cassandra.DB.getSession().execute(postQueryStatement)).all();
            List<Post> results = new ArrayList<Post>();
            for(PostByIdAndType postByIdAndType : resultsByIdAndType)
            {
                logger.info("Post body " + postByIdAndType.getBody());
                results.add(postByIdAndType.toPost());
            }
            posts.addAll(results);
        }
        
        
        /*
        for( EntityRef ref : affiliations)
        {
             PostByIdAndType resultsByIdAndType = postByIdAndTypeMapper.get(ref.getId(), ref.getType());
            
        }
        */
        postsContainer.setPosts(posts);
        return postsContainer;
    }
    
    public Post getPostById(UUID postId)
    {
        return postMapper.get(postId);
    }
    //User related stuff
    public User getUser(EntityRef userRef)
    {
        return userMapper.get(userRef.getId());
    }

    public EntityRef addUser(User newUser)
    {
        saveUserWithNameChange(newUser);
        return newUser.getReference();
    }
    public void removeUser(User user)
    {
        userMapper.delete(user);
        userByNameAndHashedPasswordMapper.delete(user.toUserByNameAndHashedPassword());
        refMapper.delete(user.getReference().toEntityRefByName());
    }

    public boolean authenticateUser(String username, String hashedPassword)
    {
        User user = userByNameAndHashedPasswordMapper.get(username, hashedPassword).toUser();
        if(user == null)
        {
            return false;
        }
        return true;
    }

    public void changeUserName(EntityRef userRef, String newName)
    {
        User user = userMapper.get(userRef.getId());
        user.setName(newName);
        saveUserWithNameChange(user);
    }
    public void changeUserMission(EntityRef userRef, String newMission)
    {
        User user = userMapper.get(userRef.getId());
        user.setMission(newMission);
        saveUserWithNameChange(user);
    }

    public EntityRef getEntityRef(String username)
    {
        return refMapper.get(username).getReference();
    }

    
    //For groups
    public EntityRef addGroup(Group newGroup)
    {
        saveGroupWithNameChange(newGroup);
        return newGroup.getReference();
    }
    public Group getGroup(EntityRef groupRef)
    {
        return groupMapper.get(groupRef.getId());
    }
    public EntityRef changeGroupName(EntityRef groupRef, String newName)
    {
        Group group = groupMapper.get(groupRef.getId());
        group.setName(newName);
        saveGroupWithNameChange(group);
        return group.getReference();
    }
    public void changeGroupMission(EntityRef groupRef, String newMission)
    {
        Group group = groupMapper.get(groupRef);
        group.setMission(newMission);
    }


}
