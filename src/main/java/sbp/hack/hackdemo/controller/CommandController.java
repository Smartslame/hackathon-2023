package sbp.hack.hackdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sbp.hack.hackdemo.dto.DistributionRq;
import sbp.hack.hackdemo.dto.RqNodePort;
import sbp.hack.hackdemo.dto.StringValue;
import sbp.hack.hackdemo.service.CitusService;
import sbp.hack.hackdemo.service.TableService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cmd")
public class CommandController {

    private final CitusService citusService;
    private final TableService tableService;

    @PostMapping("/rebalance")
    public void startRebalance() {
        citusService.doBalance();
    }

    @GetMapping("/pk")
    public List<String> getPk(@RequestParam("tableName") String tableName) {
        return tableService.getPk(tableName);
    }

    @GetMapping("/rebalance/status")
    public String getRebalanceStatus() {
        return citusService.getStatus();
    }

    @PostMapping("/add/node")
    public void addNode(@RequestBody RqNodePort rqNodePort) {
        citusService.addNode(rqNodePort.getNode(), rqNodePort.getPort());
    }

    @PostMapping("/distribute")
    public void createDistributedTable(@RequestBody DistributionRq distributionRq) {
        citusService.createDistributedTable(distributionRq.getTableName(), distributionRq.getDistrColumn());
    }

    @PostMapping("/undistribute")
    public void undistributeTable(@RequestBody StringValue tableName) {
        citusService.undistributeTable(tableName.getValue());
    }

    @PostMapping("/make-reference")
    public void makeReferenceTable(@RequestBody StringValue tableName) {
        citusService.makeReference(tableName.getValue());
    }

    @PostMapping("/unreference")
    public void unreferenceTable(@RequestBody String tableName) {
        citusService.unreference(tableName);
    }
}