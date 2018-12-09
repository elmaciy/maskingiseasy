package com.maskingiseasy.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.maskingiseasy.libs.CommonLib;
import com.maskingiseasy.service.GenService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI(path="/home")
@Theme("valo")
public class Home extends UI   {


	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	GenService genService;
	
	VerticalLayout root=new VerticalLayout();


	@Override
	protected void init(VaadinRequest request) {
		
		Designer designer=new Designer(genService);
		UI.getCurrent().addWindow(designer);

		
		setContent(root);
		
		CommonLib.setStyle(UI.getCurrent());
			
		
	}

	
}
