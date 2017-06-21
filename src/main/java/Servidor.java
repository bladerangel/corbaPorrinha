import compilacaoIDL.*;
import org.omg.CORBA.Object;
import servicos.ComunicacaoServico;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Servidor extends ServidorPOA {

    private static final int LIMITE_JOGADORES = 4;
    private Map<Jogador, Eventos> jogadores;
    private Jogador jogadorTurno;
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
            jogadores.put(new Jogador(nome, 0, 3, 0, false, false, 0), evento);
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
        System.out.println("nome:" + nome);
        Jogador jogador = getJogador(nome);
        System.out.println("passou aki");
        if (jogador.lugar == 0) {
            if (jogadores.keySet().stream().anyMatch(j -> j.lugar == lugar)) {
                System.out.println("cadeira ocupada");
                return false;
            }
            System.out.println("cadeira livre");

            jogador.lugar = lugar;

            jogadores.entrySet().stream().sorted(Comparator.comparingInt(j -> -j.getKey().lugar));
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
                    //} else {
                    //  jogadorTurno = jogadores.keySet().stream().filter(j -> j.lugar > jogadorTurno.lugar).findFirst().get();
                }
            }

            System.out.println(jogadorTurno.nome);

            return true;
        }

        return false;

    }

    @Override
    public boolean verificarPalpitar(String nome) {
        System.out.println("chegou aki");
        Jogador jogador = getJogador(nome);
        //System.out.println(jogadorTurno.nome);
        return jogadorTurno != null && jogador.apostou && jogadorTurno.equals(jogador) && !jogador.palpitou;
    }

    @Override
    public void palpitar(String nome, int quantidadePalitosTotal) {
        Jogador jogador = getJogador(nome);
        jogador.palpitou = true;
        jogador.palpite = quantidadePalitosTotal;
        System.out.println("Jogador palpitou:" + jogadorTurno.nome + jogador.palpite);
        jogadores.keySet().stream().filter(j -> j.lugar > jogadorTurno.lugar).findFirst().ifPresent(jogadoraux -> {
            jogadorTurno = jogadoraux;
        });

        jogadores.values().forEach(evento -> {
            try {
                evento.palpite(jogador.lugar, jogador.palpite);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println("proximo jogador a palpitar:" + jogadorTurno.nome + jogador.palpite);
    }

    public void vencedor(String nome) {

    }

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
