package enabler.ssh;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.transport.HostKeyVerification;
import com.sshtools.j2ssh.transport.TransportProtocolException;
import com.sshtools.j2ssh.transport.publickey.SshPublicKey;

public class SSHEnabler {
	SshClient client;
	public SSHEnabler(String ip,String user,String password) throws Exception{
		client=new SshClient();
		client.connect(ip,22,new HostKeyVerification() {
			
			@Override
			public boolean verifyHost(String arg0, SshPublicKey arg1) throws TransportProtocolException {
				return true;
			}
		});
	    //设置用户名和密码
	    PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
	    pwd.setUsername(user);
	    pwd.setPassword(password);
	    int result=client.authenticate(pwd);
	    if(result!=AuthenticationProtocolState.COMPLETE){
	    	 throw new Exception("连接失败");
	    }
	}
	public String readFile(String path) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			client.openSftpClient().get(path, bos);
			String content=new String(bos.toByteArray());
			return content;
		} catch (Exception e) {
			return "";
		}
	}
	public static void main(String[] args) throws Exception {
		SSHEnabler en=new SSHEnabler("192.168.1.20", "root", "jieroudlb2017!@#");
		String rs=en.readFile("/home/yap2.0test2/bizSystem/tomcat7/bin/log/userMgr-2017-03-08.log");
//		String rs=en.readFile("/home/source/demo1.txt");
		System.out.println(rs);
	}
}
