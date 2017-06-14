package controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import servicos.PartidaService;

import java.net.URL;
import java.util.ResourceBundle;

public class MainControlador implements Initializable {

    @FXML
    private TextArea chat;

    @FXML
    private TextField texto;

    private PartidaService partidaService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partidaService = new PartidaService(chat);
    }

    @FXML
    void enviarMensagem() {
        partidaService.enviarRequisicaoServidor(texto.getText());
    }

    //o jogador clica no para sair do jogo
    public void sairPartida() {
        //mainServico.getTabuleiroEnviarPacoteServico().enviarPacoteSairPartida();
    }
}
