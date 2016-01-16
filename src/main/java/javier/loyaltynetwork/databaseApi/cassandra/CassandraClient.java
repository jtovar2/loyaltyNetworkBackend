package javier.loyaltynetwork.databaseApi.cassandra;

import com.datastax.driver.core.Cluster;

/**
 * Created by JXT0589 on 1/8/2016.
 */
public class CassandraClient
{
    private Cluster cluster;

    public void connect(String node)
    {
        cluster = Cluster.builder()
                .addContactPoint(node)
                .build();
    }
    public void close()
    {
        cluster.close();
    }

    public CassandraClient()
    {

    }
}
