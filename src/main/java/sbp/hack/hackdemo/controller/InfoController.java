package sbp.hack.hackdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sbp.hack.hackdemo.dto.NodeInfoDTO;
import sbp.hack.hackdemo.entity.PgDistNodeEntity;
import sbp.hack.hackdemo.service.ClusterService;
import sbp.hack.hackdemo.service.InfoService;
import sbp.hack.hackdemo.service.TableService;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/info")
public class InfoController {

    private final TableService tableService;
    private final ClusterService clusterService;
    private final InfoService infoService;

    @GetMapping("/table-node-info")
    public List<NodeInfoDTO> getTableNodeInfo(@RequestParam(name = "isCoordinator", defaultValue = "false") Boolean isCoordinator) {
        if (isCoordinator) {
            return infoService.getCoordinatorInfo();
        }

        return infoService.getCitusInfo();
    }

    @GetMapping("/dicts")
    public List<String> dicts() {
        return tableService.getDictTables();
    }

    @GetMapping("/tables")
    public List<String> tables() {
        return tableService.getNonDictTables();
    }

    @GetMapping("/dicts/meta")
    public Map<String, List<String>> dictsMeta() {
        return tableService.listDicts();
    }

    @GetMapping("/dependent")
    public List<String> dependentTables(@PathParam("tableName") String tableName) {
        return tableService.getDependentTables(tableName);
    }

    @GetMapping("/stupid")
    public void stupid() {
        for (int i = 0; i < 10000; ++i) {
            tableService.listDicts();
        }
    }

    @GetMapping("/cluster")
    public List<ClusterInfoDao> getClusterInfo() {
        return clusterService.getClusterInfo();
    }
}
