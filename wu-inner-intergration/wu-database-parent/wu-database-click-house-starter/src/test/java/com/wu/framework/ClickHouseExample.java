package com.wu.framework;

import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDriver;
import ru.yandex.clickhouse.ClickHousePreparedStatement;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClickHouseExample {
    public static void main(String[] args) {
        // 设置ClickHouse连接属性
        ClickHouseProperties properties = new ClickHouseProperties();
        properties.setUser("your username");
        properties.setPassword("your password");

        try {
            // 创建ClickHouse连接
            ClickHouseDriver driver = new ClickHouseDriver();
            ClickHouseConnection connection = driver.connect("jdbc:clickhouse://your_host:your_port/your_database", properties);

            // 执行查询
            String sql = "SELECT * FROM your_table";
            ClickHousePreparedStatement statement = (ClickHousePreparedStatement) connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // 处理查询结果
            while (resultSet.next()) {
                // 从结果集中获取数据
                String column1 = resultSet.getString("column1");
                int column2 = resultSet.getInt("column2");

                // 处理数据
                System.out.println("column1: " + column1 + ", column2: " + column2);
            }

            // 关闭连接
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error connecting to ClickHouse: " + e.getMessage());
        }
    }
}
