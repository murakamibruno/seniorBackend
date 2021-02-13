package senior.com.example.criteria.predicates;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.*;
import senior.com.example.criteria.SearchCriteria;
import senior.com.example.models.ProdServico;

import java.util.UUID;

public class ProdServicoPredicate {
    private final String regex = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"; // the regex
    private SearchCriteria criteria;

    public ProdServicoPredicate(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public BooleanExpression getPredicate() {
        final PathBuilder<ProdServico> entityPath = new PathBuilder<>(ProdServico.class, "prodServico");

        if (isUUID(criteria.getValue().toString())) {
            PathBuilder<UUID> path = entityPath.get(criteria.getKey(), UUID.class);
            UUID value = UUID.fromString(criteria.getValue().toString());
            return path.eq(value);
        }
        else if (isNumeric(criteria.getValue().toString())) {
            NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            int value = Integer.parseInt(criteria.getValue().toString());
            switch (criteria.getOperation()) {
                case ":":
                    return path.eq(value);
                case ">":
                    return path.gt(value);
                case "<":
                    return path.lt(value);
            }
        } else if (isBoolean(criteria.getValue().toString())) {
            BooleanPath path = entityPath.getBoolean(criteria.getKey());
            Boolean value = Boolean.parseBoolean(criteria.getValue().toString());
            return path.eq(value);
        } else {
            StringPath path = entityPath.getString(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return path.containsIgnoreCase(criteria.getValue().toString());
            }
        }
        return null;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public static boolean isNumeric(final String str) {
        try {
            Integer.parseInt(str);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isBoolean(final String str) {
        if(str.equalsIgnoreCase("true")||str.equalsIgnoreCase("false")){
            return true;
        }else {
            return false;
        }
    }

    public boolean isUUID(final String str) {
        return str.toString().matches(this.regex);
    }
}
