import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Spanel extends JPanel implements KeyListener,ActionListener {
    private String action = "start";//初始状态显示“别紧张” -> start    抽取时滚动状态 -> roll    打印名字后的瞬间 -> stop   抽取名字后静止 -> static
    static int[] StateArray = new int[4];
    static String[][] NameList = {
            {"1","柴佳慧","女"},
            {"2","贾静雯","女"},
            {"3","鲍一方","男"},
            {"4","赵雯婧","女"},
            {"5","马子桓","男"},
            {"6","张明健","男"},
            {"7","陈剑飞","男"},
            {"8","何天宇","男"},
            {"9","曾垂硕","男"},
            {"10","全世翔","男"},
            {"11","邓诗旭","女"},
            {"12","王志广","男"},
            {"13","崔田枫","男"},
            {"14","施礼凯","男"},
            {"15","李芷函","女"},
            {"16","郭玉婷","女"},
            {"17","周炜杰","男"},
            {"18","胡颖彤","女"},
            {"19","陈宇涵","男"},
            {"20","秦欣彤","女"},
            {"21","康宇辉","男"},
            {"22","彭紫薇","女"},
            {"23","张娅娟","女"},
            {"24","鲍荣炎","男"},
            {"25","秦子鸣","男"},
            {"26","陈元畅","男"},
            {"27","何元旗","男"},
            {"28","陈永乐","男"},
            {"29","夏宇航","男"},
            {"30","宋江宇","女"},
            {"31","贺楚轩","男"},
            {"32","彭天翔","男"},
            {"33","聂熔楠","女"},
            {"34","王兆鹏","男"},
            {"35","王陈奥雅","女"},
            {"36","湛旭燃","男"},
            {"37","张晨昊","男"},
            {"38","李默琰","女"},
            {"39","雷潇扬","男"},
            {"40","徐佳怡","女"},
            {"41","雷焱琳","女"},
            {"42","马语涵","女"},
            {"43","严圩宸","男"},
            {"44","刘欢","男"},
            {"45","乐金枝","女"},
            {"46","刘杨梅","女"},
            {"47","王书恒","男"},
            {"48","蔡知霖","男"},
            {"49","黄贻然","男"},
            {"50","王宇瑶","女"},
            {"51","尤紫颖","女"},
            {"52","姜昱均","女"},
            {"53","付兰奇","男"},
            {"54","方悠游","女"},
            {"55","王鲲鹏","男"}};//内置初始的名单
    static ArrayList<ArrayList<String>> NameArray = new ArrayList<>();
    static ArrayList<ArrayList<String>> ShowArray = new ArrayList<>();
    static ArrayList<String> AlreadyRolledName = new ArrayList<>();
    static boolean isblock = false;

    private ImageIcon background;
    private ImageIcon SettingsButton_normal;
    private ImageIcon CountingButton_normal;
    private ImageIcon RollButton_normal;
    private ImageIcon RollButton_press;
    private ImageIcon CountingButton_press;
    private ImageIcon SettingsButton_press;

    public Spanel() {
        Main.LoadList();
        LoadImages();
        this.setFocusable(true);
        this.addKeyListener(this);
        JButton RollButton = new JButton();
        JButton SettingsButton = new JButton();
        JButton CountingButton = new JButton();
        //JButton LookUpButton = new JButton("查看名单");
        //JButton RemoveButton = new JButton("去除重复");
        //JButton LookUpRemoveButton = new JButton("查看被除去的人");

        RollButton.setBorder(null);
        RollButton.setOpaque(false);
        RollButton.setContentAreaFilled(false);
        RollButton.setFocusable(false);
        RollButton.setIcon(RollButton_normal);
        RollButton.setPressedIcon(RollButton_press);

        SettingsButton.setBorder(BorderFactory.createEmptyBorder());
        SettingsButton.setContentAreaFilled(false);
        SettingsButton.setFocusable(false);
        SettingsButton.setIcon(SettingsButton_normal);
        SettingsButton.setPressedIcon(SettingsButton_press);

        CountingButton.setBorder(BorderFactory.createEmptyBorder());
        CountingButton.setContentAreaFilled(false);
        CountingButton.setFocusable(false);
        CountingButton.setIcon(CountingButton_normal);
        CountingButton.setPressedIcon(CountingButton_press);
      //JButton simulation = new JButton("模拟");

        setLayout(null);//去除默认流式布局

        RollButton.setBounds(370, 370, RollButton_normal.getIconWidth(), RollButton_normal.getIconHeight());
        SettingsButton.setBounds(220, 415, SettingsButton_normal.getIconWidth(), SettingsButton_normal.getIconHeight());
        CountingButton.setBounds(550,415,CountingButton_normal.getIconWidth(),CountingButton_normal.getIconHeight());

        //抽取按钮的逻辑处理
        RollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                //System.out.println("触发了事件");
                if (action.equals("static")) {
                    action = "roll";
                } else if(action.equals("roll")) {
                    action = "stop";
                } else if(action.equals("start")) {
                    action = "roll";
                }
                repaint();
            }
        });

        //设置按钮的逻辑处理
        SettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                new SettingsDialog(Main.jf).setVisible(true);
            }
        });

        //统计按钮的逻辑处理
        CountingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                new CountingDialog(Main.jf).setVisible(true);
            }
        });

        this.add(RollButton);
        this.add(SettingsButton);
        this.add(CountingButton);
    }

    public String RandomName () {
        if(NameArray.isEmpty()) {
            return "名单为空";
        } else {
            int random = new Random().nextInt(NameArray.size());
            return NameArray.get(random).get(1);
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("等线", Font.BOLD, 20));
        background.paintIcon(this,g,0,0);

        Rectangle clip = g.getClipBounds();
        Color FontColor = new Color(237,237,237);
        Font font = new Font("等线",Font.BOLD,80);
        FontMetrics fm = g.getFontMetrics(font);
        int x = (clip.width - fm.stringWidth("别紧张")) / 2;
        int y = 250;

        if (action.equals("roll")) {
            g.setColor(FontColor);
            g.setFont(font);
            String RandomName = RandomName();
            x = (clip.width - fm.stringWidth(RandomName)) / 2;
            g.drawString(RandomName, x, y);
            repaint();
        }else if(action.equals("start")) {
            g.setColor(FontColor);
            g.setFont(font);
            g.drawString("别紧张", x, y);
        }else if(action.equals("stop")) {
            g.setColor(FontColor);
            g.setFont(font);
            String name = RandomName();
            if (StateArray[0] == 1) { //当抽不重复的人开启时
                if (AlreadyRolledName.size() == NameArray.size()) {
                    g.drawString("无法抽取", (clip.width - fm.stringWidth("无法抽取")) / 2, y);
                    Font second_font = new Font("等线", Font.BOLD, 60);
                    g.setFont(second_font);
                    FontMetrics sfm = g.getFontMetrics(font);
                    g.drawString("*所有人都已经被除去*",(clip.width - sfm.stringWidth("*所有人都已经被除去*")) / 2,380);
                } else {
                    for ( ; ; ) {
                        if (AlreadyRolledName.contains(name)) {
                            name = RandomName();
                        } else {
                            AlreadyRolledName.add(name);
                            x = (clip.width - fm.stringWidth(name)) / 2;
                            g.drawString(name, x, y);
                            action = "static";
                            break;
                        }
                    }
                }
            } else {
                x = (clip.width - fm.stringWidth(name)) / 2;
                g.drawString(name, x, y);
                CountingPanel.Count(name);
                action = "static";
            }
        }
    }

    public void LoadImages() {
        InputStream input;
        try {
            input = getClass().getClassLoader().getResourceAsStream("images/background.jpg");
            background = new ImageIcon(ImageIO.read(input));

            input = getClass().getClassLoader().getResourceAsStream("images/SettingsButton_normal.png");
            SettingsButton_normal = new ImageIcon(ImageIO.read(input));

            input = getClass().getClassLoader().getResourceAsStream("images/CountingButton_normal.png");
            CountingButton_normal = new ImageIcon(ImageIO.read(input));

            input = getClass().getClassLoader().getResourceAsStream("images/RollButton_normal.png");
            RollButton_normal = new ImageIcon(ImageIO.read(input));

            input = getClass().getClassLoader().getResourceAsStream("images/RollButton_press.gif");
            RollButton_press = new ImageIcon(ImageIO.read(input));

            input = getClass().getClassLoader().getResourceAsStream("images/SettingsButton_press.gif");
            SettingsButton_press = new ImageIcon(ImageIO.read(input));

            input = getClass().getClassLoader().getResourceAsStream("images/CountingButton_press.gif");
            CountingButton_press = new ImageIcon(ImageIO.read(input));
        } catch (IOException e) {
            e.printStackTrace();
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
