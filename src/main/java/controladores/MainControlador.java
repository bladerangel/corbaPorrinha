package controladores;

import compilacaoIDL.Jogador;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import servicos.MainServico;

import java.net.URL;
import java.util.ResourceBundle;

public class MainControlador implements Initializable {

    @FXML
    private TextArea chat;

    @FXML
    private TextField texto;

    private MainServico mainServico;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainServico = new MainServico();
    }


    @FXML
    void enviarMensagem() {
        mainServico.getPartidaService().escreverChat(mainServico.getPartidaService().getJogador(), texto.getText());

    }

    @FXML
    void iniciarServidor(){
        try {
            mainServico.getComunicacaoServico().ativandoPOA();
            new Thread( () -> {
                mainServico.getComunicacaoServico().executandoORB();
            }).start();

        } catch (AdapterInactive adapterInactive) {
            adapterInactive.printStackTrace();
        }

    }


    //o jogador clica no para sair do jogo
    public void sairPartida() {
        //mainServico.getTabuleiroEnviarPacoteServico().enviarPacoteSairPartida();
    }
}
