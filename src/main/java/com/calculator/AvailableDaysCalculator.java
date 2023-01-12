package com.calculator;

import com.converter.MyHolidayConverter;
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
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AvailableDaysCalculator {

    public static List<LocalDate> getAvailableDays(LocalDate startDate, LocalDate endDate) {

        if (startDate == null || endDate == null) {
            return Collections.emptyList();
        }

        final Set<LocalDate> availableDays = new HashSet<>();

        while (!startDate.isAfter(endDate)) {
            if (!isWeekends(startDate) && !isHolidays(startDate, endDate)) {
                availableDays.add(startDate);
            }
            startDate = startDate.plusDays(1);
        }

        if (availableDays.isEmpty()) {
            throw new AvailableDaysException();
        }
        return new ArrayList<>(availableDays)
            .stream()
            .sorted()
            .collect(Collectors.toList());
    }

    private static boolean isWeekends(LocalDate startDate) {
        final Set<DayOfWeek> weekends = getWeekends();
        return weekends.contains(startDate.getDayOfWeek());
    }

    private static boolean isHolidays(LocalDate startDate, LocalDate endDate) {
        final Set<MyHoliday> holidays = getHolidays(startDate, endDate);
        for (MyHoliday myHoliday : holidays) {
            if (startDate.isEqual(myHoliday.getStartDate()) ||
                startDate.isEqual(myHoliday.getEndDate()) ||
                (startDate.isAfter(myHoliday.getStartDate()) && startDate.isBefore(myHoliday.getEndDate()))) {
                return true;
            }
        }
        return false;
    }

    private static Set<DayOfWeek> getWeekends() {
        return EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    }

    private static Set<MyHoliday> getHolidays(LocalDate startDate, LocalDate endDate) {
        ManagerParameter trManager = ManagerParameters.create(new Locale("tr"));
        HolidayManager hm = HolidayManager.getInstance(trManager);

        Set<Holiday> startDateHolidays = hm.getHolidays(startDate.getYear());
        Set<Holiday> endDateHolidays = hm.getHolidays(endDate.getYear());

        Set<Holiday> totalHolidays = new HashSet<>();
        totalHolidays.addAll(startDateHolidays);
        totalHolidays.addAll(endDateHolidays);

        return MyHolidayConverter.convert(totalHolidays);
    }
}
