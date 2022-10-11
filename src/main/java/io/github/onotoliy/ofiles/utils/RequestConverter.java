package io.github.onotoliy.ofiles.utils;

import io.github.onotoliy.ofiles.data.UploadFileRequest;

/**
 * Запрос на загрузку файла.
 *
 * @author Anatoliy Pokhresnyi
 */
public interface RequestConverter {

    /**
     * Получение запроса на загрузку файла.
     *
     * @return Запрос на загрузку файла.
     */
    UploadFileRequest toRequest();

}
