package com.calculator;

import com.calculator.AvailableDaysCalculator;
import com.exception.AvailableDaysException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.constant.ErrorCodes.NOT_AVAILABLE_DAYS;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class AvailableDaysCalculatorTest {

    @Test
    void shouldReturnEmptyListWhenStartDateNull() {
        assertTrue(AvailableDaysCalculator.getAvailableDays(null, LocalDate.now()).isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenEndDateNull() {
        assertTrue(AvailableDaysCalculator.getAvailableDays(LocalDate.now(), null).isEmpty());
    }

    @Test
    void shouldThrowAvailableDaysExceptionWhenAvailableDaysNotFound() {
        final LocalDate startDate = LocalDate.of(2023, 1, 14);
        final LocalDate endDate = LocalDate.of(2023, 1, 15);

        final AvailableDaysException exception = assertThrows(
            AvailableDaysException.class,
            () -> AvailableDaysCalculator.getAvailableDays(startDate, endDate)
        );
        assertEquals(NOT_AVAILABLE_DAYS, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals("not.available.days", exception.getMessage());
    }

    @Test
    void shouldReturnAvailableDays() {
        final LocalDate startDate = LocalDate.of(2023, 6, 20);
        final LocalDate endDate = LocalDate.of(2023, 7, 13);
        final List<LocalDate> availableDays = AvailableDaysCalculator.getAvailableDays(startDate, endDate);
        assertEquals(10, availableDays.size());
    }
}