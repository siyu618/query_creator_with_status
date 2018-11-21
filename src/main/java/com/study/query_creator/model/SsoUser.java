package com.study.query_creator.model;

import lombok.*;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SsoUser {
    private String userName;
    private String traceId;
}
