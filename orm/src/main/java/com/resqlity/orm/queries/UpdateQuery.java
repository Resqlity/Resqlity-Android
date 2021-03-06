package com.resqlity.orm.queries;

import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.consts.Endpoints;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.functions.join.JoinFunction;
import com.resqlity.orm.helpers.JsonHelper;
import com.resqlity.orm.helpers.ResqlityHelpers;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.models.querymodels.UpdateModel;
import com.resqlity.orm.models.responses.ResqlityResponse;
import com.resqlity.orm.queryobjects.update.UpdateQueryObject;

import java.net.HttpURLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class UpdateQuery extends BaseFilterableQuery {
    UpdateModel updateModel;

    public UpdateQuery(Class<?> tableClass, ResqlityContext context) {
        super(tableClass, context);
        updateModel = new UpdateModel(context.getApiKey(), getTableName(), getTableSchema());
    }

    /**
     * @param fieldName Field Name To Update
     * @param value     Value To Set
     * @return UpdateQuery
     * @throws ResqlityDbException
     */
    public UpdateQuery Update(String fieldName, Object value) throws ResqlityDbException {
        List<UpdateQueryObject> updateQueryObjects = updateModel.getModel();
        String columnName = getPropertyName(fieldName);
        String tableName = getTableName();
        String tableSchema = getTableSchema();
        UpdateQueryObject object = new UpdateQueryObject(tableName, tableSchema, columnName, value);
        if (updateQueryObjects.stream().anyMatch(x -> x.getColumnName() == columnName && x.getTableName() == tableName && x.getTableSchema() == tableSchema))
            throw new ResqlityDbException("Key already exists");
        updateQueryObjects.add(object);
        updateModel.setModel(updateQueryObjects);
        return this;
    }

    /**
     * @param fieldName  Field Name To Apply Condition
     * @param compareTo  Value To Compare
     * @param comparator Comparator (such as Comparator.Equal,Comparator.NotEqual)
     * @return UpdateWhereFunction
     * @throws ResqlityDbException
     */
    @Override
    public UpdateWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        WhereClauseModel root = new WhereClauseModel(super.getTableName(), super.getTableSchema(), super.getPropertyName(fieldName), compareTo, comparator);
        whereRootClause = root;
        return new UpdateWhereFunction(root, this);
    }

    /**
     * @param joinClass       Join With Class
     * @param fieldName       Field Name
     * @param parentFieldName Parent Class Field Name
     * @param comparator      Object Comparator
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    public JoinFunction InnerJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    /**
     * @param joinClass       Join With Class
     * @param fieldName       Field Name
     * @param parentFieldName Parent Class Field Name
     * @param comparator      Object Comparator
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    public JoinFunction LeftJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    /**
     * @param joinClass       Join With Class
     * @param fieldName       Field Name
     * @param parentFieldName Parent Class Field Name
     * @param comparator      Object Comparator
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    public JoinFunction RightJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    /**
     * @param joinClass       Join With Class
     * @param fieldName       Field Name
     * @param parentFieldName Parent Class Field Name
     * @param comparator      Object Comparator
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    public JoinFunction LeftOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    /**
     * @param joinClass       Join With Class
     * @param fieldName       Field Name
     * @param parentFieldName Parent Class Field Name
     * @param comparator      Object Comparator
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    public JoinFunction RightOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    /**
     * Completes Linked Where Object
     */
    @Override
    protected void CompleteWhere() {
        List<WhereClauseModel> whereClauseModels = updateModel.getWheres();
        whereClauseModels.add(whereRootClause);
        updateModel.setWheres(whereClauseModels);
        whereRootClause = null;
    }

    /**
     * @throws UnsupportedOperationException
     */
    @Override
    protected void CompleteJoin() throws ResqlityDbException {
        throw new UnsupportedOperationException("Not Implemented");
    }


    /**
     * @return ResqlityResponse With Affected Rows
     * @throws ResqlityDbException
     */
    public ResqlityResponse<Integer> Execute() throws ResqlityDbException {
        return Execute(false);
    }

    /**
     * @param useTransaction Specify To Use Transaction
     * @return ResqlityResponse With Affected Rows
     * @throws ResqlityDbException
     */
    public ResqlityResponse<Integer> Execute(boolean useTransaction) throws ResqlityDbException {
        if (updateModel.getModel() == null || updateModel.getModel().isEmpty())
            throw new ResqlityDbException("Invalid Operation");

        if (whereRootClause != null)
            CompleteWhere();
        updateModel.setUseTransaction(useTransaction);
        final ResqlityResponse<Integer> response = new ResqlityResponse<Integer>(null, false);
        Runnable insertRequest = () -> {
            try {
                HttpURLConnection urlConnection = ResqlityHelpers.getHttpURLConnection(
                        JsonHelper.Serialize(updateModel),
                        "PUT",
                        Endpoints.UPDATE_URL,
                        ResqlityHelpers.getDefaultHeaders(dbContext.getApiKey(), getTableName(), getTableSchema()),
                        true,
                        true);
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    ResqlityResponse<Integer> t = ResqlityHelpers.getResqlityResponse(urlConnection.getInputStream(), response.getClass());
                    response.setMessage(t.getMessage());
                    response.setSuccess(t.isSuccess());
                    response.setData(t.getData());
                } else if (responseCode == HttpsURLConnection.HTTP_BAD_REQUEST) {
                    String errorMessage = ResqlityHelpers.tryGetHttpErrors(urlConnection.getErrorStream());
                    response.setSuccess(false);
                    response.setMessage(errorMessage);
                } else if (responseCode == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                    response.setSuccess(false);
                    response.setMessage("Unauthorized Access");
                } else {
                    response.setSuccess(false);
                    response.setMessage(null);
                }
            } catch (Exception ignored) {
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
