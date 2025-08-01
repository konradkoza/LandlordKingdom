package pl.lodz.p.it.landlordkingdom.mol.mappers;

import pl.lodz.p.it.landlordkingdom.model.Application;
import pl.lodz.p.it.landlordkingdom.model.User;
import pl.lodz.p.it.landlordkingdom.mol.dto.GetOwnLocalApplicationsResponse;
import pl.lodz.p.it.landlordkingdom.mol.dto.OwnApplicationResponse;
import pl.lodz.p.it.landlordkingdom.util.DateUtils;

import java.util.List;

public class ApplicationMapper {
    public static OwnApplicationResponse toOwnApplicationResponse(Application application) {
        User user = application.getTenant().getUser();
        String timezone = user.getTimezone() != null ?
                user.getTimezone().getName() : "UTC";
        String createdAt = DateUtils.convertUTCToAnotherTimezoneSimple(application.getCreatedAt(), timezone, user.getLanguage());
        return new OwnApplicationResponse(
                application.getId(),
                createdAt,
                application.getLocal().getId(),
                application.getLocal().getName(),
                application.getLocal().getAddress().getCountry(),
                application.getLocal().getAddress().getCity(),
                application.getLocal().getAddress().getStreet());
    }

    public static List<OwnApplicationResponse> toGetOwnApplications(List<Application> applications) {
        return applications.stream().map(ApplicationMapper::toOwnApplicationResponse).toList();
    }

    public static GetOwnLocalApplicationsResponse toGetOwnLocalApplicationResponse(Application application) {
        User user = application.getTenant().getUser();
        String timezone = user.getTimezone() != null ?
                user.getTimezone().getName() : "UTC";
        String createdAt = DateUtils.convertUTCToAnotherTimezoneSimple(application.getCreatedAt(), timezone, user.getLanguage());
        return new GetOwnLocalApplicationsResponse(
                application.getId(),
                createdAt,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }

    public static List<GetOwnLocalApplicationsResponse> getOwnLocalApplicationsResponses(List<Application> applications) {
        return applications.stream().map(ApplicationMapper::toGetOwnLocalApplicationResponse).toList();
    }
}
