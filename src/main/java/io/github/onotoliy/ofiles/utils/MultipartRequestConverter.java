package io.github.onotoliy.ofiles.utils;

import io.github.onotoliy.core.data.Option;
import io.github.onotoliy.core.rpc.IKeycloakRPC;
import io.github.onotoliy.core.utils.GUIDs;
import io.github.onotoliy.core.utils.Strings;
import io.github.onotoliy.ofiles.data.Defaults;
import io.github.onotoliy.ofiles.data.FileMetadata;
import io.github.onotoliy.ofiles.data.UploadFileRequest;
import kotlinx.datetime.Clock;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Запрос на загрузку файла в формате Multipart.
 *
 * @author Anatoliy Pokhresnyi
 */
public class MultipartRequestConverter extends AbstractRequestConverter {



    /**
     * Уникальный идентификатор.
     */
    private static final String UID = "uid";

    /**
     * Файл.
     */
    private static final String FILE = "file";

    /**
     * Автор.
     */
    private static final String AUTHOR = "author";

    /**
     * Аккаунт.
     */
    private static final String ACCOUNT = "account";

    /**
     * Название.
     */
    private static final String NAME = "name";

    /**
     * Описание.
     */
    private static final String DESCRIPTION = "description";

    /**
     * Запрос на загрузку файла.
     */
    private final MultipartHttpServletRequest request;

    /**
     * Конструктор.
     *
     * @param users   Сервис чтения данных о пользователях из Keycloak.
     * @param request Запрос на загрузку файла.
     */
    public MultipartRequestConverter(
        final IKeycloakRPC users,
        final MultipartHttpServletRequest request
    ) {
        super(users);

        this.request = request;
    }

    @Override
    public UploadFileRequest toRequest() {
        final String uid = getUID(getPartString(request, UID));
        final String name = getPartString(request, NAME);
        final String originalName = getFileOriginalName(request);
        final String description = getPartString(request, DESCRIPTION);
        final String account = getPartString(request, ACCOUNT);
        final InputStream content = getPartInputStream(request, FILE);
        final FileMetadata info = new FileMetadata(
            uid,
            getAuthor(getPartString(request, AUTHOR)),
            getCreationDate(Clock.System.INSTANCE.now()),
            Strings.isEmpty(name) ? uid : name,
            Strings.isEmpty(originalName) ? uid : originalName,
            Strings.isEmpty(description) ? "" : description,
            Strings.isEmpty(account) ? Defaults.UID : account,
            getFileExtension(originalName),
            getContentType(originalName),
            request.getContentLengthLong(),
            getParameters(request)
        );

        return new UploadFileRequest(info, content);
    }

    /**
     * Получение параметров файла.
     *
     * @param request Запрос на загрузку файла.
     * @return Параметры файла.
     */
    private List<Option> getParameters(
        final MultipartHttpServletRequest request
    ) {
        final List<String> defaults = List
            .of(UID, FILE, AUTHOR, ACCOUNT, NAME, DESCRIPTION);
        final Map<String, Option> parameters = new HashMap<>();

        Optional
            .ofNullable(request)
            .map(req -> {
                try {
                    return req.getParts();
                } catch (ServletException | IOException exception) {
                    return Collections.emptyList();
                }
            })
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList())
            .stream()
            .map(part -> (Part) part)
            .filter(part -> !defaults.contains(part.getName()))
            .forEach(part -> {
                final String name = part.getName();
                final String value = getPartString(request, name);

                parameters.putIfAbsent(
                    name + ":" + value,
                    new Option(GUIDs.format(GUIDs.random()), name, value)
                );
            });

        return new ArrayList<>(parameters.values());
    }

    /**
     * Преобразование {@see Part} в {@see String}.
     *
     * @param request Запрос на загрузку файла.
     * @param part Part.
     * @return Данные.
     */
    private String getPartString(
        final MultipartHttpServletRequest request,
        final String part
    ) {
        return Optional
            .ofNullable(getPartInputStream(request, part))
            .map(value -> {
                try {
                    return value.readAllBytes();
                } catch (Exception exception) {
                    return null;
                }
            })
            .map(String::new)
            .orElse(null);
    }

    /**
     * Преобразование {@see Part} в {@see InputStream}.
     *
     * @param request Запрос на загрузку файла.
     * @param part Название.
     * @return Данные.
     */
    private InputStream getPartInputStream(
        final MultipartHttpServletRequest request,
        final String part
    ) {
        return Optional
            .ofNullable(getPart(request, part))
            .map(this::getPartInputStream)
            .orElse(null);
    }

    /**
     * Преобразование {@see Part} в {@see InputStream}.
     *
     * @param part Part.
     * @return Данные.
     */
    private InputStream getPartInputStream(final Part part) {
        return Optional
            .ofNullable(part)
            .map(value -> {
                try {
                    return value.getInputStream();
                } catch (Exception exception) {
                    return null;
                }
            })
            .orElse(null);
    }

    /**
     * Получение {@see Part}.
     *
     * @param request Запрос на загрузку файла.
     * @param part Название.
     * @return {@see Part}.
     */
    private Part getPart(
        final MultipartHttpServletRequest request,
        final String part
    ) {
        return Optional
            .ofNullable(request)
            .map(value -> {
                try {
                    return value.getPart(part);
                } catch (Exception exception) {
                    return null;
                }
            })
            .orElse(null);
    }

    /**
     * Получение оригинального названия файла.
     *
     * @param request Запрос на загрузку файла.
     * @return Оригинальное названия файла.
     */
    private String getFileOriginalName(
        final MultipartHttpServletRequest request
    ) {
        return Optional
            .ofNullable(request)
            .map(value -> getPart(value, FILE))
            .map(value -> value.getHeader("Content-Disposition"))
            .map(disposition -> disposition.split(";"))
            .flatMap(dispositions -> Arrays
                .stream(dispositions)
                .map(String::trim)
                .filter(disposition -> disposition.startsWith("filename="))
                .findFirst()
            )
            .map(disposition -> disposition.replaceAll("filename=", ""))
            .map(disposition -> {
                if (disposition.startsWith("\"") && disposition
                    .endsWith("\"")) {
                    return disposition.substring(1, disposition.length() - 1);
                }
                if (disposition.startsWith("\"")) {
                    return disposition.substring(1);
                }
                if (disposition.endsWith("\"")) {
                    return disposition.substring(0, disposition.length() - 1);
                }
                return disposition;
            })
            .orElse(null);
    }

}
