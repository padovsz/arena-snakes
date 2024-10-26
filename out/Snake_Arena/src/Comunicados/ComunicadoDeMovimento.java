package Comunicados;
public class ComunicadoDeMovimento extends Comunicado{
    private String movimento;

    public ComunicadoDeMovimento(String movimento) throws Exception {
        if (movimento == null)
            throw new Exception("Movimento inválido");

        this.movimento = movimento;
    }

    public String getMovimento() {
        return this.movimento;
    }
}