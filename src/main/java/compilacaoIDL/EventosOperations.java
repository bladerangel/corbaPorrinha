package compilacaoIDL;


/**
* compilacaoIDL/EventosOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Tuesday, June 20, 2017 12:50:00 PM GMT-03:00
*/

public interface EventosOperations 
{
  void sentar (String nome, int lugar);
  void enviarMensagem (String mensagem);
  void esolherQuantidadePalitos (int quantidadePalitos);
  void apostar (int lugar);
  void entrarSala ();
  void sairSala (int lugar);
} // interface EventosOperations
