package com.resqlity.orm.queries;

import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.annotations.ResqlityTable;
import com.resqlity.orm.consts.Endpoints;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.functions.join.JoinFunction;
import com.resqlity.orm.helpers.JsonHelper;
import com.resqlity.orm.helpers.ResqlityHelpers;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.models.querymodels.DeleteModel;
import com.resqlity.orm.models.responses.ResqlityResponse;

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
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class DeleteQuery extends BaseFilterableQuery {
    DeleteModel deleteModel;

    public DeleteQuery(Class<?> tableClass, ResqlityContext dbContext) {
        super(tableClass, dbContext);
        deleteModel = new DeleteModel(dbContext.getApiKey(), getTableName(), getTableSchema());
    }

    /**
     * @param fieldName  Field Name To Apply Condition
     * @param compareTo  Value To Compare
     * @param comparator Comparator (such as Comparator.Equal,Comparator.NotEqual)
     * @return DeleteWhereFunction
     * @throws ResqlityDbException
     */
    @Override
    public DeleteWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        WhereClauseModel root = null;
        root = new WhereClauseModel(super.getTableName(), super.getTableSchema(), super.getPropertyName(fieldName), compareTo, comparator);
        whereRootClause = root;
        return new DeleteWhereFunction(root, this);
    }

    @Override
    public JoinFunction InnerJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public JoinFunction LeftJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public JoinFunction RightJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public JoinFunction LeftOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public JoinFunction RightOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void CompleteWhere() {
        List<WhereClauseModel> whereClauseModels = deleteModel.getWheres();
        whereClauseModels.add(whereRootClause);
        deleteModel.setWheres(whereClauseModels);
        whereRootClause = null;
    }

    @Override
    protected void CompleteJoin() throws ResqlityDbException {
        throw new UnsupportedOperationException();
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
        if (whereRootClause != null)
            CompleteWhere();

        deleteModel.setUseTransaction(useTransaction);

        final ResqlityResponse<Integer> response = new ResqlityResponse<Integer>(null, false);
        Runnable insertRequest = () -> {
            try {
                HttpURLConnection urlConnection = ResqlityHelpers.getHttpURLConnection(
                        JsonHelper.Serialize(deleteModel),
                        "DELETE",
                        Endpoints.DELETE_URL,
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
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new ResqlityDbException(e.getMessage(), e);
        }
        return response;

    }


}
