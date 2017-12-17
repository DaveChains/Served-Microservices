package com.serveme.service.notification.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by DavidChains on 16/01/16.
 */

@Table(name = "MAILS")
public class Mail implements Serializable {

	@Id
	@Column(name = "id")
	private long id;
	
	@Column(name = "message_type")
    private String type;
	
	@Column(name = "service_from")
    private String from;
	
	@Column(name = "receiver")
    private String receiver;

	@Column(name = "email_subject")
    private String subject;
	
	@Column(name = "content")
    private String content;
	
	@Column(name = "createdAt")
    private Timestamp createdAt;

	@Column(name = "lastTryAt")
    private Timestamp lastTryAt;

	@Column(name = "tries")
    private int tries = 1;

	@Column(name = "sent")
    private boolean sent;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastTryAt() {
        return lastTryAt;
    }

    public void setLastTryAt(Timestamp lastTryAt) {
        this.lastTryAt = lastTryAt;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
    public String toString() {
        return "Mail{" +
                "type='" + type + '\'' +
                ", from='" + from + '\'' +
                ", receiverList=" + receiver +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", lastTryAt=" + lastTryAt +
                ", tries=" + tries +
                '}';
    }
}
