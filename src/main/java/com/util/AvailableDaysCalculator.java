package com.util;

import com.dto.MyHoliday;
import com.exception.AvailableDaysException;
import de.jollyday.Holiday;
import de.jollyday.HolidayManager;
import de.jollyday.ManagerParameter;
import de.jollyday.ManagerParameters;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AvailableDaysCalculator {

    public static List<LocalDate> getAvailableDays(LocalDate startDate, LocalDate endDate, Locale locale) {

        if (startDate == null || endDate == null) {
            return Collections.emptyList();
        }

        final Set<LocalDate> availableDays = new HashSet<>();
        final Set<DayOfWeek> weekends = getWeekends();
        final Set<MyHoliday> myHolidayList = getHolidays(startDate);

        while (!startDate.isAfter(endDate)) {
            if (!weekends.contains(startDate.getDayOfWeek()) && !isHoliday(myHolidayList, startDate)) {
                availableDays.add(startDate);
            }
            startDate = startDate.plusDays(1);
        }

        if (availableDays.isEmpty()) {
            throw new AvailableDaysException(locale);
        }
        return new ArrayList<>(availableDays);
    }

    private static Set<DayOfWeek> getWeekends() {
        return EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    }

    private static Set<MyHoliday> getHolidays(LocalDate startDate) {
        ManagerParameter trManager = ManagerParameters.create(new Locale("tr"));
        HolidayManager hm = HolidayManager.getInstance(trManager);
        Set<Holiday> holidays = hm.getHolidays(startDate.getYear());
        return MyHolidayConverter.convert(holidays);
    }

    private static boolean isHoliday(Set<MyHoliday> myHolidayList, LocalDate startDate) {
        for (MyHoliday myHoliday : myHolidayList) {
            if (startDate.isEqual(myHoliday.getStartDate()) ||
                startDate.isEqual(myHoliday.getEndDate()) ||
                (startDate.isAfter(myHoliday.getStartDate()) && startDate.isBefore(myHoliday.getEndDate()))) {
                return true;
            }
        }
        return false;
    }
}
