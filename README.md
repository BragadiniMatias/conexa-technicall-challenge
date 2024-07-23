# conexa-technical-challenge

La aplicacion es una API Rest que se integra con la API de Star Wars (https://www.swapi.tech/documentation). La API permite el listado de Vehicles, Starships, People y Films, de manera paginada. Tambien permite filtrar por el ID de las entidades para obtener informacion en detalle de cada entidad.

La misma esta estructurada en una arquitectura simple y limpia por capas. Una capa de DTO, para modelar y manejar respuestas del sistema. Una capa de dominio, para implementar logica de negocio y operaciones. Y una capa de repositorio que es la que se termina conectando con la API externa de Star Wars y maneja las respuestas mismas de la API y las modela en obtejos propios del sistema.

Asimismo, posee un sistema de autenticacion simple con JWT.

Para poder **logearse**, utilizar el endpoint: 

**/login?user=conexa&password=challenge**

El mismo retornara un token para poder acceder al listado y/o consulta por ID de cada entidad

Hasta que no se autentique, no permite el acceso al resto de los endpoints.

**Para la consultas**: 

**Films**:

/films: Retorna lista de los 6 films de Star Wars
/films/id: Retorna informacion especifica de la pelicula con el id enviado por parametro. 


**People**: 

/people: Retorna de forma paginada el listado de people. Por defecto, 1 pagina y 10 resultados

/people?page=1&size=20 Lista de forma paginada, 1 pagina y 20 resultados. (Por ejemplo)

/people/id: Retorma de forma especifica la persona

**Starships**: 

/starships    Retorna de forma paginada el listado de starships. Por defecto, 1 pagina y 10 resultados

/starships?page=1&size=20 Lista de forma paginada, 1 pagina y 20 resultados. (Por ejemplo)

/starhips/id Retorma de forma especifica el starship

**Vehicle**: 

/vehicles Retorna de forma paginada el listado de vehicles. Por defecto, 1 pagina y 10 resultados

/vehicles?page=1&size=20 Lista de forma paginada, 1 pagina y 20 resultados. (Por ejemplo)

/starhips/id Retorma de forma especifica el starship





**TODO**

Implementar filtrado por nombre. Quedo pendiente porque la construccion de las urls y el armado de los objetos varia bastante.

Implementar el mostrado de informacion con respecto a la paginacion (pagina actual, next, previous, etc). Quedo pendiente

Implementar documentacion mas robusta.

