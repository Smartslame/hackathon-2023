package sbp.hack.hackdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NodeInfoDTO {
    private String tableName;
    private String nodeName;
    private String prettySize;
}