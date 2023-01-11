package com.validator;

import com.exception.PhoneNumberException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    private static final String TURKEY_PHONE_REGEX = "^\\d{10}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (Strings.isBlank(value)) {
            throw new PhoneNumberException(Locale.ENGLISH);
        }

        Pattern pattern = Pattern.compile(TURKEY_PHONE_REGEX);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
