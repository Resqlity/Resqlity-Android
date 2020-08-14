package com.resqlity.orm.queries;

import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.consts.Endpoints;
import com.resqlity.orm.consts.Pagination;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.JoinType;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.helpers.JsonHelper;
import com.resqlity.orm.helpers.ResqlityHelpers;
import com.resqlity.orm.models.clausemodels.JoinClauseModel;
import com.resqlity.orm.models.clausemodels.OrderByClauseModel;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.models.querymodels.SelectModel;
import com.resqlity.orm.models.responses.ResqlityResponse;
import com.resqlity.orm.queryobjects.select.SelectColumn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class SelectQuery extends BaseFilterableQuery {
    private SelectModel selectModel;

    public SelectQuery(Class<?> tableClass, ResqlityContext dbContext) {
        super(tableClass, dbContext);
        selectModel = new SelectModel(dbContext.getApiKey(), getTableName(), getTableSchema());
    }


    /**
     * @param field Field Name In Class T
     * @return SelectQuery
     * @throws ResqlityDbException
     */
    public SelectQuery Select(String field) throws ResqlityDbException {
        List<SelectColumn> columns = selectModel.getSelectedColumns();
        SelectColumn column = new SelectColumn(super.getTableName(), super.getTableSchema(), super.getPropertyName(field), field);
        columns.add(column);
        selectModel.setSelectedColumns(columns);
        return this;
    }

    /**
     * @param fieldName  Field Name To Apply Condition
     * @param compareTo  Value To Compare
     * @param comparator Comparator (such as Comparator.Equal,Comparator.NotEqual)
     * @return SelectWhereFunction
     * @throws ResqlityDbException
     */
    public SelectWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        WhereClauseModel root = new WhereClauseModel(super.getTableName(), super.getTableSchema(), super.getPropertyName(fieldName), compareTo, comparator);
        whereRootClause = root;
        return new SelectWhereFunction(root, this);
    }

    public SelectOrderByFunction OrderBy(Class<?> tableClass, String field, boolean isAsc) throws ResqlityDbException {
        if (selectModel.getOrderBy() == null) {
            selectModel.setOrderBy(new OrderByClauseModel(getTableName(tableClass), getTableSchema(tableClass), getPropertyName(field), isAsc));
            return new SelectOrderByFunction(this, selectModel.getOrderBy());
        }
        SelectOrderByFunction func = new SelectOrderByFunction(this, selectModel.getOrderBy());
        func.ThenBy(tableClass, field, isAsc);
        return func;
    }

    /**
     * @param field Field To Order
     * @param isAsc Is Ascending
     * @return
     * @throws ResqlityDbException
     */
    public SelectOrderByFunction OrderBy(String field, boolean isAsc) throws ResqlityDbException {
        return OrderBy(getBaseTableClass(), field, isAsc);
    }

    /**
     * @param joinClass       Class to join
     * @param fieldName       Field to compare
     * @param parentFieldName Parent field to compare with child
     * @param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @return
     * @throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction InnerJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.INNER);
    }

    /**
     * @param joinClass       Class to join
     * @param fieldName       Field to compare
     * @param parentFieldName Parent field to compare with child
     * @param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @return
     * @throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction LeftJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.LEFT);
    }

    /**
     * @param joinClass       Class to join
     * @param fieldName       Field to compare
     * @param parentFieldName Parent field to compare with child
     * @param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @return
     * @throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction RightJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.RIGHT);
    }

    /**
     * @param joinClass       Class to join
     * @param fieldName       Field to compare
     * @param parentFieldName Parent field to compare with child
     * @param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @return
     * @throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction LeftOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.LEFT_OUTER);
    }

    /**
     * @param joinClass       Class to join
     * @param fieldName       Field to compare
     * @param parentFieldName Parent field to compare with child
     * @param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @return
     * @throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction RightOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.RIGHT_OUTER);
    }

    private SelectJoinFunction Join(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator, JoinType type) throws ResqlityDbException {
        String tableName = getTableName(joinClass);
        String tableSchema = getTableSchema(joinClass);
        String parentColumnName = getPropertyName(parentFieldName);
        String childColumnName = getPropertyName(joinClass, fieldName);
        JoinClauseModel joinClauseModel = new JoinClauseModel(tableName, tableSchema, childColumnName, parentColumnName, comparator, type);
        lastJoinClause = joinClauseModel;
        return new SelectJoinFunction(this, joinClauseModel, joinClass);
    }


    /**
     * @return SelectQuery
     */
    public SelectQuery PageBy() {
        return PageBy(1);
    }

    /**
     * @param page Page Index
     * @return
     */
    public SelectQuery PageBy(int page) {
        return PageBy((page - 1) * Pagination.PageSize, Pagination.PageSize);
    }

    /**
     * @param page     Page Index
     * @param pageSize Page Size
     * @return
     */
    public SelectQuery PageBy(int page, long pageSize) {
        return PageBy((page - 1) * pageSize, pageSize);
    }

    /**
     * @param skipCount      Skip Count
     * @param maxResultCount Page Size
     * @return
     */
    public SelectQuery PageBy(long skipCount, long maxResultCount) {
        selectModel.setMaxResultCount(maxResultCount);
        selectModel.setSkipCount(skipCount);
        return this;
    }


    protected void CompleteWhere() {
        List<WhereClauseModel> whereClauseModels = selectModel.getWheres();
        whereClauseModels.add(whereRootClause);
        selectModel.setWheres(whereClauseModels);
        whereRootClause = null;
    }

    protected void CompleteOrderBy() {
        selectModel.setOrderBy(selectModel.getOrderBy());
    }

    @Override
    protected void CompleteJoin() {
        List<JoinClauseModel> joinClauseModels = selectModel.getJoins();
        joinClauseModels.add(lastJoinClause);
        selectModel.setJoins(joinClauseModels);
        lastJoinClause = null;
    }

    /**
     * @param <T> Response Data Type
     * @return ResqlityResponse With T Data
     * @throws ResqlityDbException
     */
    public <T> ResqlityResponse<T> Execute() throws ResqlityDbException {
        return Execute(false, false);
    }

    /**
     * @param useCache Specify to use cache
     * @param flushCache Specify to flush cache
     * @param <T> Data Type
     * @return ResqlityResponse With T Data
     * @throws ResqlityDbException
     */
    public <T> ResqlityResponse<T> Execute(boolean useCache, boolean flushCache) throws ResqlityDbException {
        if (flushCache && !useCache)
            throw new ResqlityDbException("Flush Cache Needs Use Cache");

        selectModel.setFlushCache(flushCache);
        selectModel.setUseCache(useCache);
        CompleteOrderBy();
        if (whereRootClause != null)
            CompleteWhere();
        if (lastJoinClause != null)
            CompleteJoin();

        final ResqlityResponse<T> response = new ResqlityResponse<T>("", false);
        Runnable insertRequest = () -> {
            try {
                HttpURLConnection urlConnection = ResqlityHelpers.getHttpURLConnection(
                        JsonHelper.Serialize(selectModel),
                        "POST",
                        Endpoints.SELECT_URL,
                        ResqlityHelpers.getDefaultHeaders(dbContext.getApiKey(), getTableName(), getTableSchema()),
                        true,
                        true);
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    ResqlityResponse<T> t = ResqlityHelpers.getResqlityResponse(urlConnection.getInputStream(), response.getClass());
                    response.setMessage(t.getMessage());
                    response.setSuccess(t.isSuccess());
                    response.setData(t.getData());
                } else if (responseCode == HttpsURLConnection.HTTP_BAD_REQUEST) {
                    String errorMessage = ResqlityHelpers.tryGetHttpErrors(urlConnection.getErrorStream());
                    response.setSuccess(false);
                    response.setMessage(errorMessage);

                } else {
                    response.setSuccess(false);
                    response.setMessage(null);
                }
                urlConnection.connect();

            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        };

        Thread t = new Thread(insertRequest);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new ResqlityDbException(e.getMessage(), e);
        }
        return response;
    }


}
