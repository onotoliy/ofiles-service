package io.github.onotoliy.ofiles.utils;

import io.github.onotoliy.core.data.Option;
import io.github.onotoliy.core.rpc.IKeycloakRPC;
import io.github.onotoliy.ofiles.data.FileBase64Request;
import io.github.onotoliy.ofiles.data.FileMetadata;
import io.github.onotoliy.ofiles.data.UploadFileRequest;

import java.io.ByteArrayInputStream;
import java.util.Base64;

/**
 * Запрос на загрузку файла в формате Base64.
 *
 * @author Anatoliy Pokhresnyi
 */
public class Base64RequestConverter extends AbstractRequestConverter {

    /**
     * Запрос на загрузку файла.
     */
    private final FileBase64Request request;

    /**
     * Конструктор.
     *
     * @param users   Сервис чтения данных о пользователях из Keycloak.
     * @param request Запрос на загрузку файла.
     */
    public Base64RequestConverter(
        final IKeycloakRPC users,
        final FileBase64Request request
    ) {
        super(users);

        this.request = request;
    }

    @Override
    public UploadFileRequest toRequest() {
        final String originalName = request.getName();
        final byte[] content = Base64
            .getDecoder()
            .decode(request.getContent());
        final Option author = request.getAuthor();
        final FileMetadata info = new FileMetadata(
            request.getUid(),
            getAuthor(author.getUid()),
            request.getCreationDate(),
            request.getName(),
            originalName,
            request.getDescription(),
            request.getAccount(),
            getFileExtension(originalName),
            getContentType(originalName),
            content.length,
            request.getParameters()
        );

        return new UploadFileRequest(info, new ByteArrayInputStream(content));
    }
}
