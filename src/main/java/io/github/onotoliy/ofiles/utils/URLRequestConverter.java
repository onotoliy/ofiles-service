package io.github.onotoliy.ofiles.utils;

import io.github.onotoliy.core.data.Option;
import io.github.onotoliy.core.rpc.IKeycloakRPC;
import io.github.onotoliy.ofiles.data.FileMetadata;
import io.github.onotoliy.ofiles.data.FileURLRequest;
import io.github.onotoliy.ofiles.data.HeadersFileURLRequest;
import io.github.onotoliy.ofiles.data.UploadFileRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

/**
 * Запрос на загрузку файла в формате URL.
 *
 * @author Anatoliy Pokhresnyi
 */
public class URLRequestConverter extends AbstractRequestConverter {

    /**
     * Запрос на загрузку файла.
     */
    private final FileURLRequest request;

    /**
     * Конструктор.
     *
     * @param users   Сервис чтения данных о пользователях из Keycloak.
     * @param request Запрос на загрузку файла.
     */
    public URLRequestConverter(
        final IKeycloakRPC users,
        final FileURLRequest request
    ) {
        super(users);

        this.request = request;
    }

    @Override
    public UploadFileRequest toRequest() {
        final String originalName = request.getName();
        final Option author = request.getAuthor();
        final InputStream content = getContent();
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
            getAvailable(content),
            request.getParameters()
        );

        return new UploadFileRequest(info, content);
    }

    /**
     * Получение размера файла.
     *
     * @param content Файл.
     * @return Размер файла.
     */
    private long getAvailable(final InputStream content) {
        try {
            return content.available();
        } catch (IOException e) {
            throw new IllegalArgumentException(
                String.format("URL %s. %s", request.getUrl(), e.getMessage()),
                e
            );
        }
    }

    /**
     * Скачивание файла по указанному URL.
     *
     * @return Файл.
     */
    private InputStream getContent() {
        try {
            final HttpRequest.Builder builder = HttpRequest
                .newBuilder()
                .GET()
                .uri(new URI(request.getUrl()));
            final List<Option> headers =
                request instanceof HeadersFileURLRequest
                    ? ((HeadersFileURLRequest) request).getHeaders()
                    : Collections.emptyList();

            headers.forEach(header ->
                builder.header(header.getName(), header.getValue())
            );

            final HttpResponse<InputStream> response = HttpClient
                .newBuilder()
                .build()
                .send(
                    builder.build(),
                    HttpResponse.BodyHandlers.ofInputStream()
                );

            if (response.statusCode() / 100 == 2) {
                return response.body();
            } else {
                throw new IllegalArgumentException(String.format(
                    "URL %s. Headers %s. Status %s. Body %s",
                    request.getUrl(),
                    headers,
                    response.statusCode(),
                    new String(response.body().readAllBytes())
                ));
            }
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new IllegalArgumentException(
                String.format("URL %s. %s", request.getUrl(), e.getMessage()),
                e
            );
        }
    }

}
