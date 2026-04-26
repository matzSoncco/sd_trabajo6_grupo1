package ejemploAplicacion;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class DashboardGantt extends JFrame {
    private GanttFila f1, f2, f3;
    private JButton btnCargar, btnCancelar;
    private JLabel lblStatus, lblArchivo;
    private Thread hiloPrincipal;
    private int contadorExitosos = 0;

    /**
     * Constructor para el dashboard de Gantt
     * Configura la interfaz gráfica, los componentes y los eventos de los botones
     */
    public DashboardGantt() {
        setTitle("Procesador de Pagos con Control de Flujo");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        lblArchivo = new JLabel("Archivo: Ninguno seleccionado", SwingConstants.LEFT);
        lblStatus = new JLabel("Estado: Esperando acción...", SwingConstants.LEFT);

        f1 = new GanttFila("VALIDACIÓN");
        f2 = new GanttFila("EJECUCIÓN");
        f3 = new GanttFila("NOTIFICACIÓN");

        btnCargar = new JButton("Cargar Lote (.txt)");
        btnCancelar = new JButton("Cancelar Operación");
        btnCancelar.setEnabled(false);
        btnCancelar.setForeground(Color.RED);

        JPanel pInfo = new JPanel(new GridLayout(2, 1));
        pInfo.add(lblArchivo);
        pInfo.add(lblStatus);
        pInfo.setBorder(BorderFactory.createTitledBorder("Monitoreo de Archivo"));

        add(pInfo);
        add(f1);
        add(f2);
        add(f3);

        JPanel pBotones = new JPanel();
        pBotones.add(btnCargar);
        pBotones.add(btnCancelar);
        add(pBotones);

        btnCargar.addActionListener(e -> seleccionarArchivo());
        btnCancelar.addActionListener(e -> cancelarTodo());
    }

    /**
     * Método para seleccionar un archivo de texto que contiene las transacciones a procesar
     * Utiliza JFileChooser para abrir un diálogo de selección de archivos
     * y luego inicia el proceso de lote con el archivo seleccionado   
     */
    private void seleccionarArchivo() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            lblArchivo.setText("Archivo: " + fc.getSelectedFile().getName());
            iniciarLote(fc.getSelectedFile());
        }
    }

    /**
     * Método para iniciar el procesamiento del lote de transacciones a partir de un archivo
     * @param archivo - Archivo de texto que contiene las transacciones
     */
    private void iniciarLote(File archivo) {
        btnCargar.setEnabled(false);
        btnCancelar.setEnabled(true);
        contadorExitosos = 0;

        hiloPrincipal = new Thread(() -> {
            try {
                List<String> lineas = Files.readAllLines(archivo.toPath());
                for (String linea : lineas) {
                    if (Thread.interrupted())
                        break;

                    String[] d = linea.split(",");
                    Transaccion t = new Transaccion(d[0].trim(), Double.parseDouble(d[1].trim()));

                    lblStatus.setText("Procesando: " + t.getId());
                    f1.setProgreso(0);
                    f2.setProgreso(0);
                    f3.setProgreso(0);

                    ProcesoGantt p1 = new ProcesoGantt(f1, null, t);
                    ProcesoGantt p2 = new ProcesoGantt(f2, p1, t);
                    ProcesoGantt p3 = new ProcesoGantt(f3, p2, t);

                    p1.start();
                    p2.start();
                    p3.start();
                    p3.join();

                    if (t.getMonto() > 0)
                        contadorExitosos++;
                }
                finalizar("Procesamiento completado.\nTotal: " + contadorExitosos + " pagos.");
            } catch (Exception ex) {
                finalizar("Operación interrumpida por el usuario.");
            }
        });
        hiloPrincipal.start();
    }

    /**
     * Método para cancelar la operación en curso
     * Interrumpe el hilo principal lo que a su vez interrumpe todos los demás en ejecución
     */
    private void cancelarTodo() {
        if (hiloPrincipal != null) {
            hiloPrincipal.interrupt();
            lblStatus.setText("Estado: CANCELANDO...");
        }
    }

    /**
     * Método para finalizar el proceso y mostrar un mensaje al usuario
     * @param mensaje - Mensaje que se mostrará en el diálogo al finalizar el proceso
     */
    private void finalizar(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, mensaje);
            lblStatus.setText("Estado: Terminado.");
            btnCargar.setEnabled(true);
            btnCancelar.setEnabled(false);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardGantt().setVisible(true));
    }
}