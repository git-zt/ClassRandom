import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.*;

public class CountingPanel extends JPanel implements KeyListener, ActionListener {
    public CountingPanel() {
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("等线",Font.BOLD,30));
        textArea.setForeground(Color.BLACK);

        String BeginSaveDate = Props.GetValueByKey("/Counting.properties","date","0");
        String[] CountingList = Props.GetValueByKey("/Counting.properties","CL","").split("&&&!!!");

        if(BeginSaveDate.equals("0")) {
            textArea.setText("还没有任何数据记录");
        } else {
            textArea.setText("自" + BeginSaveDate +"开始记录起，被点名次数最多的是：\n" + CountingList[0].substring(0,CountingList[0].indexOf("###!!!")));
        }

        textArea.setBounds(20,40,380,200);

        this.add(textArea);

        setLayout(null);//去除默认流式布局
    }

    static void Count(String name) {
        String BeginSaveDate = Props.GetValueByKey("/Counting.properties","date","0");
        if(BeginSaveDate.equals("0")) { // 如果尚未开始记录，就记录下现在的日期，表示从现在起开始记录
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Props.WriteProperties("/Counting.properties","date",formatter.format(date));
            Props.WriteProperties("/Counting.properties","CL",name + "###!!!1");
        } else {
            //将原始存储的表格读取出来，并且分割为数组
            String[] CountingList = Props.GetValueByKey("/Counting.properties","CL","").split("&&&!!!");
            boolean exist = false;
            int index = 0;
            String[] memberinfo = new String[2];
            String[] CountingList_new;
            for(int i = 0;i < CountingList.length;i++) {
                memberinfo = CountingList[i].split("###!!!");
                if(memberinfo[0].equals(name)) { // 检测原存储的内容里是否有该成员
                    index = i;
                    exist = true;
                    break;
                }
            }

            if(exist) {// 如果存在则更改该成员数据
                CountingList[index] = memberinfo[0] + "###!!!" + (Integer.parseInt(memberinfo[1]) + 1);
                CountingList_new = CountingList;
            } else {// 不存在就创建一个成员数据
                CountingList_new = new String[CountingList.length + 1];
                for(int i = 0;i < CountingList.length;i++) {
                    CountingList_new[i] = CountingList[i];
                }
                CountingList_new[CountingList_new.length - 1] = name + "###!!!1";
            }

            Arrays.sort(CountingList_new, new Comparator<String>() {// 利用Arrays.sort重写比较器的方法对更改数据后的数组进行排序
                @Override
                public int compare(String o1, String o2) {
                    int number0 = Integer.parseInt(o1.substring(o1.indexOf("###!!!") + 6));
                    int number1 = Integer.parseInt(o2.substring(o2.indexOf("###!!!") + 6));
                    int numberOrd = number1 - number0;

                    if(numberOrd == 0) {
                        int name0 = string2ascii(o1.substring(0,o1.indexOf("###!!!")));
                        int name1 = string2ascii(o2.substring(0,o2.indexOf("###!!!")));

                        return name1 - name0;
                    } else return numberOrd;
                }
            });

            String storage = "";
            // 存储更改数据后的数组
            for(int i = 0;i < CountingList_new.length - 1;i++) {
                storage += CountingList_new[i] + "&&&!!!";
            }
            storage += CountingList_new[CountingList_new.length - 1];
            Props.WriteProperties("/Counting.properties","CL",storage);
        }
    }

    private static int string2ascii(String context) {
        char[] chars = context.toCharArray();
        int result = 0;
        for (int i = 0;i < chars.length;i++) {
            result += chars[i];
        }
        return result;
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
