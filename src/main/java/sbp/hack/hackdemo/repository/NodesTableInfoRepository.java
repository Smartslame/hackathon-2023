package sbp.hack.hackdemo.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import sbp.hack.hackdemo.model.DistributedNodeTable;
import sbp.hack.hackdemo.model.NodeTable;

import java.util.List;

@Repository
@AllArgsConstructor
public class NodesTableInfoRepository {

    private JdbcTemplate jdbcTemplate;

    public List<DistributedNodeTable> getTableNodeInfoCitus(Integer offset, Integer perPage) {
        return jdbcTemplate.query(
                "SELECT cs.table_name, \n" +
                        "cs.nodename as node_name, \n" +
                        "sum(cs.shard_size) as byte_size,\n" +
                        "pg_size_pretty(sum(cs.shard_size)) as pretty_size,\n" +
                        "cs.citus_table_type,\n" +
                        "cs.nodeport\n" +
                        "FROM citus_shards cs\n" +
                        "where cs.table_name in (SELECT csh.table_name\n" +
                        "FROM citus_shards csh\n" +
                        "group by csh.table_name\n" +
                        "order by sum(csh.shard_size) desc\n" +
                        "offset " + offset + " rows fetch next " + perPage + " rows only)\n" +
                        "group by cs.table_name, cs.nodename, cs.citus_table_type, cs.nodeport\n" +
                        "order by byte_size desc\n", (rs, rowNum) -> {
                    DistributedNodeTable nodeInfo = new DistributedNodeTable();
                    nodeInfo.setTableName(rs.getString(1));
                    nodeInfo.setNode(rs.getString(2));
                    nodeInfo.setByteSize(rs.getLong(3));
                    nodeInfo.setPrettySize(rs.getString(4));
                    nodeInfo.setType(rs.getString(5));
                    nodeInfo.setPort(rs.getString(6));
                    return nodeInfo;
                }
        );

    }

    public List<NodeTable> getTableNodeInfoMaster(Integer offset, Integer perPage) {
        return jdbcTemplate.query(
                "SELECT\n" +
                        "relname AS table_name,\n" +
                        "pdn.nodename as node_name,\n" +
                        "pg_total_relation_size(C .oid) AS byte_size, \n" +
                        "pg_size_pretty(pg_total_relation_size(C .oid)) AS pretty_size \n" +
                        "FROM\n" +
                        "pg_class C\n" +
                        "LEFT JOIN pg_namespace N ON (N.oid = C .relnamespace)\n" +
                        "LEFT JOIN (SELECT nodename, shouldhaveshards from pg_dist_node) pdn \n" +
                        "ON pdn.shouldhaveshards=false\n" +
                        "WHERE\n" +
                        "nspname NOT IN ('pg_catalog','information_schema')\n" +
                        "AND C .relkind <> 'i'\n" +
                        "AND nspname !~ '^pg_toast'\n" +
                        "AND relname not in (SELECT DISTINCT cs.table_name::varchar(255) FROM citus_shards cs)\n" +
                        "ORDER BY byte_size DESC\n" +
                        "offset " + offset + " rows fetch next " + perPage + " rows only\n", (rs, rowNum) -> {
                    NodeTable nodeInfo = new NodeTable();
                    nodeInfo.setTableName(rs.getString(1));
                    nodeInfo.setNode(rs.getString(2));
                    nodeInfo.setByteSize(rs.getLong(3));
                    nodeInfo.setPrettySize(rs.getString(4));
                    return nodeInfo;
                }
        );
    }


}
