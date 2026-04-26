# Simulador de Ciclo de Vida de Pagos (Hilos)

Este proyecto es una **simulación académica** desarrollada para la asignatura de **Sistemas Distribuidos**

## Conceptos Importantes
Para la defensa del proyecto, lo más importante es entender cómo se aplican estos tres pilares:

1.  **Sincronización por Relevos (`join()`):** El sistema garantiza que una etapa (ej. Ejecución) no inicie hasta que la anterior (ej. Validación) haya terminado. Es el concepto de "testigo" en una carrera de relevos que se ha mostrado en el repositorio de ejemplo proporcionado por el docente
2.  **Carga de Trabajo Real:** A diferencia de un loader estético, las barras de progreso responden a un bucle de procesamiento que consume ciclos de CPU, simulando el costo computacional de una firma digital o validación criptográfica, en este caso usamos una variable `dummyWork` que consume CPU del sistema y simula algun proceso en el background
3.  **Control de Interrupciones:** El sistema permite cancelar el procesamiento de un lote completo de forma segura, utilizando señales de interrupción nativas de Java para detener los hilos de inmediato sin dejar procesos "huérfanos"

## Estructura de Clases
* `DashboardGantt.java`: Orquestador de la interfaz y control de eventos
* `ProcesoGantt.java`: Lógica del hilo. Implementa el `join()` y la simulación de carga
* `GanttFila.java`: Representación visual (UI) de cada etapa del pipeline
* `Transaccion.java`: Objeto de negocio que transporta los datos del pago

## Ejecución

### Requisitos
* Tener instalado el **JDK 8** o superior.
* Contar con un archivo, podría ser `pagos.txt` (formato: `ID_PAGO, MONTO`)

### Pasos
1.  **Compilar:** En una terminal en la carpeta `ejemploAplicacion` del proyecto se ejecuta:
    ```bash
    javac *.java
    ```
2.  **Iniciar:** Ejecuta la clase principal:
    ```bash
    java ejemploAplicacion.DashboardGantt
    ```
3.  **Procesar:** Haz clic en **"Cargar Lote"**, selecciona tu archivo `.txt`
