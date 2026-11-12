package MainUi;


import info.User;
import info.Userdata;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginJFrame extends JFrame implements ActionListener {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginBtn;


    public LoginJFrame() {
        // 1. 初始化窗口基础设置
        this.setSize(350, 250);
        this.setTitle("拼图单机版 - 登录");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null); // 取消默认居中

        //用户输入框
        JLabel userLabel = new JLabel("用户名");
        userLabel.setBounds(50, 40, 60, 30);
        this.getContentPane().add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 40, 150, 30);
        this.add(usernameField);

        //密码输入框
        JLabel passLabel = new JLabel("密  码:");
        passLabel.setBounds(50, 90, 60, 30);
        this.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 90, 150, 30);
        this.add(passwordField);


        //登录按钮
        loginBtn = new JButton("登 录");
        loginBtn.setBounds(120, 150, 100, 35);
        loginBtn.addActionListener(this); // 绑定点击事件
        this.add(loginBtn);

        //  界面显示
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();
        if (source == loginBtn) {
            System.out.println("选择登录");
            // 获取用户输入的内容
            String userInput = usernameField.getText();
            String passwordInput = new String(passwordField.getPassword());

            //判空,并添加弹框
            if (userInput.isEmpty() || passwordInput.length() == 0) {
                JOptionPane.showMessageDialog(this, "用户名或密码不能为空！");
                return;
            }

            // 去 UserData 里面比对

            boolean isFound = false;

            for (User user : Userdata.getUserList()) {
                if (user.getName().equals(userInput) && user.getPassword().equals(passwordInput)) {

                    isFound = true;
                    break;//找到就跳出
                }
            }
            // 判断结果
            if (isFound) {
                // 弹窗提示成功
                    JOptionPane.showMessageDialog(this, "登录成功！即将进入游戏...","系统提示",JOptionPane.INFORMATION_MESSAGE);

                //更新：添加游玩须知 1.使用方向键上下左右 2.长按A可以预览目标图

                JOptionPane.showMessageDialog(this,"使用方向键: 上下左右游玩","游玩须知",-1);
                JOptionPane.showMessageDialog(this,"长按A键可预览目标图 ","游玩须知",-1);
                JOptionPane.showMessageDialog(this,"更多玩法，请预览功能!","游玩须知",-1);


                // 关闭当前登录界面
                this.setVisible(false);

                // 打开你的拼图主界面（确保你之前写的 GameJFrame 已经写好了）
                new GameJFrame();

            } else {
                // 登录失败，弹窗提示，并清空密码框
                JOptionPane.showMessageDialog(this, "用户名或密码错误！");
                passwordField.setText("");
            }
        }

    }

}
