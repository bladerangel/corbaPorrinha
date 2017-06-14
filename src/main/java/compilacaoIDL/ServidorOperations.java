package compilacaoIDL;


/**
* compilacaoIDL/ServidorOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Wednesday, June 14, 2017 4:58:15 PM GMT-03:00
*/

public interface ServidorOperations 
{
  compilacaoIDL.Jogador[] getJogadores ();
  boolean verificarNomeJogador (String nome);
  void atualizarChat (compilacaoIDL.Jogador jogador, String mensagem);
  void passarTurno (compilacaoIDL.Jogador jogador);
  void vencedor (compilacaoIDL.Jogador jogador);
  void perdedor (compilacaoIDL.Jogador jogador);
  void adicionarJogador (compilacaoIDL.Jogador jogador);
  void removerJogador (compilacaoIDL.Jogador jogador);
} // interface ServidorOperations