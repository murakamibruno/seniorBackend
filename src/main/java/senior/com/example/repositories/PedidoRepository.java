package senior.com.example.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import senior.com.example.models.Pedido;
import senior.com.example.models.ProdServico;
import senior.com.example.models.QPedido;
import senior.com.example.models.QProdServico;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoRepository extends PagingAndSortingRepository<Pedido, UUID>, QuerydslPredicateExecutor<Pedido>, QuerydslBinderCustomizer<QPedido> {
    @Query(value="Select * from prodservico where itens_pedido_id = ?1", nativeQuery = true)
    List<ProdServico> findProdServicoById(UUID id);

    @Override
    default public void customize(final QuerydslBindings bindings, final QPedido root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
