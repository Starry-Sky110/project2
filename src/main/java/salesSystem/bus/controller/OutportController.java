package salesSystem.bus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import salesSystem.bus.domain.Outport;
import salesSystem.bus.service.OutportService;
import salesSystem.bus.vo.OutPortVO;
import salesSystem.sys.domain.User;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;
import salesSystem.sys.utils.TimeZoneInChina;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-23
 */
@RestController
@RequestMapping("outport")
public class OutportController {

    @Autowired
    private OutportService outportService;

    @RequestMapping("addOutPort")
    public ResultObj addOutPort(Outport outport, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            outport.setOperateperson(user.getName());
            outport.setOutporttime(new Date());
            outportService.addOutPort(outport);
            return ResultObj.OUTPORT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.OUTPORT_FAIL;
        }
    }

    @RequestMapping("loadAllOutport")
    public DataGridView loadAllOutport(OutPortVO outPortVO) {

        return outportService.loadAllOutport(outPortVO);
    }


}

