import compilacaoIDL.*;
import org.omg.CORBA.Object;
import servicos.ComunicacaoServico;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Servidor extends ServidorPOA {

    private Comparator<Jogador> comparadorJogador;
    private static final int LIMITE_JOGADORES = 4;
    private Map<Jogador, Eventos> jogadores;
    private Jogador jogadorTurno;
    private ComunicacaoServico comunicacaoServico;

    public Servidor() {
        comparadorJogador = Comparator.comparingInt(jogador -> jogador.lugar);
        jogadores = new TreeMap<>(comparadorJogador);
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
            jogadores.put(new Jogador(nome, 0, 3, 0, false, 0), evento);
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
    public void adicionarPalito(String nome) {
        Jogador jogador = getJogador(nome);
        if (jogador.quantidadePalitosRestantes > 0 && !jogador.apostou) {
            jogador.quantidadePalitosApostados++;
            jogador.quantidadePalitosRestantes--;
        }
    }

    @Override
    public void removerPalito(String nome) {
        Jogador jogador = getJogador(nome);
        if (jogador.quantidadePalitosApostados > 0 && !jogador.apostou) {
            jogador.quantidadePalitosApostados--;
            jogador.quantidadePalitosRestantes++;
        }
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
                    evento.sentar(nome, lugar);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            jogadores.keySet().forEach(j -> System.out.println(j.nome + j.lugar));
            return true;
        }
        return false;
    }

    @Override
    public void atualizarLugares() {
        jogadores.values().forEach(evento -> {
            jogadores.keySet().forEach(jogador -> {
                if (jogador.lugar != 0)
                    evento.sentar(jogador.nome, jogador.lugar);
            });
        });
    }

    @Override
    public Jogador[] getJogadores() {
        return (Jogador[]) jogadores.entrySet().toArray();
    }

    @Override
    public boolean apostar(String nome) {

        Jogador jogador = getJogador(nome);
        if (!jogador.apostou && jogador.lugar != 0) {

            jogador.apostou = true;
            jogadores.values().forEach(evento -> {
                try {
                    evento.apostar(jogador.lugar);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            if (jogadores.keySet().stream().allMatch(j -> j.apostou)) {
                if (jogadorTurno == null) {
                    jogadorTurno = jogadores.keySet().stream().findFirst().get();
                } else {
                    jogadorTurno = jogadores.keySet().stream().filter(j -> j.lugar > jogadorTurno.lugar).findFirst().get();
                }
            }

            // System.out.println(jogadorTurno.nome);

            return true;
        }

        return false;

    }

    @Override
    public void palpitar(String nome, int quantidadePalitosTotal) {
        Jogador jogador = getJogador(nome);
        if (jogador.apostou && jogadorTurno.equals(jogador)) {
            jogador.palpite = quantidadePalitosTotal;
            System.out.println(jogadorTurno.nome + jogador.palpite);
        }
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
