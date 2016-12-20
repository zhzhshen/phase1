package mapping;

import util.FinderUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CriteriaBuilder {
    private final FinderUtil finderUtil;
    private final Class klass;
    private List<Criterion> criteria;

    public <T> CriteriaBuilder(FinderUtil finderUtil, Class<T> klass) {
        this.finderUtil = finderUtil;
        this.klass = klass;
        this.criteria = new ArrayList<>();
    }

    public CriteriaBuilder criterion(Criterion criterion) {
        this.criteria.add(criterion);
        return this;
    }

    public <T> T get() throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        return (T) finderUtil.get(klass, criteria.toArray(new Criterion[criteria.size()]));
    }
}