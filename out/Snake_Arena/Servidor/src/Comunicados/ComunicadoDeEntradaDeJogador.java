package Comunicados;

public class ComunicadoDeEntradaDeJogador extends Comunicado
{
    private String eu;

    public ComunicadoDeEntradaDeJogador(String cobrinha)
    {
        if(cobrinha.equals("PlayerLulu") || cobrinha.equals("Player1"))
            this.eu = cobrinha;
    }

    public String getEu()
    {
        return this.eu;
    }
}
