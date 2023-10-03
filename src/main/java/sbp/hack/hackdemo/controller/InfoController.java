package sbp.hack.hackdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sbp.hack.hackdemo.dao.ClusterInfoDao;
import sbp.hack.hackdemo.dto.NodeInfoDTO;
import sbp.hack.hackdemo.service.ClusterService;
import sbp.hack.hackdemo.service.InfoService;
import sbp.hack.hackdemo.service.TableService;

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
    public List<NodeInfoDTO> getTableNodeInfo(@RequestParam(name = "isCoordinator", defaultValue = "false") Boolean isCoordinator,
                                              @RequestParam(name = "page", defaultValue = "1") Integer page,
                                              @RequestParam(name = "per_page", defaultValue = "20") Integer perPage) {
        Integer offset = perPage * (page - 1);
        return isCoordinator
                ? infoService.getCoordinatorInfo(offset, perPage)
                : infoService.getCitusInfo(offset, perPage);
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
    public List<String> dependentTables(@RequestParam("tableName") String tableName) {
        return tableService.getDependentTables(tableName);
    }

    @GetMapping("/cluster")
    public List<ClusterInfoDao> getClusterInfo() {
        return clusterService.getClusterInfo();
    }
}
