package compilacaoIDL;


/**
* compilacaoIDL/ListaJogadoresHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Monday, June 19, 2017 8:52:01 PM GMT-03:00
*/

public final class ListaJogadoresHolder implements org.omg.CORBA.portable.Streamable
{
  public compilacaoIDL.Jogador value[] = null;

  public ListaJogadoresHolder ()
  {
  }

  public ListaJogadoresHolder (compilacaoIDL.Jogador[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = compilacaoIDL.ListaJogadoresHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    compilacaoIDL.ListaJogadoresHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return compilacaoIDL.ListaJogadoresHelper.type ();
  }

}
