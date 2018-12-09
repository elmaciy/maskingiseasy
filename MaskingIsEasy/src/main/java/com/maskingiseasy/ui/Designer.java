package com.maskingiseasy.ui;

import java.util.ArrayList;
import java.util.Locale;

import com.maskingiseasy.libs.CommonLib;
import com.maskingiseasy.model.Database;
import com.maskingiseasy.model.DatabaseType;
import com.maskingiseasy.model.Table;
import com.maskingiseasy.service.GenService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.HeaderRow;


@SpringComponent
public class Designer extends Window   {

	
	GenService genService;

	private static final long serialVersionUID = 1L;
	
	VerticalLayout root=new VerticalLayout();

	
	HorizontalLayout top=new HorizontalLayout();
	VerticalLayout left=new VerticalLayout();
	VerticalLayout main=new VerticalLayout();
	HorizontalLayout bottom=new HorizontalLayout();


	
	public Designer(GenService generalService) {
		
		super();
		
		this.genService=generalService;

		setModal(true);
		setWidth(100, Unit.PERCENTAGE);
		setHeight(100, Unit.PERCENTAGE);
		setResizable(false);
		setClosable(true);
		
		
		
		//generalService.makeSureWorkingDirectoriesExists();
		
		drawScreen();
		
		HorizontalSplitPanel splitter=new HorizontalSplitPanel();
		splitter.setFirstComponent(left);
		splitter.setSecondComponent(main);
		splitter.setWidth("100%");
		splitter.setSplitPosition(15);
		splitter.setMinSplitPosition(5, Unit.PERCENTAGE);
		splitter.setMaxSplitPosition(50, Unit.PERCENTAGE);
		

		
		top.setHeight("5%");
		splitter.setHeight("85%");
		bottom.setHeight("10%");

		
		root.addComponents(top , splitter , bottom);
		root.setSizeFull();
		
		root.setExpandRatio(top, 1);
		root.setExpandRatio(splitter, 10);
		root.setExpandRatio(bottom, 1);
		
		root.setSpacing(false);
		root.setMargin(false);
		
		setContent(root);
		
		

	}


	private void drawScreen() {
		drawTop();
		drawLeft();
		drawMain();
		drawBottom();
		
	}


	
	
	
	private void drawTop() {
		Label lblWorkingDir=new Label("Working @"+genService.getWorkingDir());
		top.addComponent(lblWorkingDir);
		
	}
	
	ComboBox<Database> comboForDatabase=new ComboBox<Database>("Database");

	Grid<Table> gridForTableInDatabase=new Grid<>(Table.class);
	
	
	private void drawLeft() {
		
		ArrayList<Database> databaseList=genService.getAvailableDatabaseList();
		comboForDatabase.setItems(databaseList);
		comboForDatabase.setItemCaptionGenerator(p->p.getDatabaseType().getName()+" : " + p.getDatabaseName());
		comboForDatabase.addValueChangeListener(e-> {
			setCurrentDatabase(e.getValue());

		});
		
		
		
		
		
		comboForDatabase.setWidth("100%");
		
		
		gridForTableInDatabase.setWidth("100%");
		gridForTableInDatabase.setSizeFull();
		
		
		
		gridForTableInDatabase.setCaption("List of Table(s)");
		gridForTableInDatabase.removeAllColumns();
		gridForTableInDatabase.addColumn(p->p.getTableName()).setHidable(true).setHidable(false).setId("table").setCaption("Table");
		gridForTableInDatabase.addColumn(p->(p.getSchema()!=null) ? p.getSchema().getSchemaName() : "" ).setHidable(true).setHidable(false).setId("schema").setCaption("Schema");
		gridForTableInDatabase.addColumn(p->(p.getSchema()!=null && p.getSchema().getCatalog()!=null) ? p.getSchema().getCatalog().getCatalogName() : "" ).setHidable(true).setHidable(false).setId("catalog").setCaption("Catalog");
		gridForTableInDatabase.addColumn(p->p.getObjectType().substring(0, 1)).setHidable(true).setHidable(false).setId("objectType").setCaption("Type");

		gridForTableInDatabase.setHeightByRows(10);
		
		gridForTableInDatabase.setSelectionMode(SelectionMode.SINGLE);
		
		HeaderRow headerRow=gridForTableInDatabase.addHeaderRowAt(1);
		headerRow.getCell("table").setComponent(CommonLib.makeFilterFieldWithEvent(event->{applyFilterForDatabaseTables();}));
		headerRow.getCell("schema").setComponent(CommonLib.makeFilterFieldWithEvent(event->{applyFilterForDatabaseTables();}));
		headerRow.getCell("catalog").setComponent(CommonLib.makeFilterFieldWithEvent(event->{applyFilterForDatabaseTables();}));


		
		VerticalLayout formLeft=new VerticalLayout(comboForDatabase,gridForTableInDatabase);
		


		
		formLeft.setSpacing(false);
		formLeft.setMargin(false);
		
		left.setSpacing(false);
		left.setMargin(false);
		left.setHeight(100, Unit.PERCENTAGE);
		
		left.addComponent(formLeft);
		
	}
	
	
	private void applyFilterForDatabaseTables() {
		String fTable=CommonLib.getFilterFieldByCellId(gridForTableInDatabase, "table").toUpperCase(Locale.ENGLISH).trim();
		String fSchema=CommonLib.getFilterFieldByCellId(gridForTableInDatabase, "schema").toUpperCase(Locale.ENGLISH).trim();
		String fCatalog=CommonLib.getFilterFieldByCellId(gridForTableInDatabase, "catalog").toUpperCase(Locale.ENGLISH).trim();
		
		ArrayList<Table> filtered=new ArrayList<Table>();
		
		if (comboForDatabase.getValue()==null) {
			gridForTableInDatabase.setItems(filtered);
			return;
		}
		
		Database currentDatabase=comboForDatabase.getValue();
		boolean hasSchema=currentDatabase.getDatabaseType().isHasSchema();
		boolean hasCatalog=currentDatabase.getDatabaseType().isHasCatalog();
		
		for (Table table : currentDatabase.getTables()) {
			if (fTable.length()>0 && !table.getTableName().toUpperCase(Locale.ENGLISH).contains(fTable)) continue;
			if (fSchema.length()>0 && hasSchema && !table.getSchema().getSchemaName().toUpperCase(Locale.ENGLISH).contains(fSchema)) continue;
			if (fCatalog.length()>0 && hasCatalog && !table.getSchema().getCatalog().getCatalogName().toUpperCase(Locale.ENGLISH).contains(fCatalog)) continue;
			filtered.add(table);
		}
		
		gridForTableInDatabase.setItems(filtered);
		gridForTableInDatabase.setCaption("Table(s) found : "+filtered.size());
		
	}


	private void setCurrentDatabase(Database newDatabase) {
		
		if (newDatabase==null) {
			applyFilterForDatabaseTables();
			return;
		}
		
		newDatabase.connect();
		
		if (newDatabase.isValid()==false || CommonLib.nvl(newDatabase.getError(), "").length()>0) {
			applyFilterForDatabaseTables();
			Notification.show("Database connection to : " + newDatabase.getDatabaseName()+" is invalid. Error : " + newDatabase.getError(), Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		
		
		newDatabase.testConnection();
		
		if (newDatabase.isValid()==false || newDatabase.getTables().size()==0)
			DatabaseType.getTableList(genService, newDatabase);
		
		applyFilterForDatabaseTables();


		
		gridForTableInDatabase.getColumn("catalog").setHidden(!newDatabase.getDatabaseType().isHasCatalog());
		gridForTableInDatabase.getColumn("schema").setHidden(!newDatabase.getDatabaseType().isHasSchema());
		
		
	}






	private void drawMain() {
		// TODO Auto-generated method stub
		
	}


	
	private void drawBottom() {
		// TODO Auto-generated method stub
		
	}

	
}
