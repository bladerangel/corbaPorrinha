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
    private Button palpite;
    private ImageView imagemMao;
    private ImageView imageCadeira;
    private ImageView imagemAdicionar;
    private ImageView imagemRemover;
    private ImageView imagemPalpite;
    private Text nomeJogador;
    private Text numeroLugarMesa;
    private Text quantidadePalitosRestantes;
    private Text numeroPalpite;

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

        palpite = new Button();
        imagemPalpite = new ImageView();
        imagemPalpite.getStyleClass().add("palpite");
        palpite.setGraphic(imagemPalpite);

        acoes.getChildren().add(palpite);
        acoes.setVisible(false);
        acoes.setAlignment(Pos.CENTER);

        nomeJogador = new Text();
        nomeJogador.setVisible(false);

        numeroLugarMesa = new Text();
        numeroLugarMesa.setText("Lugar: " + numeroLugar);

        quantidadePalitosRestantes = new Text();
        quantidadePalitosRestantes.setVisible(false);

        numeroPalpite = new Text();
        numeroPalpite.setVisible(false);

        this.getChildren().add(cadeira);
        this.getChildren().add(numeroPalpite);
        this.getChildren().add(mao);
        this.getChildren().add(acoes);
        this.getChildren().add(nomeJogador);
        this.getChildren().add(numeroLugarMesa);
        this.getChildren().add(quantidadePalitosRestantes);
        this.setAlignment(Pos.CENTER);
        this.setRotate(rotacao);
    }

    public Text getNomeJogador() {
        return nomeJogador;
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

    public Button getRemover() {
        return remover;
    }

    public Button getPalpite() {
        return palpite;
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

    public Text getNumeroPalpite() {
        return numeroPalpite;
    }
}
