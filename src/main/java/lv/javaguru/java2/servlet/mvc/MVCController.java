package lv.javaguru.java2.servlet.mvc;

import javax.servlet.http.HttpServletRequest;

public interface MVCController {

    MVCModel execute(HttpServletRequest request);

}
