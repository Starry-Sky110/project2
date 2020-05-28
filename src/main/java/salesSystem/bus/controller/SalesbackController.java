package salesSystem.bus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import salesSystem.bus.domain.Salesback;
import salesSystem.bus.service.SalesbackService;
import salesSystem.bus.vo.SalesBackVO;
import salesSystem.sys.domain.User;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-25
 */
@RestController
@RequestMapping("salesback")
public class SalesbackController {

    @Autowired
    private SalesbackService salesbackService;


    @RequestMapping("addSalesBack")
    public ResultObj addSalesBack(Salesback salesback, HttpSession session) {

        try {
            //当前用户
            User user = (User) session.getAttribute("user");
            salesback.setOperateperson(user.getName());
            salesback.setSalesbacktime(new Date());
            salesbackService.addSalesBack(salesback);

            return ResultObj.OUTPORT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();

            return ResultObj.OUTPORT_FAIL;
        }
    }


    @RequestMapping("loadAllSalesback")
    public DataGridView loadAllSalesback(SalesBackVO salesBackVO) {

        return salesbackService.loadAllSalesback(salesBackVO);
    }

}

