package pl.lodz.p.it.landlordkingdom.mok.dto;

import java.util.List;

public record FilteredUsersResponse(
        List<UserResponse> users,
        long totalPages
) {
}
