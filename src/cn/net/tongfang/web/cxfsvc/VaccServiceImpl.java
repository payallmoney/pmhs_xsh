package cn.net.tongfang.web.cxfsvc;

import javax.jws.WebService;

@WebService(endpointInterface = "cn.net.tongfang.web.cxfsvc.VaccService")
public class VaccServiceImpl implements VaccService {
    public String sayHi(String text) {
        System.out.println("sayHi called");
        return "Hello " + text;
    }
}
