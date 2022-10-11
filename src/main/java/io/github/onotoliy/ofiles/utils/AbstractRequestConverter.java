package io.github.onotoliy.ofiles.utils;

import io.github.onotoliy.core.data.Option;
import io.github.onotoliy.core.rpc.IKeycloakRPC;
import io.github.onotoliy.core.utils.Dates;
import io.github.onotoliy.core.utils.GUIDs;
import io.github.onotoliy.core.utils.Objects;
import io.github.onotoliy.core.utils.Strings;
import kotlinx.datetime.Instant;
import org.apache.tika.Tika;

/**
 * Базовый запрос на загрузку файла.
 *
 * @author Anatoliy Pokhresnyi
 */
public abstract class AbstractRequestConverter implements RequestConverter {

    /**
     * Сервис чтения данных о пользователях из Keycloak.
     */
    private final IKeycloakRPC users;

    /**
     * Конструктор.
     *
     * @param users Сервис чтения данных о пользователях из Keycloak.
     */
    protected AbstractRequestConverter(final IKeycloakRPC users) {
        this.users = users;
    }

    /**
     * Получение уникального идентификатора.
     *
     * @param uid Уникальный идентификатор.
     * @return Уникальный идентификатор.
     */
    protected String getUID(final String uid) {
        if (Strings.isEmpty(uid)) {
            return GUIDs.format(GUIDs.random());
        } else {
            return uid;
        }
    }

    /**
     * Получение даты создания.
     *
     * @param creationDate Дата создания.
     * @return Дата создания.
     */
    protected Instant getCreationDate(final Instant creationDate) {
        if (Objects.isEmpty(creationDate)) {
            return Dates.toKotlinInstant(Dates.now());
        } else {
            return creationDate;
        }
    }

    /**
     * Получение автора.
     *
     * @param author Автор.
     * @return Автор.
     */
    protected Option getAuthor(final String author) {
        if (Strings.isEmpty(author)) {
            return users.getCurrentUser();
        } else {
            return new Option(author, author, author);
        }
    }

    /**
     * Получение Content-Type.
     *
     * @param name Content-Type.
     * @return Content-Type.
     */
    protected String getContentType(final String name) {
        return new Tika().detect(name);
    }

    /**
     * Получение расширения файла.
     *
     * @param name Название файла.
     * @return Расширение файла.
     */
    protected String getFileExtension(final String name) {
        if (Strings.isEmpty(name)) {
            return "";
        }

        final int dotIndex = name.lastIndexOf('.');
        return (dotIndex == -1) ? "" : name.substring(dotIndex + 1);
    }
}
