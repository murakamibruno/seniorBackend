package senior.com.example.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import senior.com.example.models.ProdServico;
import org.springframework.stereotype.Repository;
import senior.com.example.models.QProdServico;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdServicoRepository extends PagingAndSortingRepository<ProdServico, UUID>, QuerydslPredicateExecutor<ProdServico>, QuerydslBinderCustomizer<QProdServico> {

    @Query(value="Select * from prodservico where pedido_id = ?1", nativeQuery = true)
    List<ProdServico> findByPedidoId(UUID id);

    @Override
    default public void customize(final QuerydslBindings bindings, final QProdServico root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
