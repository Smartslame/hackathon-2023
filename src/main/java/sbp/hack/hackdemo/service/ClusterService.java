package sbp.hack.hackdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbp.hack.hackdemo.entity.PgDistNodeEntity;
import sbp.hack.hackdemo.repository.ClusterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClusterService {
    private final ClusterRepository clusterRepository;

    public List<PgDistNodeEntity> getClusterInfo() {
        return clusterRepository.getClusterNodesInfo();
    }
}
