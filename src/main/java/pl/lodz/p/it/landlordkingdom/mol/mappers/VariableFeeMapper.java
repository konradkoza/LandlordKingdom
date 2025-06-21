package pl.lodz.p.it.landlordkingdom.mol.mappers;

import org.springframework.data.domain.Page;
import pl.lodz.p.it.landlordkingdom.model.VariableFee;
import pl.lodz.p.it.landlordkingdom.mol.dto.RentVariableFeesResponse;
import pl.lodz.p.it.landlordkingdom.mol.dto.VariableFeeResponse;

import java.util.List;

public class VariableFeeMapper {
    private VariableFeeMapper() {
    }

    public static VariableFeeResponse variableFeeResponse(VariableFee variableFee) {
        return new VariableFeeResponse(
                variableFee.getDate().toString(), variableFee.getAmount()
        );
    }

    public static List<VariableFeeResponse> variableFeeResponseList(List<VariableFee> variableFeeList) {
        return variableFeeList.stream().map(VariableFeeMapper::variableFeeResponse).toList();
    }

    public static RentVariableFeesResponse toRentVariableFeesResponse(Page<VariableFee> variableFees) {
        return new RentVariableFeesResponse(
                variableFees.map(VariableFeeMapper::variableFeeResponse).toList(),
                variableFees.getTotalPages()
        );
    }
}
