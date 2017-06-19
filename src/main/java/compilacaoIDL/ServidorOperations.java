package compilacaoIDL;


/**
* compilacaoIDL/ServidorOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Monday, June 19, 2017 4:54:29 PM GMT-03:00
*/

public interface ServidorOperations 
{
  compilacaoIDL.Jogador[] getJogadores ();
  compilacaoIDL.Jogador getJogador (String nome);
  boolean verificarNomeJogador (String nome);
  boolean verificarLugar (String nome, int lugar);
  void atualizarLugares ();
  void atualizarChat (String nome, String mensagem);
  void adicionarPalito (String nome);
  void removerPalito (String nome);
  void apostar (String nome, int lugar);
  void palpitar (int quantidadePalitosTotal);
  void vencedor (String nome);
  void perdedor (String nome);
  void adicionarJogador (String nome);
  void removerJogador (String nome);
} // interface ServidorOperations
