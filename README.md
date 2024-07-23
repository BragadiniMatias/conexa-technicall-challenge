# conexa-technical-challenge

La aplicacion es una API Rest que se integra con la API de Star Wars (https://www.swapi.tech/documentation). La API permite el listado de Vehicles, Starships, People y Films, de manera paginada. Tambien permite filtrar por el ID de las entidades para obtener informacion en detalle de cada una.

La misma esta basada en una arquitectura simple y limpia por capas. Una capa de DTO, para modelar y manejar respuestas del sistema hacia el exterior, Una capa de dominio, para implementar logica de negocio y operaciones. Y una capa de repositorio que es la que se termina conectando con la API externa de Star Wars y maneja las respuestas mismas de la API y las modela en obtejos propios del sistema. Se utiliza MapStruct para la conversion entre los objetos de las distintas capas del sistema.

Asimismo, posee un sistema de autenticacion simple con JWT.

# **URL**

**https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/**

## **Login - POST**

**/login?user=conexa&password=challenge**

El mismo retornara un token para poder acceder al listado y/o consulta por ID de cada entidad

Hasta que no se autentique, no permite el acceso al resto de los endpoints.

### Ejemplo
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/login?user=conexa&password=challenge


## **Films - GET**

- /films: Retorna lista de los 6 films de Star Wars
- /films/id: Retorna informacion especifica de la pelicula con el id enviado por parametro.

### Ejemplos: 
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/films
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/films/1


## **People - GET**: 

- /people: Retorna de forma paginada el listado de people. Por defecto, 1 pagina y 10 resultados
- /people?page=1&size=20 Permite listado paginado con valores especificos para la pagina y la cantidad de elementos de la pagina.
- /people/id: Retorma de forma especifica la persona

### Ejemplos: 

- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/people
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/people?page=1&size=20
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/people?page=2&size=20
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/people?page=3&size=20
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/people?page=4&size=20
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/people/2

## **Starships - GET**: 

- /starships    Retorna de forma paginada el listado de starships. Por defecto, 1 pagina y 10 resultados
- /starships?page=1&size=20 Permite listado paginado con valores especificos para la pagina y la cantidad de elementos de la pagina.
- /starhips/id Retorma de forma especifica el starship

### Ejemplos:

- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/starships
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/starships?page=1&size=10
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/starships?page=2&size=10
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/starships?page=3&size=10

## **Vehicle - GET**: 

- /vehicles Retorna de forma paginada el listado de vehicles. Por defecto, 1 pagina y 10 resultados
- /vehicles?page=1&size=20 Permite listado paginado con valores especificos para la pagina y la cantidad de elementos de la pagina.
- /starhips/id Retorma de forma especifica el starship

### Ejemplos
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/vehicles
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/vehicles?page=1&size=10
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/vehicles?page=2&size=10
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/vehicles?page=3&size=10
- https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/vehicles/54



### **TODO | Mejoras**

1. Implementar filtrado por nombre. Quedo pendiente porque la construccion de las urls y el armado de los objetos varia bastante. Por sencillez y tiempo deje filtrado por ID.
2. Implementar el mostrado de informacion con respecto a la paginacion (pagina actual, next, previous, etc). Quedo pendiente
4. Implementar documentacion mas robusta (Swagger, por ej)

