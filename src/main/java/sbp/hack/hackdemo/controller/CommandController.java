package sbp.hack.hackdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sbp.hack.hackdemo.service.CommandService;

import java.util.List;


@RestController
@RequestMapping("/api/cmd")
@RequiredArgsConstructor
public class CommandController {

    CommandService commandService;

    @GetMapping("/rebalance")
    public String startRebalance() {
        commandService.doBalance();
        return null;
    }

    @GetMapping("/rebalance/status")
    public String getRebalanceStatus() {
        return commandService.getStatus();
    }

    @GetMapping("/add/node")
    public String addNode(@RequestParam(name = "node") String node,
                          @RequestParam(name = "node") String port) {
        commandService.addNode(node, port);
        return null;
    }


}
