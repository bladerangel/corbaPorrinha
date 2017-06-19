package compilacaoIDL;


/**
* compilacaoIDL/EventosPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Porrinha.idl
* Monday, June 19, 2017 8:52:01 PM GMT-03:00
*/

public abstract class EventosPOA extends org.omg.PortableServer.Servant
 implements compilacaoIDL.EventosOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("sentar", new java.lang.Integer (0));
    _methods.put ("enviarMensagem", new java.lang.Integer (1));
    _methods.put ("esolherQuantidadePalitos", new java.lang.Integer (2));
    _methods.put ("apostar", new java.lang.Integer (3));
    _methods.put ("entrarSala", new java.lang.Integer (4));
    _methods.put ("sairSala", new java.lang.Integer (5));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // compilacaoIDL/Eventos/sentar
       {
         int lugar = in.read_long ();
         this.sentar (lugar);
         out = $rh.createReply();
         break;
       }

       case 1:  // compilacaoIDL/Eventos/enviarMensagem
       {
         String mensagem = in.read_string ();
         this.enviarMensagem (mensagem);
         out = $rh.createReply();
         break;
       }

       case 2:  // compilacaoIDL/Eventos/esolherQuantidadePalitos
       {
         int quantidadePalitos = in.read_long ();
         this.esolherQuantidadePalitos (quantidadePalitos);
         out = $rh.createReply();
         break;
       }

       case 3:  // compilacaoIDL/Eventos/apostar
       {
         int lugar = in.read_long ();
         this.apostar (lugar);
         out = $rh.createReply();
         break;
       }

       case 4:  // compilacaoIDL/Eventos/entrarSala
       {
         this.entrarSala ();
         out = $rh.createReply();
         break;
       }

       case 5:  // compilacaoIDL/Eventos/sairSala
       {
         int lugar = in.read_long ();
         this.sairSala (lugar);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:compilacaoIDL/Eventos:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Eventos _this() 
  {
    return EventosHelper.narrow(
    super._this_object());
  }

  public Eventos _this(org.omg.CORBA.ORB orb) 
  {
    return EventosHelper.narrow(
    super._this_object(orb));
  }


} // class EventosPOA
