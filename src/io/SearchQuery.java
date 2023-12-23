package io;

import person.Person;

import java.sql.*;
import java.util.ArrayList;

public class SearchQuery {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/addresslist";
    private static String username = "root";
    private static String password = "123456";

    public static ArrayList<Person> queryAllByName() throws Exception {
        String sql = "select * from person order by name";
        return select(sql);
    }
    public static ArrayList<Person> queryAllByNumber() throws Exception {
        String sql = "select * from person order by phonenumber";
        return select(sql);
    }
    public static ArrayList<Person> queryByName(String name) throws Exception {
        String sql = "select * from person where name="+"'"+name+"'";
        return select(sql);
    }
    public static ArrayList<Person> queryByNumber(String number) throws Exception {

        String sql = "select * from person where phonenumber="+"'"+number+"'";
        return select(sql);
    }
    private static ArrayList<Person> select(String sql) throws Exception{
        //加载驱动
        Class.forName(driver);
        //创建连接
        Connection connection = DriverManager.getConnection(url, username, password);
        //创建statement
        Statement statement = connection.createStatement();
        //执行sql
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Person> data=new ArrayList<>();
        while(resultSet.next()){
            data.add(new Person(resultSet.getString(1),resultSet.getString(2),
                    resultSet.getString(3),resultSet.getString(4)));
        }
        //关闭
        resultSet.close();
        statement.close();
        connection.close();
        return data;
    }
}
