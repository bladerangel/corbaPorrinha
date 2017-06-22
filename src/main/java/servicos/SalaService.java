package servicos;

import compilacaoIDL.*;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import models.LugarModelo;
import org.omg.CORBA.Object;
import utilitarios.JanelaAlerta;
import utilitarios.NomeDialogo;

import java.util.List;


public class SalaService extends ClientePOA {

    private ComunicacaoServico comunicacaoServico;

    private String nomeJogador;

    private Servidor servidor;

    private TextArea chat;

    private List<LugarModelo> listaLugares;

    public SalaService(TextArea chat, List<LugarModelo> listaLugares) {
        this.listaLugares = listaLugares;
        carregarEventosLugares();
        this.chat = chat;
        try {
            comunicacaoServico = new ComunicacaoServico();
            comunicacaoServico.obtendoRootPOA();
            comunicacaoServico.ativandoPOA();
            comunicacaoServico.obtendoServidorNomes();
            Object objeto = comunicacaoServico.localizandoNome("Servidor", "text");
            servidor = ServidorHelper.narrow(objeto);
            enviarRequisicaoPerguntarNome();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //faz o carregamento dos eventos dos lugares
    private void carregarEventosLugares() {
        listaLugares.forEach(lugar -> {
            lugar.getCadeira().setOnMouseClicked(evento -> enviarRequisicaoSentar(lugar));
            lugar.getMao().setOnMouseClicked(evento -> enviarRequisicaoApostar(lugar.getNumeroLugar()));
            lugar.getAdicionar().setOnMouseClicked(evento -> enviarRequisicaoAdicionar());
            lugar.getRemover().setOnMouseClicked(evento -> enviarRequisicaoRemover());
            lugar.getPalpite().setOnMouseClicked(evento -> enviarRequisicaoPalpite());
        });
    }

    //janela inicial de pergunta do nome do jogador
    private void enviarRequisicaoPerguntarNome() {
        try {
            String nome = NomeDialogo.nomeDialogo(null, "Informe o nome do jogador", "Digite o nome do jogador:", true);
            if (servidor.verificarNomeJogador(nome)) {
                nomeJogador = nome;
                comunicacaoServico.criandoNome(this, nome, "text");
                servidor.adicionarJogador(nome);
                servidor.atualizarLugares();
            } else {
                JanelaAlerta.janelaAlerta(null, "Este nome já existe/inválido!", null);
                enviarRequisicaoPerguntarNome();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //escolher o lugar aonde o jogador quer sentar
    private void enviarRequisicaoSentar(LugarModelo lugar) {
        if (servidor.verificarLugar(nomeJogador, lugar.getNumeroLugar())) {
            lugar.getAcoes().setVisible(true);
            lugar.getQuantidadePalitosRestantes().setVisible(true);
            lugar.getQuantidadePalitosRestantes().setText("Palitos Restantes: " + servidor.getJogador(nomeJogador).quantidadePalitosRestantes + "");
        }
    }

    //jogador recebe requisicao do servidor e atualiza o posicao de sentar do jogador
    @Override
    public void sentar(String nome, int lugar) {
        LugarModelo lugarModelo = listaLugares.get(lugar - 1);
        lugarModelo.getCadeira().setVisible(false);
        lugarModelo.getMao().setVisible(true);
        if (servidor.getJogador(nome).apostou) {
            lugarModelo.getMao().getGraphic().getStyleClass().remove("mao-aberta");
            lugarModelo.getMao().getGraphic().getStyleClass().add("mao-fechada");
        }
        lugarModelo.getNomeJogador().setVisible(true);
        lugarModelo.getNomeJogador().setText("Jogador: " + nome);
    }

    //jogador recebe requisicao para escrever no chat
    @Override
    public void escreverMensagem(String mensagem) {
        chat.appendText(mensagem);
    }

    //jogador envia uma mensagem
    public void enviarRequisicaoAtualizarChat(String mensagem) {
        servidor.enviarMensagem(nomeJogador, mensagem);
    }

    //metodo auxiliar para atualizar a numeracao dos palitos
    private void atualizarPalitos() {
        Jogador jogador = servidor.getJogador(nomeJogador);
        LugarModelo lugarModelo = listaLugares.get(jogador.lugar - 1);
        lugarModelo.getMao().setText(jogador.quantidadePalitosApostados + " Palitos");
        lugarModelo.getQuantidadePalitosRestantes().setText("Palitos Restante: " + jogador.quantidadePalitosRestantes + "");
    }

    //jogador adiciona um palito da sua mao
    private void enviarRequisicaoAdicionar() {
        servidor.adicionarPalito(nomeJogador);
        atualizarPalitos();
    }

    //jogador remove um palito da sua mao
    private void enviarRequisicaoRemover() {
        servidor.removerPalito(nomeJogador);
        atualizarPalitos();
    }

    //jogador faz uma aposta
    private void enviarRequisicaoApostar(int lugar) {
        if (servidor.getJogador(nomeJogador).lugar == lugar)
            servidor.apostar(nomeJogador);
    }

    //jogador recebe a requisicao da aposta feita
    @Override
    public void apostar(int lugar) {
        LugarModelo lugarModelo = listaLugares.get(lugar - 1);
        lugarModelo.getMao().getGraphic().getStyleClass().remove("mao-aberta");
        lugarModelo.getMao().getGraphic().getStyleClass().add("mao-fechada");
    }

    //jogador da um palpite
    private void enviarRequisicaoPalpite() {
        if (servidor.verificarPalpitar(nomeJogador)) {
            String palpite = NomeDialogo.nomeDialogo(null, "Informe o seu palpite", "Digite o seu palpite:", false);
            if (palpite != null && !servidor.palpitar(nomeJogador, Integer.parseInt(palpite))) {
                JanelaAlerta.janelaAlerta(null, "Este palpite já foi dado!", null);
            }
        }
    }

    //jogador recebe a requisicao da do palpite feito
    @Override
    public void palpite(int lugar, int palpite) {
        LugarModelo lugarModelo = listaLugares.get(lugar - 1);
        lugarModelo.getNumeroPalpite().setVisible(true);
        lugarModelo.getNumeroPalpite().setText("Palpite: " + palpite);
    }

    //jogador recebe a requisicao para reiniciar a rodada
    @Override
    public void reiniciarRodada(int lugar, int quantidadePalitosRestantes) {
        LugarModelo lugarModelo = listaLugares.get(lugar - 1);
        lugarModelo.getNumeroPalpite().setVisible(false);
        Platform.runLater(() -> lugarModelo.getMao().setText(""));
        lugarModelo.getMao().getGraphic().getStyleClass().remove("mao-fechada");
        lugarModelo.getMao().getGraphic().getStyleClass().add("mao-aberta");
        lugarModelo.getQuantidadePalitosRestantes().setText("Palitos Restante: " + quantidadePalitosRestantes);
    }

    //jogador saiu da sala
    public void enviarRequisicaoSairSala() {
        servidor.removerJogador(nomeJogador);
    }

    //jogador recebe a requisicao do jogador que saiu da sala
    @Override
    public void sairSala(int lugar) {
        LugarModelo lugarModelo = listaLugares.get(lugar - 1);
        lugarModelo.getCadeira().setVisible(true);
        lugarModelo.getMao().setVisible(false);
    }
}
