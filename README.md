# conexa-technical-challenge

La aplicacion es una API Rest que se integra con la API de Star Wars (https://www.swapi.tech/documentation). La API permite el listado de Vehicles, Starships, People y Films, de manera paginada. Tambien permite filtrar por el ID de las entidades para obtener informacion en detalle de cada una.

La misma esta basada en una arquitectura simple y limpia por capas. Una capa de DTO, para modelar y manejar respuestas del sistema hacia el exterior, Una capa de dominio, para implementar logica de negocio y operaciones. Y una capa de repositorio que es la que se termina conectando con la API externa de Star Wars y maneja las respuestas mismas de la API y las modela en obtejos propios del sistema. Se utiliza MapStruct para la conversion entre los objetos de las distintas capas del sistema.

Asimismo, posee un sistema de autenticacion simple con JWT. 

Para el facil testeo de la app, agregue un swagger para que se puedan testear los endpoints.

# **URL**

https://unchanged-karlie-matiasbragadini-ef7121c6.koyeb.app/conexa-api/v1/swagger-ui/index.html#


## **Login - POST**

Para poder autenticarse y que el resto de los endpoints no devuelvan un 401 (Unauthorized), se debe ejecutar primero el POST de login, con las credenciales username = conexa y password = challenge. Dicha ejecucion devuelve un bearer token que vamos a copiar y luego vamos a ingresar
en la parte superior de Swagger -> Authorize. Una vez que se hizo esto, vamos a poder acceder al resto de los endpoints.

