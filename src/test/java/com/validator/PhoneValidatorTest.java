package com.validator;

import com.exception.PhoneNumberException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.constant.ErrorCodes.INVALID_PHONE_NUMBER;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
class PhoneValidatorTest {

    @InjectMocks
    private PhoneValidator phoneValidator;

    @ParameterizedTest
    @ValueSource(strings = {"05465533993", "905465533993", "+905465533993", "02161111111"})
    void shouldValidate(String phoneNumber) {
        boolean valid = phoneValidator.isValid(phoneNumber, null);
        assertFalse(valid);
    }

    @Test
    void shouldValidate() {
        final String phoneNumber = "5465533993";
        boolean valid = phoneValidator.isValid(phoneNumber, null);
        assertTrue(valid);
    }

    @Test
    void shouldThrowPhoneNumberExceptionWhenPhoneNumberIsNull() {

        final PhoneNumberException exception = assertThrows(
            PhoneNumberException.class,
            () -> phoneValidator.isValid(null, null)
        );

        assertEquals(INVALID_PHONE_NUMBER, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals("phone.number.invalid", exception.getMessage());
    }

    @Test
    void shouldThrowPhoneNumberExceptionWhenPhoneNumberIsEmpty() {

        final PhoneNumberException exception = assertThrows(
            PhoneNumberException.class,
            () -> phoneValidator.isValid("", null)
        );

        assertEquals(INVALID_PHONE_NUMBER, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals("phone.number.invalid", exception.getMessage());
    }
}