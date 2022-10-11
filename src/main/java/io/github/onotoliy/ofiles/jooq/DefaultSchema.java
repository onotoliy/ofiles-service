/*
 * This file is generated by jOOQ.
 */
package io.github.onotoliy.ofiles.jooq;


import io.github.onotoliy.ofiles.jooq.tables.OfilesInfo;
import io.github.onotoliy.ofiles.jooq.tables.OfilesParameters;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>DEFAULT_SCHEMA</code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>OFILES_INFO</code>.
     */
    public final OfilesInfo OFILES_INFO = OfilesInfo.OFILES_INFO;

    /**
     * The table <code>OFILES_PARAMETERS</code>.
     */
    public final OfilesParameters OFILES_PARAMETERS = OfilesParameters.OFILES_PARAMETERS;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            OfilesInfo.OFILES_INFO,
            OfilesParameters.OFILES_PARAMETERS);
    }
}