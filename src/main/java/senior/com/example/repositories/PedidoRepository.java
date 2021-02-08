package senior.com.example.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import senior.com.example.models.Pedido;
import senior.com.example.models.ProdServico;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoRepository extends PagingAndSortingRepository<Pedido, UUID> {
    @Query(value="Select * from prodservico where itens_pedido_id = ?1", nativeQuery = true)
    List<ProdServico> findProdServicoById(UUID id);
}
