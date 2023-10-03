package sbp.hack.hackdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DistributedNodeInfoDTO {
    private String tableName;
    private String type;
    private String prettySize;
    private List<DistributedNodeDetails> details;
}