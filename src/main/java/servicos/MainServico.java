package servicos;


import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class MainServico {

    private ComunicacaoServico comunicacaoServico;
    private PartidaService partidaService;

    public MainServico(){
        comunicacaoServico = new ComunicacaoServico();
        try {
            comunicacaoServico.obtendoRootPOA();
            comunicacaoServico.obtendoServidorNomes();

        } catch (org.omg.CORBA.ORBPackage.InvalidName cannotProceed) {
            cannotProceed.printStackTrace();
        }
        partidaService = new PartidaService(comunicacaoServico);

    }

    public ComunicacaoServico getComunicacaoServico() {
        return comunicacaoServico;
    }

    public PartidaService getPartidaService() {
        return partidaService;
    }
}
