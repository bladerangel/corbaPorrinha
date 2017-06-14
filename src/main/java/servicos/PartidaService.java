package servicos;

import compilacaoIDL.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.omg.CORBA.Object;


public class PartidaService extends AcoesJogadorPOA {

    private ComunicacaoServico comunicacaoServico;

    private Jogador jogador;

    private Servidor servidor;

    private TextArea chat;

    public PartidaService(TextArea chat) {
        this.chat = chat;
        jogador = new Jogador("Jogador1", 3, 0);
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

    @Override
    public void sairPartida(Jogador jogador) {

    }
}
