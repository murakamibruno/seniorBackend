package senior.com.example.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import senior.com.example.models.ProdServico;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdServicoRepository extends PagingAndSortingRepository<ProdServico, UUID> {

    //@Query(value="Select * from prodservico where is_produto = true", nativeQuery = true)
    List<ProdServico> findByItensPedidoId(UUID id);
}
