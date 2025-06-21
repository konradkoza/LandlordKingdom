package pl.lodz.p.it.landlordkingdom.model.filtering.builders;

import org.springframework.data.jpa.domain.Specification;
import pl.lodz.p.it.landlordkingdom.exceptions.InvalidDataException;
import pl.lodz.p.it.landlordkingdom.model.filtering.SearchCriteria;

import java.util.List;

public interface SpecificationBuilder<T> {
    SpecificationBuilder<T> with(String key, String operation, Object value);

    SpecificationBuilder<T> with(SearchCriteria searchCriteria);

    SpecificationBuilder<T> with(List<SearchCriteria> searchCriteria);

    Specification<T> build() throws InvalidDataException;
}