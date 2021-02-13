package senior.com.example.controllers;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import senior.com.example.criteria.predicates.ProdServicoPredicateBuilder;
import senior.com.example.models.ProdServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senior.com.example.models.QProdServico;
import senior.com.example.repositories.ProdServicoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/prodservicos")
public class ProdServicoController {
    private Pageable pageSize = PageRequest.ofSize(10);
    private ProdServicoRepository prodServicoRepository;
    private QProdServico qProdServico = QProdServico.prodServico;

    @Autowired
    public ProdServicoController(ProdServicoRepository prodServicoRepository) {
        this.prodServicoRepository = prodServicoRepository;
    }

    @GetMapping
    public Iterable<ProdServico> getAllProdServicos() {
        return prodServicoRepository.findAll(pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdServico> getProdServicoById(@PathVariable(value = "id") UUID id) {
        Optional<ProdServico> prodServico = prodServicoRepository.findById(id);
        if(prodServico.isPresent()) {
            return new ResponseEntity<ProdServico>(prodServico.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pedido/{id}")
    public Iterable<ProdServico> getProdServicoByPedidoId(@PathVariable(value = "id") UUID id) {
        List<ProdServico> prodServico = prodServicoRepository.findByItensPedidoId(id);
        return prodServico;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filter")
    @ResponseBody
    public Iterable<ProdServico> search(@RequestParam(value = "search") String search) {
        ProdServicoPredicateBuilder builder = new ProdServicoPredicateBuilder();

        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w.+?)(:|<|>)((\\w+?\\-?)*),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return prodServicoRepository.findAll(exp);
    }


    @PostMapping
    public ResponseEntity<ProdServico> saveProdServico(@RequestBody ProdServico prodServico) {
        ProdServico prodServicoCreated = prodServicoRepository.save(prodServico);
        return new ResponseEntity<ProdServico>(prodServicoCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdServico> updateProdServico(@RequestBody ProdServico prodServico, @PathVariable("id") UUID id) {
        if (prodServicoRepository.existsById(id)) {
            prodServico.setId(id);
            ProdServico prodServicoUpdated = prodServicoRepository.save(prodServico);
            return new ResponseEntity<ProdServico>(prodServicoUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProdServico> deleteProdServico (@PathVariable("id") UUID id) {
        Optional<ProdServico> prodServico = prodServicoRepository.findById(id);

        if (prodServico.isPresent()) {
            ProdServico prodServicoDeleted = prodServico.get();
            prodServicoRepository.delete(prodServicoDeleted);
            return new ResponseEntity<ProdServico>(prodServicoDeleted, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
