package migration;

import config.ConnectionConfig;
import org.flywaydb.core.Flyway;

public class MigrationManager {
    private Flyway flyway;

    public MigrationManager(ConnectionConfig connectionConfig) {
        init(connectionConfig);
    }

    private void init(ConnectionConfig connectionConfig) {
        flyway = new Flyway();
        flyway.setDataSource(connectionConfig.getBaseUrl(),
                connectionConfig.getUserName(),
                connectionConfig.getPassword());
        flyway.setSchemas(connectionConfig.getDBName());
    }

    public void migrate() {
        flyway.migrate();
    }

    public void clean() {
        flyway.clean();
    }
}
