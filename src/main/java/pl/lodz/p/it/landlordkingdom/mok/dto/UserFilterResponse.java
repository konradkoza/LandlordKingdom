package pl.lodz.p.it.landlordkingdom.mok.dto;

public record UserFilterResponse(String login,
                                 String email,
                                 String firstName,
                                 String lastName,
                                 Boolean blocked,
                                 Boolean verified,
                                 String role,
                                 int pageSize) {
}
