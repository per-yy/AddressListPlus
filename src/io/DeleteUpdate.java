package io;

import java.sql.*;

public class DeleteUpdate {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/addresslist";
    private static String username = "root";
    private static String password = "123456";

    public static int byName(String name) throws Exception {
        //加载驱动
        Class.forName(driver);
        //创建连接
        Connection connection = DriverManager.getConnection(url, username, password);
        //创建statement
        Statement statement = connection.createStatement();
        //执行sql
        int i = statement.executeUpdate("delete from person where name=" + "'" + name + "'");
        statement.close();
        connection.close();
        return i;
    }
    public static int byNumber(String number) throws Exception {
        //加载驱动
        Class.forName(driver);
        //创建连接
        Connection connection = DriverManager.getConnection(url, username, password);
        //创建statement
        Statement statement = connection.createStatement();
        //执行sql
        int i = statement.executeUpdate("delete from person where phonenumber=" + "'" + number + "'");
        statement.close();
        connection.close();
        return i;
    }
}
