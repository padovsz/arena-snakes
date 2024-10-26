package Comunicados;

import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;

public class Parceiro
{
    //var da conexao, recepção, transmissor
    private Socket conexao;
    private ObjectInputStream receptor;
    private ObjectOutputStream transmissor;

    private Comunicado proximoComunicado=null;

    private Semaphore mutEx = new Semaphore(1, true); //semamforo

    //construtor
    public Parceiro(Socket conexao, ObjectInputStream receptor, ObjectOutputStream transmissor) throws Exception
    {
        //verificando se esta tudo certo
        if(conexao==null)
            throw new Exception("Conexao ausente");

        if(receptor==null)
            throw new Exception("Conexao receptor");

        if(transmissor==null)
            throw new Exception("Conexao transmissor");

        this.conexao    =conexao;
        this.transmissor=transmissor;
        this.receptor   =receptor;
    }

    //metodos obrigatorios(run, espie, envie, adeus)
    public void receba(Comunicado x) throws Exception
    {
        try {
                this.transmissor.writeObject(x);
                this.transmissor.flush();
        } 
        catch (IOException erro) 
        {
           throw new Exception("Erro de transmissão");
        }
    }

    public Comunicado espie() throws Exception
    {
        try{
            this.mutEx.acquireUninterruptibly();
            if(this.proximoComunicado==null)
                this.proximoComunicado=(Comunicado)this.receptor.readObject();
            this.mutEx.release();
            return this.proximoComunicado;
        }
        catch(Exception erro)
        {
            throw new Exception("Erro de recepção");
        }
    }

    public Comunicado envie() throws Exception
    {
        try{
            if(this.proximoComunicado==null)
                this.proximoComunicado=(Comunicado)this.receptor.readObject();
            Comunicado ret = this.proximoComunicado;
            this.proximoComunicado=null;
            return ret;
        }
        catch(Exception erro)
        {
            throw new Exception("Erro de recepção");
        }
    }

    public void adeus() throws Exception
    {
        try{
            this.transmissor.close();
            this.receptor  .close();
            this.conexao    .close();
        }
        catch(Exception erro)
        {
            throw new Exception("Erro de desconexão");
        }
    }
}