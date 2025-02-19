package spring.boot.demo.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022-10-31 10:53
 */

@Data
@Slf4j
public class TableToExcel {

    /**
     * 数据库连接
     */
    private static final String DATA_SOURCES ="ka5020";
    private static final String URL ="jdbc:mysql://127.0.0.1:3306/" + DATA_SOURCES + "?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=Asia/Shanghai&allowMultiQueries=true";
    private static final String NAME = "root";
    private static final String PASSWORD = "1234";
    private static final String DRIVER ="com.mysql.jdbc.Driver";

    /**
     * 获取当前数据库下所有的表
     * @return
     */
    public List<String> getAllTableName() {
        List<String> resultList = new LinkedList<>();
        String sql = "select table_name from information_schema.tables where table_schema = (select database())";
        PreparedStatement preparedStatement;
        Connection con = null;
        try {
            Class.forName(DRIVER);
            // 创建连接
            con = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String tableName = resultSet.getString("table_name");
                resultList.add(tableName);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    /**
     * 获取表结构
     * @param tableNames
     */
    public List<TableInfo> genEntitySomeTable(List<String> tableNames){
        List<TableInfo> tableInfos = new LinkedList<>();
        for (String tableName : tableNames) {
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableName(tableName);
            // 创建连接
            Connection con = null;
            // 查要生成实体类的表
            String sql1 = "select * from " + tableName;
            String sql2 = "show full fields from " + tableName;
            PreparedStatement statement1;
            PreparedStatement statement2;
            try {
                Class.forName(DRIVER);
                con = DriverManager.getConnection(URL, NAME, PASSWORD);
                statement1 = con.prepareStatement(sql1);
                ResultSetMetaData resultSet = statement1.getMetaData();
                statement2 = con.prepareStatement(sql2);
                ResultSet rsResultSet = statement2.executeQuery();
                // 统计列
                int size = resultSet.getColumnCount();
                tableInfo.setColNames(new String[size]);
                tableInfo.setColTypes(new String[size]);
                tableInfo.setColSizes(new int[size]);
                tableInfo.setColComment(new String[size]);
                tableInfo.setColNullAbles(new String[size]);
                tableInfo.setColScales(new int[size]);
                int j = 0;
                while (rsResultSet.next()) {
                    tableInfo.getColComment()[j] = rsResultSet.getObject(9).toString();
                    j++;
                }
                for (int i = 0; i < size; i++) {
                    tableInfo.getColNames()[i] = resultSet.getColumnName(i + 1);
                    tableInfo.getColTypes()[i] = resultSet.getColumnTypeName(i + 1).toLowerCase();
                    tableInfo.getColSizes()[i] = resultSet.getColumnDisplaySize(i + 1);
                    tableInfo.getColNullAbles()[i] = resultSet.isNullable(i + 1) == ResultSetMetaData.columnNoNulls ? "Y" : "N";
                    tableInfo.getColScales()[i] = resultSet.getScale(i + 1);
                }
                tableInfos.add(tableInfo);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return tableInfos;
    }

    /**
     * 生成单个表结构文档
     * @throws Exception
     */
    public void createTableExcel(List<TableInfo> tableInfos) throws Exception{
        for (TableInfo tableInfo : tableInfos) {
            // 利用POI3.8及其以上，每个Sheet可以存1,048,576行数据，每行可以有16,384列数据
            Workbook workbook = new SXSSFWorkbook(100);
            createWorkBook(workbook, tableInfo);
            // 输出目录
            String filePath = "D:\\" + tableInfo.getTableName() + ".xlsx";
            OutputStream out = new FileOutputStream(filePath);
            workbook.write(out);
        }
    }

    /**
     * 生成单个表结构文档
     * @throws Exception
     */
    public void createDataSourcesExcel(List<TableInfo> tableInfos) throws Exception{
        Workbook workbook = new SXSSFWorkbook(100);
        for (TableInfo tableInfo : tableInfos) {
            createWorkBook(workbook, tableInfo);
        }
        // 输出目录
        String filePath = "D:\\" + DATA_SOURCES + ".xlsx";
        OutputStream out = new FileOutputStream(filePath);
        workbook.write(out);
    }

    /**
     * 生成Excel工作簿
     * @param workbook
     * @param tableInfo
     */
    public void createWorkBook(Workbook workbook, TableInfo tableInfo){
        String tableName = tableInfo.getTableName();
        // 创建sheet工作表
        if (tableName.length() > 31) {
            tableName = "缺少前缀" + tableName.substring(tableName.length() - 27);
        }
        SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet(tableName);
        // 从模板sheet工作表第几行开始插入（注意行、列、单元格都是从0开始数）
        int startRow = 0;
        Row row = sheet.createRow(startRow++);
        row.createCell(1).setCellValue("序号");
        row.createCell(2).setCellValue("含义");
        row.createCell(3).setCellValue("名称");
        row.createCell(4).setCellValue("类型");
        row.createCell(5).setCellValue("长度");
        row.createCell(6).setCellValue("小数点");
        row.createCell(7).setCellValue("非空");
        for (int j = 0; j < tableInfo.getColSizes().length; j++) {
            row = sheet.createRow(startRow++);
            row.createCell(1).setCellValue(j + 1);
            row.createCell(2).setCellValue(tableInfo.getColComment()[j]);
            row.createCell(3).setCellValue(tableInfo.getColNames()[j]);
            row.createCell(4).setCellValue(tableInfo.getColTypes()[j]);
            row.createCell(5).setCellValue(tableInfo.getColSizes()[j]);
            row.createCell(6).setCellValue(tableInfo.getColScales()[j]);
            row.createCell(7).setCellValue(tableInfo.getColNullAbles()[j]);
        }
    }

    public static void main(String[] args) throws Exception {
        TableToExcel tableToExcel = new TableToExcel();
        List<String> allTableName = tableToExcel.getAllTableName();
        List<TableInfo> tableInfos = tableToExcel.genEntitySomeTable(allTableName);
//        tableToExcel.createTableExcel(tableInfos);
        tableToExcel.createDataSourcesExcel(tableInfos);
    }

    @Data
    class TableInfo{

        /**
         * 表名
         */
        private String  tableName;

        /**
         * 列名数组
         */
        private String[] colNames;

        /**
         * 列名备注
         */
        private String[] colComment;

        /**
         * 是否为空
         */
        private String[] colNullAbles;

        /**
         * 列名类型数组
         */
        private String[] colTypes;

        /**
         * 列名长度数组
         */
        private int[] colSizes;

        /**
         * 列名小数点位数
         */
        private int[] colScales;
    }

}

