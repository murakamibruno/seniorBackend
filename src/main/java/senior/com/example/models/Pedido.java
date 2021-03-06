package senior.com.example.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

    private double precoPedido;

    @NotNull
    @Column(name= "isFechado", nullable = false, columnDefinition = "boolean default false")
    private boolean isFechado;

    @JsonManagedReference
    public List<ProdServico> getProdServicos() {
        return prodServicos;
    }

    public void setProdServicos(List<ProdServico> prodServicos) {
        this.prodServicos = prodServicos;
    }

    public Pedido() {

    }

    public Pedido(UUID id, double precoPedido, boolean isFechado) {
        this.id = id;
        this.precoPedido = precoPedido;
        this.isFechado = isFechado;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getPrecoPedido() {
        return precoPedido;
    }

    public void setPrecoPedido(double precoPedido) {
        this.precoPedido = precoPedido;
    }

    public boolean isFechado() {
        return isFechado;
    }

    public void setFechado(boolean fechado) {
        isFechado = fechado;
    }

}
