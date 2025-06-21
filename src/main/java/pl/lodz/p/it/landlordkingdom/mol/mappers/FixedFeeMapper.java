package pl.lodz.p.it.landlordkingdom.mol.mappers;

import org.springframework.data.domain.Page;
import pl.lodz.p.it.landlordkingdom.model.FixedFee;
import pl.lodz.p.it.landlordkingdom.mol.dto.FixedFeeResponse;
import pl.lodz.p.it.landlordkingdom.mol.dto.RentFixedFeesResponse;

import java.util.List;

public class FixedFeeMapper {
    private FixedFeeMapper() {
    }

    public static FixedFeeResponse fixedFeeResponse(FixedFee fixedFee) {
        return new FixedFeeResponse(fixedFee.getDate().toString(), fixedFee.getMarginFee(), fixedFee.getRentalFee());
    }

    public static List<FixedFeeResponse> fixedFeeResponseList(List<FixedFee> fixedFeeList) {
        return fixedFeeList.stream().map(FixedFeeMapper::fixedFeeResponse).toList();
    }

    public static RentFixedFeesResponse toRentFixedFeesResponse(Page<FixedFee> fixedFees) {
        return new RentFixedFeesResponse(
                fixedFees.map(FixedFeeMapper::fixedFeeResponse).toList(),
                fixedFees.getTotalPages()
        );
    }
}
