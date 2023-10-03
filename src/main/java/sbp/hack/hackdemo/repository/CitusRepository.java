package sbp.hack.hackdemo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Repository
@RequiredArgsConstructor
public class CitusRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final JdbcTemplate jdbcTemplate;

    public void createDistributedTable(String tableName, String distrColumnName) {
        Query q = entityManager.createNativeQuery(
                "SELECT CAST(create_distributed_table(:tableName, :distrColumnName) AS varchar)"
        );

        q.setParameter("tableName", tableName);
        q.setParameter("distrColumnName", distrColumnName);

        q.getResultList();
    }

    public void makeReference(String tableName) {
        Query q = entityManager.createNativeQuery(
                "SELECT CAST(create_reference_table(:tableName) AS varchar)"
        );

        q.setParameter("tableName", tableName);

        q.getResultList();
    }

    public void unreference(String tableName) {
        undistribute(tableName);
    }

    public void undistribute(String tableName) {
        Query q = entityManager.createNativeQuery(
                "SELECT CAST(undistribute_table(:tableName) AS varchar)"
        );

        q.setParameter("tableName", tableName);

        q.getResultList();
    }

    public void balance() {
        jdbcTemplate.query(
                "SELECT citus_rebalance_start()", (rs, rowNum) -> {
                    return null;
                }
        );
    }

    public String getStatus() {
        return jdbcTemplate.query(
                "SELECT state FROM citus_rebalance_status()", (rs, rowNum) -> {
                    return rs.getString(1);
                }
        ).get(0);
    }

    public void addNode(String node, String port) {
        jdbcTemplate.query(
                "select * from citus_add_node('" + node + "', " + port + ");", (rs, rowNum) -> {
                    return null;
                }
        );
    }

}
