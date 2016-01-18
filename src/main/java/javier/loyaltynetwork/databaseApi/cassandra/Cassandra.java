package javier.loyaltynetwork.databaseApi.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

/**
 * Created by JXT0589 on 1/9/2016.
 */
public enum Cassandra
{


    DB;

    private Session session;
    private Cluster cluster;
    private MappingManager mappingManager;
    public String IP_ADDRESS = "127.0.0.1";
    public String USERNAME = "cassandra";
    public String PASSWORD = "homedepot";
    public String KEYSPACE = "loyalty_network";
    public void connect()
    {

        if( cluster == null && session == null)
        {
            cluster = Cluster.builder().addContactPoint(IP_ADDRESS).withCredentials(USERNAME, PASSWORD).build();
            session = cluster.connect(KEYSPACE);
        }
    }

    public Session getSession()
    {
        return session;
    }
    
    public MappingManager getManager()
    {
        if(session == null)
        {
            connect();
            return mappingManager = new MappingManager(session);
        }
        else
        {
            return mappingManager;
        }
        
    }
}
