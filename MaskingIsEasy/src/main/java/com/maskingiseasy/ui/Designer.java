package com.maskingiseasy.ui;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

import com.maskingiseasy.libs.CommonLib;
import com.maskingiseasy.model.Application;
import com.maskingiseasy.model.Column;
import com.maskingiseasy.model.Condition;
import com.maskingiseasy.model.Database;
import com.maskingiseasy.model.DatabaseType;
import com.maskingiseasy.model.MaskingProfile;
import com.maskingiseasy.model.MaskingRule;
import com.maskingiseasy.model.Table;
import com.maskingiseasy.service.GenService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;


@SpringComponent
public class Designer extends Window   {

	
	GenService genService;

	private static final long serialVersionUID = 1L;
	
	VerticalLayout root=new VerticalLayout();

	
	VerticalLayout left=new VerticalLayout();
	VerticalLayout main=new VerticalLayout();
	VerticalLayout right=new VerticalLayout();




	
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
		
		HorizontalSplitPanel splitterRight=new HorizontalSplitPanel();
		splitterRight.setFirstComponent(main);
		splitterRight.setSecondComponent(right);
		splitterRight.setWidth("100%");
		splitterRight.setSplitPosition(70);

		
		HorizontalSplitPanel splitter=new HorizontalSplitPanel();
		splitter.setFirstComponent(left);
		splitter.setSecondComponent(splitterRight);
		splitter.setWidth("100%");
		splitter.setSplitPosition(20);
		splitter.setMinSplitPosition(10, Unit.PERCENTAGE);
		splitter.setMaxSplitPosition(50, Unit.PERCENTAGE);
		

		
		splitter.setHeight("100%");


		
		root.addComponents(splitter);
		root.setSizeFull();


		
		root.setSpacing(false);
		root.setMargin(false);
		
		setContent(root);
		
		

	}


	private void drawScreen() {
		drawLeft();
		drawMain();
		drawRight();

	}


	

	
	ComboBox<Database> comboForDatabase=new ComboBox<Database>("<big><b><font color=green>Database</font></b></big>");
	ComboBox<Application> comboForApplication=new ComboBox<Application>("<big><b><font color=green>Application</font></b></big>");

	Grid<Table> gridForTableInDatabase=new Grid<>(Table.class);
	Grid<Table> gridForTableInApplication=new Grid<>(Table.class);

	
	Grid<Column> gridForColumnInDatabase=new Grid<>(Column.class);
	Grid<Column> gridForColumnInApplication=new Grid<>(Column.class);
	
	
	private void drawLeft() {
		
		comboForDatabase.setCaptionAsHtml(true);
		comboForApplication.setCaptionAsHtml(true);
		
		comboForDatabase.addStyleName(ValoTheme.COMBOBOX_HUGE);
		comboForApplication.addStyleName(ValoTheme.COMBOBOX_HUGE);
		
		comboForDatabase.setIcon(VaadinIcons.DATABASE);
		comboForApplication.setIcon(VaadinIcons.COMPRESS);
		

		left.addStyleName(ValoTheme.LABEL_SUCCESS+" "+ValoTheme.LABEL_TINY);

		
		
		ArrayList<Database> databaseList=genService.getAvailableDatabaseList();
		comboForDatabase.setItems(databaseList);
		comboForDatabase.setItemCaptionGenerator(p->p.getDatabaseType().getName()+" : " + p.getDatabaseName());
		comboForDatabase.addValueChangeListener(e-> {
			setCurrentDatabase(e.getValue());
			fillApplicationList(e.getValue());

		});
		
		
		
		
		
		comboForDatabase.setWidth("100%");
		
		
		gridForTableInDatabase.setWidth("100%");
		gridForTableInDatabase.setSizeFull();
		
		
		
		gridForTableInDatabase.setCaption("List of Table(s)");
		gridForTableInDatabase.removeAllColumns();
		gridForTableInDatabase.addColumn(p->p.getTableName()).setHidable(true).setHidable(false).setId("table").setCaption("Table");
		gridForTableInDatabase.addComponentColumn(p->{
			Button btAddToApp=new Button();
			btAddToApp.addStyleName(ValoTheme.BUTTON_FRIENDLY+" "+ ValoTheme.BUTTON_TINY);
			btAddToApp.setIcon(VaadinIcons.PLUS);
			btAddToApp.addClickListener(event->{
				addTableToApplication(p);
			});
			return btAddToApp;
		}).setHidable(false).setHidden(false).setWidth(100).setResizable(false);
		gridForTableInDatabase.addColumn(p->(p.getSchema()!=null) ? p.getSchema().getSchemaName() : "" ).setHidable(true).setHidable(true).setId("schema").setCaption("Schema");
		gridForTableInDatabase.addColumn(p->(p.getSchema()!=null && p.getSchema().getCatalog()!=null) ? p.getSchema().getCatalog().getCatalogName() : "" ).setHidable(true).setHidable(false).setId("catalog").setCaption("Catalog");

		gridForTableInDatabase.setHeightByRows(10);
		
		gridForTableInDatabase.setSelectionMode(SelectionMode.SINGLE);
		
		HeaderRow headerRow=gridForTableInDatabase.addHeaderRowAt(1);
		headerRow.getCell("table").setComponent(CommonLib.makeFilterFieldWithEvent(event->{applyFilterForDatabaseTables();}));
		headerRow.getCell("schema").setComponent(CommonLib.makeFilterFieldWithEvent(event->{applyFilterForDatabaseTables();}));
		headerRow.getCell("catalog").setComponent(CommonLib.makeFilterFieldWithEvent(event->{applyFilterForDatabaseTables();}));


		gridForTableInDatabase.addItemClickListener(event->{
			selectTableInDatabase(event.getItem());
		});
		
		
		gridForColumnInDatabase.setWidth("100%");
		gridForColumnInDatabase.setSizeFull();
		gridForColumnInDatabase.setHeightByRows(8);
		gridForColumnInDatabase.removeAllColumns();
		gridForColumnInDatabase.addColumn(p->p.isPk() ? "<font color=red>"+VaadinIcons.KEY_O.getHtml()+"</font>" : null,  new HtmlRenderer("")).setId("pk").setHidable(true).setHidden(false).setCaption("").setWidth(50).setResizable(false);
		gridForColumnInDatabase.addColumn(p->p.getId()).setId("id").setHidable(true).setHidden(false).setCaption("#").setWidth(80).setResizable(false);;
		gridForColumnInDatabase.addColumn(p->p.getColumnName()).setId("column").setHidable(false).setHidden(false).setCaption("Column Name");
		gridForColumnInDatabase.addColumn(p->p.getColumnType()).setId("type").setHidable(true).setHidden(false).setCaption("Type");
		gridForColumnInDatabase.addColumn(p->p.getColumnSize()).setId("size").setHidable(true).setHidden(false).setCaption("Size").setWidth(80).setResizable(false);
		gridForColumnInDatabase.addColumn(p->p.isNullable() ? VaadinIcons.CHECK.getHtml() : null,  new HtmlRenderer("")).setId("nullable").setHidable(true).setHidden(false).setCaption("Null").setWidth(80).setResizable(false);;
		
		gridForColumnInDatabase.setSelectionMode(SelectionMode.NONE);
		
		VerticalLayout formLeft=new VerticalLayout(comboForDatabase,gridForTableInDatabase, gridForColumnInDatabase);
		


		
		formLeft.setSpacing(false);
		formLeft.setMargin(false);
		
		left.setSpacing(false);
		left.setMargin(false);
		left.setHeight(100, Unit.PERCENTAGE);
		
		left.addComponent(formLeft);
		
	}
	
	
	private void addTableToApplication(Table tableInDatabase) {
		if (comboForApplication.getValue()==null) {
			Notification.show("Should pick an application to proceed!", Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		
		if (comboForApplication.getValue().getTables().indexOf(tableInDatabase)>-1) {
			Notification.show("Table is already in the application", Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		
		if (tableInDatabase.getColumns().size()==0) {
			tableInDatabase.setColumns(DatabaseType.getColumnList(genService, comboForDatabase.getValue(), tableInDatabase));
		}
		
		
		comboForApplication.getValue().getTables().add(tableInDatabase);
		Notification.show("Table "+tableInDatabase.getTableName()+" is added to application.");
		
		applyFilterForApplicationTables();
	}


	private void fillApplicationList(Database database) {
		if (database==null) {
			comboForApplication.clear();
			return;
		}
		
		ArrayList<Application> appList=genService.getApplicationList(Application.APPLICATION_TYPE_STATIC_MASKING,database.getDatabaseType().getName());
		comboForApplication.setItems(appList);
		
		
	}


	private void drawRight() {
		
		
		
		ArrayList<Application> applicationList=new ArrayList<Application>();
		comboForApplication.setItems(applicationList);
		comboForApplication.setItemCaptionGenerator(p-> p.getDatabaseType()+" : "+ p.getApplicationName());
		comboForApplication.addValueChangeListener(e-> {
			setCurrentApplication(e.getValue());

		});
		
		
		
		
		
		comboForApplication.setWidth("100%");
		
		
		gridForTableInApplication.setWidth("100%");
		gridForTableInApplication.setSizeFull();
		
		
		
		gridForTableInApplication.setCaption("List of Table(s)");
		gridForTableInApplication.removeAllColumns();
		gridForTableInApplication.addComponentColumn(p->{
			Button btRemoveFromApp=new Button();
			btRemoveFromApp.addStyleName(ValoTheme.BUTTON_DANGER+" "+ ValoTheme.BUTTON_TINY);
			btRemoveFromApp.setIcon(VaadinIcons.MINUS);
			btRemoveFromApp.addClickListener(event->{
				removeTableFromApplication(p);
			});
			return btRemoveFromApp;
		}).setHidable(false).setHidden(false).setWidth(100).setResizable(false);
		gridForTableInApplication.addColumn(p->p.getTableName()).setHidable(true).setHidable(false).setId("table").setCaption("Table");
		gridForTableInApplication.addColumn(p->p.isMasked() ? VaadinIcons.STAR.getHtml() : null, new HtmlRenderer("")).setHidable(true).setHidable(false).setId("masked").setCaption("Mask");
		gridForTableInApplication.addColumn(p->(p.getSchema()!=null) ? p.getSchema().getSchemaName() : "" ).setHidable(true).setHidable(true).setId("schema").setCaption("Schema");
		gridForTableInApplication.addColumn(p->(p.getSchema()!=null && p.getSchema().getCatalog()!=null) ? p.getSchema().getCatalog().getCatalogName() : "" ).setHidable(true).setHidable(false).setId("catalog").setCaption("Catalog");
		
		gridForTableInApplication.setHeightByRows(20);
		
		gridForTableInApplication.setSelectionMode(SelectionMode.SINGLE);
		
		HeaderRow headerRow=gridForTableInApplication.addHeaderRowAt(1);
		headerRow.getCell("table").setComponent(CommonLib.makeFilterFieldWithEvent(event->{applyFilterForApplicationTables();}));
		headerRow.getCell("schema").setComponent(CommonLib.makeFilterFieldWithEvent(event->{applyFilterForApplicationTables();}));
		headerRow.getCell("catalog").setComponent(CommonLib.makeFilterFieldWithEvent(event->{applyFilterForApplicationTables();}));


		gridForTableInApplication.addItemClickListener(event->{
			selectTableInApplication(event.getItem());
		});
		
	

		
		VerticalLayout formRight=new VerticalLayout(comboForApplication,gridForTableInApplication);
		


		
		formRight.setSpacing(false);
		formRight.setMargin(false);
		
		right.setSpacing(false);
		right.setMargin(false);
		right.setHeight(100, Unit.PERCENTAGE);
		
		right.addComponent(formRight);
		
	}
	
	
	private void removeTableFromApplication(Table table) {
		if (comboForApplication.getValue()==null) return;
		if (table==null) return;
		
		comboForApplication.getValue().getTables().remove(table);
		applyFilterForApplicationTables();
		
	}


	private void setCurrentApplication(Application application) {
		if (application==null) {
			gridForTableInApplication.setItems(new ArrayList<Table>());
			selectTableInApplication(null);
			return;
		}
		gridForTableInApplication.getColumn("catalog").setHidden(!comboForDatabase.getValue().getDatabaseType().isHasCatalog());
		gridForTableInApplication.getColumn("schema").setHidden(!comboForDatabase.getValue().getDatabaseType().isHasSchema());
		
		applyFilterForApplicationTables();
		
	}


	private void selectTableInApplication(Table table) {
		if (table==null) {
			gridForColumnInApplication.setItems(new ArrayList<Column>());
			gridForColumnInApplication.setVisible(false);
			maskLayout.setVisible(false);
			return;
		}
		
		gridForColumnInApplication.setVisible(true);
		maskLayout.setVisible(true);
		
		gridForColumnInApplication.setItems(table.getColumns());
		gridForColumnInApplication.setCaption("<big><b><font color=green>["+table.getTableName()+"]</font></b></big>");
		
		
	}


	private void selectTableInDatabase(Table table) {
		if (table==null || comboForDatabase.getValue()==null) {
			gridForColumnInDatabase.setItems(new ArrayList<Column>());
			return;
			
		}
		
		if (table.getColumns().size()>0) {
			gridForColumnInDatabase.setItems(table.getColumns());
		} else {
			ArrayList<Column> columnList=DatabaseType.getColumnList(genService, comboForDatabase.getValue(), table);
			gridForColumnInDatabase.setItems(columnList);
			table.getColumns().clear();
			table.getColumns().addAll(columnList);
		}
		
		
		
		
		
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

	
	private void applyFilterForApplicationTables() {
		String fTable=CommonLib.getFilterFieldByCellId(gridForTableInApplication, "table").toUpperCase(Locale.ENGLISH).trim();
		String fSchema=CommonLib.getFilterFieldByCellId(gridForTableInApplication, "schema").toUpperCase(Locale.ENGLISH).trim();
		String fCatalog=CommonLib.getFilterFieldByCellId(gridForTableInApplication, "catalog").toUpperCase(Locale.ENGLISH).trim();
		
		
		
		if (comboForApplication.getValue()==null) {
			gridForTableInApplication.setItems(new ArrayList<Table>());
			return;
		}
		
		Database currentDatabase=comboForDatabase.getValue();
		boolean hasSchema=currentDatabase.getDatabaseType().isHasSchema();
		boolean hasCatalog=currentDatabase.getDatabaseType().isHasCatalog();
		
		Application currentApplication=comboForApplication.getValue();
		
		if (currentApplication==null) {
			gridForTableInApplication.setItems(new ArrayList<Table>());
			return;
		}
		
		ArrayList<Table> filtered=new ArrayList<Table>();
		
		for (Table table : currentApplication.getTables()) {
			if (fTable.length()>0 && !table.getTableName().toUpperCase(Locale.ENGLISH).contains(fTable)) continue;
			if (fSchema.length()>0 && hasSchema && !table.getSchema().getSchemaName().toUpperCase(Locale.ENGLISH).contains(fSchema)) continue;
			if (fCatalog.length()>0 && hasCatalog && !table.getSchema().getCatalog().getCatalogName().toUpperCase(Locale.ENGLISH).contains(fCatalog)) continue;
			filtered.add(table);
		}
		
		gridForTableInApplication.setItems(filtered);
		gridForTableInApplication.setCaption("Table(s) found : "+filtered.size());
		
	}

	private void setCurrentDatabase(Database newDatabase) {
		
		if (newDatabase==null) {
			applyFilterForDatabaseTables();
			return;
		}
		
		newDatabase.connect();
		
		if (newDatabase.isValid()==false || CommonLib.nvl(newDatabase.getError(), "").length()>0) {
			applyFilterForDatabaseTables();
			selectTableInDatabase(null);
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




	VerticalLayout maskLayout=new VerticalLayout();

	private void drawMain() {
		
		



		gridForColumnInApplication.removeAllColumns();
		gridForColumnInApplication.addColumn(p->p.isPk() ? "<font color=red>"+VaadinIcons.KEY_O.getHtml()+"</font>" : null,  new HtmlRenderer("")).setId("pk").setHidable(true).setHidden(false).setCaption("").setWidth(50).setResizable(false);
		gridForColumnInApplication.addColumn(p->p.getId()).setId("id").setHidable(true).setHidden(false).setCaption("#").setWidth(80).setResizable(false);;
		gridForColumnInApplication.addColumn(p->p.getColumnName()).setId("column").setHidable(false).setHidden(false).setCaption("Column Name");
		gridForColumnInApplication.addColumn(p->p.getColumnType()).setId("type").setHidable(true).setHidden(false).setCaption("Type");
		gridForColumnInApplication.addColumn(p->p.getColumnSize()).setId("size").setHidable(true).setHidden(false).setCaption("Size").setWidth(80).setResizable(false);
		gridForColumnInApplication.addColumn(p->p.isNullable() ? VaadinIcons.CHECK.getHtml() : null,  new HtmlRenderer("")).setId("nullable").setHidable(true).setHidden(false).setCaption("Null").setWidth(80).setResizable(false);
		gridForColumnInApplication.addColumn(p->p.getMaskingRules().size()==0 ? null : VaadinIcons.STAR.getHtml(),  new HtmlRenderer("")).setId("masking").setHidable(true).setHidden(false).setCaption("Masked").setWidth(80).setResizable(false);
		gridForColumnInApplication.addColumn(p->p.getDescription()).setId("description").setHidable(true).setHidden(false).setCaption("Description");
		
		gridForColumnInApplication.setCaptionAsHtml(true);
		gridForColumnInApplication.setIcon(VaadinIcons.TABLE);
		
		gridForColumnInApplication.setSelectionMode(SelectionMode.SINGLE);
		
		gridForColumnInApplication.setSizeFull();

		gridForColumnInApplication.addSelectionListener(event->{
			setMaskingRuleEditor(event.getFirstSelectedItem());
		});
		
		maskLayout.setSizeFull();
		
		maskLayout.setCaptionAsHtml(true);
		maskLayout.setIcon(VaadinIcons.STAR);
		
		main.addComponents(gridForColumnInApplication,maskLayout);
		
		main.setExpandRatio(gridForColumnInApplication, 3);
		main.setExpandRatio(maskLayout, 1);
		
		
		main.setSpacing(false);
		main.setMargin(false);
		
		main.setSizeFull();
		
	}


	Column selectedCol;
	
	private void setMaskingRuleEditor(Optional<Column> selection) {
		
		maskLayout.removeAllComponents();
		
		if (!selection.isPresent()) {
			maskLayout.setCaption("");
			return;
		}

		selectedCol=selection.get();
		
		maskLayout.setCaption("<big><b><font color=green>Masking configuration : ["+selectedCol.getColumnName()+"]</font></b></big>");

		
		Button btMask=new Button("MASK with *");
		btMask.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btMask.addClickListener(event->addMaskingRule(selectedCol));
		
		
		Button btRemoveMask=new Button("Remove Mask");
		btRemoveMask.addStyleName(ValoTheme.BUTTON_DANGER);
		btRemoveMask.addClickListener(event->removeMaskingRule(selectedCol));
		
		
		
		
		
		maskLayout.addComponent(btMask);
		maskLayout.addComponent(btRemoveMask);
		
	}
	
	private void addMaskingRule(Column col) {
		MaskingRule rule=new MaskingRule();
		Condition cond=new Condition();
		cond.setConditionOrder(Integer.valueOf(1));
		cond.setConditionType(Condition.CONDITION_TYPE_ALLWAYS);
		rule.setCondition(cond);
		
		MaskingProfile maskingProfile=MaskingProfile.buildHideProfile(2,"*",false);
		rule.setMaskingProfile(maskingProfile);

		col.getMaskingRules().add(rule);
		
		selectTableInApplication(gridForTableInApplication.getSelectedItems().iterator().next());
		gridForColumnInApplication.select(col);
		
	}
	
	private void removeMaskingRule(Column col) {
		col.getMaskingRules().clear();
		selectTableInApplication(gridForTableInApplication.getSelectedItems().iterator().next());
		gridForColumnInApplication.select(col);
	}



	
}
