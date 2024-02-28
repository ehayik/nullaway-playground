package org.github.ehayik;

import jakarta.annotation.Nonnull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class IdCardService {

    private final Set<IdCard> requestedIdCards = new HashSet<>();

    @Nonnull
    public IdCard requestNewIdCard(String idCardNumber) {
        return findIdCardByNumber(idCardNumber).orElseGet(() -> {
            var idCard = IdCard.request(idCardNumber);
            requestedIdCards.add(idCard);
            return idCard;
        });
    }

    @Nonnull
    public Optional<IdCard> findIdCardByNumber(String idCardNumber) {
        return requestedIdCards.stream().filter(idCard -> idCard.hasNumber(idCardNumber)).findFirst();
    }
}
