package x.nax.mail;

import sun.misc.BASE64Encoder;
import x.nax.tools.Debugger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 使用smtp的客户端，主要用来发送信息的客户端工具类
 */
public class SMTPClient {

  // 是否显示
  private Debugger debugger = new Debugger();
  // 发送授权用户和授权密码时，需要使用base64编码，协议规定的
  BASE64Encoder encode = new BASE64Encoder();
  // 目标smtp的服务器
  private String smtpServer;
  // 目标smtp的端口，基本是25
  private int smtpServerPort = 25;
  // 发件人
  private String sender;
  // 收件人
  private String receiver;
  // 授权密码
  private String authPswd;
  // 主题
  private String subject;
  // 内容
  private String data;

  public SMTPClient(String server, int port) {
    this.smtpServer = server;
    this.smtpServerPort = port;
  }

  public SMTPClient setDebug(boolean debug) {
    this.debugger.setDebug(debug);
    return this;
  }

  public SMTPClient setSender(String sender) {
    this.sender = sender;
    return this;
  }

  public SMTPClient setReceiver(String receiver) {
    this.receiver = receiver;
    return this;
  }

  public SMTPClient setAuthPswd(String authPswd) {
    this.authPswd = authPswd;
    return this;
  }

  public SMTPClient setSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public SMTPClient setData(String data) {
    this.data = data;
    return this;
  }

  public boolean SendMail() {
    if (ValidateMessage()) {
      try {
        Socket socket = new Socket(smtpServer, smtpServerPort);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        // HELO
        if (receiveMessage(in) != 220) {
          throw new Exception("Failed to connect to the SMTP server...");
        }
        sendMessage(out, "HELO " + smtpServer);
        if (receiveMessage(in) != 250) {
          throw new Exception("SMTP service failed..." + smtpServer + ":" + smtpServerPort);
        }
        // Auth login
        sendMessage(out, "AUTH LOGIN");
        if (receiveMessage(in) != 334) {
          throw new Exception("Can not auth login...");
        }
        sendMessage(out, encode.encode(sender.split("@")[0].getBytes()));
        if (receiveMessage(in) != 334) {
          throw new Exception("User name incorrect..." + sender);
        }
        sendMessage(out, encode.encode(authPswd.getBytes()));
        if (receiveMessage(in) != 235) {
          throw new Exception("Authorize failed...");
        }
        // Mail From
        sendMessage(out, "MAIL FROM:<" + sender + ">");
        if (receiveMessage(in) != 250) {
          throw new Exception("From incorrect..." + sender);
        }
        // RCPT To
        sendMessage(out, "RCPT TO:<" + receiver + ">");
        if (receiveMessage(in) != 250) {
          throw new Exception("To incorrect..." + receiver);
        }
        // DATA
        sendMessage(out, "DATA");
        if (receiveMessage(in) != 354) {
          throw new Exception("Can not send data...");
        }
        sendMessage(out, "From: " + sender);
        sendMessage(out, "To: " + receiver);
        sendMessage(out, "Subject: " + subject);
        sendMessage(out, "");
        sendMessage(out, data);
        sendMessage(out, ".");
        if (receiveMessage(in) != 250) {
          throw new Exception("Didn't end correctly...");
        }
        // QUIT
        sendMessage(out, "QUIT");
        if (receiveMessage(in) != 221) {
          throw new Exception("Didn't quite correctly...");
        }
        out.close();
        in.close();
        socket.close();
        debugger.writeLine("Mail send successfully...");
        return true;
      } catch (Exception e) {
        debugger.writeLine(e.getMessage());
        e.printStackTrace();
      } finally {
        clear();
      }
    }
    return false;
  }

  private boolean ValidateMessage() {
    if (isNullOrEmpty(smtpServer)
      || isNullOrEmpty(sender)
      || isNullOrEmpty(receiver)
      || isNullOrEmpty(authPswd)
      || isNullOrEmpty(subject)
      || isNullOrEmpty(data)) {
      return false;
    }
    return true;
  }

  private void sendMessage(BufferedWriter out, String message) throws IOException {
    try {
      out.write(message);
      out.newLine();
      out.flush();
      debugger.writeLine("> " + message);
    } catch (IOException ioe) {
      throw new IOException("ERROR happened with sending: " + message);
    }
  }

  private int receiveMessage(BufferedReader in) throws IOException {
    try {
      String msg = in.readLine();
      debugger.writeLine("< " + msg);
      return Integer.valueOf(msg.split(" ")[0]);
    } catch (IOException ioe) {
      throw new IOException("ERROR happened with receiving message...");
    }
  }

  private boolean isNullOrEmpty(String str) {
    return str == null || "".equals(str);
  }

  private void clear() {
    setSubject("");
    setData("");
    setReceiver("");
  }

}
