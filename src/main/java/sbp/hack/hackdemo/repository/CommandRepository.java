package sbp.hack.hackdemo.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CommandRepository {

    private JdbcTemplate jdbcTemplate;

    public void balance() {
        jdbcTemplate.query(
                "SELECT citus_rebalance_start()", (rs, rowNum) -> {
                    return null;
                }
        );
    }

    public String getStatus() {
        return jdbcTemplate.query(
                "SELECT state FROM citus_rebalance_status()", (rs, rowNum) -> {
                    return rs.getString(1);
                }
        ).get(0);
    }

    public void addNode(String node, String port) {
        jdbcTemplate.query(
                "select * from citus_add_node('" + node + "', " + port + ");", (rs, rowNum) -> {
                    return null;
                }
        );
    }


}
