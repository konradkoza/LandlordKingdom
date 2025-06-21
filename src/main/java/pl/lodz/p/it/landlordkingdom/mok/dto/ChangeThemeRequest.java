package pl.lodz.p.it.landlordkingdom.mok.dto;

import jakarta.validation.constraints.Pattern;

public record ChangeThemeRequest(
        @Pattern(regexp = "dark|light", message = "Theme must be either 'dark' or 'light'")
        String theme
) {
}
