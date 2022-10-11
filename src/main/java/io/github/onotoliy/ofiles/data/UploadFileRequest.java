package io.github.onotoliy.ofiles.data;

import java.io.InputStream;

/**
 * Запрос на сохранение файла.
 *
 * @author Anatoliy Pokhresnyi
 */
public class UploadFileRequest {

    /**
     * Данные файла.
     */
    private final FileMetadata metadata;

    /**
     * Файл.
     */
    private final InputStream content;

    /**
     * Конструктор.
     *
     * @param metadata Данные файла.
     * @param content  Файл.
     */
    public UploadFileRequest(
        final FileMetadata metadata,
        final InputStream content
    ) {
        this.metadata = metadata;
        this.content = content;
    }

    /**
     * Получение данных файла.
     *
     * @return Данные файла
     */
    public FileMetadata getMetadata() {
        return metadata;
    }

    /**
     * Получение файла.
     *
     * @return Файл.
     */
    public InputStream getContent() {
        return content;
    }
}
