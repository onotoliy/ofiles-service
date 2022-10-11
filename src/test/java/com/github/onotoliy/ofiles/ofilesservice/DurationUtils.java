package com.github.onotoliy.ofiles.ofilesservice;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public final class DurationUtils {

    private final static List<Character> PERIOD_CHARACTERS = List.of('D', 'H', 'M', 'S', 'd', 'h', 'm', 's');
    private final static List<Character> IGNORE_CHARACTERS = List.of('P', 'T', 'p', 't');

    private DurationUtils() {

    }

    public static Duration parse(final String str) {
        require(str);

        final StringBuilder period = new StringBuilder();
        final List<String> periods = new ArrayList<>();

        for (int i = 1; i < str.length(); i++) {
            final char character = str.charAt(i);

            if (IGNORE_CHARACTERS.contains(character)) {
                continue;
            }

            period.append(character);

            if (PERIOD_CHARACTERS.contains(character)) {
                periods.add(period.toString().toUpperCase());
                period.setLength(0);
            }
        }

        return toDuration(periods);
    }

    private static Duration toDuration(List<String> periods) {
        final Function<String, Long> toLong = name -> periods
            .stream()
            .filter(period -> period.endsWith(name))
            .findFirst()
            .map(period -> period.replaceAll(name, ""))
            .map(Long::parseLong)
            .orElse(0L);

        final long day = toLong.apply("D");
        final long hour = toLong.apply("H");
        final long minute = toLong.apply("M");
        final long seconds = toLong.apply("S");

        return new Duration(day, hour, minute, seconds);
    }

    private static void require(final String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Duration can not be null");
        }

        java.time.Duration.parse(str);
    }

    public static class Duration {
        private final long day;
        private final long hour;
        private final long minute;
        private final long seconds;

        private Duration(long day, long hour, long minute, long seconds) {
            this.day = day;
            this.hour = hour;
            this.minute = minute;
            this.seconds = seconds;
        }

        public long getDay() {
            return day;
        }

        public long getHour() {
            return hour;
        }

        public long getMinute() {
            return minute;
        }

        public long getSeconds() {
            return seconds;
        }

        @Override
        public String toString() {
            return "Duration{day=" + day + ", hour=" + hour + ", minute=" + minute + ", seconds=" + seconds + "}";
        }
    }
}
