package servicos;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;

public class ComunicacaoServico {

    private ORB orb;
    private POA rootPOA;
    private NamingContext servidorNomes;

    public ComunicacaoServico() {


    }

    public void iniciandoORB() {
        orb = ORB.init();
    }

    public void obtendoRootPOA() throws InvalidName {
        Object objetoPOA = orb.resolve_initial_references("RootPOA");
        rootPOA = POAHelper.narrow(objetoPOA);
    }

    public void obtendoServidorNomes() throws InvalidName {
        Object objetoServidorNomes = orb.resolve_initial_references("NameService");
        servidorNomes = NamingContextHelper.narrow(objetoServidorNomes);
    }

    public void criandoNome(Servant objeto, String nome, String tipo) throws ServantNotActive, WrongPolicy, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound {
        Object referenciaObjeto = rootPOA.servant_to_reference(objeto);
        NameComponent[] nomeObjeto = {new NameComponent(nome, tipo)};
        servidorNomes.rebind(nomeObjeto, referenciaObjeto);
    }

    public Object localizandoNome(String nome, String tipo) throws CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound {
        NameComponent[] nomeObjeto = {new NameComponent(nome, tipo)};
        return servidorNomes.resolve(nomeObjeto);
    }

    public void ativandoPOA() throws AdapterInactive {
        rootPOA.the_POAManager().activate();
    }

    public void executandoORB() {
        orb.run();
    }


}
