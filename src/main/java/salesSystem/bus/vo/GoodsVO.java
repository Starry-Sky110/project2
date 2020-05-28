package salesSystem.bus.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import salesSystem.bus.domain.Goods;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsVO extends Goods implements Serializable {

    private Integer page = 1;
    private Integer limit = 10;
}
