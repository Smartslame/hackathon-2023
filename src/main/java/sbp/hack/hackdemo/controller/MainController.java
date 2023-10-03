package sbp.hack.hackdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sbp.hack.hackdemo.dao.ClusterInfoDao;
import sbp.hack.hackdemo.entity.PgDistNodeEntity;
import sbp.hack.hackdemo.service.ClusterService;
import sbp.hack.hackdemo.service.TableService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final TableService tableService;
    private final ClusterService clusterService;

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

    @GetMapping("/stupid")
    public void stupid() {
        for (int i = 0; i < 10000; ++i) {
            tableService.listDicts();
        }
    }

    @GetMapping("/api/cluster-info")
    public List<ClusterInfoDao> getClusterInfo() {
        return clusterService.getClusterInfo();
    }

}
