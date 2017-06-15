package servicos;

import javafx.scene.control.Button;
import models.LugarModelo;

import java.util.List;

public class LugaresService {

    private List<LugarModelo> listaLugares;

    public LugaresService(List<LugarModelo> listaLugares) {
        this.listaLugares = listaLugares;
        listaLugares.forEach(lugar -> {
            lugar.getCadeira().setOnMouseClicked(event -> {
                sentar(lugar, (Button) event.getSource());
            });
        });
    }

    public void sentar(LugarModelo lugar, Button cadeira) {
        cadeira.setVisible(false);
        lugar.getMao().setVisible(true);
        lugar.getAdicionarPalito().setVisible(true);

    }
}
