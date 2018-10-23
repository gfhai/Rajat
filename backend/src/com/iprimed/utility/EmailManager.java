/**
 * 
 */
package com.iprimed.utility;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.iprimed.model.Customer;

public class EmailManager {


	//send welcome mail to newly register customer
	public void sendWelcomeMail(Customer customer, String fileName) {
		// SMTP info
		String host = "smtp.gmail.com";
		String port = "587";
		String mailFrom = "niyaz.demo@gmail.com";
		String password = "niyazdemo@123";

		// message info
		String mailTo = customer.getEmail();
		String subject = "VoizFonica -  Welcome " + customer.getFirstName() + " :)";
		String message = "Thank you for registering in VoizFonica";

		// attachments
		String attachment = fileName;
		
		try {
			sendEmailWithAttachments(host, port, mailFrom, password, mailTo, subject, message, attachment);
			System.out.println("Mail Sent");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in sending mail");
		}
	}

	//send invoice of product purchased by a particular customer
	public void sendPurchaseInvoice(Customer customer, long number, String date, String productName, String fileName) {
		// TODO Auto-generated method stub
		// SMTP info
				String host = "smtp.gmail.com";
				String port = "587";
				String mailFrom = "niyaz.demo@gmail.com";
				String password = "niyazdemo@123";

				// message info
				String mailTo = customer.getEmail();
				String subject = "VoizFonica -  Thank you " + customer.getFirstName() + " for purchasing "+productName ;
				String message = "Your "+productName+" number is in the attachment";

				// attachments
				String attachment = fileName;
				
				try {
					sendEmailWithAttachments(host, port, mailFrom, password, mailTo, subject, message, attachment);
					System.out.println("Mail Sent");
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error in sending mail");
				}
	}

	//utility method that sends mail with attachment 
	public static void sendEmailWithAttachments(String host, String port, String mailFrom, String password,
			String mailTo, String subject, String message, String attachment)
			throws AddressException, MessagingException {

		// setting SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.ssl.trust",host);
		properties.put("mail.user", mailFrom);
		properties.put("mail.password", password);

		// creating a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailFrom, password);
			}
		};

		// Creating session
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(mailFrom));
		InternetAddress[] toAddresses = { new InternetAddress(mailTo) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		MimeBodyPart attachPart = new MimeBodyPart();

		try {
			attachPart.attachFile(attachment);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		multipart.addBodyPart(attachPart);

		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);

	}

	
}
