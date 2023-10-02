package sbp.hack.hackdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PgDistNodeEntity {
    @Id
    private int nodeid;
    private int groupid;
    private String nodename;
    private int nodeport;
    private String noderack;
    private boolean hasmetadata;
    private boolean isactive;
    private String noderole;
    private String nodecluster;
    private boolean metadatasynced;
    private boolean shouldhaveshards;
}
