package com.resqlity.orm;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.examples.SampleClass;
import com.resqlity.orm.examples.SampleRelation;
import com.resqlity.orm.queries.SelectQuery;

public class ResqlityContext {


    public ResqlityContext() {

        try {
            Foo();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public SelectQuery Select(Class<?> obj) {
        return new SelectQuery(obj);
    }

    public void Foo() throws NoSuchFieldException {
        new SelectQuery(SampleClass.class)
                .Where("name","Berkay",Comparator.Equal)
                .Select("name")
                .OrderBy("name",true)
                .ThenBy("lastName",false)
                .Query()
                .Select("lastName")
                .OrderBy("name",true)
                .Query()
                .PageBy()
                .InnerJoin(SampleRelation.class,"firstName","name",Comparator.Equal)
                .ChildLeftJoin(SampleRelation.class,"parentId","id",Comparator.Equal)
                .Execute();

    }


}
