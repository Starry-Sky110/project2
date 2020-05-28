package salesSystem.sys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import salesSystem.sys.domain.Loginfo;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class LogInfoVO extends Loginfo {

    private Integer page = 1;
    private Integer limit = 10;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
