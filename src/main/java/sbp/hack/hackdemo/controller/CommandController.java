package sbp.hack.hackdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sbp.hack.hackdemo.dto.DistributionRq;
import sbp.hack.hackdemo.dto.RqNodePort;
import sbp.hack.hackdemo.service.CitusService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cmd")
public class CommandController {

    private final CitusService citusService;

    @PostMapping("/rebalance")
    public void startRebalance() {
        citusService.doBalance();
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
    public void undistributeTable(@RequestBody String tableName) {
        citusService.undistributeTable(tableName);
    }

    @PostMapping("/make-reference")
    public void makeReferenceTable(@RequestBody String tableName) {
        citusService.makeReference(tableName);
    }

    @PostMapping("/unreference")
    public void unreferenceTable(@RequestBody String tableName) {
        citusService.unreference(tableName);
    }
}
