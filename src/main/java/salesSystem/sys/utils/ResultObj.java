package salesSystem.sys.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultObj {

    private Integer code = 200;
    private String msg = "";
    public static final ResultObj LOGIN_SUCCESS = new ResultObj(200,"登陆成功");
    public static final ResultObj LOGIN_FAIL = new ResultObj(-1,"登陆失败");
    public static final ResultObj DELETE_SUCCESS = new ResultObj(200,"删除成功");
    public static final ResultObj DELETE_FAIL = new ResultObj(-1,"删除失败");
    public static final ResultObj ADD_SUCCESS = new ResultObj(200,"添加成功");
    public static final ResultObj ADD_FAIL = new ResultObj(-1,"添加失败");
    public static final ResultObj UPDATE_SUCCESS = new ResultObj(200,"更新成功");
    public static final ResultObj UPDATE_FAIL = new ResultObj(-1,"更新失败");
    public static final ResultObj OUTPORT_SUCCESS = new ResultObj(200,"退货成功");
    public static final ResultObj OUTPORT_FAIL = new ResultObj(-1,"退货失败");
    public static final ResultObj OLD_PWD_ERROR = new ResultObj(-1,"旧密码输入错误");
    public static final ResultObj CONFIRM_PASSWORD_ERROR = new ResultObj(-1,"两次密码输入不一致");
    public static final ResultObj SURPLUS_GOODS_NOT_ENOUGH = new ResultObj(-1,"剩余商品数量不足");
    public static final ResultObj ERROR_NUMBER = new ResultObj(-1,"请输入正确的数量");


    public ResultObj(String msg) {
        this.msg = msg;
    }
}
