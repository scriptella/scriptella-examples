Scriptella MongoDB Driver examples
---------------------------

This example demonstrates usage of [scriptella-mongodb-driver](https://github.com/scriptella/scriptella-mongodb) for migration between an H2 relational database and MongoDB.

1. Execute `create-hsqldb.sh` to create a relational database in H2. The database has 3 tables: `Users`, `Posts` and `Comments` defined in `schema.sql`. The database is initialized using test data from `data.sql`.
2. Execute `hsql-to-mongo.sh` to migrate the database to MongoDB. As a results 2 collections should be created: users and posts. The `posts` collection also includes matching comments from `COMMENTS` tables.
3. Execute `mongo-to-hsql.sh` to migrate data from MongoDB to the database.
