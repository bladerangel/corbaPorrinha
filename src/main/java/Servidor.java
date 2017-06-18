
import compilacaoIDL.Eventos;
import compilacaoIDL.EventosHelper;
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
    public boolean verificarNomeJogador(String nome) {
        return !jogadores.stream().anyMatch(j -> j.nome.equals(nome));
    }

    @Override
    public boolean verificarLugar(Jogador jogador, int lugar) {
        System.out.println(lugar);
        if (jogadores.stream().anyMatch(j -> j.lugar == lugar)) {
            System.out.println("cadeira ocupada");
            return false;
        }
        System.out.println("cadeira livre");
        jogadores.stream().filter(j -> j.nome.equals(jogador.nome)).findFirst().get().lugar = lugar;
        //jogadores.forEach(j -> System.out.println("lugar"+j.lugar));

        jogadores.forEach(it -> {
            try {
                Object objeto = comunicacaoServico.localizandoNome(it.nome, "text");
                Eventos acoesJogador = EventosHelper.narrow(objeto);
                acoesJogador.sentar(lugar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return true;
    }

    @Override
    public Jogador[] getJogadores() {
        return (Jogador[]) jogadores.toArray();
    }

    @Override
    public void atualizarChat(Jogador jogador, String mensagem) {
        jogadores.forEach(it -> {
            try {
                Object objeto = comunicacaoServico.localizandoNome(it.nome, "text");
                System.out.println(it.nome);
                Eventos acoesJogador = EventosHelper.narrow(objeto);
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
        System.out.println("jogador entrou partida" + jogadores.size());
    }

    @Override
    public void removerJogador(Jogador jogador) {
        jogadores.removeIf(j -> jogador.nome.equals(j.nome));
        jogadores.forEach(it -> {
            try {
                Object objeto = comunicacaoServico.localizandoNome(it.nome, "text");
                Eventos acoesJogador = EventosHelper.narrow(objeto);
                acoesJogador.sair(jogador.lugar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
