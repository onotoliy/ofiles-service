/*
 * This file is generated by jOOQ.
 */
package io.github.onotoliy.ofiles.jooq.tables;


import io.github.onotoliy.ofiles.jooq.DefaultSchema;
import io.github.onotoliy.ofiles.jooq.Keys;
import io.github.onotoliy.ofiles.jooq.tables.records.OfilesInfoRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row11;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OfilesInfo extends TableImpl<OfilesInfoRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>OFILES_INFO</code>
     */
    public static final OfilesInfo OFILES_INFO = new OfilesInfo();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OfilesInfoRecord> getRecordType() {
        return OfilesInfoRecord.class;
    }

    /**
     * The column <code>OFILES_INFO.UID</code>. Уникальный идентификатор
     */
    public final TableField<OfilesInfoRecord, UUID> UID = createField(DSL.name("UID"), SQLDataType.UUID.nullable(false), this, "Уникальный идентификатор");

    /**
     * The column <code>OFILES_INFO.NAME</code>.
     */
    public final TableField<OfilesInfoRecord, String> NAME = createField(DSL.name("NAME"), SQLDataType.VARCHAR(2147483647), this, "");

    /**
     * The column <code>OFILES_INFO.AUTHOR</code>.
     */
    public final TableField<OfilesInfoRecord, UUID> AUTHOR = createField(DSL.name("AUTHOR"), SQLDataType.UUID, this, "");

    /**
     * The column <code>OFILES_INFO.CREATION_DATE</code>.
     */
    public final TableField<OfilesInfoRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("CREATION_DATE"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>OFILES_INFO.DELETION_DATE</code>.
     */
    public final TableField<OfilesInfoRecord, LocalDateTime> DELETION_DATE = createField(DSL.name("DELETION_DATE"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>OFILES_INFO.ORIGINAL_NAME</code>.
     */
    public final TableField<OfilesInfoRecord, String> ORIGINAL_NAME = createField(DSL.name("ORIGINAL_NAME"), SQLDataType.VARCHAR(2147483647), this, "");

    /**
     * The column <code>OFILES_INFO.CONTENT_TYPE</code>.
     */
    public final TableField<OfilesInfoRecord, String> CONTENT_TYPE = createField(DSL.name("CONTENT_TYPE"), SQLDataType.VARCHAR(2147483647), this, "");

    /**
     * The column <code>OFILES_INFO.EXTENSION</code>.
     */
    public final TableField<OfilesInfoRecord, String> EXTENSION = createField(DSL.name("EXTENSION"), SQLDataType.VARCHAR(2147483647), this, "");

    /**
     * The column <code>OFILES_INFO.SIZE</code>.
     */
    public final TableField<OfilesInfoRecord, Long> SIZE = createField(DSL.name("SIZE"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>OFILES_INFO.ACCOUNT</code>.
     */
    public final TableField<OfilesInfoRecord, UUID> ACCOUNT = createField(DSL.name("ACCOUNT"), SQLDataType.UUID, this, "");

    /**
     * The column <code>OFILES_INFO.DESCRIPTION</code>.
     */
    public final TableField<OfilesInfoRecord, String> DESCRIPTION = createField(DSL.name("DESCRIPTION"), SQLDataType.VARCHAR(2147483647), this, "");

    private OfilesInfo(Name alias, Table<OfilesInfoRecord> aliased) {
        this(alias, aliased, null);
    }

    private OfilesInfo(Name alias, Table<OfilesInfoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>OFILES_INFO</code> table reference
     */
    public OfilesInfo(String alias) {
        this(DSL.name(alias), OFILES_INFO);
    }

    /**
     * Create an aliased <code>OFILES_INFO</code> table reference
     */
    public OfilesInfo(Name alias) {
        this(alias, OFILES_INFO);
    }

    /**
     * Create a <code>OFILES_INFO</code> table reference
     */
    public OfilesInfo() {
        this(DSL.name("OFILES_INFO"), null);
    }

    public <O extends Record> OfilesInfo(Table<O> child, ForeignKey<O, OfilesInfoRecord> key) {
        super(child, key, OFILES_INFO);
    }

    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<OfilesInfoRecord> getPrimaryKey() {
        return Keys.PK_OFILES_INFO;
    }

    @Override
    public List<UniqueKey<OfilesInfoRecord>> getKeys() {
        return Arrays.<UniqueKey<OfilesInfoRecord>>asList(Keys.PK_OFILES_INFO);
    }

    @Override
    public OfilesInfo as(String alias) {
        return new OfilesInfo(DSL.name(alias), this);
    }

    @Override
    public OfilesInfo as(Name alias) {
        return new OfilesInfo(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public OfilesInfo rename(String name) {
        return new OfilesInfo(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public OfilesInfo rename(Name name) {
        return new OfilesInfo(name, null);
    }

    // -------------------------------------------------------------------------
    // Row11 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row11<UUID, String, UUID, LocalDateTime, LocalDateTime, String, String, String, Long, UUID, String> fieldsRow() {
        return (Row11) super.fieldsRow();
    }
}
