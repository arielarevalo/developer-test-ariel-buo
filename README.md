Se dejaron disponibles branches para cada ejercicio completado.
Además, se deja un branch para la solución del Ejercicio 2, realizada fuera del tiempo de la prueba, basada en las observaciones dejadas abajo durante la prueba.

Ejercicio #1:
El ejercicio se llevó a cabo sin problemas.

Ejercicio #2:
Aunque se logró implementar la funcionalidad solicitada, no se tenía experiencia previa trabajando con concurrencia explícita dentro de Spring. La mayoría del tiempo total de la prueba se utilizó intentando depurar un error de concurrencia. Hacia el final de toda la prueba se descubrió que la forma adecuada de trabajar concurrentemente en Spring es haciendo uso, no del `ExecutorService` de Java, sino del `TaskExecutor` que ofrece Spring. Descubierto eso, se volvió evidente cuál hubiera sido la forma adecuada de estructurar las clases, implementando `Runnable` en las clases productor y consumidor, y armando un `AsynchService` que ejecutara la cantidad deseada de estos anteriores utilizando el `TaskExecutor` después de su inicialización. Esto anterior también aseguraría que se poblaran adecuadamente los valores de propiedad antes de iniciar la fase concurrente, lo cual lleva al próxima ejercicio.

NOTAS DE SOLUCIÓN:
La aplicación no corría al agregarse los valores de propiedades ya que se iniciaban los semáforos con un valor de cero. Esto debido a que `@Value` no pobla los valores si no hasta después de que corren los constructores para los beans. Una vez solucionado eso, no da problemas.

Ejercicio #3: No se tenía ninguna experiencia trabajando con servidores de configuración en Spring específicamente. Todavía existe ambigüedad sobre qué exactamente era lo deseado a nivel de la instrucción, particularmente en cuanto a la conexión con Github, pero ultimadamente este ejercicio se inició sin conocimiento previo con una hora de tiempo disponible. Una vez que se hizo uso de la anotación `@Value` para poblar los valores de la configuración, el trabajo previamente funcional del ejercicio 2 paró de funcionar. Se debió haber trabajado con feature branches desde el inicio, ya que el intento de rescatar la funcionalidad del ejercicio anterior terminó costando algo de tiempo.