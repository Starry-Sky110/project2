package salesSystem.bus.vo;

import lombok.Data;

import lombok.EqualsAndHashCode;
import salesSystem.bus.domain.Provider;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProviderVO extends Provider implements Serializable {

    private Integer page = 1;
    private Integer limit = 10;
}
