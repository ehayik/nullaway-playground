package org.github.ehayik;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;
import static org.github.ehayik.IdCard.Status.*;

public record IdCard(String number, @Nullable LocalDate issueDate, @Nullable LocalDate expirationDate) {

    public IdCard {

        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("idCard.number is required");
        }
    }

    public boolean isExpired() {
        if (expirationDate == null) return false;
        return LocalDate.now().isAfter(expirationDate);
    }

    public Status status() {

        if (issueDate == null || expirationDate == null) {
            return REQUESTED;
        }

        if (isExpired()) {
            return EXPIRED;
        }

        return ISSUED;
    }

    public static IdCard request(String number) {
        return new IdCard(number, null, null);
    }

    public IdCard issue(LocalDate issueDate, int validityInYears) {

        if (ISSUED == status()) {
            return this;
        }

        if (REQUESTED != status()) {
            throw new IllegalStateException("Only REQUESTED ID Cards can be issued.");
        }

        var expirationDate = requireNonNull(issueDate, "issueDate is required").plusYears(validityInYears);
        return new IdCard(this.number, issueDate, expirationDate);
    }

    public IdCard renew(int validityInYears) {

        if (EXPIRED != status()) {
            throw new IllegalStateException("Only EXPIRED ID Cards can be renewed.");
        }

        var issueDate = requireNonNull(expirationDate).plusDays(1);
        return issue(issueDate, validityInYears);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof IdCard other) && number.equals(other.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    public boolean hasNumber(String idCardNumber) {
        return number.equals(idCardNumber);
    }

    public enum Status {
        REQUESTED,
        ISSUED,
        EXPIRED
    }
}
