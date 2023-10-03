package sbp.hack.hackdemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sbp.hack.hackdemo.dao.ClusterInfoDao;
import sbp.hack.hackdemo.entity.PgDistNodeEntity;
import sbp.hack.hackdemo.repository.ClusterRepository;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClusterService {
    private final ClusterRepository clusterRepository;

    public List<ClusterInfoDao> getClusterInfo() {
            List<ClusterInfoDao> result = new ArrayList<>();
        try {
            List<PgDistNodeEntity> nodes = clusterRepository.getClusterNodesInfo();
            nodes.forEach(node -> result.add(new ClusterInfoDao(node.getNodename(), node.getNodeport(), isNodeActive(node.getNodename()), !node.isShouldhaveshards())));
            return result;
        } catch (Exception e) {
            log.error("Fail getClusterInfo", e);
            return Collections.emptyList();
        }
    }

    private boolean isNodeActive(String ip) {
        return !clusterRepository.getNodeHealth(ip).stream().filter(Objects::nonNull).allMatch(b -> b == Boolean.FALSE);
    }
}
