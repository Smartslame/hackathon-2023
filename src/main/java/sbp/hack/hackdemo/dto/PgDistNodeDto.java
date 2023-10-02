package sbp.hack.hackdemo.dto;

import lombok.Data;

@Data
public class PgDistNodeDto {
    private final int nodeid;
    private final int groupid;
    private final String nodename;
    private final int nodeport;
    private final String noderack;
    private final boolean hasmetadata;
    private final boolean isactive;
    private final String noderole;
    private final String nodecluster;
    private final boolean metadatasynced;
    private final boolean shouldhaveshards;

}
