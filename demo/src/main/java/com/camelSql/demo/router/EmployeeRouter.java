package com.camelSql.demo.router;

import com.camelSql.demo.util.EmployeeMapper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sql.SqlComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class EmployeeRouter extends RouteBuilder {

    @Autowired
    DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //define the SQL Component bean which will be used as an endpoint in our route
    @Bean
    public SqlComponent sql(DataSource dataSource) {
        SqlComponent sql = new SqlComponent();
        sql.setDataSource(dataSource);
        return sql;
    }

    @Override
    public void configure() throws Exception {

        from("direct:insert").log("Inserted new Book") //.bean(EmployeeMapper.class, "getMap")
            .to("sql:INSERT INTO employees(EmpId, EmpName) VALUES (1, Ezinwa)");
        from("direct:select").to("sql:select * from employees")
            .bean(EmployeeMapper.class, "readEmployees").log("${body}");
    }

}