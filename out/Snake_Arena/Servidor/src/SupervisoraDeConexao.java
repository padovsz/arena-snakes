
import Comunicados.*;

import java.net.*;
import java.io.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread{
    private Parceiro usuario;
    private Socket conexao;
    private ArrayList<Parceiro> usuarios;

    public SupervisoraDeConexao(Socket conexao, ArrayList<Parceiro> usuarios) throws Exception
    {
        if(conexao == null)
            throw new Exception("Conexão ausente");
        if(usuarios == null)
            throw new Exception("Usuarios ausentes");

        this.conexao = conexao;
        this.usuarios = usuarios;
    }

    public void run()
    {
        ObjectOutputStream transmissor;
        try{
            transmissor = new ObjectOutputStream(this.conexao.getOutputStream());
        } catch (Exception erro)
        {
            return;
        }
        ObjectInputStream receptor;
        try{
            receptor = new ObjectInputStream(this.conexao.getInputStream());
        } catch (Exception erro)
        {
            try{
                transmissor.close();
            } catch (Exception falha)
            {}
            return;
        }

        try{
            this.usuario = new Parceiro(this.conexao, receptor, transmissor);
        } catch (Exception erro)
        {}

        try{
            synchronized (this.usuarios)
            {
                this.usuarios.add(this.usuario);
                if(this.usuarios.size() == 2)
                    for(Parceiro cliente : usuarios)
                        if(cliente != usuario)
                            cliente.receba(new ComunicadoDeEntradaDeJogador("PlayerLulu"));
                        else
                            cliente.receba(new ComunicadoDeEntradaDeJogador("Player1"));
            }

            for(;;)
            {
                Comunicado comunicado = this.usuario.envie();

                if(comunicado == null)
                    return;
                else if (comunicado instanceof ComunicadoDeMovimento ||
                        comunicado instanceof ComunicadoDeCrescimento ||
                        comunicado instanceof ComunicadoDeMorte)
                {
                    synchronized (this.usuarios) {
                        for (Parceiro cliente : usuarios) {
                            if (cliente != usuario) {
                                cliente.receba(comunicado);
                            }
                        }
                    }
                }
                else if(comunicado instanceof PedidoParaSair)
                {
                    synchronized (this.usuarios)
                    {
                        this.usuarios.remove(this.usuario);
                    }
                    this.usuario.adeus();
                }
                else if(comunicado instanceof ComunicadoDeNovoJogo)
                {
                        synchronized (this.usuarios) {
                            for (Parceiro cliente : usuarios) {
                                cliente.receba(comunicado);
                            }
                        }
                }
            }
        }
        catch (Exception erro)
        {
            try{
                transmissor.close();
                receptor.close();
            } catch (Exception falha)
            {}
        }
    }
}
