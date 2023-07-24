package com.newgen.servlet;

import java.io.PrintStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.newgen.logger.CustomLogger;

public class SendEmail
{
  public void sendMailwithAttachment(String to, String AttachmentPath, String from, String host, String port, String subject, String mailcontent, String ccEmail)
  {
    
	  CustomLogger.printOut("sendMailwithAttachment() Sending mail...");
	  CustomLogger.printOut("Mail To()====>"+to);
	  CustomLogger.printOut("AttachmentPath()====>"+AttachmentPath);
	  CustomLogger.printOut("from ()====>"+from);
	  CustomLogger.printOut("host()====>"+host);
	  CustomLogger.printOut("port()====>"+port);
	  CustomLogger.printOut("subject()====>"+subject);
	  CustomLogger.printOut("mailcontent()====>"+mailcontent);
	  CustomLogger.printOut("ccEmail()====>"+ccEmail);
    Properties props = new Properties();
    props.put("mail.smtp.auth", "false");
    props.put("mail.smtp.starttls.enable", "false");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", port);
    
    Session session = Session.getInstance(props);
    try
    {
      Message message = new MimeMessage(session);
      
      message.setFrom(new InternetAddress(from));
      
      message.setRecipients(Message.RecipientType.TO, 
        InternetAddress.parse(to));
      
      message.setRecipients(Message.RecipientType.CC, 
    	        InternetAddress.parse(ccEmail));
      
      message.setSubject(subject);
      
      BodyPart messageBodyPart = new MimeBodyPart();
      
      messageBodyPart.setText(mailcontent);
      
      Multipart multipart = new MimeMultipart();
      
      multipart.addBodyPart(messageBodyPart);
      
      messageBodyPart = new MimeBodyPart();
      String filename = AttachmentPath;
      
      DataSource source = new FileDataSource(filename);
      messageBodyPart.setDataHandler(new DataHandler(source));
      messageBodyPart.setFileName(filename);
      multipart.addBodyPart(messageBodyPart);
      
      message.setContent(multipart);
      
      Transport.send(message);
      
      System.out.println("Sent Email successfully....");
      CustomLogger.printOut("Sent Email successfully...");
    }
    catch (MessagingException e)
    {
    	CustomLogger.printErr("Error while sending mail");
    	CustomLogger.printOut("Error while sending mail");
      System.out.println("Error while sending mail");
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  
  public void sendMailwithAttachmentNew(String to, String AttachmentPath, String from, String host, String port, String subject, String mailcontent, 
		  String ccEmail, String roCCEmail)
  {
    
	  CustomLogger.printOut("sendMailwithAttachment() Sending mail...");
	  CustomLogger.printOut("Mail To()====>"+to);
	  CustomLogger.printOut("AttachmentPath()====>"+AttachmentPath);
	  CustomLogger.printOut("from ()====>"+from);
	  CustomLogger.printOut("host()====>"+host);
	  CustomLogger.printOut("port()====>"+port);
	  CustomLogger.printOut("subject()====>"+subject);
	  CustomLogger.printOut("mailcontent()====>"+mailcontent);
	  CustomLogger.printOut("ccEmail()====>"+ccEmail);
	  CustomLogger.printOut("roCCEmail()====>"+roCCEmail);
    Properties props = new Properties();
    props.put("mail.smtp.auth", "false");
    props.put("mail.smtp.starttls.enable", "false");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", port);
    
    Session session = Session.getInstance(props);
    try
    {
      Message message = new MimeMessage(session);
      
      message.setFrom(new InternetAddress(from));
      
      message.setRecipients(Message.RecipientType.TO, 
        InternetAddress.parse(to));
      
      String recipient = ccEmail+","+roCCEmail;
      CustomLogger.printOut("recipient List====>"+recipient);
      String[] recipientList = recipient.split(",");
      InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
      int counter = 0;
      for (String recipient1 : recipientList) {
          recipientAddress[counter] = new InternetAddress(recipient1.trim());
          counter++;
      }
      message.setRecipients(Message.RecipientType.CC, recipientAddress);
      
      //message.addRecipients(Message.RecipientType.CC, "abc@abc.com;abc@def.com;ghi@abc.com");
      /*message.setRecipients(Message.RecipientType.CC, 
    	        InternetAddress.parse(ccEmail));
      
      message.setRecipients(Message.RecipientType.CC, 
  	        InternetAddress.parse(roCCEmail));*/
      
      message.setSubject(subject);
      
      BodyPart messageBodyPart = new MimeBodyPart();
      
      messageBodyPart.setText(mailcontent);
      
      Multipart multipart = new MimeMultipart();
      
      multipart.addBodyPart(messageBodyPart);
      
      messageBodyPart = new MimeBodyPart();
      String filename = AttachmentPath;
      
      DataSource source = new FileDataSource(filename);
      messageBodyPart.setDataHandler(new DataHandler(source));
      messageBodyPart.setFileName(filename);
      multipart.addBodyPart(messageBodyPart);
      
      message.setContent(multipart);
      
      Transport.send(message);
      
      System.out.println("Sent Email successfully....");
      CustomLogger.printOut("Sent Email successfully...");
    }
    catch (MessagingException e)
    {
    	CustomLogger.printErr("Error while sending mail");
    	CustomLogger.printOut("Error while sending mail");
      System.out.println("Error while sending mail");
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}

