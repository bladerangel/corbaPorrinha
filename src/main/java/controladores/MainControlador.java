package controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.LugarModelo;
import servicos.LugaresService;
import servicos.PartidaService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainControlador implements Initializable {

    @FXML
    private TextArea chat;

    @FXML
    private TextField texto;

    @FXML
    private GridPane lugares;

    private List<LugarModelo> listaLugares;

    private LugaresService lugaresService;

    private PartidaService partidaService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {



        listaLugares = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            listaLugares.add(new LugarModelo(i, 90 * (i - 1)));
        }

        lugares.add(listaLugares.get(0), 1, 2);
        lugares.add(listaLugares.get(1), 0, 1);
        lugares.add(listaLugares.get(2), 1, 0);
        lugares.add(listaLugares.get(3), 2, 1);

        lugaresService = new LugaresService(listaLugares);

        // partidaService = new PartidaService(chat);
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
