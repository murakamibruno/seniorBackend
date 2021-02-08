package senior.com.example.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import senior.com.example.models.ItensPedido;

import java.util.UUID;

@Repository
public interface ItensPedidoRepository extends PagingAndSortingRepository<ItensPedido, UUID> {

}
