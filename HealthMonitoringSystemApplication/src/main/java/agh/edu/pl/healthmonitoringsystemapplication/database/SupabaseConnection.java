package agh.edu.pl.healthmonitoringsystemapplication.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupabaseConnection {
    private Connection connection;
    private SupabaseConnectionService pool;  // Reference to the connection pool

    // Constructor that initializes the connection and pool
    public SupabaseConnection(Connection connection, SupabaseConnectionService pool) {
        this.connection = connection;
        this.pool = pool;
    }

    // Execute a query (for SELECT statements)
    public ResultSet executeQuery(String sql, Object... parameters) throws SQLException {
        try (PreparedStatement statement = prepareStatement(sql, parameters)) {
            return statement.executeQuery();  // Return the result of the query
        } finally {
            pool.returnConnection(this);  // Automatically return connection to the pool
        }
    }


    // Execute an update (for INSERT, UPDATE, DELETE statements)
    public int executeUpdate(String sql, Object... parameters) throws SQLException {
        try (PreparedStatement statement = prepareStatement(sql, parameters)) {
            return statement.executeUpdate();  // Execute the update and return the row count
        } finally {
            pool.returnConnection(this);  // Automatically return connection to the pool
        }
    }

    // Helper method to prepare a statement and set parameters to prevent SQL injection
    private PreparedStatement prepareStatement(String sql, Object[] parameters) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);

        // Set the parameters dynamically to prevent SQL injection
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }

        return statement;
    }

    // Close the connection when shutting down the pool
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
