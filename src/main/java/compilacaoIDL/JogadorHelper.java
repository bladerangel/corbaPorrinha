package compilacaoIDL;


/**
* compilacaoIDL/JogadorHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Tuesday, June 20, 2017 9:20:41 PM GMT-03:00
*/

abstract public class JogadorHelper
{
  private static String  _id = "IDL:compilacaoIDL/Jogador:1.0";

  public static void insert (org.omg.CORBA.Any a, compilacaoIDL.Jogador that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static compilacaoIDL.Jogador extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [7];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "nome",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[1] = new org.omg.CORBA.StructMember (
            "lugar",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[2] = new org.omg.CORBA.StructMember (
            "quantidadePalitosRestantes",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[3] = new org.omg.CORBA.StructMember (
            "quantidadePalitosApostados",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_boolean);
          _members0[4] = new org.omg.CORBA.StructMember (
            "apostou",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_boolean);
          _members0[5] = new org.omg.CORBA.StructMember (
            "palpitou",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[6] = new org.omg.CORBA.StructMember (
            "palpite",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (compilacaoIDL.JogadorHelper.id (), "Jogador", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static compilacaoIDL.Jogador read (org.omg.CORBA.portable.InputStream istream)
  {
    compilacaoIDL.Jogador value = new compilacaoIDL.Jogador ();
    value.nome = istream.read_string ();
    value.lugar = istream.read_long ();
    value.quantidadePalitosRestantes = istream.read_long ();
    value.quantidadePalitosApostados = istream.read_long ();
    value.apostou = istream.read_boolean ();
    value.palpitou = istream.read_boolean ();
    value.palpite = istream.read_long ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, compilacaoIDL.Jogador value)
  {
    ostream.write_string (value.nome);
    ostream.write_long (value.lugar);
    ostream.write_long (value.quantidadePalitosRestantes);
    ostream.write_long (value.quantidadePalitosApostados);
    ostream.write_boolean (value.apostou);
    ostream.write_boolean (value.palpitou);
    ostream.write_long (value.palpite);
  }

}
