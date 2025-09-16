package util.api;

import io.qameta.allure.Allure;

import java.util.function.Supplier;

public class RetryUtils {

    private static final int DEFAULT_MAX_RETRIES = 3;
    private static final int DEFAULT_RETRY_DELAY_MS = 1000;

    public static <T> T withRetry(Supplier<T> action, int maxRetries, int delayMs) {
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                if (attempt > 1) {
                    Allure.step("Попытка #" + attempt);
                }
                return action.get();
            } catch (Exception e) {
                Allure.step("Ошибка на попытке #" + attempt + ": " + e.getMessage());
                if (attempt == maxRetries) {
                    throw new RuntimeException("Действие не удалось после " + maxRetries + " попыток", e);
                }
                sleep(delayMs);
            }
        }
        throw new IllegalStateException("Неожиданная ошибка retry");
    }

    public static <T> T withRetry(Supplier<T> action) {
        return withRetry(action, DEFAULT_MAX_RETRIES, DEFAULT_RETRY_DELAY_MS);
    }

    private static void sleep(int delayMs) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Ожидание между попытками было прервано", e);
        }
    }
}