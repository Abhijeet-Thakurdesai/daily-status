package com.status.wrapper;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.status.model.Status;
import com.status.model.Team;
import com.status.service.StatusService;
import com.sun.mail.imap.IMAPFolder;

public class EmailScanner {

	@Autowired
	private Properties emailConfiguration;

	@Autowired
	private StatusService statusSvc;

	private static Logger logger = Logger.getLogger(EmailScanner.class.getName());

	private IMAPFolder folder;

	private String expectedSubject;

	private Store store;

	private Session session;

	private Message[] messages;

	public void getUnreadMailsAndStore() {
		try {
		    init();
			if (folder.getUnreadMessageCount() == 0) {
				logout();
				return;
			}
			getUnreadMessages();
			readMails();
			logout();
		} catch (MessagingException e) {
			logger.error(getProp(CRON_METHOD_FAILED + e.getMessage()));
		}
	}

	private void logout() throws MessagingException {
		folder.close(false);
		store.close();
		store = null;
		session = null;
	}

	private void readMails() {
		try {
			for (Message msg : messages) {
				String emailSubject = msg.getSubject();
				Address[] from = msg.getFrom();
				Address[] to=msg.getAllRecipients();
				Team team=statusSvc.getTeam(to[0].toString());
				String senderEmailId = from == null ? null : ((InternetAddress) from[0]).getAddress();
				if (((emailSubject).matches("(.*)" + expectedSubject + "(.*)"))
						&& !senderEmailId.equalsIgnoreCase(getProp(SCANNER_ID))) {
					Status status = new Status();
					status.setEmail(senderEmailId);
					status.setDate(msg.getReceivedDate());
					String emailBody = this.getTextFromMessage(msg);
					status.setStatus(emailBody.split("\\s(On).*\\wrote:")[0].trim());
					statusSvc.addStatus(status);
					logger.info(getProp(EMAIL_STORED));
				}

			}

		} catch (Exception e) {
			logger.error(getProp(INBOX_READ_FAILED) + e.getMessage());
		}
	}

	private void getUnreadMessages() {
		try {
			messages = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
			int mailCount = folder.getUnreadMessageCount();
			logger.info(getProp(EMAILS_FOUND) + mailCount);
		} catch (MessagingException e) {
			logger.error(getProp(INBOX_READ_FAILED) + e.getMessage());
		}

	}

	private void init() {
		expectedSubject = getProp(SCANNER_SUBJECT);
		getImapSession(getProp(SCANNER_ID), getProp(SCANNER_PASSWD));
	}

	private void getImapSession(String emailIdToScan, String password) {
		Properties props = new Properties();
		props.setProperty(getProp(SOCKET_CLASS), getProp(SSL_CLASS));
		props.setProperty(getProp(SOCKET_MODE), "false");
		session = Session.getDefaultInstance(props, null);
		try {
			store = session.getStore("imaps");
			store.connect(getProp(IMAP_URL), emailIdToScan, password);
			folder = (IMAPFolder) store.getFolder(getProp(IMAP_FOLDER));
			folder.open(Folder.READ_WRITE);
		} catch (Exception e) {
			logger.error(getProp(IMAP_SESSION_FAILED) + e.getMessage());
		}
	}

	private String getTextFromMessage(Message message) throws Exception {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break;
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

	private String getProp(String propName) {
		return emailConfiguration.getProperty(propName);
	}

	private static final String SCANNER_ID = "email_id_to_scan";
	private static final String SCANNER_PASSWD = "password";
	private static final String SCANNER_SUBJECT = "subject_to_scan";
	private static final String SOCKET_CLASS = "socket_class";
	private static final String SSL_CLASS = "ssl_class";
	private static final String SOCKET_MODE = "socket_fallback_mode";
	private static final String IMAP_URL = "gmail_imap_url";
	private static final String IMAP_FOLDER = "gmail_folder";

	private static final String INBOX_READ_FAILED = "email_scanner_unableToReadInbox";
	private static final String CRON_METHOD_FAILED = "email_scanner_cron_failed";
	private static final String EMAIL_STORED = "email_scanner_mail_stored";
	private static final String EMAILS_FOUND = "email_scanner_mails_found";
	private static final String IMAP_SESSION_FAILED = "email_scanner_imap_session_error";

}
