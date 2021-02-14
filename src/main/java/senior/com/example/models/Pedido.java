package senior.com.example.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="pedido")
public class Pedido {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Type(type="pg-uuid")
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.LAZY)
    private List<ProdServico> prodServicos;

    private float precoPedido;

    @JsonManagedReference
    public List<ProdServico> getProdServicos() {
        return prodServicos;
    }

    public void setProdServicos(List<ProdServico> prodServicos) {
        this.prodServicos = prodServicos;
    }

    public Pedido() {

    }

    public Pedido(UUID id, float precoPedido) {
        this.id = id;
        this.precoPedido = precoPedido;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public float getPrecoPedido() {
        return precoPedido;
    }

    public void setPrecoPedido(float precoPedido) {
        this.precoPedido = precoPedido;
    }
}
