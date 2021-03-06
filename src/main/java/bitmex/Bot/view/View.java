package bitmex.Bot.view;

import bitmex.Bot.controller.RunTheProgram;
import bitmex.Bot.model.DatesTimes;
import bitmex.Bot.model.Gasket;
import bitmex.Bot.model.bitMEX.enums.ChartDataBinSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class View extends Thread {
    private RunTheProgram runTheProgram;
    private JScrollPane jScrollPane;
    private JTextField jTextField;
    private JButton jButtonStart;
    private JButton jButtonStop;
    private JTextArea jTextArea;
    private JButton jButtonSet;
    private JFrame jFrame;
    private JPanel jPanel;

    private boolean startFlag;




    @Override
    public void run() {
        jFrame = getJFrame();
        jPanel = new JPanel();
        jFrame.add(jPanel, BorderLayout.NORTH);
        jPanel.setBackground(Color.LIGHT_GRAY);
        jButtonStart = new JButton("START");
        jButtonStop = new JButton("STOP");

        jPanel.add(jButtonStart);
        jPanel.add(jButtonStop);


        jPanel.add(new JLabel("Commands"));
        jTextField = new JTextField("insert commands",30);
        jPanel.add(jTextField);
        jPanel.revalidate();

        jButtonSet = new JButton("SET");
        jPanel.add(jButtonSet);

        jTextArea = new JTextArea(35, 90);
        jScrollPane = new JScrollPane(jTextArea);
        jTextArea.setLineWrap(true);
        jFrame.add(jScrollPane, BorderLayout.CENTER);

        startFlag = true;

        jButtonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // тут прописать старт программы
                jPanel.setBackground(Color.GREEN);

                if (runTheProgram != null) {
                    runTheProgram.startProgram();
                    ConsoleHelper.writeMessage(DatesTimes.getDateTerminal()
                            + " --- Программа ЗАПУЩЕНА");
                    Gasket.getListensLooksAndComparesUser().setStopStartFlag(false);
                    Gasket.getListensLooksAndCompares().setStopStartFlag(false);
                    Gasket.getListensToLooksAndFills().setStopStartFlag(false);
                }

                if (runTheProgram == null) {
                    runTheProgram = new RunTheProgram();
                    Gasket.setRunTheProgram(runTheProgram);
                }
            }
        });


        jButtonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // тут прописать Стоп программы
                jPanel.setBackground(Color.RED);

                runTheProgram.stopPrograms();
                ConsoleHelper.writeMessage(DatesTimes.getDateTerminal()
                        + " --- Программа ОСТАНОВЛЕНА");

                Gasket.getListensLooksAndComparesUser().setStopStartFlag(false);
                Gasket.getListensLooksAndCompares().setStopStartFlag(false);
                Gasket.getListensToLooksAndFills().setStopStartFlag(false);
            }
        });


        jButtonSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // тут прописать настройки программы
                new Thread() {
                    @Override
                    public void run() {
                        Color color = jPanel.getBackground();
                        jPanel.setBackground(Color.yellow);
                        try {
                            Thread.sleep(1000 * 10);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        jPanel.setBackground(color);
                    }
                }.start();

                String string = jTextField.getText();
                if (string.length() > 3) {
                    if (string.trim().equals("info")) {
                        ConsoleHelper.printInfoSettings();
                    } else if (string.trim().equalsIgnoreCase("commands")) {
                        ConsoleHelper.showCommands();
                    } else if (string.trim().equalsIgnoreCase("showSteeps")) {
                        ConsoleHelper.writeMessage(Gasket.getMartingaleClass().showSteps());
                    } else if (string.trim().equalsIgnoreCase("flag")) {
                        ConsoleHelper.printFlag();
                    } else if (string.trim().equalsIgnoreCase("price")) {
                        ConsoleHelper.writeMessage("price now === " + Gasket.getBitmexQuote().getBidPrice());
                    } else if (string.trim().equalsIgnoreCase("chart")) {
                        ConsoleHelper.writeMessage("chart === " + Gasket.getBitmexClient()
                                .getChartData(Gasket.getTicker(), 10, ChartDataBinSize.ONE_MINUTE));
                    } else {
                        Gasket.getExecutorCommandos().parseAndExecute(string.replaceAll("=", " === "));
                    }
                }
            }
        });
    }



    private static JFrame getJFrame() {
        JFrame jFrame = new JFrame() {};
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        jFrame.setSize(1110, 635);
        jFrame.setLocation(dimension.width/2 - 570, dimension.height/2 - 325);
        jFrame.setTitle("II POWER by SAMUIL_OLEGOVICH");
//        jFrame.setIconImage();
        return jFrame;
    }



    public void updateInfoView(String string) {
        if (string != null) {
            if (string.endsWith("\n")) {
                jTextArea.append(string);
            } else {
                jTextArea.append(string + "\n");
            }
        }
    }


    public boolean isStartFlag() {
        return startFlag;
    }
}
