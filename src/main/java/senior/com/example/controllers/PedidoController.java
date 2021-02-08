package senior.com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senior.com.example.models.Pedido;
import senior.com.example.models.ProdServico;
import senior.com.example.repositories.PedidoRepository;
import senior.com.example.repositories.ProdServicoRepository;
import senior.com.example.services.PedidoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
    private Pageable pageSize = PageRequest.ofSize(10);
    private PedidoRepository pedidoRepository;
    private ProdServicoRepository prodServicoRepository;

    private float discountSumValue = 0.0F;

    PedidoService pedidoService = new PedidoService();

    @Autowired
    public void setPedidoRepository(PedidoRepository pedidoRepository, ProdServicoRepository prodServicoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.prodServicoRepository = prodServicoRepository;
    }

    @GetMapping
    public Iterable<Pedido> getAllPedidos() {
        return pedidoRepository.findAll(pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable(value = "id") UUID id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if(pedido.isPresent()) {
            return new ResponseEntity<Pedido>(pedido.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/produto-servico")
    public Iterable<ProdServico> getProdServicoByPedidoId(@PathVariable(value = "id") UUID id) {
        List<ProdServico> prodServico = pedidoRepository.findProdServicoById(id);
        return prodServico;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@RequestBody Pedido pedido, @PathVariable("id") UUID id) {
        if (pedidoRepository.existsById(id)) {
            pedido.setId(id);
            Pedido pedidoUpdated = pedidoRepository.save(pedido);
            return new ResponseEntity<Pedido>(pedidoUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/desconto/{desconto}")
    public ResponseEntity<Pedido> updatePedidoDesconto(@PathVariable("id") UUID id, @PathVariable("desconto") int desconto) {
        if (pedidoRepository.existsById(id)) {
            Pedido pedido = pedidoRepository.findById(id).get();
            pedido.setPrecoPedido(pedido.getPrecoPedido() - pedidoService.getSumDiscountValue(pedido, desconto, prodServicoRepository));
            Pedido pedidoUpdated = pedidoRepository.save(pedido);
            return new ResponseEntity<Pedido>(pedidoUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Pedido> savePedido (@RequestBody Pedido pedido) {
        pedido.setPrecoPedido(pedidoService.getSumPrice(pedido, prodServicoRepository));
        Pedido pedidoCreated = pedidoRepository.save(pedido);
        return new ResponseEntity<Pedido>(pedidoCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pedido> deletePedido (@PathVariable("id") UUID id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            Pedido pedidoDeleted = pedido.get();
            pedidoRepository.delete(pedidoDeleted);
            return new ResponseEntity<Pedido>(pedidoDeleted, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
