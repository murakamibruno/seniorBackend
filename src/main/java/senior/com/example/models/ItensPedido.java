package senior.com.example.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="itens_pedido")
public class ItensPedido {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Type(type="pg-uuid")
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itensPedido", fetch = FetchType.LAZY)
    private List<ProdServico> prodServicos;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "itensPedido", fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Pedido pedido;

    @JsonManagedReference
    public List<ProdServico> getProdServicos() {
        return prodServicos;
    }

    public void setProdServicos(List<ProdServico> prodServicos) {
        this.prodServicos = prodServicos;
    }

    public ItensPedido() {
    }

    public ItensPedido(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


}
