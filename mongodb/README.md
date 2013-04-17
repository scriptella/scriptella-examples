Scriptella MongoDB Driver examples
---------------------------

This examples demonstrates usage of [scriptella-mongodb-driver](https://github.com/scriptella/scriptella-mongodb) for migration between relational HSQLDB database and MongoDB.

1. Execute `create-hsqldb.sh` to create a relational database in HSQLDB. The database has 3 tables: `Users`, `Posts` and `Comments` defined in `schema.sql`. The database is initialized using a test data from `data.sql`.
2. Execute `hsql-to-mongo.sh` to migrate the database to MongoDB. As a results 2 collections should be created: users and posts. The `posts` collection also includes matching comments from `COMMENTS` tables.
3. Execute `mongo-to-hsql.sh` to migrate data from MongoDB to the database.

