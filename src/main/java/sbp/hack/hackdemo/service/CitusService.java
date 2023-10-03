package sbp.hack.hackdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbp.hack.hackdemo.repository.CitusRepository;


@Service
@RequiredArgsConstructor
public class CitusService {

    private final CitusRepository citusRepository;

    public void doBalance() {
        citusRepository.balance();
    }

    public String getStatus() {
        return citusRepository.getStatus();
    }

    public void addNode(String node, String port) {
        citusRepository.addNode(node, port);
    }

    public void createDistributedTable(
            String tableName,
            String distrColumnName
    ) {

        citusRepository.createDistributedTable(tableName, distrColumnName);
    }

    public void undistributeTable(String tableName) {
        citusRepository.undistribute(tableName);
    }

    public void makeReference(String tableName) {
        citusRepository.makeReference(tableName);
    }

    public void unreference(String tableName) {
        citusRepository.unreference(tableName);
    }
}
