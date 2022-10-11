package io.github.onotoliy.ofiles.web;

import io.github.onotoliy.core.rpc.IKeycloakRPC;
import io.github.onotoliy.ofiles.data.FileBase64Request;
import io.github.onotoliy.ofiles.data.FileURLRequest;
import io.github.onotoliy.ofiles.services.FileService;
import io.github.onotoliy.ofiles.utils.Base64RequestConverter;
import io.github.onotoliy.ofiles.utils.MultipartRequestConverter;
import io.github.onotoliy.core.data.SearchParameter;
import io.github.onotoliy.core.exceptions.NotImplementedException;
import io.github.onotoliy.core.web.AbstractModifierResource;
import io.github.onotoliy.ofiles.data.FileMetadata;
import io.github.onotoliy.ofiles.utils.URLRequestConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.UUID;

/**
 * WEB сервис управления файлами.
 *
 * @author Anatoliy Pokhresnyi
 */
@RestController
@RequestMapping("/files")
public class FileResource extends AbstractModifierResource<
    FileMetadata,
    SearchParameter,
    FileService> {

    /**
     * Сервис чтения данных о пользователях из Keycloak.
     */
    private final IKeycloakRPC keycloak;

    /**
     * Конструктор.
     *
     * @param user    Сервис чтения данных о пользователях из Keycloak.
     * @param service Сервис управления файлами.
     */
    public FileResource(final IKeycloakRPC user, final FileService service) {
        super(service);
        this.keycloak = user;
    }

    /**
     * Загрузка файла с помощью {@link MultipartHttpServletRequest} запроса.
     *
     * @param request Запрос на загрузку файла.
     * @return Загруженный файл.
     */
    @PostMapping("/upload/multipart")
    public FileMetadata uploadMultipart(
        final MultipartHttpServletRequest request
    ) {
        return service.create(
            new MultipartRequestConverter(keycloak, request)
        );
    }

    /**
     * Загрузка файла с помощью {@link FileBase64Request} запроса.
     *
     * @param request Запрос на загрузку файла.
     * @return Загруженный файл.
     */
    @PostMapping("/upload/base64")
    public FileMetadata uploadBase64(
        final FileBase64Request request
    ) {
        return service.create(new Base64RequestConverter(keycloak, request));
    }

    /**
     * Загрузка файла с помощью {@link FileURLRequest} запроса.
     *
     * @param request Запрос на загрузку файла.
     * @return Загруженный файл.
     */
    @PostMapping("/upload/url")
    public FileMetadata uploadURL(
        final FileURLRequest request
    ) {
        return service.create(new URLRequestConverter(keycloak, request));
    }

    @Override
    public FileMetadata create(final FileMetadata dto) {
        throw new NotImplementedException(
            "Для загрузки файла используйте метод /upload/*"
        );
    }

    @Override
    public FileMetadata update(final FileMetadata dto) {
        throw new NotImplementedException("Изменение файла не возможно");
    }

    @Override
    public void delete(final UUID uuid) {
        throw new NotImplementedException("Удаление файла не возможно");
    }
}
