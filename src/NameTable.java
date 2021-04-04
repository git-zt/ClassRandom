import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class NameTable extends JFrame {

    void run() {
        /**
         * 创建按钮和其他组件
         */
        // 创建编号与编号输入框
        JLabel NumLable = new JLabel("编号：");
        JTextField NumTextField = new JTextField();
        NumLable.setBounds(30, 250, 40, 30);
        NumTextField.setBounds(72, 250, 65, 30);

        // 创建姓名与姓名输入框
        JLabel NameLable = new JLabel("姓名：");
        JTextField NameTextField = new JTextField();
        NameLable.setBounds(150, 250, 40, 30);
        NameTextField.setBounds(192, 250, 65, 30);

        //创建性别选择框
        JLabel SexLable = new JLabel("性别：");

        //创建组合框数据容器
        DefaultComboBoxModel<Object> ComboBoxModel = new DefaultComboBoxModel<>();
        ComboBoxModel.addElement("男");
        ComboBoxModel.addElement("女");
        JComboBox<Object> ComboBoxlist = new JComboBox<>(ComboBoxModel);

        SexLable.setBounds(260, 250, 40, 30);
        ComboBoxlist.setBounds(302, 250, 50, 30);

        //创建增加按钮
        JButton AddBtn = new JButton("增加");
        AddBtn.setBounds(35, 300, 65, 30);

        //创建删除按钮
        JButton DelBtn = new JButton("删除");
        DelBtn.setBounds(105, 300, 65, 30);

        //创建导入按钮
        JButton ImportBtn = new JButton("导入");
        ImportBtn.setBounds(175, 300, 65, 30);

        //创建导出按钮
        JButton ExportBtn = new JButton("导出");
        ExportBtn.setBounds(245, 300, 65, 30);

        //创建锁定按钮
        JButton BlockBtn = new JButton("锁定/解锁");
        BlockBtn.setBounds(315,300,95,30);

        /**
         * 创建数据容器
         */
        // 创建表格内容的容器
        String[][] context = new String[Spanel.ShowArray.size()][3];
        for(int i = 0;i < Spanel.ShowArray.size();i++) {
            for(int j = 0;j < 3;j++) {
                context[i][j] = Spanel.ShowArray.get(i).get(j);
            }
        }
        // 创建表头的数据容器
        String[] title = new String[]{"编号","姓名","性别"};
        // 创建DefaultTableMode模型 管理数据容器
        DefaultTableModel model = new DefaultTableModel(context, title);
        // 创建表格
        JTable table = new JTable(model);
        // 创建table滚动窗体
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 30, 440, 200);
        //创建增加按钮监听器
        AddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Spanel.isblock) {// 如果处于被锁定状态
                    JOptionPane.showMessageDialog(null, "请先解除锁定", "错误", JOptionPane.ERROR_MESSAGE);//弹出错误弹窗
                } else {
                    String Num = NumTextField.getText();
                    String Name = NameTextField.getText();
                    String sex = ComboBoxlist.getSelectedItem().toString();

                    if (Num.equals("") || Name.equals("")) {
                        JOptionPane.showMessageDialog(null, "信息未填写完整", "错误", JOptionPane.ERROR_MESSAGE);
                    } else if (CheckNumInput(Num)) {
                        JOptionPane.showMessageDialog(null, "该编号重复或为错误格式", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String[] data = new String[]{Num, Name, sex};
                        ArrayList<String> member = new ArrayList<>();
                        //将数据放入数据容器中
                        model.addRow(data);
                        //将数据加入Array里
                        member.add(Num);
                        member.add(Name);
                        member.add(sex);
                        Spanel.NameArray.add(member);
                        Sync2Arrays();
                    }

                    //增加后清空数据
                    NumTextField.setText("");
                    NameTextField.setText("");
                }
            }
        });

        //创建删除按钮监听器
        DelBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event) {
                if (Spanel.isblock) {
                    JOptionPane.showMessageDialog(null, "请先解除锁定", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    //获取所有选中行
                    int[] selected = table.getSelectedRows();
                    //依次删除所有选中行
                    for (int i = selected.length - 1; i >= 0; i--) {
                        model.removeRow(selected[i]);
                        //也同步删除位于Array里的数据
                        Spanel.NameArray.remove(selected[i]);
                        Sync2Arrays();
                    }
                }
            }
        });

        //创建导入按钮监听器
        ImportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Spanel.isblock) {
                    JOptionPane.showMessageDialog(null, "请先解除锁定", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    JFileChooser chooser = new JFileChooser();//设置选择器
                    chooser.setMultiSelectionEnabled(false);//设为多选
                    chooser.setDialogTitle("选择名单");//设置窗体标题
                    chooser.setAcceptAllFileFilterUsed(false);//禁止选择所有文件
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("ONLY TXT(.txt)", "txt");//限制仅能选择txt文件
                    chooser.addChoosableFileFilter(filter);
                    int returnVal = chooser.showOpenDialog(ImportBtn);//是否打开文件选择框

                    if (returnVal == JFileChooser.APPROVE_OPTION) {//如果符合文件类型
                        String filepath = chooser.getSelectedFile().getAbsolutePath();//获取绝对路径
                        try {
                            Spanel.NameArray = txt2NameArray(new File(filepath));
                            Sync2Arrays();
                            String[][] context_new = new String[Spanel.NameArray.size()][3];
                            for (int i = 0; i < Spanel.NameArray.size(); i++) {
                                for (int j = 0; j < 3; j++) {
                                    context_new[i][j] = Spanel.NameArray.get(i).get(j);
                                }
                            }//创建一个新的context数组，并同步新context内容
                            model.setDataVector(context_new, title);//刷新Table内容
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        //创建导出按钮监听器
        ExportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("导出名单至");//设置窗体标题
                chooser.setAcceptAllFileFilterUsed(false);//禁止选择所有文件
                FileNameExtensionFilter filter = new FileNameExtensionFilter("ONLY TXT(.txt)","txt");//限制仅能选择txt文件
                chooser.addChoosableFileFilter(filter);
                chooser.setDialogType(JFileChooser.SAVE_DIALOG);//设置窗口为保存文件样式（不过似乎没什么区别）
                int returnVal = chooser.showOpenDialog(ExportBtn);//是否打开文件选择框

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    writeFile(file.getPath());
                }
            }
        });

        //创建锁定按钮监听器
        BlockBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Spanel.isblock) {
                    new PwdDialog(Main.jf).setVisible(true);
                } else {
                    Spanel.isblock = true;
                    JOptionPane.showMessageDialog(null, "锁定成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // 创建container
        Container container = this.getContentPane();
        container.add(scrollPane);
        container.add(NumLable);
        container.add(NumTextField);
        container.add(NameLable);
        container.add(NameTextField);
        container.add(SexLable);
        container.add(ComboBoxlist);
        container.add(AddBtn);
        container.add(DelBtn);
        container.add(ImportBtn);
        container.add(ExportBtn);
        container.add(BlockBtn);
        // 设置布局管理器
        this.setLayout(null);
        /**
         * 设置窗体属性
         */
        this.setTitle("名单管理");
        this.setVisible(true);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);// 设置居中显示
        // 设置Jframe窗体关闭时 程序结束
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //验证输入的编号是否符合规范 false则符合规范 true则不符合
    private static boolean CheckNumInput(String InputNum) {
        boolean result = false;
        if (!InputNum.matches("[0-9]+")) {
            result = true;
        } else if(Spanel.NameArray.isEmpty()){
            return false;
        } else {
            for (int i = 0;i < Spanel.ShowArray.size();i++) {
                if (Spanel.ShowArray.get(i).get(0).equals(InputNum)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    //将导入的txt文件转为String二维ArrayList
    private static ArrayList<ArrayList<String>> txt2NameArray(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
        int LineNum = 0;

        ArrayList<ArrayList<String>> array = new ArrayList<>();
        String s;

        while((s = br.readLine())!=null){ //使用readLine方法，一次读一行
            String[] LineOrigin = s.split(" ");
            ArrayList<String> member = new ArrayList<>();

            for(int i = 0;i < 3;i++) {
                member.add(LineOrigin[i]);
            }
            array.add(member);
            LineNum++;
        }
        br.close();

        return array;
    }

    //写入文件
    private void writeFile(String savepath){
        FileOutputStream fos= null;
        try {
            String result = "";
            fos=new FileOutputStream(savepath + ".txt");
            int ArrayIndexMax = Spanel.ShowArray.size() - 1;
            for (int i = 0;i < ArrayIndexMax;i++) {
                for (int j = 0;j < 2;j++) {
                    result += Spanel.ShowArray.get(i).get(j) + " ";
                }
                result += Spanel.ShowArray.get(i).get(2) + "\n";
            }
            result += Spanel.ShowArray.get(ArrayIndexMax).get(0) + " " + Spanel.ShowArray.get(ArrayIndexMax).get(1) + " "
                    + Spanel.ShowArray.get(ArrayIndexMax).get(2);
            fos.write(result.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void Sync2Arrays() {
        Spanel.ShowArray.clear();
        for (int i = 0;i < Spanel.NameArray.size();i++) {
            ArrayList<String> member = new ArrayList<>();
            for (int j = 0;j < 3;j++) {
                member.add(Spanel.NameArray.get(i).get(j));
            }
            Spanel.ShowArray.add(member);
        }
    }

    static void re_Sync2Arrays() {
        for (int i = 0;i < Spanel.ShowArray.size();i++) {
            ArrayList<String> member = new ArrayList<>();
            for (int j = 0;j < 3;j++) {
                member.add(Spanel.ShowArray.get(i).get(j));
            }
            Spanel.NameArray.add(member);
        }
    }
}