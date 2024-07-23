# conexa-technicall-challenge

La aplicacion es una API Rest que se integra con la API de Star Wars (https://www.swapi.tech/documentation). La API permite el listado de Vehicles, Starships, People y Films, de manera paginada. Tambien permite filtrar por el ID de las entidades para obtener informacion en detalle de cada entidad.

La misma esta estructurada en una arquitectura simple y limpia por capas. Una capa de DTO, para modelar y manejar respuestas del sistema. Una capa de dominio, para implementar logica de negocio y operaciones. Y una capa de repositorio que es la que se termina conectando con la API externa de Star Wars y maneja las respuestas mismas de la API y las modela en obtejos propios del sistema.

Asimismo, posee un sistema de autenticacion simple con JWT.

Para poder logearse, utilizar el endpoint: login?user=conexa&password=challenge
 -El mismo retornara un token para poder acceder al listado y/o consulta por ID de cada entidad
 -Hasta que no se autentique, no permite el acceso al resto de los endpoints.

Para la consultas
  -Films: 

