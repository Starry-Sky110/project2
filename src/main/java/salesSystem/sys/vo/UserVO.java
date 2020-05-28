package salesSystem.sys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import salesSystem.sys.domain.User;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserVO extends User {
    private Integer page = 1;
    private Integer limit = 10;
}
