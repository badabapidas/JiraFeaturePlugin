
package com.sag.jira.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	private final static String smtpHostServer = "hqcas.eur.ad.sag";
	private final static String address = "IreviewAdmin@softwareag.com";
	private final static String personal = "MocIReview";
	private final static String subject = "Auto code review task is created for you as ";
	private static String reviewid = null;

	/**
	 * Utility method to send simple HTML email
	 * 
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(String toEmail, String genReviewid, String author, String itracId, String commitId,
			String reviewers) {
		try {
			reviewid = genReviewid;
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpHostServer);

			Session session = Session.getInstance(props, null);
			MimeMessage msg = new MimeMessage(session);

			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(address, personal));
			msg.setReplyTo(InternetAddress.parse(address, false));
			msg.setSubject(getSubject(genReviewid), "UTF-8");
			msg.setText(getBody(author, itracId, commitId, reviewers), "UTF-8");
			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			System.out.println("Message is ready");
			Transport.send(msg);
			System.out.println("EMail Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getSubject(String reviewId) {
		return subject + reviewId;
	}

	private static String getBody(String author, String itracId, String commitId, String reviewers) {
		StringBuffer body = new StringBuffer();
		body.append("Hi " + reviewers.toUpperCase() + ",\n\n");
		body.append("A review task has been created automatically with the review Id " + reviewid
				+ ". \n\nPlease find the details below\n");
		body.append("Itrac Id: " + itracId + "\n");
		body.append("Commit Id: " + commitId + "\n");
		body.append("Author: " + author + "\n");
		body.append("Reviewers: " + reviewers + "\n");
		body.append("Review Task: http://ireview.eur.ad.sag/cru/" + reviewid + "\n\n");

		body.append("Please do the needful. \n\n");
		body.append("Regards,\n");
		body.append("MocReviewAdmin");

		return body.toString();
	}
}
