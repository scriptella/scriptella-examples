"""Create a new file-backed source fixture using independent Python sqlite3."""
import sqlite3
import sys
from pathlib import Path

path = Path(sys.argv[1])
# Refuse to overwrite an existing database.
with path.open("xb"):
    pass
with sqlite3.connect(path) as connection:
    connection.execute("CREATE TABLE records (id INTEGER PRIMARY KEY, big_value INTEGER, "
                       "amount NUMERIC, label TEXT, note TEXT)")
    connection.executemany("INSERT INTO records VALUES (?, ?, ?, ?, ?)", [
        (1, 9007199254740993, 123.125, "Hello café — 東京 😀", None),
        (2, 9223372036854775807, -0.5, "naïve Ελληνικά", ""),
        (3, -9223372036854775808, 0, "", "ordinary text"),
        (4, None, None, None, None),
    ])
print("Created", path)
