package controladores;

import javafx.fxml.Initializable;
import servicos.MainServico;

import java.net.URL;
import java.util.ResourceBundle;

public class MainControlador implements Initializable {

    private MainServico mainServico;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainServico = new MainServico();
    }

    //o jogador clica no para sair do jogo
    public void sairPartida() {
        //mainServico.getTabuleiroEnviarPacoteServico().enviarPacoteSairPartida();
    }
}
