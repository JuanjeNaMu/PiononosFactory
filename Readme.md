Errores mas comunes con http y rest: 
<br>  <br>
200	OK	La respuesta estándar para solicitudes GET, PUT, POST y DELETE exitosas (aunque hay mejores opciones para PUT, POST y DELETE).  <br>
201	Created	La solicitud fue exitosa y se ha creado un nuevo recurso. Se usa comúnmente después de una operación POST. <br> 
204	No Content	La solicitud fue exitosa, pero la respuesta no contiene cuerpo (no hay contenido para devolver). Comúnmente usado para DELETE o PUT cuando no se necesita enviar datos de vuelta.  <br>
400	Bad Request	La solicitud no se pudo procesar debido a un error de sintaxis o datos no válidos (por ejemplo, el JSON enviado tiene un formato incorrecto).  <br>
401	Unauthorized	Se requiere autenticación (credenciales) para acceder al recurso, o la autenticación proporcionada no es válida.  <br>
403	Forbidden	La solicitud es válida, pero el servidor rehúsa autorizar el acceso al recurso (el cliente no tiene los permisos necesarios, aunque haya iniciado sesión).  <br>
404	Not Found	El recurso solicitado no existe en el servidor.  <br>
405	Method Not Allowed	El método HTTP utilizado (GET, POST, PUT, DELETE, etc.) no es compatible con el recurso al que se intenta acceder.  <br>
409	Conflict	La solicitud no pudo completarse debido a un conflicto con el estado actual del recurso (por ejemplo, intentar eliminar un usuario que tiene registros dependientes).  <br>
422	Unprocessable Entity	La solicitud está bien formada, pero contiene errores semánticos que impiden su procesamiento (por ejemplo, la validación de datos falló).  <br>
500	Internal Server Error	Un error genérico inesperado en el servidor que impide que la solicitud se complete con éxito.  <br>
503 Service Unavailable El servidor no está disponible para manejar la solicitud en ese momento (a menudo debido a mantenimiento o sobrecarga temporal)  <br>