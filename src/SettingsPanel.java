import javax.swing.*;
import java.awt.event.*;

public class SettingsPanel extends JPanel implements KeyListener, ActionListener {

    public SettingsPanel() {
        JCheckBox ck1 = new JCheckBox("不重复点同一个人"); // 创建一个复选框
        JCheckBox ck2 = new JCheckBox("使窗口一直处于顶层");
        JCheckBox ck3 = new JCheckBox("只点男性");
        JCheckBox ck4 = new JCheckBox("只点女性");

        JButton LookUpButton = new JButton("查看名单");
        JButton ResetBtn = new JButton("重置已经被抽取的人");

        setLayout(null);//去除默认流式布局

        ck1.setBounds(30,20,200,20);
        ck2.setBounds(30,50,200,20);
        ck3.setBounds(30,80,200,20);
        ck4.setBounds(30,110,200,20);
        LookUpButton.setBounds(30,140,120,40);
        ResetBtn.setBounds(30,190,180,40);

        //JCheckBox ck3 = new JCheckBox(" "); // 创建一个复选框
        JCheckBox[] boxArray = new JCheckBox[]{ck1, ck2, ck3, ck4};

        this.add(ck1); // 在顶部面板上添加复选框
        this.add(ck2);
        this.add(ck3);
        this.add(ck4);
        this.add(LookUpButton);
        this.add(ResetBtn);

        for (int i = 0;i < Spanel.StateArray.length;i++) {
            if(Spanel.StateArray[i] == 1) {
                boxArray[i].setSelected(true);
            }
        }//关闭后也可以获取复选框的状态

        //不重复点人复选框的点击监听器
        ck1.addItemListener(e -> { // 复选框的状态发生变化
            // getStateChange方法用于获取复选框的当前状态。1为勾选，0为取消勾选
            if(e.getStateChange() == ItemEvent.SELECTED) {
               Spanel.StateArray[0] = 1;
            } else {
                Spanel.StateArray[0] = 0;
            }
        });

        //窗口置顶复选框的点击监听器
        ck2.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                Spanel.StateArray[1] = 1;
                Main.jf.setAlwaysOnTop(true);
            } else {
                Spanel.StateArray[1] = 0;
                Main.jf.setAlwaysOnTop(false);
            }
        });

        //只点男生复选框的点击监听器
        ck3.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                Spanel.StateArray[2] = 1;
                Spanel.StateArray[3] = 0;
                ck4.setSelected(false);
                Spanel.NameArray.clear();
                for (int i = 0;i < Spanel.ShowArray.size();i++) {
                    String sex = Spanel.ShowArray.get(i).get(2);
                    if(sex.equals("男")) {
                        Spanel.NameArray.add(Spanel.ShowArray.get(i));
                    }//当选择此选项时将NameArray里的成员清空，并添加所有男性成员
                }
            } else {
                Spanel.StateArray[2] = 0;
                NameTable.re_Sync2Arrays();//当此选项被用户主动取消选中时，将NameArray复原（ShowArray）
            }
        });

        //只点女生复选框的点击监听器
        ck4.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                Spanel.StateArray[3] = 1;
                Spanel.StateArray[2] = 0;
                ck3.setSelected(false);
                Spanel.NameArray.clear();
                for (int i = 0;i < Spanel.ShowArray.size();i++) {
                    String sex = Spanel.ShowArray.get(i).get(2);
                    if(sex.equals("女")) {
                        Spanel.NameArray.add(Spanel.ShowArray.get(i));
                    }//当选择此选项时将NameArray里的成员清空，并添加所有女性成员
                }
            } else {
                Spanel.StateArray[3] = 0;
                NameTable.re_Sync2Arrays();//当此选项被用户主动取消选中时，将NameArray复原（ShowArray）
            }
        });

        //查看名单按钮的逻辑处理
        LookUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NameTable().run();
            }
        });

        //重置按钮的逻辑处理
        ResetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Spanel.AlreadyRolledName.clear();
                JOptionPane.showMessageDialog(null, "重置成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
