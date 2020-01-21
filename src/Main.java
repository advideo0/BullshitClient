import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(info.getClassName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }

        JTextField server = new JTextField();
        JTextField name = new JTextField();
        Object[] message = {
                "Server:", server,
                "Name:", name
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {

            Socket socket = new Socket(server.getText(), 3232);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            GameController gameController = new GameController(oos, name.getText());

            Thread isThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ObjectInputStream ois = null;
                    try {
                        ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

                        while(true) {
                            try {
                                gameController.readInput((ArrayList<String>)ois.readObject());
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            isThread.start();

        }
    }
}
