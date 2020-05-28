package salesSystem.sys.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import salesSystem.sys.domain.Permission;

@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVO extends Permission {

    private Integer page = 1;
    private Integer limit = 10;
}
