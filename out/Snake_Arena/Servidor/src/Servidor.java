

import Comunicados.ComunicadoDeDesligamento;
import Comunicados.Parceiro;

import java.util.*;

public class Servidor
{
    //declarando uma porta padrao, caso o usuario não informe
    public static String PORTA_PADRAO = "8080";

    //metodo executavel
    public static void main(String[] args)
    {
        if(args.length>1)
        {
            System.err.println("Uso esperado: java Servidor[PORTA]\n");
            return;
        }

        String porta=Servidor.PORTA_PADRAO;

        if(args.length==1)
            porta=args[0];

        ArrayList<Parceiro> usuarios = new ArrayList<Parceiro> ();

        //tem que ser a mesma porta que o cliente digitou
        AceitadoraDeConexao aceitadoraDeConexao=null;
        try {
            aceitadoraDeConexao = new AceitadoraDeConexao(porta, usuarios);
            aceitadoraDeConexao.start();
        } 
        catch (Exception erro) 
        {
            System.err.println("Escolha uma porta apropriada e liberada em uso\n");
            return;
        }

        for(;;)
        {
            System.out.println ("O servidor esta ativo! Para desativa-lo,");
            System.out.println ("use o comando \"desativar\"\n");
            System.out.print   ("> ");

            String comando=null;
            try
            {
                comando = Teclado.getUmString();
            }
            catch (Exception erro)
            {}

            if (comando.toLowerCase().equals("desativar"))
            {
                synchronized (usuarios) {
                    ComunicadoDeDesligamento comunicadoDeDesligamento =
                            new ComunicadoDeDesligamento();

                    for (Parceiro usuario : usuarios) {
                        try {
                            usuario.receba(comunicadoDeDesligamento);
                            usuario.adeus();
                        } catch (Exception erro) {
                        }
                    }

                    System.out.println("O servidor foi desativado!\n");
                    System.exit(0);
                }
            }
            else
                System.err.println ("Comando invalido!\n");
        }
    }
}