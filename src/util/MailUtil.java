package util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {

	public static void sendEmailRegistrationLink(String id, String email, String hash) throws AddressException, MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", Setup.MAIL_SMTP_HOST);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Setup.MAIL_USERNAME, Setup.MAIL_PASSWORD);
			}
		  });

		String link = Setup.MAIL_REGISTRATION_SITE_LINK+"?scope=activation&userId="+id+"&hash="+hash;
		
		  StringBuilder bodyText = new StringBuilder(); 
			bodyText.append("<div>")
			     .append("  Gentile Utente<br/><br/>")
			     .append("  Grazie per esserti registrato a TellMeAStory. La tua email ("+email+") è sotto verifica<br/>")
			     .append("  Clicca qui <a href=\""+link+"\">here</a> Oppure copia il link sottostante nella barra indirizzi del tuo browser<br/>")
			     .append("  <a href=\""+link+"\">"+link+"</a>")
			     .append("  <br/><br/>")
			     .append("  Grazie,<br/>")
			     .append("  TellMeAStory Team")
			     .append("</div>");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Setup.MAIL_USERNAME));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Email Registration");
			message.setContent(bodyText.toString(), "text/html; charset=utf-8");
			Transport.send(message);
	}

	public static void sendResetPasswordLink(String id, String email, String hash) throws AddressException, MessagingException {
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", Setup.MAIL_SMTP_HOST);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Setup.MAIL_USERNAME, Setup.MAIL_PASSWORD);
			}
		  });

		String link = Setup.MAIL_REGISTRATION_SITE_LINK+"?scope=resetPassword&userId="+id+"&hash="+hash;
		
		  StringBuilder bodyText = new StringBuilder(); 
			bodyText.append("<div>")
			     .append("  Gentile Utente<br/><br/>")
			     .append("  Abbiamo ricevuto la richiesta di reset password, Clicca qui per il reset password <br/>")
			     .append("  Please click <a href=\""+link+"\">here</a>  Oppure copia il link sottostante nella barra indirizzi del tuo browser<br/>")
			     .append("  <a href=\""+link+"\">"+link+"</a>")
			     .append("  <br/><br/>")
			     .append("  Grazie,<br/>")
			     .append("  TellMeAStory Team")
			     .append("</div>");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Setup.MAIL_USERNAME));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Reset Password");
			message.setContent(bodyText.toString(), "text/html; charset=utf-8");
			Transport.send(message);
	}

}
