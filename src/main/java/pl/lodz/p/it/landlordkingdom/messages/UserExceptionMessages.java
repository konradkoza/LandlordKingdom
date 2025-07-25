package pl.lodz.p.it.landlordkingdom.messages;

public class UserExceptionMessages {
    public final static String NOT_FOUND = "User not found";
    public final static String BLOCKED = "User is blocked";
    public final static String NOT_VERIFIED = "User is not verified";
    public final static String INVALID_LOGIN_DATA = "Invalid login data";
    public final static String SIGN_IN_BLOCKED = "User reached limit of login attempts and account is time blocked";
    public final static String INVALID_PASSWORD = "Provided incorrect current password";
    public final static String LOGIN_OR_EMAIL_EXISTS = "User with this login or email already exists";
    public static final String REFRESH_TOKEN_EXPIRED = "Refresh token expired";
    public final static String LOGIN_NOT_MATCH_TO_OTP = "Login does not match to OTP";
    public static final String ALREADY_BLOCKED = "User is already blocked";
    public static final String ALREADY_UNBLOCKED = "User is already unblocked";
    public static final String CREATION_FAILED = "User creation failed";
    public static final String PASSWORD_REPEATED = "Password must be different than any other password used by this account";
    public static final String INACTIVE = "User cannot perform action because is blocked due to inactivity";
    public static final String THEME_NOT_FOUND = "Theme not found";
    public static final String EMAIL_EXISTS = "User with this email already exists";
    public static final String ACCESS_LEVEL_ASSIGNED = "Access level you are trying to assign is already assigned";
    public static final String ACCESS_LEVEL_TAKEN = "Access level you are trying to take is already taken";
}
