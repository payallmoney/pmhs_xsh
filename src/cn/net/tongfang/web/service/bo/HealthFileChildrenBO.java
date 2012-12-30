package cn.net.tongfang.web.service.bo;

import cn.net.tongfang.framework.security.vo.ChildrenException;
import cn.net.tongfang.framework.security.vo.HealthFileChildren;
import cn.net.tongfang.framework.security.vo.MontherException;
import java.util.List;

public class HealthFileChildrenBO extends HealthFileChildren
{
  List<ChildrenException> childrenException;
  List<MontherException> montherException;

  public List<ChildrenException> getChildrenException()
  {
    return this.childrenException;
  }
  public void setChildrenException(List<ChildrenException> childrenException) {
    this.childrenException = childrenException;
  }
  public List<MontherException> getMontherException() {
    return this.montherException;
  }
  public void setMontherException(List<MontherException> montherException) {
    this.montherException = montherException;
  }
}