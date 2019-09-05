package org.javahub.submarine.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.modules.security.entity.JwtUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Auth {
    private String token;
    private JwtUser user;
}
