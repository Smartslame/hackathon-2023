package sbp.hack.hackdemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DistributedNodeTable {
    private String tableName;
    private String node;
    private Long byteSize;
    private String prettySize;
    private String type;
    private String port;

}
