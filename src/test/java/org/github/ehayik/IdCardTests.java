package org.github.ehayik;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdCardTests {

    @Test
    void isExpiredShouldReturnFalseWhenTemporalEndingIsNull() {
        // Given
        var idCard = IdCard.request("111111111");

        // Then
        assertThat(idCard.isExpired()).isFalse();
    }
}