import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Spanel extends JPanel implements KeyListener,ActionListener {
    String action = "start";//初始状态显示“别紧张” -> start    抽取时滚动状态 -> roll    抽取名字后静止 -> static
    String RemoveSwitch = "关";
    String[] NameList = {"A","B","C"};//此处是名单
    ArrayList<String> AlreadyRolledName = new ArrayList<>();

    public Spanel() {
        this.setFocusable(true);
        this.addKeyListener(this);
        JButton RollButton = new JButton("抽！");
        JButton LookUpButton = new JButton("查看名单");
        JButton RemoveButton = new JButton("去除重复");
        JButton LookUpRemoveButton = new JButton("查看被除去的人");
        //JButton simulation = new JButton("模拟");

        setLayout(null);//去除默认流式布局

        RollButton.setBounds(350, 350, 100, 80);
        LookUpButton.setBounds(490, 350, 100, 60);
        RemoveButton.setBounds(350, 440, 100, 80);
        LookUpRemoveButton.setBounds(470, 430, 140, 60);
        simulation.setBounds(350,550,140,60);

        //抽取按钮的逻辑处理
        RollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                //System.out.println("触发了事件");
                if (action.equals("static")) {
                    action = "roll";
                } else if(action.equals("roll")) {
                    action = "static";
                } else if(action.equals("start")) {
                    action = "roll";
                }
                repaint();
            }
        });

        //查看名单按钮的逻辑处理
        LookUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                String output = "";
                for (int i = 0;i < NameList.length;i++) {
                    if (i == 0) {
                        output = NameList[0];
                    } else if (i % 10 == 0){
                        output += "\n" + NameList[i];
                    } else {
                        output += "        " + NameList[i];
                    }
                }
                output += "\nTips:此名单仅供观赏，不可编辑。";
                JOptionPane.showMessageDialog(null, output, "名单",JOptionPane.DEFAULT_OPTION);
            }
        });

        //开启去除重复按钮的逻辑处理
        RemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                String output = "成功！当前状态：";
                if (RemoveSwitch.equals("关")) {
                    RemoveSwitch = "开";
                } else {
                    RemoveSwitch = "关";
                }
                output += RemoveSwitch;
                JOptionPane.showMessageDialog(null, output, "提示",JOptionPane.DEFAULT_OPTION);
            }
        });

        //查看已经去除的人的名单按钮的逻辑处理
        LookUpRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                String output = "";
                for (int i = 0;i < AlreadyRolledName.size();i++) {
                    if (i == 0) {
                        output = AlreadyRolledName.get(0);
                    } else if (i % 10 == 0){
                        output += "\n" + AlreadyRolledName.get(i);
                    } else {
                        output += "        " + AlreadyRolledName.get(i);
                    }
                }
                output += "\nTips:该名单在重启软件后清空。";
                JOptionPane.showMessageDialog(null, output, "名单",JOptionPane.DEFAULT_OPTION);
            }
        });

        //模拟抽取按钮的逻辑处理
        /*
       simulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                for (int i = 0;i < 1000;i ++) {
                    System.out.println(RandomName());
                    long random = new Random().nextInt(100) * 3;
                    try {
                        Thread.sleep(random);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }); */

        this.add(RemoveButton);
        this.add(LookUpButton);
        this.add(RollButton);
        this.add(LookUpRemoveButton);
        //this.add(simulation);

    }

    public String RandomName () {
        int random = new Random().nextInt(NameList.length);
        return NameList[random];
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.PINK);
        g.setColor(Color.BLACK);
        g.setFont(new Font("等线", Font.BOLD, 20));
        g.drawString("designed by Zt", 700, 580);
        g.drawString("version:1.0.2.20201025", 20, 580);

        if (action.equals("roll")) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("等线", Font.BOLD, 150));
            g.drawString(RandomName(), 250, 150);
            repaint();
        } else if(action.equals("start")) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("等线", Font.BOLD, 150));
            g.drawString("别紧张", 250, 150);
        } else if(action.equals("static")) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("等线", Font.BOLD, 150));
            String name = RandomName();
            if (RemoveSwitch.equals("开")) {
                if (AlreadyRolledName.size() == NameList.length) {
                    g.drawString("无法抽取", 130, 150);
                    g.setFont(new Font("等线", Font.BOLD, 60));
                    g.drawString("*所有人都已经被除去*",90,280);
                } else {
                    for ( ; ; ) {
                        if (AlreadyRolledName.contains(name)) {
                            name = RandomName();
                        } else {
                            AlreadyRolledName.add(name);
                            g.drawString(name, 250, 150);
                            break;
                        }
                    }
                }
            } else {
                g.drawString(RandomName(), 250, 150);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        int keycode = e.getKeyCode();
//
//        if (keycode == KeyEvent.VK_SPACE) {
//            action = "T";
//            repaint();
//        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
