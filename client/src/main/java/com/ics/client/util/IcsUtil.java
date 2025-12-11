package com.ics.client.util;

import com.ics.common.dto.Section;
import com.ics.common.dto.MeetingTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class IcsUtil {
    private static final DateTimeFormatter ICS_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

    public static String generateIcsContent(List<Section> schedule) {
        StringBuilder sb = new StringBuilder();
        sb.append("BEGIN:VCALENDAR\n");
        sb.append("VERSION:2.0\n");
        sb.append("PRODID:-//Intelligent Course Scheduler//EN\n");

        for (Section section : schedule) {
            for (MeetingTime mt : section.meetingTimes()) {
                DayOfWeek day = parseDay(mt.day());
                LocalTime start = parseTime(mt.start());
                LocalTime end = parseTime(mt.end());

                LocalDateTime startDateTime = calculateNextOccurrence(day, start);
                LocalDateTime endDateTime = calculateNextOccurrence(day, end);

                sb.append("BEGIN:VEVENT\n");
                sb.append("SUMMARY:").append(section.courseId()).append(" (").append(section.id()).append(")\n");
                sb.append("DTSTART:").append(startDateTime.format(ICS_FORMATTER)).append("\n");
                sb.append("DTEND:").append(endDateTime.format(ICS_FORMATTER)).append("\n");
                if (section.instructor() != null) {
                    sb.append("DESCRIPTION:Instructor: ").append(section.instructor()).append("\n");
                }
                sb.append("END:VEVENT\n");
            }
        }
        sb.append("END:VCALENDAR");
        return sb.toString();
    }

    private static LocalDateTime calculateNextOccurrence(DayOfWeek day, LocalTime time) {
        LocalDate nextDate = LocalDate.now().with(TemporalAdjusters.next(day));
        return LocalDateTime.of(nextDate, time);
    }

    private static DayOfWeek parseDay(String dayStr) {
        if (dayStr == null) return DayOfWeek.MONDAY;

        String d = dayStr.trim().toUpperCase();

        return switch (d.substring(0, 3)) {
            case "MON" -> DayOfWeek.MONDAY;
            case "TUE" -> DayOfWeek.TUESDAY;
            case "WED" -> DayOfWeek.WEDNESDAY;
            case "THU" -> DayOfWeek.THURSDAY;
            case "FRI" -> DayOfWeek.FRIDAY;
            case "SAT" -> DayOfWeek.SATURDAY;
            case "SUN" -> DayOfWeek.SUNDAY;
            default -> DayOfWeek.MONDAY;
        };
    }

    private static LocalTime parseTime(String timeStr) {
        try {
            return LocalTime.parse(timeStr);
        } catch (Exception e) {
            if (timeStr.length() == 5) {
                return LocalTime.parse(timeStr + ":00");
            }
            return LocalTime.of(9, 0);
        }
    }
}
