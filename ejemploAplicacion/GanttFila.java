package ejemploAplicacion;

import javax.swing.*;
import java.awt.*;

public class GanttFila extends JPanel {
    private JProgressBar barra;
    private JLabel etiqueta;

    /**
     * Constructor para la fila del Gantt
     * @param nombreEtapa - Nombre de la etapa del proceso que se mostrará en la etiqueta
     */
    public GanttFila(String nombreEtapa) {
        setLayout(new BorderLayout(10, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        etiqueta = new JLabel(nombreEtapa);
        etiqueta.setPreferredSize(new Dimension(120, 30));
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 12));

        barra = new JProgressBar(0, 100);
        barra.setStringPainted(true);
        barra.setForeground(new Color(70, 130, 180));
        barra.setBackground(new Color(230, 230, 230));

        add(etiqueta, BorderLayout.WEST);
        add(barra, BorderLayout.CENTER);
    }

    /**
     * Método para actualizar el progreso de la barra
     * @param valor - Valor del progreso (0 a 100)
     */
    public void setProgreso(int valor) {
        barra.setValue(valor);
    }

    /**
     * Método para marcar la fila como completada
     * @param completado - Indica si la fila está completada
     */
    public void setCompletado(boolean completado) {
        if (completado)
            barra.setForeground(new Color(46, 139, 87));
    }
}