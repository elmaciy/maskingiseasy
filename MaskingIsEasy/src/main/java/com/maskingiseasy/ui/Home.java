package com.maskingiseasy.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("home")
public class Home extends VerticalLayout   {


	private static final long serialVersionUID = 1L;
	
	

	public Home() {
		Button bt=new Button("selam");
		add(bt);
	}
}
