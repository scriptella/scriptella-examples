Scriptella MongoDB Driver examples
---------------------------

This example demonstrates usage of [scriptella-mongodb-driver](https://github.com/scriptella/scriptella-mongodb) for migration between an H2 relational database and MongoDB.

1. Execute `create-h2.sh` to create a relational database in H2. The database has 3 tables: `Users`, `Posts` and `Comments` defined in `schema.sql`. The database is initialized using test data from `data.sql`.
2. Execute `h2-to-mongo.sh` to migrate the database to MongoDB. As a result, two collections should be created: users and posts. The `posts` collection also includes matching comments from the `COMMENTS` table.
3. Execute `mongo-to-h2.sh` to migrate data from MongoDB to the database.
