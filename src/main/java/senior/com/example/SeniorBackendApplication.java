package senior.com.example;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import senior.com.example.models.ProdServico;
import senior.com.example.models.QProdServico;
import senior.com.example.repositories.ProdServicoRepository;

import java.util.List;

@SpringBootApplication
public class SeniorBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeniorBackendApplication.class, args);
	}
}
