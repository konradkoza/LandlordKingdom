package pl.lodz.p.it.landlordkingdom.mol.mappers;

import pl.lodz.p.it.landlordkingdom.model.Owner;
import pl.lodz.p.it.landlordkingdom.mol.dto.OwnerForAdminResponse;
import pl.lodz.p.it.landlordkingdom.mol.dto.OwnerForTenantResponse;

public class OwnerMapper {

    public static OwnerForTenantResponse ownerForTenantResponse(Owner owner){
        return new OwnerForTenantResponse(
                owner.getUser().getFirstName(),
                owner.getUser().getLastName(),
                owner.getUser().getLogin(),
                owner.getUser().getEmail()
        );
    }

    public static OwnerForAdminResponse toOwnerForAdminResponse(Owner owner) {
        if (owner == null) {
            return null;
        }
        return new OwnerForAdminResponse(
                owner.getUser().getId(),
                owner.getUser().getFirstName(),
                owner.getUser().getLastName(),
                owner.getUser().getLogin(),
                owner.getUser().getEmail()
        );
    }
}
