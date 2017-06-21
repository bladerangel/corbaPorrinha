import compilacaoIDL.*;
import jdk.nashorn.internal.scripts.JO;
import org.omg.CORBA.Object;
import servicos.ComunicacaoServico;

import java.util.*;

public class Servidor extends ServidorPOA {

    private static final int LIMITE_JOGADORES = 4;
    private List<Jogador> jogadores;
    private Jogador jogadorTurno;
    private ComunicacaoServico comunicacaoServico;

    public Servidor() {

        jogadores = new ArrayList<>();
        comunicacaoServico = new ComunicacaoServico();
    }

    @Override
    public Jogador getJogador(String nome) {
        return jogadores.stream().filter(jogador -> jogador.nome.equals(nome)).findFirst().get();
    }

    @Override
    public boolean verificarNomeJogador(String nome) {
        return !jogadores.stream().anyMatch(jogador -> jogador.nome.equals(nome)) && !nome.equals("");
    }

    @Override
    public void adicionarJogador(String nome) {
        try {
            jogadores.add(new Jogador(nome, 0, 3, 0, false, false, 0));
            System.out.println("jogador entrou partida" + jogadores.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void atualizarChat(String nome, String mensagem) {
        jogadores.forEach(jogador -> {
            try {
                Object objeto = comunicacaoServico.localizandoNome(jogador.nome, "text");
                Eventos evento = EventosHelper.narrow(objeto);
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
            if (jogadores.stream().anyMatch(j -> j.lugar == lugar)) {
                System.out.println("cadeira ocupada");
                return false;
            }
            System.out.println("cadeira livre");

            jogador.lugar = lugar;

            jogadores.sort(Comparator.comparingInt(j -> j.lugar));
            jogadores.forEach(j -> System.out.println("lugar" + j.lugar));

            jogadores.forEach(j -> {
                try {
                    Object objeto = comunicacaoServico.localizandoNome(j.nome, "text");
                    Eventos evento = EventosHelper.narrow(objeto);
                    evento.sentar(nome, lugar);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            jogadores.forEach(j -> System.out.println(j.nome + j.lugar));
            return true;
        }
        return false;
    }

    @Override
    public void atualizarLugares() {
        jogadores.forEach(j -> {
            try {
                Object objeto = comunicacaoServico.localizandoNome(j.nome, "text");
                Eventos evento = EventosHelper.narrow(objeto);
                jogadores.forEach(jogador -> {
                    if (jogador.lugar != 0)
                        evento.sentar(jogador.nome, jogador.lugar);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Jogador[] getJogadores() {
        return (Jogador[]) jogadores.toArray();
    }

    @Override
    public boolean apostar(String nome) {

        Jogador jogador = getJogador(nome);
        if (!jogador.apostou && jogador.lugar != 0) {

            jogador.apostou = true;
            jogadores.forEach(j -> {
                try {
                    Object objeto = comunicacaoServico.localizandoNome(j.nome, "text");
                    Eventos evento = EventosHelper.narrow(objeto);
                    evento.apostar(jogador.lugar);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            if (jogadores.stream().allMatch(j -> j.apostou)) {
                if (jogadorTurno == null) {
                    jogadorTurno = jogadores.stream().findFirst().get();
                    //} else {
                    //  jogadorTurno = jogadores.keySet().stream().filter(j -> j.lugar > jogadorTurno.lugar).findFirst().get();
                }
            }

            //System.out.println(jogadorTurno.nome);

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
    public boolean palpitar(String nome, int palpite) {
        if (!jogadores.stream().anyMatch(j -> j.palpite == palpite)) {
            Jogador jogador = getJogador(nome);
            jogador.palpitou = true;
            jogador.palpite = palpite;
            System.out.println("Jogador palpitou:" + jogadorTurno.nome + jogador.palpite);
            jogadores.stream().filter(j -> j.lugar > jogadorTurno.lugar).findFirst().ifPresent(jogadoraux -> {
                jogadorTurno = jogadoraux;
            });

            jogadores.forEach(j -> {
                try {
                    Object objeto = comunicacaoServico.localizandoNome(j.nome, "text");
                    Eventos evento = EventosHelper.narrow(objeto);
                    evento.palpite(jogador.lugar, jogador.palpite);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            if (jogadores.stream().allMatch(j -> j.palpitou)) {
                vencedorRodada();
            }
            //System.out.println("proximo jogador a palpitar:" + jogadorTurno.nome + jogador.palpite);
            return true;
        }
        return false;
    }

    public void vencedorRodada() {
        int somatorio = jogadores.stream().mapToInt(j -> j.quantidadePalitosApostados).sum();
        System.out.println("somatorio" + somatorio);
        Optional<Jogador> jogador = jogadores.stream().filter(j -> j.palpite == somatorio).findFirst();
        if (jogador.isPresent()) {
            jogadores.forEach(j -> {
                j.palpite = 0;
                j.apostou = false;
                j.palpitou = false;
                if (j.nome.equals(jogador.get().nome))
                    j.quantidadePalitosRestantes += j.quantidadePalitosApostados - 1;
                else
                    j.quantidadePalitosRestantes += j.quantidadePalitosApostados;
                j.quantidadePalitosApostados = 0;
            });

            jogadores.forEach(j -> {
                try {
                    Object objeto = comunicacaoServico.localizandoNome(j.nome, "text");
                    Eventos evento = EventosHelper.narrow(objeto);
                    evento.vencedorRodada(jogador.get().nome);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            jogadorTurno = null;
        } else {
            jogadores.forEach(j -> {
                try {
                    j.palpite = 0;
                    j.apostou = false;
                    j.palpitou = false;
                    j.quantidadePalitosRestantes += j.quantidadePalitosApostados;
                    j.quantidadePalitosApostados = 0;
                    Object objeto = comunicacaoServico.localizandoNome(j.nome, "text");
                    Eventos evento = EventosHelper.narrow(objeto);
                    evento.empateRodada();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        //System.out.println("jogador" + jogadorVencedor.nome);

    }


    @Override
    public void removerJogador(String nome) {
        Jogador jogador = getJogador(nome);
        jogadores.remove(jogador);
        if (jogador.lugar != 0) {
            jogadores.forEach(j -> {
                try {
                    Object objeto = comunicacaoServico.localizandoNome(j.nome, "text");
                    Eventos evento = EventosHelper.narrow(objeto);
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
