package compilacaoIDL;


/**
* compilacaoIDL/Jogador.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Monday, June 19, 2017 4:54:29 PM GMT-03:00
*/

public final class Jogador implements org.omg.CORBA.portable.IDLEntity
{
  public String nome = null;
  public int lugar = (int)0;
  public int quantidadePalitosRestantes = (int)0;
  public int quantidadePalitosApostados = (int)0;
  public boolean apostou = false;
  public int palpite = (int)0;

  public Jogador ()
  {
  } // ctor

  public Jogador (String _nome, int _lugar, int _quantidadePalitosRestantes, int _quantidadePalitosApostados, boolean _apostou, int _palpite)
  {
    nome = _nome;
    lugar = _lugar;
    quantidadePalitosRestantes = _quantidadePalitosRestantes;
    quantidadePalitosApostados = _quantidadePalitosApostados;
    apostou = _apostou;
    palpite = _palpite;
  } // ctor

} // class Jogador
