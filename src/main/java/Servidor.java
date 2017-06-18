import compilacaoIDL.*;
import org.omg.CORBA.Object;
import servicos.ComunicacaoServico;

import java.util.HashMap;
import java.util.Map;

public class Servidor extends ServidorPOA {


    private Map<Jogador, Eventos> jogadores;
    private ComunicacaoServico comunicacaoServico;

    public Servidor() {
        jogadores = new HashMap<>();
        comunicacaoServico = new ComunicacaoServico();
    }

    @Override
    public Jogador getJogador(String nome) {
        return jogadores.keySet().stream().filter(jogador -> jogador.nome.equals(nome)).findFirst().get();
    }

    @Override
    public boolean verificarNomeJogador(String nome) {
        return !jogadores.keySet().stream().anyMatch(jogador -> jogador.nome.equals(nome)) && !nome.equals("");
    }

    @Override
    public void adicionarJogador(String nome) {
        try {
            Object objeto = comunicacaoServico.localizandoNome(nome, "text");
            Eventos evento = EventosHelper.narrow(objeto);
            jogadores.put(new Jogador(nome, 0, 3, 0), evento);
            System.out.println("jogador entrou partida" + jogadores.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void atualizarChat(String nome, String mensagem) {
        jogadores.values().forEach(evento -> {
            try {
                evento.enviarMensagem(mensagem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean verificarLugar(String nome, int lugar) {
        Jogador jogador = getJogador(nome);
        if (jogador.lugar == 0) {
            if (jogadores.keySet().stream().anyMatch(j -> j.lugar == lugar)) {
                System.out.println("cadeira ocupada");
                return false;
            }
            System.out.println("cadeira livre");

            jogadores.keySet().stream().filter(j -> j.nome.equals(nome)).findFirst().get().lugar = lugar;

            jogadores.keySet().forEach(j -> System.out.println("lugar" + j.lugar));

            jogadores.values().forEach(evento -> {
                try {
                    evento.sentar(lugar);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void atualizarLugares() {
        jogadores.values().forEach(evento -> {
            jogadores.keySet().forEach(jogador -> {
                if (jogador.lugar != 0)
                    evento.sentar(jogador.lugar);
            });
        });
    }

    @Override
    public Jogador[] getJogadores() {
        return (Jogador[]) jogadores.entrySet().toArray();
    }

    @Override
    public void passarTurno(String nome) {

    }

    @Override
    public void vencedor(String nome) {

    }

    @Override
    public void perdedor(String nome) {

    }

    @Override
    public void removerJogador(String nome) {
        Jogador jogador = getJogador(nome);
        jogadores.keySet().remove(jogador);
        if (jogador.lugar != 0) {
            jogadores.values().forEach(evento -> {
                try {
                    evento.sairSala(jogador.lugar);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
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
