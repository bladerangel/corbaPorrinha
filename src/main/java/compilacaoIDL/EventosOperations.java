package compilacaoIDL;


/**
* compilacaoIDL/EventosOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Wednesday, June 21, 2017 12:47:11 PM GMT-03:00
*/

public interface EventosOperations 
{
  void sentar (String nome, int lugar);
  void enviarMensagem (String mensagem);
  void apostar (int lugar);
  void palpite (int lugar, int palpite);
  void entrarSala ();
  void vencedorRodada (String nome);
  void sairSala (int lugar);
} // interface EventosOperations
