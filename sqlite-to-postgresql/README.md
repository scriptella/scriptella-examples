# SQLite → PostgreSQL

This is the canonical Scriptella 1.6 file-backed SQLite migration example.
Use Java 17+, psql, a Scriptella 1.6 distribution, and an empty, disposable
PostgreSQL database. The target table `sqlite_records` must not exist.
The example creates it and commits four rows; repeat runs fail instead of
silently replacing data. It does not promise cross-database atomicity.

Download the external runtime drivers from Maven Central:

- `org.xerial:sqlite-jdbc:3.53.4.0`
- `org.postgresql:postgresql:42.7.13`

The `sqlite` and `postgresql` aliases are provided by `scriptella-drivers`.
They are available in the normal Scriptella distribution; embedded Maven
applications need `scriptella-drivers` in addition to `scriptella-core`.
Driver JARs are supplied externally.
Set absolute paths and destination connection details:

```sh
export SCRIPTELLA_JAR=/absolute/path/to/scriptella.jar
export SQLITE_JAR=/absolute/path/to/sqlite-jdbc-3.53.4.0.jar
export PG_JAR=/absolute/path/to/postgresql-42.7.13.jar
export SQLITE_DB=/absolute/path/to/new-source.db
export PG_URL=jdbc:postgresql://localhost:5432/sqlite_example
export PGUSER=example_user
export PGPASSWORD=example_password
export PGHOST=localhost PGPORT=5432 PGDATABASE=sqlite_example
```

Use disposable credentials here; JVM arguments may be visible in process
listings. For production, obtain credentials through your protected
configuration mechanism. Run from this sample directory. Point `SQLITE_DB`
at a path that does not exist yet. Scriptella creates that file, the
`records` table, and the four source rows. Seeding again fails because the
table is already there.

```sh
java -Ddb.path="$SQLITE_DB" -Dsqlite.jar="$SQLITE_JAR" \
     -jar "$SCRIPTELLA_JAR" --quiet --no-jmx seed.etl.xml
java -Ddb.path="$SQLITE_DB" -Dsqlite.jar="$SQLITE_JAR" -Dpg.jar="$PG_JAR" \
     -Dpg.url="$PG_URL" -Dpg.user="$PGUSER" -Dpg.password="$PGPASSWORD" \
     -jar "$SCRIPTELLA_JAR" --quiet --no-jmx migrate.etl.xml
psql -X -v ON_ERROR_STOP=1 -f verify.sql
```

`verify.sql` uses a fresh PostgreSQL client connection and compares every row
against constants, including both signed 64-bit boundaries, an integer above
2^53, common fractions, Unicode, NULL and empty text. A mismatch raises an
exception and produces a nonzero psql exit status. Use matching UTF-8 client
encoding (for example `PGCLIENTENCODING=UTF8`).

The bounded migration investigation tested PostgreSQL 17.11 and pgJDBC 42.7.13.
Other server destinations are not implied. See [SQLite support](https://github.com/scriptella/scriptella-etl/blob/master/docs/sqlite.md)
for the numeric precision limitation and compatibility evidence.

See [recorded compatibility evidence](VALIDATION.md) for the tested versions
and independent verifier results.
