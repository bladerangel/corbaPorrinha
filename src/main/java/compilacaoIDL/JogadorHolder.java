package compilacaoIDL;

/**
* compilacaoIDL/JogadorHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Wednesday, June 21, 2017 9:46:39 PM GMT-03:00
*/

public final class JogadorHolder implements org.omg.CORBA.portable.Streamable
{
  public compilacaoIDL.Jogador value = null;

  public JogadorHolder ()
  {
  }

  public JogadorHolder (compilacaoIDL.Jogador initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = compilacaoIDL.JogadorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    compilacaoIDL.JogadorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return compilacaoIDL.JogadorHelper.type ();
  }

}
