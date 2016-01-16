package javier.loyaltynetwork.databaseApi.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Created by JXT0589 on 1/9/2016.
 */
public enum Cassandra
{


    DB;

    private Session session;
    private Cluster cluster;

    public void connect()
    {
        String IP_ADDRESS = "127.0.0.1";
        String USERNAME = "cassandra";
        String PASSWORD = "homedepot";
        int PORT = 9042;
        String KEYSPACE = "loyalty_network";

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
}
