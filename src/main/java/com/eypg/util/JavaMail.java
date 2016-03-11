package com.eypg.util;

import com.eypg.util.email.EmailProtocol;
import com.eypg.util.email.SendMail;
import java.util.List;

class JavaMail
  implements Runnable
{
  String mailFrom = null;
  String password = null;
  String mailto = null;
  String subject = null;
  String content = null;
  List<String> attachList = null;
  
  JavaMail(String mailFrom, String password, String mailto, String subject, String content, List<String> attachList)
  {
    this.mailFrom = mailFrom;
    this.password = password;
    this.mailto = mailto;
    this.subject = subject;
    this.content = content;
    this.attachList = attachList;
  }
  
  public void run()
  {
    sendMailByJavaMail(mailFrom, password, mailto, subject, content, attachList);
  }
  
  private synchronized boolean sendMailByJavaMail(String mailFrom, String password, String mailto, String subject, String content, List<String> attachList)
  {
    EmailProtocol eProtocol = new EmailProtocol(mailFrom);
    SendMail sendMail = new SendMail(mailFrom, password, eProtocol.getSmtpProtocol(), mailto, subject, content, attachList);
    return sendMail.sendMail();
  }
}
