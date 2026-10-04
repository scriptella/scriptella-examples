# Compatibility evidence — 2026-10-04

Validated the example against Scriptella `1.6-SNAPSHOT`, based on product
revision `bf1450849db1b0137fc5df33a5e34c844c3fcc19` plus the SQLite adapter,
autodetection, and regression changes for issue #66.

| Component | Tested version |
|---|---|
| Java | Temurin 17.0.15+6 |
| SQLite JDBC | Xerial 3.53.4.0 |
| PostgreSQL | 17.11 |
| PostgreSQL JDBC | 42.7.13 |

The example's `seed.py` created a new file using independent Python sqlite3.
`migrate.etl.xml` ran through the compiled Scriptella CLI launcher using the
`sqlite` and `postgresql` aliases and external connection-classpath driver JARs.
This validated the source-build CLI; a packaged 1.6 distribution smoke run
remains part of release validation.

A fresh native psql connection ran `verify.sql` with `ON_ERROR_STOP=1`:

```text
DO
PASS: exact integers, common numerics, Unicode, NULL and empty text
```

After deliberately changing row 1's label in the disposable destination,
the same verifier rejected the data with psql exit status 3:

```text
ERROR: SQLite migration values differ from the expected fixture
```

This covers the four sample rows, including signed 64-bit extrema and the
integer above 2^53. It is bounded compatibility evidence, not a SQLite version
matrix or a claim of arbitrary-decimal fidelity. The product regression also
passed file-backed schema/read/write/persistence and commit/rollback checks
with fresh direct JDBC verification.

The [original #62 investigation](https://gist.github.com/ejboy/e3768dd452cbae4ecf5775e176a0da70)
provides reproducible evidence for NUMERIC/REAL precision loss and a direct-JDBC
control. The support documentation explains this limitation. SQLite is tested
as a real file; it is not added to the Testcontainers matrix.
