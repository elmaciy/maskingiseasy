package com.maskingiseasy.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.maskingiseasy.service.GeneralService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@SpringComponent
@Route("home")
@PageTitle("Masking is easy")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class Home extends VerticalLayout   {


	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	GeneralService generalService;

	public Home() {

		Button bt=new Button("selam");
		bt.addClickListener(event-> {
			Notification.show("clicked", 10000, Position.MIDDLE);
		});
		add(bt);
	}
}
