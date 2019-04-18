package io.westerngun.qualifier_vs_primary_vs_varnamematching;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class BinarySortAlgorithm implements SortAlgorithm {
    @Override
    public void sort() {
        System.out.println("Binary sort!");
    }
}
