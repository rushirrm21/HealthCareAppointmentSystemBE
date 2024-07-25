package com.hcaptsys.aptemail;

public interface EmailService {
	void sendEmail(String to, String subject, String message);
}
