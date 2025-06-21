package pl.lodz.p.it.landlordkingdom.mol.mappers;

import org.springframework.data.domain.Page;
import pl.lodz.p.it.landlordkingdom.model.RoleRequest;
import pl.lodz.p.it.landlordkingdom.model.Timezone;
import pl.lodz.p.it.landlordkingdom.mol.dto.GetRoleRequestResponse;
import pl.lodz.p.it.landlordkingdom.mol.dto.RoleRequestPageResponse;
import pl.lodz.p.it.landlordkingdom.mol.dto.RoleRequestResponse;
import pl.lodz.p.it.landlordkingdom.util.DateUtils;

public class RoleRequestMapper {

    public static GetRoleRequestResponse toRoleResponse(RoleRequest roleRequest, Timezone timezone, String lang) {
        String timezoneStr = timezone == null ? "UTC" : timezone.getName();
        String createdAt = DateUtils.convertUTCToAnotherTimezoneSimple(roleRequest.getCreatedAt(), timezoneStr, lang);

        return new GetRoleRequestResponse(roleRequest.getId(), createdAt);
    }

    public static RoleRequestResponse toRoleRequestResponse(RoleRequest roleRequest) {
        return new RoleRequestResponse(roleRequest.getId(),
                roleRequest.getTenant().getUser().getLogin(),
                roleRequest.getTenant().getUser().getEmail(),
                roleRequest.getTenant().getUser().getFirstName(),
                roleRequest.getTenant().getUser().getLastName(),
                roleRequest.getTenant().getUser().getId());
    }

    public static RoleRequestPageResponse toRoleRequestPageResponse(Page<RoleRequest> page) {
       return new RoleRequestPageResponse(
               page.getTotalPages(),
               page.map(RoleRequestMapper::toRoleRequestResponse).toList()
       );
    }
}
