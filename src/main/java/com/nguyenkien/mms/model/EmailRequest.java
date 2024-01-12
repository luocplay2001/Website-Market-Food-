package com.nguyenkien.mms.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailRequest {
    private String to;
    private String subject;
    private String body;
}
