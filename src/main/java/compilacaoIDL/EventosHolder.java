package compilacaoIDL;

/**
* compilacaoIDL/EventosHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Wednesday, June 21, 2017 4:09:25 PM GMT-03:00
*/

public final class EventosHolder implements org.omg.CORBA.portable.Streamable
{
  public compilacaoIDL.Eventos value = null;

  public EventosHolder ()
  {
  }

  public EventosHolder (compilacaoIDL.Eventos initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = compilacaoIDL.EventosHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    compilacaoIDL.EventosHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return compilacaoIDL.EventosHelper.type ();
  }

}
