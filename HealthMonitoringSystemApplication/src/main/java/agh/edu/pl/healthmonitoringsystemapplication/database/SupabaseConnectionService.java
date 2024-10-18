package agh.edu.pl.healthmonitoringsystemapplication.database;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SupabaseConnectionService {
    private BlockingQueue<SupabaseConnection> connectionPool;
    private final static int POOL_SIZE = 2;

    private String dbUrl;
    public SupabaseConnectionService() throws SQLException {
        this.connectionPool = new LinkedBlockingQueue<>(POOL_SIZE);
        dbUrl = "NOT YET";
        // Initialize the pool with the specified number of connections
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = DriverManager.getConnection(dbUrl);
            connectionPool.add(new SupabaseConnection(connection, this));  // Pass pool reference to DBConnection
        }
    }

    // Lease a connection from the pool
    public SupabaseConnection leaseConnection() throws InterruptedException {
        return connectionPool.take();  // Take a connection from the pool
    }

    // Return the connection to the pool
    public boolean returnConnection(SupabaseConnection connection) {
        return connectionPool.offer(connection);  // Return the connection to the pool
    }

    // Close all connections in the pool
    public void shutdownPool() throws SQLException {
        while (!connectionPool.isEmpty()) {
            SupabaseConnection connection = connectionPool.poll();
            if (connection != null) {
                connection.close();
            }
        }
    }
}
