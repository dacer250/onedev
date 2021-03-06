package io.onedev.server.web.page.security;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import io.onedev.server.OneDev;
import io.onedev.server.manager.SettingManager;
import io.onedev.server.web.WebSession;
import io.onedev.server.web.page.base.BasePage;

@SuppressWarnings("serial")
public class LogoutPage extends BasePage {

	public LogoutPage(PageParameters params) {
		super(params);
		
		WebSession.get().logout();
		
		if (getLoginUser() != null || OneDev.getInstance(SettingManager.class).getSecuritySetting().isEnableAnonymousAccess())
			getSession().warn("You've been logged out");
        
        throw new RestartResponseException(getApplication().getHomePage());
	}
	
}
