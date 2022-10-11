package io.github.onotoliy.ofiles.services;

import io.github.onotoliy.core.exceptions.NotFoundException;
import io.github.onotoliy.core.exceptions.NotUniqueException;
import io.github.onotoliy.core.utils.GUIDs;
import io.github.onotoliy.ofiles.repositories.FileRepository;
import io.github.onotoliy.ofiles.data.UploadFileRequest;
import io.github.onotoliy.core.data.SearchParameter;
import io.github.onotoliy.core.services.AbstractModifierService;
import io.github.onotoliy.ofiles.data.FileMetadata;
import io.github.onotoliy.ofiles.utils.RequestConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Сервис управления файлами.
 *
 * @author Anatoliy Pokhresnyi
 */
@Service
public class FileService extends AbstractModifierService<
    FileMetadata,
    SearchParameter,
    FileRepository> {

    /**
     * Директория для сохранения файла.
     */
    private final String directory;

    /**
     * Конструктор.
     *
     * @param directory  Директория для сохранения файла.
     * @param repository Репозиторий управления файлами.
     */
    @Autowired
    public FileService(
        @Value("${ofiles.directory}") final String directory,
        final FileRepository repository
    ) {
        super(repository);

        this.directory = directory;
    }

    /**
     * Сохранение файла.
     *
     * @param converter Запрос на сохранение файла.
     * @return Загруженный файл.
     */
    public FileMetadata create(final RequestConverter converter) {
        final UploadFileRequest request = converter.toRequest();
        final UUID uid = GUIDs.parse(request.getMetadata());

        if (repository.exists(uid)) {
            throw new NotUniqueException(
                "Файл с таким uid уже существует " + uid
            );
        }

        upload(request.getMetadata(), request.getContent());

        return super.create(request.getMetadata());
    }

    /**
     * Скачивание файла.
     *
     * @param uid Унакальный идентификатор.
     * @return Файл.
     */
    public InputStream download(final UUID uid) {
        final FileMetadata metadata = repository
            .getOptional(uid)
            .orElse(null);

        if (metadata == null) {
            throw new NotFoundException("Файл с таким uid не найден " + uid);
        }

        final Path path = Path.of(
            directory,
            metadata.getAccount(),
            metadata.getExtension(),
            metadata.getUid()
        );

        if (Files.notExists(path)) {
            throw new NotFoundException("Файл с таким uid не найден " + uid);
        }

        try {
            return new FileInputStream(path.toFile());
        } catch (FileNotFoundException e) {
            throw new NotFoundException("Файл с таким uid не найден " + uid);
        }
    }

    /**
     * Сохранение файла.
     *
     * @param metadata Данные файла.
     * @param context  Файл.
     */
    private void upload(
        final FileMetadata metadata,
        final InputStream context
    ) {
        try {
            final Path path = Path.of(
                directory,
                metadata.getAccount(),
                metadata.getExtension()
            );
            final Path file = Path.of(
                path.toString(),
                metadata.getUid()
            );

            if (Files.notExists(path)) {
                Files.createDirectories(path);
            }

            Files.copy(context, file);
        } catch (IOException exception) {
            throw new RuntimeException();
        }
    }
}
