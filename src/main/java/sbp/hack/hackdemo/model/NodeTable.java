package sbp.hack.hackdemo.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NodeTable {
    private String tableName;
    private String node;
    private Long byteSize;
    private String prettySize;
}
