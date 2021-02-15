package senior.com.example.demo;

import org.apache.commons.collections4.IterableUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import senior.com.example.controllers.ProdServicoController;
import senior.com.example.models.Pedido;
import senior.com.example.models.ProdServico;
import senior.com.example.repositories.ProdServicoRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMockitoApplicationTests {

    @MockBean
    private ProdServicoRepository prodServicoRepository;

    @Autowired
    private ProdServicoController prodServico;

    Page<ProdServico> myPage;

    public List<ProdServico> getListFromIterator(Iterable<ProdServico> iterable)
    {
        List<ProdServico> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }


    @Test
    public void getProdServicosTest() {

        myPage = new PageImpl(Stream.of(
                new ProdServico(UUID.fromString("b20e70a3-b9a2-4831-9996-d17957599ab0"),"Produto5", 15.5, true, true, new Pedido()),
                new ProdServico(UUID.fromString("b5407f8a-9a2d-4761-8cd3-beef16e5166f"),"Servico1", 30.0, false, true, new Pedido())).collect(Collectors.toList()));

        Mockito.when(prodServicoRepository.findAll(PageRequest.ofSize(10))).thenReturn(myPage);

        Assert.assertEquals(2, getListFromIterator(prodServico.getAllProdServicos()).size());
    }

    public void getProdServicoByIdTest() {

    }


}
