package servicos;

import compilacaoIDL.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.LugarModelo;
import org.omg.CORBA.Object;

import java.util.List;


public class PartidaService extends EventosPOA {

    private ComunicacaoServico comunicacaoServico;


    private Jogador jogador;

    private Servidor servidor;

    private TextArea chat;

    private List<LugarModelo> listaLugares;

    public PartidaService(TextArea chat, List<LugarModelo> listaLugares) {
        this.listaLugares = listaLugares;
        listaLugares.forEach(lugar -> {
            lugar.getCadeira().setOnMouseClicked(event -> {
                sentar(lugar, (Button) event.getSource());
            });
        });
        this.chat = chat;
        jogador = new Jogador("Jogador6", 0, 3, 0);
        try {
            comunicacaoServico = new ComunicacaoServico();
            comunicacaoServico.obtendoRootPOA();
            comunicacaoServico.ativandoPOA();
            comunicacaoServico.obtendoServidorNomes();
            comunicacaoServico.criandoNome(this, jogador.nome, "text");
            Object objeto = comunicacaoServico.localizandoNome("Servidor", "text");
            servidor = ServidorHelper.narrow(objeto);
            entrarPartida(jogador);
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
