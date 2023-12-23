package io;

import java.sql.*;
public class AddUpdate {
    private static String driver="com.mysql.cj.jdbc.Driver";
    private static String url="jdbc:mysql://localhost:3306/addresslist";
    private static String username="root";
    private static String password="123456";
    public static void add(String name, String sex,String phonenumber,String address) throws Exception {
        //加载驱动
        Class.forName(driver);
        //创建连接
        Connection connection= DriverManager.getConnection(url,username,password);
        String sql="insert into person(name,sex,phonenumber,address) values (?,?,?,?)";
        //创建statement
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,name);
        ps.setString(2,sex);
        ps.setString(3,phonenumber);
        ps.setString(4,address);

        //执行sql 返回影响行数
        ps.executeUpdate();

        //关闭
        ps.close();
        connection.close();
    }

    public static boolean checkNumber(String number) throws Exception {
        //加载驱动
        Class.forName(driver);
        //创建连接
        Connection connection= DriverManager.getConnection(url,username,password);
        StringBuilder sql=new StringBuilder("select * from person where phonenumber=");
        sql.append("'").append(number).append("'");
        //创建statement
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql.toString());
        if(resultSet.next()){
            statement.close();
            connection.close();
            return true;
        }else{
            statement.close();
            connection.close();
            return false;
        }
    }
}

