package sbp.hack.hackdemo.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;


@Repository
public class TableRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getTables() {
        return entityManager.createNativeQuery(
                "SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname = 'public'"
                )
                .getResultList();
    }

    public List<String> getDescriptionFromReferenceTable(String tableName) {
        Query q = entityManager.createNativeQuery(
                "SELECT cast(_description as varchar) FROM :tableName");

        q.setParameter("tableName", tableName);

        return q.getResultList();
    }

    public List<String> getDependentTables(String tableName) {
        String lower = tableName.toLowerCase(Locale.ROOT);

        var tokens = List.of(lower.split(Pattern.quote("_")));
        if (tokens.size() <= 1 || tokens.get(tokens.size() - 1).startsWith("vt")) {
            return Collections.emptyList();
        }

        return doGetDependentTablesFromDb(tokens.get(1));
    }

    private List<String> doGetDependentTablesFromDb(String tableNameTrimmed) {
        Query q = entityManager.createNativeQuery(
                "SELECT table_name\n" +
                        "FROM information_schema.tables\n" +
                        "WHERE table_type = 'BASE TABLE'\n" +
                        " AND table_name LIKE :parentTablePattern");
        String parentTablePattern = "_" + tableNameTrimmed + "\\_%";

        q.setParameter("parentTablePattern", parentTablePattern);

        return q.getResultList();
    }
}
