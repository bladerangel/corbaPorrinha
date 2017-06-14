import compilacaoIDL.AcoesJogador;
import compilacaoIDL.AcoesJogadorHelper;
import compilacaoIDL.Jogador;
import compilacaoIDL.ServidorPOA;
import org.omg.CORBA.Object;
import servicos.ComunicacaoServico;

import java.util.ArrayList;
import java.util.List;

public class Servidor extends ServidorPOA {

    private List<Jogador> jogadores;
    private ComunicacaoServico comunicacaoServico;

    public Servidor() {
        jogadores = new ArrayList<>();
        comunicacaoServico = new ComunicacaoServico();
    }

    @Override
    public Jogador[] getJogadores() {
        return (Jogador[]) jogadores.toArray();
    }

    @Override
    public boolean verificarNomeJogador(String nome) {
        return false;
    }

    @Override
    public void atualizarChat(Jogador jogador, String mensagem) {
        jogadores.forEach(it -> {
            try {
                Object objeto = comunicacaoServico.localizandoNome(it.nome, "text");
                System.out.println(it.nome);
                AcoesJogador acoesJogador = AcoesJogadorHelper.narrow(objeto);
                acoesJogador.enviarMensagem(mensagem);
            } catch (Exception e) {
                e.printStackTrace();
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
    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
        System.out.println("jogador entrou partida"+ jogadores.size());
    }

    @Override
    public void removerJogador(Jogador jogador) {
        jogadores.remove(jogador);
    }

    public ComunicacaoServico getComunicacaoServico() {
        return comunicacaoServico;
    }

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor();
            servidor.getComunicacaoServico().obtendoRootPOA();
            servidor.getComunicacaoServico().ativandoPOA();
            servidor.getComunicacaoServico().obtendoServidorNomes();
            servidor.getComunicacaoServico().criandoNome(servidor, "Servidor", "text");
            servidor.getComunicacaoServico().executandoORB();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
