package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientController implements ActionListener {

    ClientWindow clientWindow;

    public ClientController(ClientWindow clientWindow) {
        this.clientWindow = clientWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
