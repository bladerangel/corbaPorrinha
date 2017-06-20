package servicos;

import compilacaoIDL.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jdk.nashorn.internal.scripts.JO;
import models.LugarModelo;
import org.omg.CORBA.Object;
import utilitarios.JanelaAlerta;
import utilitarios.NomeDialogo;

import java.util.List;


public class SalaService extends EventosPOA {

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

    public void carregarEventosLugares() {
        listaLugares.forEach(lugar -> {
            lugar.getCadeira().setOnMouseClicked(event -> enviarRequisicaoSentar(lugar));
            lugar.getMao().setOnMouseClicked(event -> enviarRequisicaoApostar());
            lugar.getAdicionar().setOnMouseClicked(event -> enviarRequisicaoAdicionar());
            lugar.getRemover().setOnMouseClicked(event -> enviarRequisicaoRemover());
            lugar.getPalpite().setOnMouseClicked(event -> enviarRequisicaoPalpite());
        });
    }

    public void enviarRequisicaoPerguntarNome() {
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

    public void enviarRequisicaoSentar(LugarModelo lugar) {
        if (servidor.verificarLugar(nomeJogador, lugar.getNumeroLugar())) {
            lugar.getAcoes().setVisible(true);
            lugar.getQuantidadePalitosRestantes().setVisible(true);
            lugar.getQuantidadePalitosRestantes().setText("Palitos Restante: " + servidor.getJogador(nomeJogador).quantidadePalitosRestantes + "");
        }
    }

    @Override
    public void sentar(String nome, int lugar) {
        LugarModelo lugarModelo = listaLugares.get(lugar - 1);
        lugarModelo.getCadeira().setVisible(false);
        lugarModelo.getMao().setVisible(true);
        lugarModelo.getNomeJogador().setVisible(true);
        lugarModelo.getNomeJogador().setText("Jogador: " + nome);
    }


    @Override
    public void enviarMensagem(String mensagem) {
        chat.appendText(mensagem);
    }

    public void enviarRequisicaoAtualizarChat(String mensagem) {
        try {
            servidor.atualizarChat(nomeJogador, mensagem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarRequisicaoAdicionar() {
        servidor.adicionarPalito(nomeJogador);
        Jogador jogador = servidor.getJogador(nomeJogador);
        listaLugares.get(jogador.lugar - 1).getMao().setText(jogador.quantidadePalitosApostados + " Palitos");
        listaLugares.get(jogador.lugar - 1).getQuantidadePalitosRestantes().setText("Palitos Restante: " + jogador.quantidadePalitosRestantes + "");
    }

    public void enviarRequisicaoRemover() {
        servidor.removerPalito(nomeJogador);
        Jogador jogador = servidor.getJogador(nomeJogador);
        listaLugares.get(jogador.lugar - 1).getMao().setText(jogador.quantidadePalitosApostados + " Palitos");
        listaLugares.get(jogador.lugar - 1).getQuantidadePalitosRestantes().setText("Palitos Restante: " + jogador.quantidadePalitosRestantes + "");
    }

    public void enviarRequisicaoApostar() {
        Jogador jogador = servidor.getJogador(nomeJogador);
        if (servidor.apostar(nomeJogador)) {
            //listaLugares.get(jogador.lugar - 1).getAcoes().setVisible(false);
        }
    }

    public void enviarRequisicaoPalpite() {
        String palpite = NomeDialogo.nomeDialogo(null, "Informe o seu palpite", "Digite o seu palpite:", false);
        servidor.palpitar(nomeJogador, Integer.parseInt(palpite));
    }

    @Override
    public void esolherQuantidadePalitos(int quantidadePalitos) {

    }

    @Override
    public void apostar(int lugar) {
        listaLugares.get(lugar - 1).getMao().getGraphic().getStyleClass().add("mao-fechada");
    }

    @Override
    public void entrarSala() {

    }

    public void enviarRequisicaoSairSala() {
        servidor.removerJogador(nomeJogador);
    }

    @Override
    public void sairSala(int lugar) {
        LugarModelo lugarModelo = listaLugares.get(lugar - 1);
        lugarModelo.getCadeira().setVisible(true);
        lugarModelo.getMao().setVisible(false);
    }
}
