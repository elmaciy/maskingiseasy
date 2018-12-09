package com.maskingiseasy.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.maskingiseasy.service.GeneralService;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@SpringComponent
@Route("designer")
@Theme(Lumo.class)
@PageTitle("Masking is easy")
public class Designer extends VerticalLayout   {

	@Autowired
	GeneralService generalService;

	private static final long serialVersionUID = 1L;
	
	HorizontalLayout top=new HorizontalLayout();
	VerticalLayout left=new VerticalLayout();
	VerticalLayout main=new VerticalLayout();
	HorizontalLayout bottom=new HorizontalLayout();


	
	public Designer() {
			
		
		drawScreen();

		SplitLayout splitter=new SplitLayout();
		splitter.setOrientation(Orientation.HORIZONTAL);
		splitter.addToPrimary(left);
		splitter.addToSecondary(main);
		splitter.setSizeFull();
		
		add(top , splitter , bottom);
		
		setFlexGrow(2, top);
		setFlexGrow(10, splitter);
		setFlexGrow(1, top);
		

	}


	private void drawScreen() {
		drawTop();
		drawLeft();
		drawMain();
		drawBottom();
		
	}


	

	private void drawTop() {
		Label lblWorkingDir=new Label();
		lblWorkingDir.setText("Working @"+generalService.getWorkingDir());
		
		top.add(lblWorkingDir);
		
	}
	
	private void drawLeft() {
		// TODO Auto-generated method stub
		
	}
	
	private void drawMain() {
		// TODO Auto-generated method stub
		
	}


	
	private void drawBottom() {
		// TODO Auto-generated method stub
		
	}

	
}
