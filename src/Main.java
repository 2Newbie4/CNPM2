import javax.swing.SwingUtilities;
import view.LoginFrm;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrm().setVisible(true);
        });
    }
}
