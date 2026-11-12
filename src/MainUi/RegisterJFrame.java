package MainUi;

import javax.swing.*;

public class RegisterJFrame extends JFrame {
    //注册界面

    public RegisterJFrame(){
        //设置宽高
        this.setSize(488,500);
        //设置界面的标题
        this.setTitle("拼图 注册");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置界面关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置界面可见
        this.setVisible(true);
    }
}
