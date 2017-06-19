package models;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LugarModelo extends VBox {

    private int numeroLugar;
    private Button cadeira;
    private Button mao;
    private Button adicionar;
    private Button remover;
    private Button turno;
    private ImageView imagemMao;
    private ImageView imageCadeira;
    private ImageView imagemAdicionar;
    private ImageView imagemRemover;
    private ImageView imagemTurno;
    private Text numeroLugarMesa;
    private Text quantidadePalitosRestantes;

    private HBox acoes;

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

        acoes = new HBox();
        adicionar = new Button();
        imagemAdicionar = new ImageView();
        imagemAdicionar.getStyleClass().add("adicionar");
        adicionar.setGraphic(imagemAdicionar);

        acoes.getChildren().add(adicionar);

        remover = new Button();
        imagemRemover = new ImageView();
        imagemRemover.getStyleClass().add("remover");
        remover.setGraphic(imagemRemover);

        acoes.getChildren().add(remover);

        turno = new Button();
        imagemTurno = new ImageView();
        imagemTurno.getStyleClass().add("turno");
        turno.setGraphic(imagemTurno);

        acoes.getChildren().add(turno);
        acoes.setVisible(false);
        acoes.setAlignment(Pos.CENTER);

        numeroLugarMesa = new Text();
        numeroLugarMesa.setText("Lugar: " + numeroLugar);

        quantidadePalitosRestantes = new Text();
        quantidadePalitosRestantes.setVisible(false);

        this.getChildren().add(cadeira);
        this.getChildren().add(mao);
        this.getChildren().add(acoes);
        this.getChildren().add(numeroLugarMesa);
        this.getChildren().add(quantidadePalitosRestantes);
        this.setAlignment(Pos.CENTER);
        this.setRotate(rotacao);
    }

    public Button getCadeira() {
        return cadeira;
    }

    public Button getMao() {
        return mao;
    }

    public Button getAdicionar() {
        return adicionar;
    }

    public HBox getAcoes() {
        return acoes;
    }

    public Text getQuantidadePalitosRestantes() {
        return quantidadePalitosRestantes;
    }

    public int getNumeroLugar() {
        return numeroLugar;
    }
}
