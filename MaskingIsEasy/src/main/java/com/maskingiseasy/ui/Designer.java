package com.maskingiseasy.ui;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.maskingiseasy.model.Catalog;
import com.maskingiseasy.model.Database;
import com.maskingiseasy.model.Schema;
import com.maskingiseasy.model.Table;
import com.maskingiseasy.service.GeneralService;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;


@Route("designer")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle("Masking is easy")
@SpringComponent
public class Designer extends VerticalLayout   {

	//@Autowired(required=true)
	//GeneralService generalService;

	private static final long serialVersionUID = 1L;
	
	HorizontalLayout top=new HorizontalLayout();
	VerticalLayout left=new VerticalLayout();
	VerticalLayout main=new VerticalLayout();
	HorizontalLayout bottom=new HorizontalLayout();


	
	public Designer(@Autowired GeneralService generalService) {
		
		
		
		drawScreen(generalService);

		SplitLayout splitter=new SplitLayout();
		splitter.setOrientation(Orientation.HORIZONTAL);
		splitter.addToPrimary(left);
		splitter.addToSecondary(main);
		splitter.setWidth("100%");
		splitter.setSplitterPosition(15);

		
		top.setHeight("5%");
		splitter.setHeight("85%");
		bottom.setHeight("10%");

		
		add(top , splitter , bottom);
		setSizeFull();
		
		
		setFlexGrow(2, top);
		setFlexGrow(10, splitter);
		setFlexGrow(1, bottom);
		

	}


	private void drawScreen(GeneralService generalService) {
		drawTop(generalService);
		drawLeft(generalService);
		drawMain(generalService);
		drawBottom(generalService);
		
	}


	
	
	
	private void drawTop(GeneralService generalService) {
		Label lblWorkingDir=new Label("working @  :");
		lblWorkingDir.setText("Working @"+generalService.getWorkingDir());
		
		top.add(lblWorkingDir);
		
	}
	
	ComboBox<Database> comboForDatabase=new ComboBox<Database>("Database");
	ComboBox<Catalog> comboForCatalog=new ComboBox<Catalog>("Catalog");
	ComboBox<Schema> comboForSchema=new ComboBox<Schema>("Schema/Owner");
	TextField filterForTable=new TextField();
	CheckboxGroup<Table> listOfTablesInDatabase=new CheckboxGroup<Table>();
	
	private void drawLeft(GeneralService generalService) {
		
		ArrayList<Database> databaseList=generalService.getAvailableDatabaseList();
		comboForDatabase.setItems(databaseList);
		comboForDatabase.setItemLabelGenerator(p->p.getDatabaseType().getName()+" : " + p.getDatabaseName());
		comboForDatabase.addValueChangeListener(e-> {
			setCurrentDatabase(generalService, e.getValue());

		});
		
		
		
		
		comboForDatabase.setWidth("100%");
		comboForCatalog.setWidth("100%");
		comboForSchema.setWidth("100%");
		filterForTable.setWidth("100%");
		listOfTablesInDatabase.setWidth("100%");
		
		
		
		
		listOfTablesInDatabase.setLabel("List of Table(s)");
		filterForTable.setPlaceholder("Enter filter...");
		filterForTable.setPrefixComponent(VaadinIcon.FILTER.create());
		
		
		VerticalLayout formFilter=new VerticalLayout(comboForDatabase, comboForCatalog, comboForSchema, filterForTable);
		formFilter.setWidth("100%");
		formFilter.setHeight(null);
		formFilter.setSpacing(false);
		formFilter.setPadding(false);
		formFilter.setMargin(false);
		
		VerticalLayout formLeft=new VerticalLayout(formFilter,listOfTablesInDatabase);
		
		
		
		
		formLeft.setFlexGrow(1, formFilter);
		formLeft.setFlexGrow(10, listOfTablesInDatabase);
		formLeft.setSpacing(false);
		formLeft.setPadding(false);
		formLeft.setMargin(false);
		
		left.add(formLeft);
		
	}
	
	
	private void setCurrentDatabase(GeneralService generalService, Database newDatabase) {
		comboForCatalog.clear();
		comboForSchema.clear();
		filterForTable.clear();
		
		if (newDatabase==null) return;
		
		StringBuilder sbErr=new StringBuilder();
		newDatabase.connect();
		
		if (sbErr.length()>0) {
			Notification.show("Database connection to : " + newDatabase.getDatabaseName()+" is invalid. Error : " + newDatabase.getError(), 5000, Position.TOP_START);
			comboForDatabase.setInvalid(true);
			return;
		}
		
		
		


		fillCatalogList(newDatabase);
		
		
		
		
		
	}


	private void fillCatalogList(Database database) {
		ArrayList<Catalog> catalogList=new ArrayList<Catalog>();
		
		for (Table table : database.getTables()) {
			
			System.out.println("Checking table : "+table.getTableName() +" @"+table.getSchema()+"@"+table.getSchema().getCatalog());
			if (table.getSchema().getCatalog()==null) continue;
			if (table.getSchema().getCatalog().getCatalogName().length()==0) continue;
			if (catalogList.indexOf(table.getSchema().getCatalog())>-1) continue;
			
			catalogList.add(table.getSchema().getCatalog());
			System.out.println("Adding : "+table.getSchema().getCatalog().getCatalogName());
		}
		
		if (catalogList.size()==0) {
			Catalog emptyCatalog=new Catalog();
			emptyCatalog.setCatalogName("*");
			catalogList.add(emptyCatalog);
		}
		
		comboForCatalog.setItems(catalogList);
		
	}


	private void drawMain(GeneralService generalService) {
		// TODO Auto-generated method stub
		
	}


	
	private void drawBottom(GeneralService generalService) {
		// TODO Auto-generated method stub
		
	}

	
}
