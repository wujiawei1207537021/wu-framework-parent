package com.wu.framework.database.generator.config;

import com.wu.framework.database.generator.repository.MySQLGeneratorDao;
import com.wu.framework.database.generator.repository.OracleGeneratorDao;
import com.wu.framework.database.generator.repository.PostgreSQLGeneratorDao;
import com.wu.framework.database.generator.repository.SQLServerGeneratorDao;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "wu.database.generator")
public class DbConfig {


    private final MySQLGeneratorDao mySQLGeneratorDao;
    private final OracleGeneratorDao oracleGeneratorDao;
    private final SQLServerGeneratorDao sqlServerGeneratorDao;
    private final PostgreSQLGeneratorDao postgreSQLGeneratorDao;
    private GeneratorEnums.GeneratorTypeEnums type = GeneratorEnums.GeneratorTypeEnums.MySQL;

//    @Bean("generatorRepository")
//    @Primary
//    public GeneratorRepository getGeneratorDao(){
//        if(GeneratorEnums.GeneratorTypeEnums.MySQL.equals(type)){
//            return mySQLGeneratorDao;
//        }else if(GeneratorEnums.GeneratorTypeEnums.ORACLE.equals(type)){
//            return oracleGeneratorDao;
//        }else if(GeneratorEnums.GeneratorTypeEnums.SQL_SERVER.equals(type)){
//            return sqlServerGeneratorDao;
//        }else if(GeneratorEnums.GeneratorTypeEnums.POSTGRES_SQL.equals(type)){
//            return postgreSQLGeneratorDao;
//        }else {
//            throw new RuntimeException("不支持当前数据库：" + type);
//        }
//    }
}
