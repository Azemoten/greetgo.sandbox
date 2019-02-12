package kz.greetgo.sandbox.register.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.nio.channels.Channel;

public class Ssh {
  public static void main(String args[]) throws JSchException, SftpException {JSch jsch = new JSch();
    Session session = jsch.getSession("amukhter", "192.168.26.26");
    session.setPassword("111");
    session.connect();

    ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
    sftpChannel.connect();

    sftpChannel.;
  }
}
