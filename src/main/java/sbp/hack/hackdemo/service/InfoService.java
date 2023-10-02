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

    public List<NodeInfoDTO> getCitusInfo() {
        List<NodeTable> nodeInfoCitus = nodesTableInfoRepository.getTableNodeInfoCitus();
        return nodeInfoCitus.stream().map(x -> new NodeInfoDTO(x.getTableName(),
                        x.getNode(),
                        x.getPrettySize()))
                .collect(Collectors.toList());
    }

    public List<NodeInfoDTO> getMasterInfo() {
        List<NodeTable> nodeInfoMaster = nodesTableInfoRepository.getTableNodeInfoMaster();
        return nodeInfoMaster.stream().map(x -> new NodeInfoDTO(x.getTableName(),
                        x.getNode(),
                        x.getPrettySize()))
                .collect(Collectors.toList());
    }
}
