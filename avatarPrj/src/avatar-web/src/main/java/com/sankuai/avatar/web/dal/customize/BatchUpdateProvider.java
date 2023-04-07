package com.sankuai.avatar.web.dal.customize;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

/**
 * @author chenxinli
 */
public class BatchUpdateProvider extends MapperTemplate {
    public BatchUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }


    public String batchUpdate(MappedStatement ms) {
        //1.创建StringBuilder用于拼接SQL语句的各个组成部分
        StringBuilder builder = new StringBuilder();

        //2.拼接foreach标签
        builder.append("<foreach collection=\"list\" item=\"record\" separator=\";\" >");

        //3.获取实体类对应的Class对象
        Class<?> entityClass = getEntityClass(ms);

        //4.获取实体类在数据库中对应的表名
        String tableName = super.tableName(entityClass);

        //5.生成update子句
        String updateClause = SqlHelper.updateTable(entityClass, tableName);

        builder.append(updateClause);

        //6.获取所有字段信息
        Set<EntityColumn> columns = EntityHelper.getColumns(entityClass);

        String appKeyColumn = null;
        String appKeyHolder = null;
        String setColumn = null;
        String setHolder = null;

        for (EntityColumn entityColumn : columns) {
            boolean isPrimaryKey = entityColumn.isId();
            if (isPrimaryKey) {
                continue;
            } else if ("appkey".equals(entityColumn.getColumn())) {
                appKeyColumn = entityColumn.getColumn();
                appKeyHolder = entityColumn.getColumnHolder("record");
            } else if ("set_name".equals(entityColumn.getColumn())) {
                setColumn = entityColumn.getColumn();
                setHolder = entityColumn.getColumnHolder("record");
            } else {
            }

        }
        builder.append(SqlHelper.updateSetColumns(entityClass, "record", true, isNotEmpty()));

        //10.使用前面缓存的主键名、主键值拼接where子句
        builder.append("where ").append(appKeyColumn).append("=").append(appKeyHolder).append(" and ").append(setColumn).append("=").append(setHolder);

        builder.append("</foreach>");

        //11.将拼接好的字符串返回
        return builder.toString();
    }

//    public String batchInsertOrUpdate(MappedStatement ms){
//        final Class<?> entityClass = getEntityClass(ms);
//        //开始拼sql
//        StringBuilder sql = new StringBuilder();
//        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
//        sql.append(SqlHelper.insertColumns(entityClass, true, false, false));
//        sql.append(" VALUES ");
//        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
//        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
//        //获取全部列
//        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
//        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
//        for (EntityColumn column : columnList) {
//            if (!column.isId() && column.isInsertable()) {
//                sql.append(column.getColumnHolder("record") + ",");
//            }
//        }
//        sql.append("</trim>");
//        sql.append("</foreach>");
//
//        sql.append(" ON DUPLICATE KEY UPDATE ");
//        sql.append("<trim suffixOverrides=\",\">");
//        for (EntityColumn column : columnList) {
//            if (!column.isId() && column.isInsertable()) {
//                String columnName = column.getColumn();
//                sql.append(String.format("%s = values(%s)", columnName, columnName) + ",");
//            }
//        }
//        sql.append("</trim>");
//        return sql.toString();
//    }
}
