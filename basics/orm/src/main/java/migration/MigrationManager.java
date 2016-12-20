package migration;

import org.flywaydb.core.Flyway;

public class MigrationManager {
    private final String base_url;
    private final String db_name;
    private final String user_name;
    private final String password;
    private Flyway flyway;

    public MigrationManager(String base_url, String db_name, String user_name, String password) {
        this.base_url = base_url;
        this.db_name = db_name;
        this.user_name = user_name;
        this.password = password;
        init();
    }

    private void init() {
        flyway = new Flyway();
        flyway.setDataSource(base_url, user_name, password);
        flyway.setSchemas(db_name);
    }

    public void migrate() {
        flyway.migrate();
    }

    public void clean() {
        flyway.clean();
    }
}
