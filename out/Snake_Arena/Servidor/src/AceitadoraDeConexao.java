
import Comunicados.Parceiro;

import java.net.*;
import java.util.*;

public class AceitadoraDeConexao extends Thread
{
    //declarando o socket e o vetor de usuarios
    private ServerSocket pedido;
    private ArrayList<Parceiro> usuarios;

    //construtor
    public AceitadoraDeConexao(String porta, ArrayList<Parceiro> usuarios) throws Exception
    {
        //verificando se a porta esta correta, e os usuarios tambem
        if(porta==null)
            throw new Exception("Porta ausente");

        try 
        {
            this.pedido = new ServerSocket(Integer.parseInt(porta));
        } 
        catch (Exception erro) 
        {
            throw new Exception("Porta invalida");
        }

        if(usuarios==null)
            throw new Exception("Usuarios ausente");

        this.usuarios = usuarios;
    }

    //metodo essencial -> run
    public void run()
    {
        for(;;)
        {
            Socket conexao=null;
            try{
                conexao=this.pedido.accept();
            }
            catch(Exception erro)
            {
                continue;
            }

            SupervisoraDeConexao supervisoraDeConexao=null;
            try{
                supervisoraDeConexao=new SupervisoraDeConexao(conexao, usuarios);
            }
            catch(Exception erro)
            {} //sei que não vai ocorrer erros

            //inicia a classe
            supervisoraDeConexao.start();
        }
    }
}