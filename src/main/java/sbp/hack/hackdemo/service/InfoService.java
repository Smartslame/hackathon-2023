package sbp.hack.hackdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbp.hack.hackdemo.dto.DistributedNodeDetails;
import sbp.hack.hackdemo.dto.DistributedNodeInfoDTO;
import sbp.hack.hackdemo.dto.NodeInfoDTO;
import sbp.hack.hackdemo.model.DistributedNodeTable;
import sbp.hack.hackdemo.model.NodeTable;
import sbp.hack.hackdemo.repository.NodesTableInfoRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final NodesTableInfoRepository nodesTableInfoRepository;

    public List<DistributedNodeInfoDTO> getCitusInfo(Integer offset, Integer perPage) {
        List<DistributedNodeTable> nodeInfoCitus = nodesTableInfoRepository.getTableNodeInfoCitus(offset, perPage);
        HashMap<String, Long> summary = new LinkedHashMap<>();
        HashMap<String, String> typeDict = new LinkedHashMap<>();
        HashMap<String, List<DistributedNodeDetails>> details = new LinkedHashMap<>();
        for (int i = 0; i < nodeInfoCitus.size(); i++) {
            String table = nodeInfoCitus.get(i).getTableName();
            typeDict.put(table, nodeInfoCitus.get(i).getType());
            if (summary.containsKey(table)) {
                summary.put(table, summary.get(table) + nodeInfoCitus.get(i).getByteSize());
                details.get(table).add(new DistributedNodeDetails(table,
                        nodeInfoCitus.get(i).getPort(),
                        nodeInfoCitus.get(i).getPrettySize()));
            } else {
                summary.put(table, nodeInfoCitus.get(i).getByteSize());
                ArrayList<DistributedNodeDetails> tmp = new ArrayList<>();
                tmp.add(new DistributedNodeDetails(table,
                        nodeInfoCitus.get(i).getPort(),
                        nodeInfoCitus.get(i).getPrettySize()));
                details.put(table, tmp);
            }
        }
        ArrayList<DistributedNodeInfoDTO> result = new ArrayList<>();
        for (String table : summary.keySet()) {
            result.add(new DistributedNodeInfoDTO(table,
                    typeDict.get(table),
                    getPrettySize(summary.get(table)),
                    details.get(table)));
        }
        return result;

    }

    public List<NodeInfoDTO> getCoordinatorInfo(Integer offset, Integer perPage) {
        List<NodeTable> nodeInfoMaster = nodesTableInfoRepository.getTableNodeInfoMaster(offset, perPage);
        return nodeInfoMaster.stream().map(x -> new NodeInfoDTO(x.getTableName(),
                        x.getNode(),
                        x.getPrettySize()))
                .collect(Collectors.toList());
    }

    private static String getPrettySize(Long size) {
        String[] postfixes = {"B", "kB", "MB", "GB", "TB"};
        double current = size.doubleValue();
        int i = 0;
        while ((current / 1024.) > 1.0) {
            i++;
            current = current / 1024.;
        }
        return String.format("%,.2f %s", current, postfixes[i]);
    }
}
