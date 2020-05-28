package salesSystem.sys.utils;


import lombok.Data;
import salesSystem.sys.domain.User;

import java.io.Serializable;
import java.util.List;

@Data
public class ActiveUser implements Serializable {

    private User user;
    private List<String> roles;
    private List<String> permission;

}
