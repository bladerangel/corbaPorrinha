package compilacaoIDL;


/**
* compilacaoIDL/EventosOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Tuesday, June 20, 2017 9:20:41 PM GMT-03:00
*/

public interface EventosOperations 
{
  void sentar (String nome, int lugar);
  void enviarMensagem (String mensagem);
  void apostar (int lugar);
  void palpite (int lugar, int palpite);
  void entrarSala ();
  void sairSala (int lugar);
} // interface EventosOperations
