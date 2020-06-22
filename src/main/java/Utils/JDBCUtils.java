package Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Elaine
 * @Description:
 * @Date: Created in 上午1:22 2020/5/22
 * @Version: 1.0
 */
public class JDBCUtils {
    private static String FOR_NAME = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://39.104.14.232:3306/newecshop";
    private static String USERNAME = "test";
    private static String PASSWORD = "123456";

    public static Object[][] search(String sql) {
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        //声明存储测试数据的List对象
        List<Object[]> records = new ArrayList<Object[]>();

        try {
            // 加载JDBC驱动程序
            Class.forName(FOR_NAME);
            // 创建数据库的连接
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // 创建一个preparedStatement
            preparedStatement = conn.prepareStatement(sql);
            // 执行SQL语句
            rs = preparedStatement.executeQuery();

            //声明一个ResultSetMetaData对象
            ResultSetMetaData rsMetaData = rs.getMetaData();
            //调用ResultSetMetaData对象的getColumnCount方法获取数据行的列数
            int cols = rsMetaData.getColumnCount();

            while (rs.next()) {
                String fields[] = new String[cols];
                //遍历所有数据行中的所有数据，并存储在字符串数组中
                for (int colIdx = 0; colIdx < cols; colIdx++) {
                    fields[colIdx] = rs.getString(colIdx + 1);
                }
                //将每一行的数据存储到字符串数组后，存储到records中
                records.add(fields);

            }
            // 处理异常，关闭JDBC对象资源
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 1、先关闭requestSet
            // 2、再关闭preparedStatement
            // 3、最后关闭连接对象connection
            if (rs != null) {   // 关闭记录集
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {   // 关闭声明
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {  // 关闭连接对象
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        //定义函数返回值,即Object[][]
        //将存储测试数据的list转换为一个Object的二维数组
        Object[][] results = new Object[records.size()][];
        //设置二维数组每行的值，每行是一个Object对象
        for (int i = 0; i < records.size(); i++) {
            results[i] = records.get(i);
        }

        return results;
    }
}
