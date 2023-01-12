package com.converter;

import com.converter.MyHolidayConverter;
import com.dto.MyHoliday;
import com.enums.IslamicHolidays;
import de.jollyday.Holiday;
import de.jollyday.HolidayType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MyHolidayConverterTest {

    @Test
    void shouldConvertToIdAlFitr() {

        final LocalDate date = LocalDate.now();
        final String name = "ID_AL_FITR";

        Holiday holiday = new Holiday(date, name, HolidayType.OFFICIAL_HOLIDAY);
        Set<MyHoliday> convert = MyHolidayConverter.convert(Collections.singleton(holiday));
        assertEquals(name, convert.iterator().next().getName());
        assertEquals(date, convert.iterator().next().getStartDate());
        assertEquals(
            holiday.getDate().plusDays(IslamicHolidays.ID_AL_FITR.getHolidaysCount()),
            convert.iterator().next().getEndDate()
        );
    }

    @Test
    void shouldConvertToIdulAdha() {

        final LocalDate date = LocalDate.now();
        final String name = "ID_UL_ADHA";

        Holiday holiday = new Holiday(date, name, HolidayType.OFFICIAL_HOLIDAY);
        Set<MyHoliday> convert = MyHolidayConverter.convert(Collections.singleton(holiday));
        assertEquals(name, convert.iterator().next().getName());
        assertEquals(date, convert.iterator().next().getStartDate());
        assertEquals(
            holiday.getDate().plusDays(IslamicHolidays.ID_UL_ADHA.getHolidaysCount()),
            convert.iterator().next().getEndDate()
        );
    }

    @Test
    void shouldConvertToOther() {

        final LocalDate date = LocalDate.now();
        final String name = "test";

        Holiday holiday = new Holiday(date, name, HolidayType.OFFICIAL_HOLIDAY);
        Set<MyHoliday> convert = MyHolidayConverter.convert(Collections.singleton(holiday));
        assertEquals(name, convert.iterator().next().getName());
        assertEquals(date, convert.iterator().next().getStartDate());
        assertEquals(date, convert.iterator().next().getEndDate());
    }
}