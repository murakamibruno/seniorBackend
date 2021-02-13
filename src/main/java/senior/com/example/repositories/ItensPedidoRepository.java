package senior.com.example.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import senior.com.example.models.ItensPedido;
import senior.com.example.models.QItensPedido;
import senior.com.example.models.QProdServico;

import java.util.UUID;

@Repository
public interface ItensPedidoRepository extends PagingAndSortingRepository<ItensPedido, UUID>, QuerydslPredicateExecutor<ItensPedido>, QuerydslBinderCustomizer<QItensPedido> {

    @Override
    default public void customize(final QuerydslBindings bindings, final QItensPedido root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
