package info;

import java.util.ArrayList;

public class Userdata {

    //将集合设置为全局唯一变量
    private  static ArrayList<User> userList = new ArrayList<>();

    //静态代码快
    //只创建一次，默认账号密码
    static{
        userList.add(new User("Admin","123"));
        userList.add(new User("test","456"));
    }


    //供登录界面获取该列表
     public static ArrayList<User> getUserList(){
        return userList;
     }

     //注册暂时写不出来,先留个方法
    public static void addUser(User user){
        userList.add(user);
    }
}
