package salesSystem.sys.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salesSystem.sys.domain.Loginfo;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataGridView implements Serializable {
    // loginfoManager 中的表格数据格式
    private Integer code = 0;
    private String msg = "";
    private Long count = 0L; //总条数
    private Object data; //每页的数据

    public DataGridView(Object data) {
        this.data = data;
    }

    public DataGridView(Long count, Object data) {
        this.count = count;
        this.data = data;
    }
}
