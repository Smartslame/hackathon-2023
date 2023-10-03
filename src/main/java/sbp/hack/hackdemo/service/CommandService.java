package sbp.hack.hackdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbp.hack.hackdemo.repository.CommandRepository;

@Service
@RequiredArgsConstructor
public class CommandService {

    private final CommandRepository commandRepository;

    public void doBalance() {
        commandRepository.balance();
    }

    public String getStatus() {
        return commandRepository.getStatus();
    }

    public void addNode(String node, String port){
        commandRepository.addNode(node, port);
    }

}
