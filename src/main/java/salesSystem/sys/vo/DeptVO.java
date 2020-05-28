package salesSystem.sys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.HashCodeExclude;
import salesSystem.sys.domain.Dept;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeptVO extends Dept {
    private Integer page = 1;
    private Integer limit = 10;
}
