package agh.edu.pl.healthmonitoringsystemapplication.services;

import agh.edu.pl.healthmonitoringsystemapplication.database.SupabaseConnection;
import agh.edu.pl.healthmonitoringsystemapplication.database.SupabaseConnectionService;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.DbConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DbConnectionService {

    SupabaseConnectionService connectionService;

    @Autowired
    public DbConnectionService(SupabaseConnectionService supabaseConnectionService) {
        this.connectionService = supabaseConnectionService;
    }

    public void isDbConnectionUsable() throws DbConnectionException {
        try{
            SupabaseConnection connection = connectionService.leaseConnection();
            connectionService.returnConnection(connection);
        } catch (Exception e){
            log.error("Establish connection failed", e);
            throw new DbConnectionException(String.format("Establish connection failed: %s", e.getMessage()));
        }
    }
    public SupabaseConnectionService getDbConnection() {
        return connectionService;
    }
}
