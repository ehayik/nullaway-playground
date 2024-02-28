package org.github.ehayik;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class Main {

    public static void main(String[] args) {
        var idCardNumber = "11111112";
        var idCardService = new IdCardService();

        var idCard = idCardService
                .findIdCardByNumber(idCardNumber)
                .orElseGet(() -> idCardService.requestNewIdCard(idCardNumber))
                .issue(LocalDate.now(), 5);

        log.info("ID card {} requested, status {}", idCard.number(), idCard.status());
    }
}