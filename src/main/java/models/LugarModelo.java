package models;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class LugarModelo extends VBox {

    private int numeroLugar;
    private Button cadeira;
    private Button mao;
    private ImageView imagemMao;
    private ImageView imageCadeira;
    private Button adicionarPalito;


    public LugarModelo(int numeroLugar, int rotacao) {

        this.numeroLugar = numeroLugar;

        cadeira = new Button();
        imageCadeira = new ImageView();
        imageCadeira.getStyleClass().add("cadeira");
        cadeira.setGraphic(imageCadeira);

        mao = new Button();
        imagemMao = new ImageView();
        imagemMao.getStyleClass().add("mao-aberta");
        mao.setText("1 palito");
        mao.setGraphic(imagemMao);
        mao.setVisible(false);


        adicionarPalito = new Button();
        adicionarPalito.setText("Adicionar Palito");
        adicionarPalito.setVisible(false);

        this.getChildren().add(cadeira);
        this.getChildren().add(mao);
        this.getChildren().add(adicionarPalito);
        this.setAlignment(Pos.CENTER);
        this.setRotate(rotacao);
    }
}
