package com.pmease.gitplex.web.page.account.setting;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import com.pmease.commons.wicket.component.tabbable.PageTab;

@SuppressWarnings("serial")
public class AccountSettingTab extends PageTab {

	public AccountSettingTab(IModel<String> titleModel, Class<? extends AccountSettingPage> mainPageClass) {
		super(titleModel, mainPageClass);
	}

	public AccountSettingTab(IModel<String> titleModel, Class<? extends AccountSettingPage> mainPageClass, 
			Class<? extends AccountSettingPage> additionalPageClass) {
		super(titleModel, mainPageClass, additionalPageClass);
	}

	@Override
	public Component render(String componentId) {
		return new AccountSettingTabLink(componentId, this);
	}

}