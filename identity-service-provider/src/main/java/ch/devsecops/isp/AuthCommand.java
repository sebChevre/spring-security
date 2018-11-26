package ch.devsecops.isp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "pass")
public class AuthCommand {

    private String uid;
    private String pass;

}
