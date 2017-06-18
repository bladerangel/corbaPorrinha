package servicos;

import compilacaoIDL.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.LugarModelo;
import org.omg.CORBA.Object;
import utilitarios.JanelaAlerta;
import utilitarios.NomeDialogo;

import java.util.List;


public class PartidaService extends EventosPOA {

    private ComunicacaoServico comunicacaoServico;


    private Jogador jogador;

    private Servidor servidor;

    private TextArea chat;

    private List<LugarModelo> listaLugares;

    public PartidaService(TextArea chat, List<LugarModelo> listaLugares) {
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
            perguntarNome();
            entrarPartida(jogador);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregarEventosLugares() {
        listaLugares.forEach(lugar -> {
            lugar.getCadeira().setOnMouseClicked(event -> {
                sentar(lugar, (Button) event.getSource());
            });
        });
    }

    public void perguntarNome() {
        try {
            String nome = NomeDialogo.nomeDialogo(null, "Informe o nome do jogador", "Digite o nome do jogador:");
            if (servidor.verificarNomeJogador(nome)) {
                jogador = new Jogador(nome, 0, 3, 0);
                comunicacaoServico.criandoNome(this, nome, "text");
            } else {
                JanelaAlerta.janelaAlerta(null, "Este nome já existe!", null);
                perguntarNome();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sentar(LugarModelo lugar, Button cadeira) {
        if (jogador.lugar == 0) {
            if (servidor.verificarLugar(jogador, lugar.getNumeroLugar())) {
                jogador.lugar = lugar.getNumeroLugar();
                lugar.getAdicionarPalito().setVisible(true);
            }
        }
        System.out.println(jogador.lugar);
    }

    @Override
    public void sentar(int lugar) {
        LugarModelo lugarModelo = listaLugares.stream().filter(l -> l.getNumeroLugar() == lugar).findFirst().get();
        lugarModelo.getCadeira().setVisible(false);
        lugarModelo.getMao().setVisible(true);

    }


    @Override
    public void enviarMensagem(String mensagem) {
        chat.appendText(mensagem);
    }

    public void enviarRequisicaoServidor(String mensagem) {
        try {
            servidor.atualizarChat(jogador, mensagem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void esolherQuantidadePalitos(Jogador jogador, int quantidadePalitos) {

    }

    @Override
    public void apostar(Jogador jogador, int quantidadePalitos) {

    }

    @Override
    public void entrarPartida(Jogador jogador) {
        servidor.adicionarJogador(jogador);
    }

    public void sairPartida() {
        servidor.removerJogador(jogador);
    }

    @Override
    public void sair(int lugar) {
        LugarModelo lugarModelo = listaLugares.stream().filter(l -> l.getNumeroLugar() == lugar).findFirst().get();
        lugarModelo.getCadeira().setVisible(true);
        lugarModelo.getMao().setVisible(false);
    }
}
