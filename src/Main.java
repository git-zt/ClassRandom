import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;


public class Main extends JPanel {
    public static JFrame jf = new JFrame("随机点名系统");

    public static void main(String[] args) {
        jf.setBounds(10,10,900,640);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(new Spanel());

        jf.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Props.WriteProperties("/NameList.properties","list", Spanel.ShowArray.toString());
                Props.WriteProperties("/NameList.properties","length", String.valueOf(Spanel.ShowArray.size()));
                if(Spanel.isblock) {
                    Props.WriteProperties("/NameList.properties","BK","1");
                }
            }
        });

        jf.setVisible(true);
    }

    static void LoadList() {
        String origin = Props.GetValueByKey("/NameList.properties","list","0");
        String length = Props.GetValueByKey("/NameList.properties","length","0");
        String blocking = Props.GetValueByKey("/NameList.properties","BK","0");
        if(!origin.equals("0")) {
            int length_int = Integer.parseInt(length);
            String list_origin = origin.substring(1, origin.length() - 1);
            String[] list_temp = list_origin.split(",");
            for (int i = 0; i < length_int; i++) {
                ArrayList<String> member = new ArrayList<>();
                for(int j = 0;j < 3;j++) {
                    member.add(list_temp[j + (i * 3)].replace("[", "").replace("]", "").replace(" ",""));
                }
                Spanel.NameArray.add(member);
            }
            NameTable.Sync2Arrays();
        } else {
            for (int i = 0; i < Spanel.NameList.length; i++) {
                ArrayList<String> member = new ArrayList<>();
                for(int j = 0;j < 3;j++) {
                    member.add(Spanel.NameList[i][j]);
                }
                Spanel.NameArray.add(member);
            }
            NameTable.Sync2Arrays();
        }

        if(blocking.equals("1")) {
            Spanel.isblock = true;
        }
    }
}
