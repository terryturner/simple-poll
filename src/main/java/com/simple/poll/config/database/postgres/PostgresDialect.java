package com.simple.poll.config.database.postgres;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL94Dialect;

public class PostgresDialect extends PostgreSQL94Dialect {
    public PostgresDialect() {
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
    }
}
