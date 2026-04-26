package ejemploAplicacion;

public class ProcesoGantt extends Thread {
    private GanttFila fila;
    private Thread hiloAnterior;
    private Transaccion t;

    /**
     * Constructor para el proceso de Gantt
     * @param fila - Componente gráfico asociado a esta etapa del proceso
     * @param hiloAnterior - hilo que debe finalizar antes de iniciar este proceso
     * @param t - Transacción que se está procesando, para marcarla como procesada al finalizar
     */
    public ProcesoGantt(GanttFila fila, Thread hiloAnterior, Transaccion t) {
        this.fila = fila;
        this.hiloAnterior = hiloAnterior;
        this.t = t;
    }

    /**
     * Método principal del hilo que simula el procesamiento de una etapa del proceso de pago
     */
    @Override
    public void run() {
        try {
            if (hiloAnterior != null)
                hiloAnterior.join();

            if (Thread.currentThread().isInterrupted())
                return;

            for (int i = 0; i <= 100; i += 10) {
                if (Thread.currentThread().isInterrupted())
                    break;

                ejecutarCalculoPesado();

                Thread.sleep(150); // Tiempo de latencia de red simulado
                fila.setProgreso(i);
            }

            fila.setCompletado(true);
            t.setProcesada(true);

        } catch (InterruptedException e) {
            System.out.println("Hilo interrumpido.");
        }
    }

    @SuppressWarnings("unused")
    /**
     * Método que simula un cálculo pesado para representar el tiempo de procesamiento real de cada etapa
     */
    private void ejecutarCalculoPesado() {
        long dummy = 0;
        for(int j=0; j<1000000; j++) { dummy += j; }
    }
}