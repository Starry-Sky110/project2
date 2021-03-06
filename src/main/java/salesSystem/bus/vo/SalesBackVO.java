package salesSystem.bus.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import salesSystem.bus.domain.Salesback;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalesBackVO extends Salesback {

    private Integer page = 1;
    private Integer limit = 10;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
