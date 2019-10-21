[![][license img]][license]
[![][gitter img]][gitter]


# GraphQL

This project provides an example implementation of using GraphQL with Knotx.
//TODO link to tutorial

## Run
Build first docker image
```
$ gradlew clean build
```

Run Knot.x instance and example Web API services (User details, Payment API) in a single node Docker Swarm:
```
$ docker swarm init

$ docker stack deploy -c graphql-api.yml graphql-api
```

## Queries

GraphQL schema can be found [here](modules/books/src/main/resources/books.graphqls). Example query asking for everything:

```graphql
{
    book(id: "q5NoDwAAQBAJ") { #google api book id
        title
        publisher
        authors
    }
    books(match: "java") {
        title
        publisher
        authors
    }
}
```

[license]:https://github.com/Cognifide/knotx/blob/master/LICENSE
[license img]:https://img.shields.io/badge/License-Apache%202.0-blue.svg

[gitter]:https://gitter.im/Knotx/Lobby
[gitter img]:https://badges.gitter.im/Knotx/knotx-extensions.svg

