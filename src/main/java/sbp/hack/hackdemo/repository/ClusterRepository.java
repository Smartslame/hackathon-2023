package sbp.hack.hackdemo.repository;

import org.springframework.stereotype.Repository;
import sbp.hack.hackdemo.dto.PgDistNodeDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ClusterRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PgDistNodeDto> getClusterNodesInfo() {
        List<PgDistNodeDto> result =
        entityManager.createNativeQuery(
                "SELECT * from pg_dist_node"
                )
                .getResultList();
        return result;
    }
}
