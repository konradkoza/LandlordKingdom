package pl.lodz.p.it.landlordkingdom.mol.mappers;

import org.springframework.data.domain.Page;
import pl.lodz.p.it.landlordkingdom.model.Local;
import pl.lodz.p.it.landlordkingdom.mol.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class LocalMapper {
    public static GetOwnLocalsResponse toGetOwnLocalsResponse(Local local) {
        return new GetOwnLocalsResponse(
                local.getId(),
                local.getName(),
                local.getDescription(),
                local.getState().toString(),
                local.getSize(),
                local.getMarginFee(),
                local.getRentalFee(),
                local.getNextMarginFee(),
                local.getNextRentalFee(),
                AddressMapper.toAddressResponse(local.getAddress())
        );
    }

    public static GetOwnLocalsPage toGetOwnLocalsResponseList(Page<Local> locals) {
        return new GetOwnLocalsPage(
                locals.map(LocalMapper::toGetOwnLocalsResponse).toList(),
                locals.getTotalPages()
        );
    }

    public static GetAllLocalsResponse toGetAllLocalsResponse(Local local) {
        String login = local.getOwner() == null ? null : local.getOwner().getUser().getLogin();
        return new GetAllLocalsResponse(
                local.getId(),
                login,
                local.getName(),
                local.getDescription(),
                local.getState().toString(),
                local.getSize(),
                local.getMarginFee(),
                local.getRentalFee(),
                local.getNextMarginFee(),
                local.getNextRentalFee(),
                AddressMapper.toAddressResponse(local.getAddress())
        );
    }

    public static List<GetAllLocalsResponse> toGetAllLocalsResponseList(List<Local> locals) {
        return locals.stream().map(LocalMapper::toGetAllLocalsResponse).collect(Collectors.toList());
    }

    public static GetAllLocalsFiltered toGetAllLocalsFiltered(Page<Local> localsPage) {
        return new GetAllLocalsFiltered(
                localsPage.map(LocalMapper::toGetAllLocalsResponse).toList(),
                localsPage.getTotalPages()
        );
    }

    public static LocalForAdministratorResponse toLocalForAdministratorResponse(Local local) {
        String login = local.getOwner() == null ? null : local.getOwner().getUser().getLogin();
        return new LocalForAdministratorResponse(
                local.getId(),
                login,
                local.getName(),
                local.getDescription(),
                local.getState().toString(),
                local.getSize(),
                local.getMarginFee(),
                local.getRentalFee(),
                local.getNextMarginFee(),
                local.getNextRentalFee(),
                AddressMapper.toAddressResponse(local.getAddress())
        );
    }

    public static LocalForTenantResponse localForTenantResponse(Local local) {
        return new LocalForTenantResponse(
                local.getName(),
                local.getSize(),
                AddressMapper.toAddressResponse(local.getAddress()),
                local.getMarginFee(),
                local.getRentalFee()
        );
    }

    public static LocalDetailsForAdminResponse toLocalDetailsForAdminResponse(Local local) {
        return new LocalDetailsForAdminResponse(
                local.getName(),
                local.getSize(),
                local.getDescription(),
                local.getState().toString(),
                OwnerMapper.toOwnerForAdminResponse(local.getOwner()),
                AddressMapper.toAddressResponse(local.getAddress()),
                local.getMarginFee(),
                local.getRentalFee(),
                local.getNextMarginFee(),
                local.getNextRentalFee()
        );
    }

    public static OwnLocalDetailsResponse toOwnLocalDetailsResponse(Local local) {
        return new OwnLocalDetailsResponse(
                local.getName(),
                local.getSize(),
                local.getDescription(),
                local.getState(),
                AddressMapper.toAddressResponse(local.getAddress()),
                local.getMarginFee(),
                local.getRentalFee(),
                local.getNextMarginFee(),
                local.getNextRentalFee()
        );
    }

    public static ActiveLocalResponse toLocalPublicResponse(Local local) {
        return new ActiveLocalResponse(
                local.getName(),
                local.getDescription(),
                local.getSize(),
                local.getOwner().getUser().getFirstName(),
                local.getAddress().getCity(),
                local.getMarginFee().add(local.getRentalFee())
        );
    }

    public static GetActiveLocalsResponse toGetAllActiveLocalsResponse(Local local) {
        return new GetActiveLocalsResponse(
                local.getId(),
                local.getName(),
                local.getDescription(),
                local.getSize(),
                local.getAddress().getCity()
        );
    }

    public static EditLocalResponse toEditLocalResponse(Local local) {
        return new EditLocalResponse(
                local.getId(),
                local.getName(),
                local.getDescription(),
                local.getSize()
        );
    }

    public static GetActiveLocalsResponsePage toGetAllActiveLocalsResponseList(Page<Local> locals) {
        return new GetActiveLocalsResponsePage(
                locals.map(LocalMapper::toGetAllActiveLocalsResponse).toList(),
                locals.getTotalPages()
        );
    }

    public static AddLocalResponse toGetAddLocalResponse(Local local) {
        return new AddLocalResponse(
                local.getId(),
                local.getName(),
                local.getDescription(),
                local.getState().toString(),
                local.getSize(),
                local.getMarginFee(),
                local.getRentalFee(),
                AddressMapper.toAddressResponse(local.getAddress())
        );
    }

    public static GetUnapprovedLocalPageResponse toGetUnapprovedLocalPageResponse(Page<Local> page) {
        return new GetUnapprovedLocalPageResponse(
                page.getTotalPages(),
                page.map(LocalMapper::toGetAllLocalsResponse).toList()
        );
    }
}
