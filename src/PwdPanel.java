import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.MessageDigest;

public class PwdPanel extends JPanel implements KeyListener, ActionListener {
    public PwdPanel() {
        JPasswordField passwordField = new JPasswordField(20);

        // 创建一个按钮，点击后获取密码框中输入的密码
        JButton submitBtn = new JButton("确认");

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String submit = new String(passwordField.getPassword());
                String password = "f865b53623b121fd34ee5426c792e5c33af8c227";
                try {
                    if(shaEncode(submit).equals(password)) {
                        Spanel.isblock = false;
                        JOptionPane.showMessageDialog(null, "解锁成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "管理员密码校验失败", "失败", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.add(passwordField);
        this.add(submitBtn);
    }

    //SHA1加密算法
    private static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
