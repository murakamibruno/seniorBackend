package senior.com.example.criteria.predicates;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import senior.com.example.criteria.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProdServicoPredicateBuilder {
    private List<SearchCriteria> params;

    public ProdServicoPredicateBuilder() {
        params = new ArrayList<>();
    }

    public ProdServicoPredicateBuilder with(final String key, final String operation, final Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public BooleanExpression build() {
        if (params.size() == 0) {
            return null;
        }

        final List<BooleanExpression> predicates = params.stream().map(param -> {
            ProdServicoPredicate predicate = new ProdServicoPredicate(param);
            return predicate.getPredicate();
        }).filter(Objects::nonNull).collect(Collectors.toList());

        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }

}
