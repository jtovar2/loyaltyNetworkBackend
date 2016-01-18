package javier.loyaltynetwork.model;

import java.io.Serializable;
import java.util.List;
@SuppressWarnings("serial")
public class PostContainer implements Serializable
{
    
    List<Post> posts;
    
    public PostContainer()
    {}
    
    public void setPosts(List<Post> newPosts)
    {
        posts = newPosts;
    }
    
    public List<Post> getPosts()
    {
        return posts;
    }
}