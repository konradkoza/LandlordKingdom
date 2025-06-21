package pl.lodz.p.it.landlordkingdom.mok.dto;

import java.util.StringJoiner;

public record PasswordHolder(
        String password
) {
    @Override
    public String toString() {
        return new StringJoiner(", ", PasswordHolder.class.getSimpleName() + "[", "]")
                .add("password='*********'")
                .toString();
    }
}
