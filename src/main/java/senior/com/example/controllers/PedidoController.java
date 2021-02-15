package senior.com.example.controllers;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senior.com.example.criteria.predicates.PedidoPredicateBuilder;
import senior.com.example.criteria.predicates.ProdServicoPredicateBuilder;
import senior.com.example.exception.PedidoFechadoException;
import senior.com.example.exception.PedidoNotFound;
import senior.com.example.models.Pedido;
import senior.com.example.models.ProdServico;
import senior.com.example.repositories.PedidoRepository;
import senior.com.example.repositories.ProdServicoRepository;
import senior.com.example.services.PedidoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
    private Pageable pageSize = PageRequest.ofSize(10);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdServicoRepository prodServicoRepository;

    private float discountSumValue = 0.0F;

    PedidoService pedidoService = new PedidoService();

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
            throw new PedidoNotFound(id);
        }
    }

    @GetMapping("/{id}/listarProdutos")
    public Iterable<ProdServico> getProdServicoByPedidoId(@PathVariable(value = "id") UUID id) {
        List<ProdServico> prodServico = prodServicoRepository.findByPedidoId(id);
        return prodServico;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filter")
    @ResponseBody
    public Iterable<Pedido> search(@RequestParam(value = "search") String search) {
        PedidoPredicateBuilder builder = new PedidoPredicateBuilder();

        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w.+?)(:|<|>)((\\w+?\\-?)*),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return pedidoRepository.findAll(exp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@RequestBody Pedido pedido, @PathVariable("id") UUID id) {
        if (pedidoRepository.existsById(id)) {
            pedido.setId(id);
            Pedido pedidoUpdated = pedidoRepository.save(pedido);
            return new ResponseEntity<Pedido>(pedidoUpdated, HttpStatus.OK);
        } else {
            throw new PedidoNotFound(id);
        }
    }

    @PutMapping("/{id}/desconto/{desconto}")
    public ResponseEntity<Pedido> updatePedidoDesconto(@PathVariable("id") UUID id, @PathVariable("desconto") int desconto) {
        if (pedidoRepository.existsById(id)) {
            Pedido pedido = pedidoRepository.findById(id).get();
            if (!pedido.isFechado()) {
                pedido.setPrecoPedido(pedidoService.getProdServicosSum(pedido) - pedidoService.getSumDiscountValue(pedido, desconto, prodServicoRepository));
                Pedido pedidoUpdated = pedidoRepository.save(pedido);
                return new ResponseEntity<Pedido>(pedidoUpdated, HttpStatus.OK);
            } else {
                throw new PedidoFechadoException(id);
            }
        } else {
            throw new PedidoNotFound(id);
        }
    }

    @PutMapping("/{id}/finalizarPedido")
    public ResponseEntity<Pedido> fecharPedido(@PathVariable("id") UUID id) {
        if (pedidoRepository.existsById(id)) {
            Pedido pedido = pedidoRepository.findById(id).get();
            pedido.setFechado(true);
            Pedido pedidoUpdated = pedidoRepository.save(pedido);
            return new ResponseEntity<Pedido>(pedidoUpdated, HttpStatus.OK);
        } else {
            throw new PedidoNotFound(id);
        }
    }

    @PostMapping
    public ResponseEntity<Pedido> savePedido (@RequestBody Pedido pedido) {
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
            throw new PedidoNotFound(id);
        }
    }
}
