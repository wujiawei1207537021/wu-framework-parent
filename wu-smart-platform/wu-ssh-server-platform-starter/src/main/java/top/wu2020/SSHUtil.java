package top.wu2020;

import com.jcraft.jsch.JSchException;
 
import java.io.IOException;
 
public class SSHUtil {
    public static SSHExecutor sshExecutor;
    
    public static void start (){
        // 此处配置服务器信息
        SSHExecutor.SSHInfo sshInfo = new SSHExecutor.SSHInfo("root", "wujiawei123!", "47.92.27.215", 22);
        try {
            sshExecutor = new SSHExecutor(sshInfo);
            System.err.println("ssh灾备日志服务器已链接");
        } catch (Exception e) {
            System.err.println("ssh灾备日志服务器链接失败！！");
            e.printStackTrace();
            sshExecutor.close();
        }
    }
    
    /**
    * 运行cmd命令
    */
    public static void runCmd (String cmd) throws JSchException, IOException, InterruptedException {
        sshExecutor.exec(cmd);
    }
}