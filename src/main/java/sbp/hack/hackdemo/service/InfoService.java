package sbp.hack.hackdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbp.hack.hackdemo.dto.NodeInfoDTO;
import sbp.hack.hackdemo.model.NodeTable;
import sbp.hack.hackdemo.repository.NodesTableInfoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final NodesTableInfoRepository nodesTableInfoRepository;

    public List<NodeInfoDTO> getCitusInfo(Integer offset, Integer perPage) {
        List<NodeTable> nodeInfoCitus = nodesTableInfoRepository.getTableNodeInfoCitus(offset, perPage);
        return nodeInfoCitus.stream().map(x -> new NodeInfoDTO(x.getTableName(),
                        x.getNode(),
                        x.getPrettySize()))
                .collect(Collectors.toList());
    }

    public List<NodeInfoDTO> getCoordinatorInfo(Integer offset, Integer perPage) {
        List<NodeTable> nodeInfoMaster = nodesTableInfoRepository.getTableNodeInfoMaster(offset, perPage);
        return nodeInfoMaster.stream().map(x -> new NodeInfoDTO(x.getTableName(),
                        x.getNode(),
                        x.getPrettySize()))
                .collect(Collectors.toList());
    }
}
