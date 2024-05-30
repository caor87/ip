# IP Tracker

Esta aplicación permite determinar el país al que pertenece una dirección IP y proporciona la siguiente información:
- Nombre y código ISO del país.
- Idiomas oficiales del país.
- Hora(s) actual(es) en el país (si el país cubre más de una zona horaria, se muestran todas).
- Distancia estimada entre Buenos Aires y el país, en km.
- Moneda local y su cotización actual en dólares (si está disponible).

Además, la aplicación proporciona estadísticas sobre el uso del servicio, como la distancia más lejana y más cercana desde la cual se ha consultado el servicio y la distancia promedio de todas las consultas.

## APIs Utilizadas
- [Geolocalización de IPs](https://api.ipgeolocation.io/)
- [Información sobre monedas](https://open.er-api.com/v6/latest/USD/)

## Requisitos
- Java 17
- Maven
- Docker

## Compilación y Ejecución

### Compilación del Proyecto

1. Clonar el repositorio:

    ```sh
    git clone https://github.com/tu-usuario/ip-tracker.git
    cd ip-tracker
    ```

2. Compilar el proyecto usando Maven:

    ```sh
    mvn clean package
    ```

### Construcción y Ejecución con Docker

1. Construir la imagen Docker:

    ```sh
    docker build -t ip-tracker .
    ```

2. Ejecutar el contenedor Docker:

    ```sh
    docker run -p 8080:8080 ip-tracker
    ```

### Uso de la Aplicación

1. Abre tu navegador y accede a:

    ```sh
    http://localhost:8080/traceip?ip=83.44.196.93
    ```

    Esto devolverá información sobre la dirección IP proporcionada.

2. Para ver las estadísticas de las consultas realizadas, accede a:

    ```sh
    http://localhost:8080/stats
    ```

## Estructura del Proyecto# ip
