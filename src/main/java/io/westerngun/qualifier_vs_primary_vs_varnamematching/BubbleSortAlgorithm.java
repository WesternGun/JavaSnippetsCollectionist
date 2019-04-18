package io.westerngun.qualifier_vs_primary_vs_varnamematching;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("bubble")
public class BubbleSortAlgorithm implements SortAlgorithm {
    @Override
    public void sort() {
        System.out.println("Bubble sort!");
    }
}
