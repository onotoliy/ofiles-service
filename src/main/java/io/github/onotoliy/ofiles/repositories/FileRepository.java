package io.github.onotoliy.ofiles.repositories;

import io.github.onotoliy.core.data.Option;
import io.github.onotoliy.core.data.SearchParameter;
import io.github.onotoliy.core.repositories.AbstractModifierRepository;
import io.github.onotoliy.core.rpc.IKeycloakRPC;
import io.github.onotoliy.core.utils.Dates;
import io.github.onotoliy.core.utils.GUIDs;
import io.github.onotoliy.core.utils.Longs;
import io.github.onotoliy.core.utils.Strings;
import io.github.onotoliy.ofiles.data.FileMetadata;
import io.github.onotoliy.ofiles.jooq.tables.OfilesInfo;
import io.github.onotoliy.ofiles.jooq.tables.records.OfilesInfoRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.onotoliy.ofiles.jooq.Tables.OFILES_PARAMETERS;
import static io.github.onotoliy.ofiles.jooq.tables.OfilesInfo.OFILES_INFO;

/**
 * Репозиторий управления файлами.
 *
 * @author Anatoliy Pokhresnyi
 */
@Repository
public class FileRepository extends AbstractModifierRepository<
    FileMetadata,
    SearchParameter,
    OfilesInfoRecord,
    OfilesInfo> {

    /**
     * Конструктор.
     *
     * @param dsl  Контекст подключения к БД.
     * @param user Сервис чтения пользователей.
     */
    @Autowired
    public FileRepository(
        final DSLContext dsl,
        final IKeycloakRPC user
    ) {
        super(
            OFILES_INFO,
            OFILES_INFO.UID,
            OFILES_INFO.NAME,
            OFILES_INFO.AUTHOR,
            OFILES_INFO.CREATION_DATE,
            OFILES_INFO.DELETION_DATE,
            dsl,
            user
        );
    }

    @Override
    public FileMetadata create(
        final Configuration configuration,
        final FileMetadata dto
    ) {
        final FileMetadata metadata = super.create(configuration, dto);

        createOrUpdate(configuration, GUIDs.parse(dto), dto.getParameters());

        return metadata;
    }

    @Override
    public FileMetadata update(
        final Configuration configuration,
        final FileMetadata dto
    ) {
        final FileMetadata metadata = super.update(configuration, dto);

        createOrUpdate(configuration, GUIDs.parse(dto), dto.getParameters());

        return metadata;
    }

    @Override
    public InsertSetMoreStep<OfilesInfoRecord> insertQuery(
        final Configuration configuration,
        final FileMetadata dto
    ) {
        return super.insertQuery(configuration, dto)
                    .set(table.ORIGINAL_NAME, dto.getOriginalName())
                    .set(table.CONTENT_TYPE, dto.getContentType())
                    .set(table.EXTENSION, dto.getExtension())
                    .set(table.SIZE, dto.getSize())
                    .set(table.ACCOUNT, GUIDs.parse(dto.getAccount()))
                    .set(table.DESCRIPTION, dto.getDescription());
    }

    @Override
    public UpdateSetMoreStep<OfilesInfoRecord> updateQuery(
        final Configuration configuration,
        final FileMetadata dto
    ) {
        return super.updateQuery(configuration, dto)
                    .set(table.ORIGINAL_NAME, dto.getOriginalName())
                    .set(table.CONTENT_TYPE, dto.getContentType())
                    .set(table.EXTENSION, dto.getExtension())
                    .set(table.SIZE, dto.getSize())
                    .set(table.ACCOUNT, GUIDs.parse(dto.getAccount()))
                    .set(table.DESCRIPTION, dto.getDescription());
    }

    @Override
    protected FileMetadata toDTO(final Record record) {
        return new FileMetadata(
            GUIDs.format(record, table.UID),
            formatUser(record, author),
            Dates.format(record, table.CREATION_DATE),
            Strings.format(record, table.NAME),
            Strings.format(record, table.ORIGINAL_NAME),
            Strings.format(record, table.DESCRIPTION),
            GUIDs.format(record, table.ACCOUNT),
            Strings.format(record, table.EXTENSION),
            Strings.format(record, table.CONTENT_TYPE),
            Longs.format(record, table.SIZE),
            Collections.emptyList()
        );
    }

    /**
     * Сохранение фала.
     *
     * @param configuration Настройка транзакции.
     * @param file Уникальный идентификатор файла.
     * @param parameters Параметры файла.
     */
    private void createOrUpdate(
        final Configuration configuration,
        final UUID file,
        final List<Option> parameters
    ) {
        DSL
            .using(configuration)
            .delete(OFILES_PARAMETERS)
            .where(OFILES_PARAMETERS.INFO_UID.eq(file))
            .execute();

        Optional.ofNullable(parameters)
                .orElse(Collections.emptyList())
                .forEach(parameter -> {
                    final Query query = DSL
                        .using(configuration)
                        .insertInto(OFILES_PARAMETERS)
                        .set(OFILES_PARAMETERS.UID, GUIDs.parse(parameter))
                        .set(OFILES_PARAMETERS.INFO_UID, file)
                        .set(OFILES_PARAMETERS.NAME, parameter.getName())
                        .set(OFILES_PARAMETERS.VALUE, parameter.getValue());

                    execute(OFILES_PARAMETERS, GUIDs.parse(parameter), query);
                });
    }

}
