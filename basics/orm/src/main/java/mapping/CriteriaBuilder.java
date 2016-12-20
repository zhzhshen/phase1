package mapping;

import util.MappingUtil;

import java.util.ArrayList;
import java.util.List;

public class CriteriaBuilder {
    private final MappingUtil mappingUtil;
    private final Class klass;
    private List<Criterion> criteria;

    public <T> CriteriaBuilder(MappingUtil mappingUtil, Class<T> klass) {
        this.mappingUtil = mappingUtil;
        this.klass = klass;
        this.criteria = new ArrayList<>();
    }

    public CriteriaBuilder criterion(Criterion criterion) {
        this.criteria.add(criterion);
        return this;
    }

    public <T> T get() {
        return (T) mappingUtil.get(klass, criteria.toArray(new Criterion[criteria.size()]));
    }
}