package cliente;

import Comunicados.Parceiro;
import Comunicados.PedidoParaSair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SalaDeEspera extends JFrame {
    private boolean iniciou = false;
    private Parceiro servidor;

    protected JLabel lbPlayer1 = new JLabel("Você:");
    protected JLabel lbPlayer2 = new JLabel("Player 2:");

    protected JLabel lbScorePlayer1 = new JLabel("-> 0");
    protected JLabel lbScorePlayer2 = new JLabel("0");

    protected JLabel lbReadyP1 = new JLabel("Waiting");
    protected JLabel lbReadyP2 = new JLabel("Waiting");

    protected JButton btnIniciar = new JButton("Iniciar");

    public SalaDeEspera(Parceiro servidor) throws Exception
    {
        super("Sala de espera");

        this.servidor = servidor;

        btnIniciar.addActionListener(new IniciarJogo());
        btnIniciar.setEnabled(false);

        JPanel pnlPlayer1 = new JPanel();
        FlowLayout flwPlayer1 = new FlowLayout();
        pnlPlayer1.setLayout(flwPlayer1);
        pnlPlayer1.add(lbScorePlayer1);
        pnlPlayer1.add(lbPlayer1);
        pnlPlayer1.add(lbReadyP1);

        JPanel pnlPlayer2 = new JPanel();
        FlowLayout flwPlayer2 = new FlowLayout();
        pnlPlayer2.setLayout(flwPlayer2);
        pnlPlayer1.add(lbScorePlayer2);
        pnlPlayer1.add(lbPlayer2);
        pnlPlayer1.add(lbReadyP2);

        JPanel pnlIniciar = new JPanel();
        FlowLayout flwBotao = new FlowLayout();
        pnlIniciar.setLayout(flwBotao);
        pnlIniciar.add(btnIniciar);

        JPanel pnlComponentes = new JPanel();
        GroupLayout grpComponentes = new GroupLayout(pnlComponentes);
        pnlComponentes.setLayout(grpComponentes);
        grpComponentes.setAutoCreateGaps(true);
        grpComponentes.setAutoCreateContainerGaps(true);
        grpComponentes.setHorizontalGroup(
                grpComponentes.createSequentialGroup()
                        .addGroup(grpComponentes.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lbScorePlayer1)
                                .addComponent(lbScorePlayer2))
                        .addGroup(grpComponentes.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lbPlayer1)
                                .addComponent(lbPlayer2)
                                .addComponent(btnIniciar))
                        .addGroup(grpComponentes.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lbReadyP1)
                                .addComponent(lbReadyP2))
        );
        grpComponentes.setVerticalGroup(
                grpComponentes.createSequentialGroup()
                        .addGroup(grpComponentes.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lbScorePlayer1)
                                .addComponent(lbPlayer1)
                                .addComponent(lbReadyP1))
                        .addGroup(grpComponentes.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lbScorePlayer2)
                                .addComponent(lbPlayer2)
                                .addComponent(lbReadyP2))
                        .addGroup(grpComponentes.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lbScorePlayer2)
                                .addComponent(lbPlayer2)
                                .addComponent(btnIniciar)
                                .addComponent(lbReadyP2))
        );

        JPanel pnlJanela = new JPanel();
        FlowLayout flwJanela = new FlowLayout();
        pnlJanela.setLayout(flwJanela);
        pnlJanela.add(pnlComponentes);

        Container cntForm = this.getContentPane();
        cntForm.setLayout(new BorderLayout());
        cntForm.add(pnlJanela, BorderLayout.CENTER);

        this.addWindowListener(new FechamentoDeJanela());

        this.setSize(700, 300);
        this.setVisible(true);
    }

    protected class IniciarJogo implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            iniciou = true;
        }
    }

    protected class FechamentoDeJanela extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            try {
                servidor.receba(new PedidoParaSair());
                servidor.adeus();
            }
            catch (Exception err)
            {}
            System.exit(0);
            //coisas a mudar
        }
    }

    public boolean isIniciou() {
        return iniciou;
    }

    public void setIniciou(boolean iniciou) {
        this.iniciou = iniciou;
    }

    public void setEnabledBtnIniciar(boolean b) {
        this.btnIniciar.setEnabled(b);
    }
}
