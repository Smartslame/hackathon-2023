package sbp.hack.hackdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sbp.hack.hackdemo.dto.NodeInfoDTO;
import sbp.hack.hackdemo.service.InfoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;

    @GetMapping("api/info/table-node-info")
    public List<NodeInfoDTO> getTableNodeInfo(@RequestParam(name = "isMaster", defaultValue = "false") Boolean isMaster) {
        if (isMaster) {
            return infoService.getMasterInfo();
        }
        return infoService.getCitusInfo();
    }

}
