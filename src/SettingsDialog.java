import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {
    /**
     *
     */
    public SettingsDialog(JFrame frame){
        super(frame,"设置");
        Container conn = getContentPane();
        conn.add(new SettingsPanel());
        setBounds(50,50,300,400);
        //System.out.println("test succesfully");

        // 设置Jframe窗体关闭时 程序结束
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}