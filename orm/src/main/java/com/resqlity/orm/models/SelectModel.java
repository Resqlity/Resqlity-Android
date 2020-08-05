package com.resqlity.orm.models;

import com.resqlity.orm.models.clausemodels.JoinClauseModel;
import com.resqlity.orm.models.clausemodels.OrderByClauseModel;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.consts.Pagination;
import com.resqlity.orm.queryobjects.SelectColumn;

import java.util.ArrayList;
import java.util.List;

public class SelectModel extends BaseModel {
    private List<SelectColumn> selectedColumns;
    private List<WhereClauseModel> wheres;
    private List<JoinClauseModel> joins;
    private OrderByClauseModel orderBy;
    private long skipCount;
    private long maxResultCount;
    private boolean useCache;

    public SelectModel(String apiKey, String tableName, String tableSchema) {
        super(apiKey, tableName, tableSchema);
        selectedColumns = new ArrayList<>();
        wheres = new ArrayList<>();
        joins = new ArrayList<>();
        skipCount = 0;
        maxResultCount = Pagination.PageSize;
    }


    public void setSelectedColumns(List<SelectColumn> selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public void setWheres(List<WhereClauseModel> wheres) {
        this.wheres = wheres;
    }

    public void setJoins(List<JoinClauseModel> joins) {
        this.joins = joins;
    }

    public void setOrderBy(OrderByClauseModel orderBy) {
        this.orderBy = orderBy;
    }

    public void setSkipCount(long skipCount) {
        this.skipCount = skipCount;
    }

    public void setMaxResultCount(long maxResultCount) {
        this.maxResultCount = maxResultCount;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    public List<SelectColumn> getSelectedColumns() {
        return selectedColumns;
    }

    public List<WhereClauseModel> getWheres() {
        return wheres;
    }

    public List<JoinClauseModel> getJoins() {
        return joins;
    }

    public OrderByClauseModel getOrderBy() {
        return orderBy;
    }

    public long getSkipCount() {
        return skipCount;
    }

    public long getMaxResultCount() {
        return maxResultCount;
    }

    public boolean isUseCache() {
        return useCache;
    }
}
