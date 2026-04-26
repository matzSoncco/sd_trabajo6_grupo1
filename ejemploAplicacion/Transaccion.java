package ejemploAplicacion;

@SuppressWarnings("unused")
/**
 * Clase simple que representa una transacción financiera
 */
public class Transaccion {
    private String id;
    private double monto;
    private boolean procesada = false;

    public Transaccion(String id, double monto) {
        this.id = id;
        this.monto = monto;
    }

    public String getId() {
        return id;
    }

    public double getMonto() {
        return monto;
    }

    public void setProcesada(boolean p) {
        this.procesada = p;
    }
}
