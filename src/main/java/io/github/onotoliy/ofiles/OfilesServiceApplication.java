package io.github.onotoliy.ofiles;

import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Главный класс приложения.
 *
 * @author Anatoliy Pokhresnyi
 */
@SpringBootApplication(
    exclude = {SecurityAutoConfiguration.class}
)
public class OfilesServiceApplication {

    /**
     * Конструктор по умолчанию.
     */
    public OfilesServiceApplication() {
    }

    /**
     * Запускает приложение.
     *
     * @param args Аргументы запуска.
     */
    public static void main(final String[] args) {
        SpringApplication.run(OfilesServiceApplication.class, args);
    }

    /**
     * Получение настроек для JOOQ.
     *
     * @return Настройка JOOQ.
     */
    @Bean
    public Settings jooqSettings() {
        return new Settings().withRenderQuotedNames(RenderQuotedNames.NEVER);
    }
}
