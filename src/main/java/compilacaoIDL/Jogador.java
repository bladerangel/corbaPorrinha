package compilacaoIDL;


/**
* compilacaoIDL/Jogador.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Wednesday, June 21, 2017 9:04:23 PM GMT-03:00
*/

public final class Jogador implements org.omg.CORBA.portable.IDLEntity
{
  public String nome = null;
  public int lugar = (int)0;
  public int quantidadePalitosRestantes = (int)0;
  public int quantidadePalitosApostados = (int)0;
  public int palpite = (int)0;
  public boolean apostou = false;
  public boolean palpitou = false;

  public Jogador ()
  {
  } // ctor

  public Jogador (String _nome, int _lugar, int _quantidadePalitosRestantes, int _quantidadePalitosApostados, int _palpite, boolean _apostou, boolean _palpitou)
  {
    nome = _nome;
    lugar = _lugar;
    quantidadePalitosRestantes = _quantidadePalitosRestantes;
    quantidadePalitosApostados = _quantidadePalitosApostados;
    palpite = _palpite;
    apostou = _apostou;
    palpitou = _palpitou;
  } // ctor

} // class Jogador
