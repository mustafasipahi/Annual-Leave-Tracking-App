package com.util;

import com.dto.MyHoliday;
import com.enums.IslamicHolidays;
import de.jollyday.Holiday;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyHolidayConverter {

    public static Set<MyHoliday> convert(Set<Holiday> holidays) {
        return holidays.stream()
            .map(holiday -> MyHoliday.builder()
                .name(holiday.getPropertiesKey())
                .startDate(holiday.getDate())
                .endDate(getEndDate(holiday))
                .build())
            .collect(Collectors.toSet());
    }

    private static LocalDate getEndDate(Holiday holiday) {
        if (holiday.getPropertiesKey().contains(IslamicHolidays.ID_AL_FITR.name())) {
            return holiday.getDate().plusDays(IslamicHolidays.ID_AL_FITR.getHolidaysCount());
        } else if (holiday.getPropertiesKey().contains(IslamicHolidays.ID_UL_ADHA.name())) {
            return holiday.getDate().plusDays(IslamicHolidays.ID_UL_ADHA.getHolidaysCount());
        }
        return holiday.getDate();
    }
}
