import javax.swing.SwingUtilities;
import view.FrmEmpleado;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmEmpleado().setVisible(true));
    }   
}