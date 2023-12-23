package io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ChangeUpdate {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/addresslist";
    private static String username = "root";
    private static String password = "123456";

    public static void changeName(String name,String oldNumber) throws Exception{
        String sql = "update person set name ="+"'"+name+"'"+" where phonenumber="+"'"+oldNumber+"'";
        change(sql);
    }
    public static void changeSex(String sex,String oldNumber) throws Exception{
        String sql = "update person set sex ="+"'"+sex+"'"+" where phonenumber="+"'"+oldNumber+"'";
        change(sql);
    }
    public static void changeNumber(String number,String oldNumber) throws Exception{
        String sql = "update person set phonenumber ="+"'"+number+"'"+" where phonenumber="+"'"+oldNumber+"'";
        change(sql);
    }
    public static void changeAddress(String address,String oldNumber) throws Exception{
        String sql = "update person set address ="+"'"+address+"'"+" where phonenumber="+"'"+oldNumber+"'";
        change(sql);
    }

    public static void change(String sql) throws Exception{
        //加载驱动
        Class.forName(driver);
        //创建连接
        Connection connection = DriverManager.getConnection(url, username, password);

        //创建statement
        PreparedStatement ps = connection.prepareStatement(sql);
        //执行sql
        ps.executeUpdate();
        ps.close();
        connection.close();
    }
}
