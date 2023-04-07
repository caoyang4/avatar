package com.sankuai.avatar.web.dal.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

/**
 * 自定义 Mybatis 插件，自动设置 createTime 和 updatTime 的值。
 * 拦截 update 操作（添加和修改）
 * @author Jie.li.sh
 * @create 2020-03-18
 **/

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class TimeUpdateInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object object = invocation.getArgs()[1];
        //sql类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            //插入操作时，自动插入env
            try {
                Field fieldCreate = object.getClass().getDeclaredField("createTime");
                fieldCreate.setAccessible(true);
                fieldCreate.set(object, new Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Field fieldUpdate = object.getClass().getDeclaredField("updateTime");
                fieldUpdate.setAccessible(true);
                fieldUpdate.set(object, new Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                //update时，自动更新updated_at
                try {
                    Field fieldUpdate = object.getClass().getDeclaredField("updatetime");
                    fieldUpdate.setAccessible(true);
                    fieldUpdate.set(object, new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
