package config;

public class ConnectionConfig {
    private final String baseUrl;
    private final String dbName;
    private final String userName;
    private final String password;

    public ConnectionConfig(String baseUrl, String dbName, String userName, String password) {
        this.baseUrl = baseUrl;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getDBName() {
        return dbName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
