package com.resqlity.orm;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.examples.SampleClass;
import com.resqlity.orm.examples.SampleRelation;
import com.resqlity.orm.queries.DeleteQuery;
import com.resqlity.orm.queries.SelectQuery;
import com.resqlity.orm.queries.UpdateQuery;

public class ResqlityContext {


    public ResqlityContext() {

        try {
            Foo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SelectQuery Select(Class<? extends Object> obj) {
        return new SelectQuery(obj);
    }

    public UpdateQuery Update(Class<? extends Object> obj) {
        return new UpdateQuery(obj);
    }

    public DeleteQuery Delete(Class<? extends Object> obj) {
        return new DeleteQuery(obj);
    }

    public void Foo() throws Exception {
//        new SelectQuery(SampleClass.class)
//                .Where("name", "Berkay", Comparator.Equal)
//                .Select("name")
//                .OrderBy("name", true)
//                .ThenBy("lastName", false)
//                .Query()
//                .Select("lastName")
//                .OrderBy("name", true)
//                .Query()
//                .PageBy()
//                .InnerJoin(SampleRelation.class, "firstName", "name", Comparator.Equal)
//                .ChildLeftJoin(SampleRelation.class, "parentId", "id", Comparator.Equal)
//                .Execute();
//        Update(SampleClass.class)
//                .Update("name", "Berkay")
//                .Update("lastName", "YALÇIN")
//                .Where("name", "Ahmet", Comparator.Equal)
//                .And("lastName", "YALÇIN", Comparator.Equal)
//                .Execute();
        Delete(SampleClass.class)
                .Where("name", "Berkay", Comparator.Equal)
                .Or("name", "Ahmet", Comparator.Equal)
                .Execute();
    }


}
