import javax.swing.*;
import java.awt.*;

public class PwdDialog extends JDialog {
    /**
     *
     */
    public PwdDialog(JFrame frame){
        super(frame,"请输入管理员密码");
        Container conn = getContentPane();
        conn.add(new PwdPanel());
        setBounds(100,100,400,100);
        //System.out.println("test succesfully");

        // 设置Jframe窗体关闭时 程序结束
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}