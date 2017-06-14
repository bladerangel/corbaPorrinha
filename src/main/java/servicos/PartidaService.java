package servicos;

import compilacaoIDL.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import java.util.ArrayList;
import java.util.List;

public class PartidaService extends PartidaPOA {

    private TextArea chat;

    private TextField texto;

    private ComunicacaoServico comunicacaoServico;
    private Jogador jogador;
    private List<Jogador> jogadores;


    public Jogador getJogador() {
        return jogador;
    }

    public PartidaService(TextArea chat, TextField texto, ComunicacaoServico comunicacaoServico) {
        this.chat = chat;
        this.texto = texto;
        this.comunicacaoServico = comunicacaoServico;
        jogador = new Jogador("Jogador1", 3);
        jogadores = new ArrayList<>();
        try {
            comunicacaoServico.criandoNome(this, jogador.nome, "text");
            entrar(jogador);
        } catch (ServantNotActive | WrongPolicy | CannotProceed | InvalidName | NotFound servantNotActive) {
            servantNotActive.printStackTrace();
        }

    }

    @Override
    public Jogador[] getJogadores() {
        return new Jogador[0];
    }

    @Override
    public boolean verificarNomeJogador(String nome) {
        return false;
    }

    @Override
    public void escreverChat(Jogador jogador, String mensagem) {
        System.out.println("entrou aki");
        jogadores.forEach(it -> {
            try {
                if (!it.equals(jogador)) {
                    Object objetoLocalizado = comunicacaoServico.localizandoNome(it.nome, "text");
                    chat.appendText(texto.getText());
                }

            } catch (CannotProceed | InvalidName | NotFound cannotProceed) {
                cannotProceed.printStackTrace();
            }
        });
    }


    @Override
    public void passarTurno(Jogador jogador) {

    }

    @Override
    public void vencedor(Jogador jogador) {

    }

    @Override
    public void perdedor(Jogador jogador) {

    }

    @Override
    public void entrar(Jogador jogador) {
        System.out.println(jogadores.size());
        jogadores.add(jogador);
    }

    @Override
    public void sair(Jogador jogador) {
        jogadores.remove(jogador);
    }
}
