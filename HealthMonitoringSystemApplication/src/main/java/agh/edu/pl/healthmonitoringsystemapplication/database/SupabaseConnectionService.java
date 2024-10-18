package agh.edu.pl.healthmonitoringsystemapplication.database;


import agh.edu.pl.healthmonitoringsystemapplication.exceptions.DbConnectionException;
import agh.edu.pl.healthmonitoringsystemapplication.providers.DatabaseArgumentsProvider;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class SupabaseConnectionService {
    private final DatabaseArgumentsProvider databaseArgumentsProvider;
    private BlockingQueue<SupabaseConnection> connectionPool;


    public SupabaseConnectionService(DatabaseArgumentsProvider databaseArgumentsProvider){
        this.databaseArgumentsProvider = databaseArgumentsProvider;
        try {
            establishConnection();
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private void establishConnection() throws DbConnectionException {
        try{
            int poolSize = databaseArgumentsProvider.providePoolSize();
            String dbUrl = databaseArgumentsProvider.provideDbUrl();
            this.connectionPool = new LinkedBlockingQueue<>(poolSize);
            // Initialize the pool with the specified number of connections
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(dbUrl);
                connectionPool.add(new SupabaseConnection(connection, this));  // Pass pool reference to DBConnection
            }
            log.info("Database connection established.");
        } catch (SQLException e){
            log.error("Establish connection failed", e);
            throw new DbConnectionException(String.format("Establish connection failed: %s", e.getMessage()));
        }
    }

    // Lease a connection from the pool
    public SupabaseConnection leaseConnection() throws DbConnectionException {
        try {
            if (connectionPool.isEmpty()){
                log.error("Cannot lease connection Connection pool is empty.");
                throw new DbConnectionException("Cannot lease connection: Connection pool is empty.");
            }
            return connectionPool.take();  // Take a connection from the pool
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            log.error("Cannot lease connection.", e);
            throw new DbConnectionException(String.format("Cannot lease connection: %s", e.getMessage()));
        }
    }

    // Return the connection to the pool
    public void returnConnection(SupabaseConnection connection) { // can be bool
        connectionPool.offer(connection);
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
