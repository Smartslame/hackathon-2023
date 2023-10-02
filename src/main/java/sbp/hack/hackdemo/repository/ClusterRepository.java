package sbp.hack.hackdemo.repository;

import org.springframework.stereotype.Repository;
import sbp.hack.hackdemo.entity.PgDistNodeEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ClusterRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PgDistNodeEntity> getClusterNodesInfo() {
        return entityManager.createNativeQuery(
                "select * from pg_dist_node",
                PgDistNodeEntity.class
                )
                .getResultList();
    }
}
