import compilacaoIDL.*;
import org.omg.CORBA.Object;
import servicos.ComunicacaoServico;

import java.util.*;

public class Servidor extends ServidorPOA {

    private static final int LIMITE_JOGADORES = 4;
    private static final int QUANTIDADE_PALITOS = 3;
    private List<Jogador> jogadores;
    private Jogador jogadorTurno;
    private ComunicacaoServico comunicacaoServico;

    private Servidor() {
        jogadores = new ArrayList<>();
        comunicacaoServico = new ComunicacaoServico();
    }

    //metodo auxilar no envio de requicoes para os jogadores
    private void enviarRequisicao(String evento, Jogador jogador, String mensagem) {
        jogadores.forEach(j -> {
            try {
                Object objeto = comunicacaoServico.localizandoNome(j.nome, "text");
                Cliente cliente = ClienteHelper.narrow(objeto);
                switch (evento) {
                    case "sair":
                        cliente.sairSala(jogador.lugar);
                        break;
                    case "sentar":
                        cliente.sentar(jogador.nome, jogador.lugar);
                        break;
                    case "atualizarLugares":
                        jogadores.forEach(jaux -> {
                            if (jaux.lugar != 0)
                                cliente.sentar(jaux.nome, jaux.lugar);
                        });

                        break;
                    case "enviarMensagem":
                        cliente.escreverMensagem(mensagem + "\n");
                        break;
                    case "apostar":
                        cliente.apostar(jogador.lugar);
                        break;
                    case "palpite":
                        cliente.palpite(jogador.lugar, jogador.palpite);
                        break;
                    case "reiniciarRodada":
                        jogadores.forEach(jaux -> {
                            if (jaux.lugar != 0)
                                cliente.reiniciarRodada(jaux.lugar, jaux.quantidadePalitosRestantes);
                        });
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    //retorna o jogador baseado no nome
    @Override
    public Jogador getJogador(String nome) {
        return jogadores.stream().filter(j -> j.nome.equals(nome)).findFirst().get();
    }

    //adiciona o jogador na lista de jogadores
    @Override
    public void adicionarJogador(String nome) {
        try {
            jogadores.add(new Jogador(nome, 0, QUANTIDADE_PALITOS, 0, -1, false, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //remove o jogador na lista de jogadores
    @Override
    public void removerJogador(String nome) {
        Jogador jogador = getJogador(nome);
        jogadores.remove(jogador);
        if (jogador.lugar != 0) {
            enviarRequisicao("sair", jogador, null);
            enviarRequisicao("enviarMensagem", null, "O jogaodor " + nome + " saiu do jogo!");
        }
    }

    //verifica se o nome do jogador está valido
    @Override
    public boolean verificarNomeJogador(String nome) {
        return jogadores.stream().noneMatch(j -> j.nome.equals(nome)) && !nome.equals("");
    }

    //verifica se o jogador pode sentar no lugar desejado
    @Override
    public boolean verificarLugar(String nome, int lugar) {
        Jogador jogador = getJogador(nome);
        if (jogador.lugar == 0) {
            if (jogadores.stream().anyMatch(j -> j.lugar == lugar)) {
                return false;
            } else {
                jogador.lugar = lugar;
                jogadores.sort(Comparator.comparingInt(j -> j.lugar));//ordena os jogadores de acordo com o lugar
                enviarRequisicao("sentar", jogador, null);
                enviarRequisicao("enviarMensagem", null, "O jogador " + nome + " sentou no lugar:" + lugar);
                return true;
            }
        }
        return false;
    }

    //atualiza os lugares quando o jogador entrar na sala
    @Override
    public void atualizarLugares() {
        enviarRequisicao("atualizarLugares", null, null);
    }

    //enviar mensagem para os jogadores
    @Override
    public void enviarMensagem(String nome, String mensagem) {
        enviarRequisicao("enviarMensagem", null, "O jogador " + nome + " digitou:" + mensagem);
    }

    //jogador adiciona um palito da sua mao
    @Override
    public void adicionarPalito(String nome) {
        Jogador jogador = getJogador(nome);
        if (jogador.quantidadePalitosRestantes > 0 && !jogador.apostou) {
            jogador.quantidadePalitosApostados++;
            jogador.quantidadePalitosRestantes--;
        }
    }

    //jogador remove um palito da sua mao
    @Override
    public void removerPalito(String nome) {
        Jogador jogador = getJogador(nome);
        if (jogador.quantidadePalitosApostados > 0 && !jogador.apostou) {
            jogador.quantidadePalitosApostados--;
            jogador.quantidadePalitosRestantes++;
        }
    }

    //jogador faz uma aposta
    @Override
    public void apostar(String nome) {
        Jogador jogador = getJogador(nome);
        if (!jogador.apostou) {
            jogador.apostou = true;
            enviarRequisicao("apostar", jogador, null);
            enviarRequisicao("enviarMensagem", null, "O jogador " + nome + " apostou!");
            if (jogadores.stream().filter(j -> j.lugar != 0).allMatch(j -> j.apostou)) {
                if (jogadorTurno == null) {
                    jogadorTurno = jogadores.stream().findFirst().get();
                }
            }
        }
    }

    //verificar se o jogador pode fazer um palpite
    @Override
    public boolean verificarPalpitar(String nome) {
        Jogador jogador = getJogador(nome);
        return jogadorTurno != null && jogador.apostou && jogadorTurno.equals(jogador) && !jogador.palpitou;
    }

    //o jogador dar o seu palpite e verifica se existe um palpite igual dado por outro jogador
    @Override
    public boolean palpitar(String nome, int palpite) {
        if (jogadores.stream().noneMatch(j -> j.palpite == palpite)) {
            Jogador jogador = getJogador(nome);
            jogador.palpitou = true;
            jogador.palpite = palpite;

            //passa o turno para o proximo jogador
            jogadores.stream().filter(j -> j.lugar > jogadorTurno.lugar).findFirst().ifPresent(j -> jogadorTurno = j);
            enviarRequisicao("palpite", jogador, null);
            enviarRequisicao("enviarMensagem", null, "O jogador " + nome + " palpitou: " + palpite);
            //quando todos os jogadores palpitarem verifica-se o ganhador
            if (jogadores.stream().filter(j -> j.lugar != 0).allMatch(j -> j.palpitou)) {
                finalRodada();
            }
            return true;
        }
        return false;
    }

    //verifica o jogador vencedor ou se ocorreu empate da rodada
    private void finalRodada() {
        int somatorio = jogadores.stream().mapToInt(j -> j.quantidadePalitosApostados).sum();

        Optional<Jogador> jogador = jogadores.stream().filter(j -> j.palpite == somatorio).findFirst();

        //caso algum jogador tenha acertado o palpite
        if (jogador.isPresent()) {
            reiniciarRodada(jogador.get(), true);
            enviarRequisicao("reiniciarRodada", null, null);
            enviarRequisicao("enviarMensagem", null, "O jogador " + jogador.get().nome + " foi o vencedor da rodada!");
            //caso de empate
        } else {
            reiniciarRodada(null, false);
            enviarRequisicao("reiniciarRodada", null, null);
            enviarRequisicao("enviarMensagem", null, "A partida deu empate!");
        }
        jogadorTurno = null;
    }

    //metodo auxilar para reiniciar a partida
    private void reiniciarRodada(Jogador jogador, boolean vencedor) {
        jogadores.forEach(j -> {
            j.palpite = -1;
            j.apostou = false;
            j.palpitou = false;
            if (vencedor && j.nome.equals(jogador.nome))
                j.quantidadePalitosRestantes += j.quantidadePalitosApostados - 1;
            else
                j.quantidadePalitosRestantes += j.quantidadePalitosApostados;
            j.quantidadePalitosApostados = 0;

            if (j.quantidadePalitosRestantes == 0) {
                j.quantidadePalitosRestantes = QUANTIDADE_PALITOS;
                enviarRequisicao("enviarMensagem", null, "O jogador " + j.nome + " foi o vencedor do jogo!");
            }
        });
    }


    private ComunicacaoServico getComunicacaoServico() {
        return comunicacaoServico;
    }

    public static void main(String[] args) {
        try {
            Runtime.getRuntime().exec("tnameserv");
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
