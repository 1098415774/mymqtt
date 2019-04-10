package com.sh.base.mybatis.interceptor;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import com.sh.base.mybatis.MybatisQueryConstants;
import com.sh.base.utils.ReflectUtil;
import com.sh.base.utils.StringUtils;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class,Integer.class})})
public class MybatisPageInterceptor implements Interceptor {

    //每页显示的条目数
    private int pageSize;
    //数据库类型
    private String dbType;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler,"delegate");
        BoundSql boundSql = delegate.getBoundSql();
        Object object = (Object) boundSql.getParameterObject();
        if (object instanceof Map){
             addQuery(boundSql);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private void addQuery(BoundSql boundSql){
        Map map = (Map) boundSql.getParameterObject();
        int operations = 0;
        try {
            operations = (int) map.get(MybatisQueryConstants.OPERATION);
        }catch (NullPointerException e){

        }
        int cc = 0xfe;
        if ((operations | cc) == 0xff ){
            String sql = boundSql.getSql();
            if (StringUtils.isEmpty(sql)){
                return;
            }
            StringBuilder sqlbuilder = new StringBuilder(sql);
            Integer startindex = (Integer) map.get(MybatisQueryConstants.PAGE_START_INDEX);
            if (startindex == null){
                startindex = 1;
            }
            paging(sqlbuilder,startindex);
            ReflectUtil.setFieldValue(boundSql,"sql",sqlbuilder.toString());
        }
    }

    private void paging(StringBuilder sqlbuilder,int startindex){
        switch (this.dbType){
            case "sqlserver":
                sqlbuilder.insert(0,"select top " + pageSize + " o.* from (select row_number() over(order by id asc ) as rownumber,* from (");
                sqlbuilder.append(") as oo ) as o where rownumber > " + startindex + ";");
                break;
            case "mysql":
                sqlbuilder.append("limit " + startindex + " , " + pageSize);
                break;
        }

    }

}
