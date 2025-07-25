package pl.lodz.p.it.landlordkingdom.model.filtering.builders;

import org.springframework.data.jpa.domain.Specification;
import pl.lodz.p.it.landlordkingdom.exceptions.InvalidDataException;
import pl.lodz.p.it.landlordkingdom.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.landlordkingdom.messages.FilterMessages;
import pl.lodz.p.it.landlordkingdom.model.User;
import pl.lodz.p.it.landlordkingdom.model.filtering.SearchCriteria;
import pl.lodz.p.it.landlordkingdom.model.filtering.SearchOperation;
import pl.lodz.p.it.landlordkingdom.model.filtering.specifications.BasicSpecification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecificationBuilder implements SpecificationBuilder<User> {
    private final List<SearchCriteria> params;

    public UserSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public final UserSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final UserSpecificationBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    @Override
    public UserSpecificationBuilder with(List<SearchCriteria> searchCriteria) {
        params.addAll(searchCriteria);
        return this;
    }

    public Specification<User> build() throws InvalidDataException {
        try {
            if (params.isEmpty()) {
                return null;
            }

            Specification<User> result = new BasicSpecification<>(params.getFirst());
            for (int i = 1; i < params.size(); i++) {
                SearchCriteria criteria = params.get(i);
                result = SearchOperation.getDataOption(criteria.getDataOption()).equals(SearchOperation.ALL) ?
                        Specification.where(result).and(new BasicSpecification<>(criteria)) :
                        Specification.where(result).or(new BasicSpecification<>(criteria));
            }

            return result;
        } catch (Exception e) {
            throw new InvalidDataException(FilterMessages.INVALID_DATA, ErrorCodes.INVALID_DATA);
        }
    }
}