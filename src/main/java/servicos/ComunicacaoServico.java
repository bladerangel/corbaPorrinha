package servicos;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.Servant;

public class ComunicacaoServico {

    private ORB orb;
    private POA rootPOA;
    private NamingContext servidorNomes;

    public ComunicacaoServico() {

        iniciandoORB();
    }

    public void iniciandoORB() {
        String args[] = {};
        orb = ORB.init(args, null);
    }

    public void obtendoRootPOA() throws Exception {
        Object objetoPOA = orb.resolve_initial_references("RootPOA");
        rootPOA = POAHelper.narrow(objetoPOA);
    }

    public void obtendoServidorNomes() throws Exception {
        Object objetoServidorNomes = orb.resolve_initial_references("NameService");
        servidorNomes = NamingContextHelper.narrow(objetoServidorNomes);
    }

    public void criandoNome(Servant objeto, String nome, String tipo) throws Exception {
        Object referenciaObjeto = rootPOA.servant_to_reference(objeto);
        NameComponent[] nomeObjeto = {new NameComponent(nome, tipo)};
        servidorNomes.rebind(nomeObjeto, referenciaObjeto);
    }

    public Object localizandoNome(String nome, String tipo) throws Exception {
        NameComponent[] nomeObjeto = {new NameComponent(nome, tipo)};
        return servidorNomes.resolve(nomeObjeto);
    }

    public void ativandoPOA() throws Exception {
        rootPOA.the_POAManager().activate();

    }

    public void executandoORB() {
        orb.run();
    }


}
