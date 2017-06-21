package compilacaoIDL;


/**
* compilacaoIDL/ListaJogadoresHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Wednesday, June 21, 2017 4:09:25 PM GMT-03:00
*/

abstract public class ListaJogadoresHelper
{
  private static String  _id = "IDL:compilacaoIDL/ListaJogadores:1.0";

  public static void insert (org.omg.CORBA.Any a, compilacaoIDL.Jogador[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static compilacaoIDL.Jogador[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = compilacaoIDL.JogadorHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (compilacaoIDL.ListaJogadoresHelper.id (), "ListaJogadores", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static compilacaoIDL.Jogador[] read (org.omg.CORBA.portable.InputStream istream)
  {
    compilacaoIDL.Jogador value[] = null;
    int _len0 = istream.read_long ();
    value = new compilacaoIDL.Jogador[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = compilacaoIDL.JogadorHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, compilacaoIDL.Jogador[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      compilacaoIDL.JogadorHelper.write (ostream, value[_i0]);
  }

}
