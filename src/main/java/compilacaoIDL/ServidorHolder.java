package compilacaoIDL;

/**
* compilacaoIDL/ServidorHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Wednesday, June 21, 2017 4:09:25 PM GMT-03:00
*/

public final class ServidorHolder implements org.omg.CORBA.portable.Streamable
{
  public compilacaoIDL.Servidor value = null;

  public ServidorHolder ()
  {
  }

  public ServidorHolder (compilacaoIDL.Servidor initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = compilacaoIDL.ServidorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    compilacaoIDL.ServidorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return compilacaoIDL.ServidorHelper.type ();
  }

}
