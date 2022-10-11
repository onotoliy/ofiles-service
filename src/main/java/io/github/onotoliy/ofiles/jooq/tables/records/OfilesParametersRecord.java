/*
 * This file is generated by jOOQ.
 */
package io.github.onotoliy.ofiles.jooq.tables.records;


import io.github.onotoliy.ofiles.jooq.tables.OfilesParameters;

import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OfilesParametersRecord extends UpdatableRecordImpl<OfilesParametersRecord> implements Record4<UUID, UUID, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>OFILES_PARAMETERS.UID</code>.
     */
    public void setUid(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>OFILES_PARAMETERS.UID</code>.
     */
    public UUID getUid() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>OFILES_PARAMETERS.INFO_UID</code>.
     */
    public void setInfoUid(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>OFILES_PARAMETERS.INFO_UID</code>.
     */
    public UUID getInfoUid() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>OFILES_PARAMETERS.NAME</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>OFILES_PARAMETERS.NAME</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>OFILES_PARAMETERS.VALUE</code>.
     */
    public void setValue(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>OFILES_PARAMETERS.VALUE</code>.
     */
    public String getValue() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<UUID, UUID, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<UUID, UUID, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return OfilesParameters.OFILES_PARAMETERS.UID;
    }

    @Override
    public Field<UUID> field2() {
        return OfilesParameters.OFILES_PARAMETERS.INFO_UID;
    }

    @Override
    public Field<String> field3() {
        return OfilesParameters.OFILES_PARAMETERS.NAME;
    }

    @Override
    public Field<String> field4() {
        return OfilesParameters.OFILES_PARAMETERS.VALUE;
    }

    @Override
    public UUID component1() {
        return getUid();
    }

    @Override
    public UUID component2() {
        return getInfoUid();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public String component4() {
        return getValue();
    }

    @Override
    public UUID value1() {
        return getUid();
    }

    @Override
    public UUID value2() {
        return getInfoUid();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public String value4() {
        return getValue();
    }

    @Override
    public OfilesParametersRecord value1(UUID value) {
        setUid(value);
        return this;
    }

    @Override
    public OfilesParametersRecord value2(UUID value) {
        setInfoUid(value);
        return this;
    }

    @Override
    public OfilesParametersRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public OfilesParametersRecord value4(String value) {
        setValue(value);
        return this;
    }

    @Override
    public OfilesParametersRecord values(UUID value1, UUID value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OfilesParametersRecord
     */
    public OfilesParametersRecord() {
        super(OfilesParameters.OFILES_PARAMETERS);
    }

    /**
     * Create a detached, initialised OfilesParametersRecord
     */
    public OfilesParametersRecord(UUID uid, UUID infoUid, String name, String value) {
        super(OfilesParameters.OFILES_PARAMETERS);

        setUid(uid);
        setInfoUid(infoUid);
        setName(name);
        setValue(value);
    }
}