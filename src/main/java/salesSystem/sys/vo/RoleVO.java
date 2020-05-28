package salesSystem.sys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import salesSystem.sys.domain.Role;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVO extends Role {

    private Integer page = 1;
    private Integer limit = 10;

}
