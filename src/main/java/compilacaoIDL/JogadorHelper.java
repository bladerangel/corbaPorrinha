package compilacaoIDL;


/**
* compilacaoIDL/JogadorHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Wednesday, June 14, 2017 4:58:15 PM GMT-03:00
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [3];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "nome",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[1] = new org.omg.CORBA.StructMember (
            "totalPalitos",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[2] = new org.omg.CORBA.StructMember (
            "quantidadePalitosApostados",
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
    value.totalPalitos = istream.read_long ();
    value.quantidadePalitosApostados = istream.read_long ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, compilacaoIDL.Jogador value)
  {
    ostream.write_string (value.nome);
    ostream.write_long (value.totalPalitos);
    ostream.write_long (value.quantidadePalitosApostados);
  }

}