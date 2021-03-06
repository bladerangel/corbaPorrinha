package compilacaoIDL;


/**
* compilacaoIDL/ServidorOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Thursday, June 22, 2017 10:32:59 AM GMT-03:00
*/

public interface ServidorOperations 
{
  compilacaoIDL.Jogador getJogador (String nome);
  void adicionarJogador (String nome);
  void removerJogador (String nome);
  boolean verificarNomeJogador (String nome);
  boolean verificarLugar (String nome, int lugar);
  void atualizarLugares ();
  void enviarMensagem (String nome, String mensagem);
  void adicionarPalito (String nome);
  void removerPalito (String nome);
  void apostar (String nome);
  boolean verificarPalpitar (String nome);
  boolean palpitar (String nome, int palpite);
} // interface ServidorOperations
