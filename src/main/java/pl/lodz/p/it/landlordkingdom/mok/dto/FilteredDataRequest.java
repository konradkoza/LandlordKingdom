package pl.lodz.p.it.landlordkingdom.mok.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import pl.lodz.p.it.landlordkingdom.model.filtering.SearchCriteria;

import java.util.List;

public record FilteredDataRequest(
        @NotNull(message = "Search criteria list cannot be null.")
        List<SearchCriteria> searchCriteriaList,

        @NotBlank(message = "Data option cannot be blank.")
        @Pattern(regexp = "^(all|any)$", message = "Data option must be 'all' or 'any'.")
        String dataOption
) {
}
