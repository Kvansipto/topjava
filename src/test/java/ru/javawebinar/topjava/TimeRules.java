package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TimeRules {
    private static final Logger logger = LoggerFactory.getLogger("result");
    private static final StringBuilder results = new StringBuilder();
    public static final Stopwatch STOPWATCH = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-95s %7d", description.getDisplayName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append('\n');
            logger.info(result + " ms\n");
        }
    };
    private static final String DELIM = "-".repeat(103);
    public static final ExternalResource SUMMARY = new ExternalResource() {
        @Override
        protected void before() {
            results.setLength(0);
        }

        @Override
        protected void after() {
            logger.info(DELIM + "\nTest" + "\s".repeat(87) + "Duration, ms" + '\n' + DELIM + '\n' + results + DELIM + '\n');
        }
    };
}
