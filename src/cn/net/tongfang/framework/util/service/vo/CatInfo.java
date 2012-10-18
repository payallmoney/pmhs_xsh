package cn.net.tongfang.framework.util.service.vo;

import java.io.Serializable;

import cn.net.tongfang.framework.security.vo.SamModule;
import cn.net.tongfang.framework.security.vo.SamModuleCategory;

@SuppressWarnings("serial")
public class CatInfo implements Serializable{
	
	private SamModule module;
	private SamModuleCategory category;
	private SamModuleCategory rootCategory;

	public CatInfo() {
		super();
	}

	public CatInfo(SamModule module, SamModuleCategory cate,SamModuleCategory rootCategory) {
		super();
		this.module = module;
		this.category = cate;
		this.rootCategory = rootCategory;
	}

	public SamModule getModule() {
		return module;
	}

	public void setModule(SamModule module) {
		this.module = module;
	}

	public SamModuleCategory getCategory() {
		return category;
	}

	public void setCategroy(SamModuleCategory cate) {
		this.category = cate;
	}

	public SamModuleCategory getRootCategory() {
		return rootCategory;
	}

	public void setRootCategory(SamModuleCategory rootCategory) {
		this.rootCategory = rootCategory;
	}

	
}
