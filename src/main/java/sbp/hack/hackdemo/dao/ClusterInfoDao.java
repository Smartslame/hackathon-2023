package sbp.hack.hackdemo.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClusterInfoDao {
    private final String nodeName;
    private int nodePort;
    private boolean isActive;
    private boolean isCoordinator;
}
