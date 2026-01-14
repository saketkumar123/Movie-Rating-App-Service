package com.chaiaurjava.config_client.controller;

import com.chaiaurjava.config_client.datasource.MySQLDatasource;
import com.chaiaurjava.config_client.datasource.OracleDatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    private MySQLDatasource mySQLDatasource;
    private OracleDatasource oracleDatasource;

    public GreetingController(MySQLDatasource mySQLDatasource, OracleDatasource oracleDatasource) {
        this.mySQLDatasource = mySQLDatasource;
        this.oracleDatasource = oracleDatasource;
    }

    @Value("${my.greeting}")
    private String greetingMessage;

    @Autowired
    private Environment environment;

    @GetMapping("/greeting")
    public String getGreetings(){
        return greetingMessage+"\t"+mySQLDatasource.getConnection()+"\t"+oracleDatasource.getConnection();
    }

    @GetMapping("/envDetails")
    public String getEnvDetails(){
        return environment.toString();
    }
}
