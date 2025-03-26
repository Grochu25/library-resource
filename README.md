# Library Resource
This is a part of a library application created to understand JWT tokens and the OAuth2 authentication.

The server connects to the database and exposes it content through endpoints. It requires a valid JWT token generated and validated by auth server.

Other parts of application:
- [Auth server](https://github.com/Grochu25/authServer)
- [Admin client app](https://github.com/Grochu25/library-admin-client)
- client app (in progress)

The server only allows client app and admin app to get resources. Client can look through books and authros. Admin can manipulate the data in the database through their application.

## Database and Auth server
To run this application, an external **MySQL server** is required with *library_data* database (Structure and example data are stored in library_data.sql).
It contains tables for users, authors, books, copies of books and borrowed books by users.

To run it also requires working auth server.

Both servers addresses and ports are specified in environmental variables.

Users can log in to the client application, and the admin can log in to the admin application.

## Environment variables
To allow changing the addresses and ports of all servers that make up the library app, they are specified in environmental variables. The basic setup is placed in the **library.env** file:
- MYSQL_HOSTNAME=localhost
- ADMIN_HOSTNAME=127.0.0.1
- AUTH_HOSTNAME=localhost
- RESOURCE_HOSTNAME=localhost
- CLIENT_HOSTNAME=localhost
- MYSQL_PORT=3306
- ADMIN_PORT=8000
- AUTH_PORT=9000
- RESOURCE_PORT=8080
- CLIENT_PORT=5173

## Docker
To dockerize this application segment there is ready to use **Dockerfile** inside project root folder.
To use whole application with all segments there is a **docker-compose.yml**, that pulls all necessary images and sets up the network between them. Only thing user have to do to use compose file without changes is to add alias AUTH_HOSTNAME for localhost.
