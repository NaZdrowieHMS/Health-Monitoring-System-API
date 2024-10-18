package agh.edu.pl.healthmonitoringsystemapplication.providers;

import agh.edu.pl.healthmonitoringsystemapplication.configurations.SecretsConfigProperties;

public class DatabaseArgumentsProvider {

    SecretsConfigProperties secrets;

    private final static int POOL_SIZE = 2;

    public DatabaseArgumentsProvider(SecretsConfigProperties secretsConfigProperties){
        this.secrets = secretsConfigProperties;
    }

    public String provideDbUrl(){
        return secrets.dbHost() + ":" + secrets.dbPort() + "/" + secrets.dbName() + "?user=" + secrets.dbUser() +
                "&password=" + secrets.dbPassword() + "&sslmode=" + secrets.sslMode();
    }

    public int providePoolSize(){
        return POOL_SIZE;
    }
}