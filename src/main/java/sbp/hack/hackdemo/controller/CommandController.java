package sbp.hack.hackdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sbp.hack.hackdemo.dto.RqNodePort;
import sbp.hack.hackdemo.service.CitusService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cmd")
public class CommandController {

    private final CitusService citusService;

    @PostMapping("/rebalance")
    public String startRebalance() {
        citusService.doBalance();
        return null;
    }

    @GetMapping("/rebalance/status")
    public String getRebalanceStatus() {
        return citusService.getStatus();
    }

    @PostMapping("/add/node")
    public String addNode(@RequestBody RqNodePort rqNodePort) {
        citusService.addNode(rqNodePort.getNode(), rqNodePort.getPort());
        return null;
    }

    @PostMapping("/distribute")
    public void createDistributedTable(@RequestParam("tableName") String tableName,
                                       @RequestParam("distrColumn") String distrColumn) {
        citusService.createDistributedTable(tableName, distrColumn);
    }

    @PostMapping("/undistribute")
    public void undistributeTable(@RequestParam("tableName") String tableName) {
        citusService.undistributeTable(tableName);
    }

    @PostMapping("/make-reference")
    public void makeReferenceTable(@RequestParam("tableName") String tableName) {
        citusService.makeReference(tableName);
    }

    @PostMapping("/unreference")
    public void unreferenceTable(@RequestParam("tableName") String tableName) {
        citusService.unreference(tableName);
    }
}
