package utilitarios;

import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class NomeDialogo {

    private static TextInputDialog dialog;
    private static Optional<String> resultado;

    public static String nomeDialogo(String titulo, String cabecalho, String conteudo) {
        dialog = new TextInputDialog();
        dialog.setTitle(titulo);
        dialog.setHeaderText(cabecalho);
        dialog.setContentText(conteudo);
        resultado = dialog.showAndWait();
        if (!resultado.isPresent()) {
            Platform.exit();
            System.exit(0);
        }
        return resultado.get();
    }
}
