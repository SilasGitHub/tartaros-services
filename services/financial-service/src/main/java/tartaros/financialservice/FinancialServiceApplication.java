package tartaros.financialservice;


import graphql.scalars.ExtendedScalars;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@SpringBootApplication
public class FinancialServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialServiceApplication.class, args);
    }
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.UUID);
    }

}
