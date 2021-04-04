import javax.swing.*;
import java.awt.*;

public class CountingDialog extends JDialog {
    /**
     *
     */
    public CountingDialog(JFrame frame){
        super(frame,"统计");
        Container conn = getContentPane();
        conn.add(new CountingPanel());
        setBounds(50,50,400,300);

        // 设置Jframe窗体关闭时 程序结束
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}