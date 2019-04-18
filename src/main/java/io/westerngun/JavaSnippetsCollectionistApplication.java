package io.westerngun;

import io.westerngun.qualifier_vs_primary_vs_varnamematching.SortService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JavaSnippetsCollectionistApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JavaSnippetsCollectionistApplication.class, args);
        SortService sortService = context.getBean(SortService.class);
        sortService.sort(); // @Qualifier > @Primary > var name matching class name
        // >  - if I autowire without `@Qualifier` and variable name is `sortAlgorithm`: choose binary
        // >  - if I autowire without annotation but var name is `bubbleSortAlgorithm`: still choose binary
        // >  - if I autowire with `@Qualifier("bubble")`: will instead choose bubble
    }
}
