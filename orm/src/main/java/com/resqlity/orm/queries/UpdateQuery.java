package com.resqlity.orm.queries;

import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.consts.Endpoints;
import com.resqlity.orm.enums.Comparator;
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

    public UpdateQuery Update(String fieldName, Object value) throws Exception {
        List<UpdateQueryObject> updateQueryObjects = updateModel.getModel();
        String columnName = getPropertyName(fieldName);
        String tableName = getTableName();
        String tableSchema = getTableSchema();
        UpdateQueryObject object = new UpdateQueryObject(tableName, tableSchema, columnName, value);
        if (updateQueryObjects.stream().anyMatch(x -> x.getColumnName() == columnName && x.getTableName() == tableName && x.getTableSchema() == tableSchema))
            throw new Exception("Key already exists");
        updateQueryObjects.add(object);
        updateModel.setModel(updateQueryObjects);
        return this;
    }

    @Override
    public UpdateWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException {
        WhereClauseModel root = new WhereClauseModel(super.getTableName(), super.getTableSchema(), super.getPropertyName(fieldName), compareTo, comparator);
        whereRootClause = root;
        return new UpdateWhereFunction(root, this);
    }

    @Override
    public JoinFunction InnerJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public JoinFunction LeftJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public JoinFunction RightJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public JoinFunction LeftOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public JoinFunction RightOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    protected void CompleteWhere() {
        List<WhereClauseModel> whereClauseModels = updateModel.getWheres();
        whereClauseModels.add(whereRootClause);
        updateModel.setWheres(whereClauseModels);
        whereRootClause = null;
    }

    @Override
    protected void CompleteJoin() throws NoSuchMethodException {
        throw new NoSuchMethodException("Not Implemented");
    }

    public void Execute() throws Exception {
        Execute(false);
    }

    public ResqlityResponse<Integer> Execute(boolean useTransaction) throws Exception {
        if (updateModel.getModel() == null || updateModel.getModel().isEmpty())
            throw new Exception("Invalid Operation");

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
                } else {
                    response.setSuccess(false);
                    response.setMessage(null);
                }
            } catch (Exception ignored) {
            }

        };

        Thread t = new Thread(insertRequest);
        t.start();
        t.join();
        return response;
    }
}
