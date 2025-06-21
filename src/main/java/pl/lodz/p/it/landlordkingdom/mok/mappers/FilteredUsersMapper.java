package pl.lodz.p.it.landlordkingdom.mok.mappers;

import org.springframework.data.domain.Page;
import pl.lodz.p.it.landlordkingdom.model.AccessLevel;
import pl.lodz.p.it.landlordkingdom.model.User;
import pl.lodz.p.it.landlordkingdom.mok.dto.FilteredUsersResponse;

public class FilteredUsersMapper {

    public static FilteredUsersResponse accesslevelToFilteredUsersResponse(Page<? extends AccessLevel> result) {
        return new FilteredUsersResponse(result.stream().map(AccessLevel::getUser).map(UserMapper::toUserResponse).toList(),
                result.getTotalPages());
    }

    public static FilteredUsersResponse userToFilteredUsersResponse(Page<User> result) {
        return new FilteredUsersResponse(result.stream().map(UserMapper::toUserResponse).toList(),
                result.getTotalPages());
    }


}
