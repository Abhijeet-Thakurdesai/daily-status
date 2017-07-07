package com.status.wrapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;
import com.status.model.Status;
import com.status.service.StatusService;

public class EmailSender implements InitializingBean {

	@Autowired
	private Properties emailConfiguration;

	@Autowired
	private StatusService statusSvc;

	private VelocityEngine velocityEngine;

	private static Logger logger = Logger.getLogger(EmailSender.class.getName());

	private Properties smtpProps = new Properties();

	private Session session;

	private Message mimeMessage;

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setup();
	}

	public void createSummary() {
		Date yesterdaysDate = getYesterdaysDate();
		DateFormat dateFormat = new SimpleDateFormat(getProp(DATE_FORMAT));
		List<Status> statusList = statusSvc.getStatus(yesterdaysDate);
		if (statusList.isEmpty()) {
			logger.info(getProp(NO_SUMMARY));
			return;
		}
		sendSummaryMail(getProp(SUMMARY_SUBJECT) + " " + dateFormat.format(yesterdaysDate),
				statusSvc.getStatus(yesterdaysDate));
	}

	public void askForStatusMail() {
		try {
			logger.info(getProp(STATUS_QUERY_START_MESSAGE));
			LocalDate localDate = LocalDate.now();
			mimeMessage.setSubject(getProp(STATUS_QUERY_SUBJECT) + " "
					+ DateTimeFormatter.ofPattern(getProp(DATE_FORMAT)).format(localDate));
			String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, getProp(STATUS_QUERY_TMPL),
					"UTF-8", null);
			mimeMessage.setContent(content, "text/html; charset=utf-8");
			Transport.send(mimeMessage);
			logger.info(getProp(STATUS_QUERY_SUCCESS_MESSAGE));
		} catch (Exception e) {
			logger.error(getProp(STATUS_QUERY_FAILED_MESSAGE) + e.getMessage());
		}
	}

	private void setup() {
		try {
			setUpSmtpProperties();
			setUpSmtpSession();
			SetupMimeMessage();
			logger.info(MAIL_SENDER_INIT_SUCCESS);
		} catch (Exception e) {
			logger.error(getProp(MAIL_SENDER_INIT_FAILED) + e.getMessage());
		}
	}

	private void sendSummaryMail(String subject, List<Status> statusList) {
		try {
			logger.info(getProp(SUMMARY_START_MESSAGE));
			mimeMessage.setSubject(subject);
			Map<String, List<Status>> model = new HashMap<String, List<Status>>();
			model.put("summary", statusList);
			String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
					getProp(SUMMARY_TMPL), "UTF-8",model);
			mimeMessage.setContent(content, "text/html; charset=utf-8");
			Transport.send(mimeMessage);
			logger.info(getProp(SUMMARY_MAIL_SENT));
		} catch (MessagingException e) {
			logger.error(getProp(SUMMARY_MAIL_FAILED) + e.getMessage());
		}
	}

	private void SetupMimeMessage() throws AddressException, MessagingException {
		mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(getProp(SENDER_ID)));
		mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getProp(MAIL_RECIPIENT)));

	}

	private void setUpSmtpSession() {
		session = Session.getInstance(smtpProps, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getProp(SENDER_ID), getProp(SENDER_PASSWD));
			}
		});
	}

	private void setUpSmtpProperties() {
		smtpProps.put(getProp(SMTP_TTL), "true");
		smtpProps.put(getProp(SMTP_AUTH), "true");
		smtpProps.put("mail.smtp.host", getProp(SMTP_HOST));
		smtpProps.put("mail.smtp.port", getProp(SMTP_PORT));
	}

	private Date getYesterdaysDate() {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date todaysDate = c.getTime();
		Date yesterdaysDate = DateUtils.addDays(todaysDate, -1);
		return yesterdaysDate;
	}

	private String getProp(String propName) {
		return emailConfiguration.getProperty(propName);
	}

	private static final String SMTP_TTL = "gmail_smpt_ttl";
	private static final String SMTP_AUTH = "gmail_smpt_auth";
	private static final String SMTP_HOST = "gmail_smpt_host";
	private static final String SMTP_PORT = "gmail_smtp_port";
	private static final String SENDER_ID = "sender_mail";
	private static final String STATUS_QUERY_SUBJECT = "sender_subject";
	private static final String SENDER_PASSWD = "sender_password";
	private static final String STATUS_QUERY_TMPL = "sender_content";
	private static final String DATE_FORMAT = "date_format";
	private static final String MAIL_RECIPIENT = "reciepient";
	private static final String SUMMARY_TMPL = "summary_content";
	private static final String SUMMARY_SUBJECT = "summary_subject";

	private static final String STATUS_QUERY_START_MESSAGE = "ask_for_status_process_start";
	private static final String STATUS_QUERY_SUCCESS_MESSAGE = "email_sender_status_query_sent";
	private static final String STATUS_QUERY_FAILED_MESSAGE = "email_sender_statusQuery_mail_failed";
	private static final String SUMMARY_START_MESSAGE = "summary_cron_started";
	private static final String NO_SUMMARY = "email_sender_no_summary_found";
	private static final String SUMMARY_MAIL_SENT = "summary_mail_sent";
	private static final String SUMMARY_MAIL_FAILED = "summary_mail_not_sent";
	private static final String MAIL_SENDER_INIT_FAILED = "email_sender_init_error";
	private static final String MAIL_SENDER_INIT_SUCCESS = "email_sender_init_success";
}
