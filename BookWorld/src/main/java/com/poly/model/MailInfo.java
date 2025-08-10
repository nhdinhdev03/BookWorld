package com.poly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
    String from;
    String to;
    String[] cc;
    String[] bcc;
    String subject;
    String body;
    String[] attachments;
    String anh;

    public MailInfo(String to, String subject, String body,String anh) {
        this.from = "Success 202 - BOOKWORLD <tainvhpc03208@fpt.edu.vn>";
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.anh = anh;
    }
}
