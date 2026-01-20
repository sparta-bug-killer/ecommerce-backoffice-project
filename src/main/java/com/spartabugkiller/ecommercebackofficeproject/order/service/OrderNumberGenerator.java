package com.spartabugkiller.ecommercebackofficeproject.order.service;

import com.spartabugkiller.ecommercebackofficeproject.order.entity.OrderDailySequence;
import com.spartabugkiller.ecommercebackofficeproject.order.repository.OrderDailySequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class OrderNumberGenerator {

    private final OrderDailySequenceRepository sequenceRepository;

    @Transactional
    public String generate() {

        String today = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        OrderDailySequence sequence = sequenceRepository
                .findByDateForUpdate(today)
                .orElseGet(() -> new OrderDailySequence(today));

        int nextSeq = sequence.nextSeq();
        sequenceRepository.save(sequence);

        return today + "-" + String.format("%03d", nextSeq);
    }
}
