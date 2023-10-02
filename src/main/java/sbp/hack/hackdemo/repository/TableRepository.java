package sbp.hack.hackdemo.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
        return entityManager.createNativeQuery(
                "SELECT cast(_description as varchar) FROM " + tableName
        ).getResultList();
    }
}
