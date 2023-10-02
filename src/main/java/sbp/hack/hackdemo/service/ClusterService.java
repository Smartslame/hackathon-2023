package sbp.hack.hackdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbp.hack.hackdemo.dto.PgDistNodeDto;
import sbp.hack.hackdemo.repository.ClusterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClusterService {
    private final ClusterRepository clusterRepository;

    public List<PgDistNodeDto> getClusterInfo() {
        return clusterRepository.getClusterNodesInfo();
    }
}
