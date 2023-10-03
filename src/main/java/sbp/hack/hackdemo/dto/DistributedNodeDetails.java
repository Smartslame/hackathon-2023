package sbp.hack.hackdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DistributedNodeDetails {
    private String node;
    private String port;
    private String size;
}
