package swc3.server.PrimaryDatasource.privilegesTesting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.bind.annotation.*;

//testing the priviledges of the application
//remove CREATE privilege from the application login and try to execute the endpoint.
@RestController
@RequestMapping("/privileges")
public class PrivilegesTestingController {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public PrivilegesTestingController(@Qualifier("jdbcTemplateDb1") JdbcTemplate jdbcTemplate,
                                       @Qualifier("namedParameterJdbcTemplateDb1") NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //vulnerable to SQL injection
    @PostMapping("/vulnerable/createTable")
    public void createTableVulnerable(@RequestParam(required = true) String name) {
        String sql = "CREATE TABLE "+ name + "(id integer)"; // SQL injection vulnerability
        jdbcTemplate.execute(sql);
    }

    //TODO: this does not work, it will not take the name value
    @PostMapping("/safe/createTable")
    public void createTableSafe(@RequestParam(required = true) String name) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("name", name);
        var sql = "CREATE TABLE :name(id integer);";
        namedParameterJdbcTemplate.update(sql, namedParameters);
    }





}

