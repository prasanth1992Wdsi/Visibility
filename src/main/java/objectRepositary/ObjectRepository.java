package objectRepositary;

import org.openqa.selenium.By;

public class ObjectRepository {
//kit
	public static final String NVNU_BOX="(//*[@data-label='QUANTITY' and @id='BinnerDashboard_BoxLabel'])[1]";
	public final String CLOSEKIT="//span[contains(.,' × ')]";
	public static final String CARTLABELFIELD="//input[contains(@id,'cartLabel')]";
	public int SHORTWAIT=10;
	//bulk dispatch
    public static final String SCAN_DROPDOWN="//select [@id='Dispatch Process']";
    public static final String SELECTALL_BTN="(//input[@id='PO_CheckBoxId'])[2]";
    public static final String BULK_SUBMIT="(//button[@id='Despatch_back'])[3]";
    public static final String DISPATCH_FINISH="//*[@id='Despatch_finish']";
	//CARTLABEL

	public static final String CANCELNVPART = "//b[contains(text(),'PARTNVFIFO')]";
	public static final String BINNING_SHORT_BTN="//span[contains(text(),'SHORT')]";
	public static final String BINNING_SUBMIT_BTN1="//*[@id='BinnerDashboard_Submit']/span[1]";
	public static final String BINNINGORDERFORM="//b[text()='Binning']";
	public static final String PROGRESS=".//img[@class='buttonProgress']";
	public static final String DOCK="(//*[contains(.,'Docked ')])[12]";
	//public static final	String VEH="(//td[contains(@id,'vehNumber')])[1]";
	public static final    String VEH="//*[@id='DashboardPO_Table']/td[5]/span/img";
	public static final String BINNING_GRID="//b[text()='Binning']";
	public static final String SUBMIT_BTN="//span[text()=' SUBMIT']";
	public static final String OUTBOUNDLABEL="//b[text()='Outbound Label']";
	public static final String ORDER_ID="//*[@id='root']/div/div/main/div/div/div[2]/div[3]/div/div[1]/table/tbody/tr/td[1]";
	public static final String VERIFY_2="//*[contains(text(),'1-1 of 1')]";
	public static final String VERIFY_3="//span[contains(text(),'1-3 of 3')]";
	public static final String VLPN_ENTER="//*[@id='Picking_serialLPN']";
	public static final String VLPN_QNTY="//*[@id='Picking_enterQty']";
	public static final String VLPN_SUBMIT="//*[@id='Picking_SubmitButton']";
	public static final String NOOFLABELS="//*[@id='nooflabels']";
	public static final String PRINT="//*[text()='Print']";
	public static final String VPICKED_QNTY="//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[2]/div[2]/span[2]";
	public static final String INFO_CLOSE="//*[@id='simple-popper']/div[2]/p/div[1]/span";
	public static final String INFO_OPEN="//img[@alt='o']";
	public static final String VPART_TABLE="//*[@id='simple-popper']/div[2]/p/div[2]/table";
	public static final String INFO_CLICK="(//img[contains(@alt,'o')])[5]";
	public static final String NVLPNS="//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr[2]/td[4]";
	//RETURN ORDER
	public static final String INBOUND="//*[text()='Inbound']";
	public static final String RETURN_BTN="//*[@id='PO_Return']";
	public static final String SCAN_PART="//*[@id='PO_ScanPart']";
	public static final String SCAN_LPN="//input[@id='PO_ScanLpn']";
	public static final String POSCAN_BTN="//*[@id='PO_Scan']";
	public static final String PO_OK="//*[@id='PO_Ok']";
	public static final String PO_STOCKTYPE="//*[@id='PO_STOCKTYPE']/div/div[1]";
	public static final String PO_SAVE="//*[@id='PO_Save'][1]";
	public static final String RETURN_YES="//*[text()='YES']";
	public static final String RETURN_CLOSE_BTN="//span[@class='close']";
	public static final String RETURN_COMPLETE_BTN="//button[@id='DockIn_FinishUpload']";
	public static final String RETURN_QUANTITY_BOX="//*[@id='PO_quantity']";
	public static final String RETURN_CUST_REF="//*[@id='PO_customerReference']";
	public static final String RETURN_REF_2="//input[@id='PO_reference2']";
	public static final String RETURN_REF_3="//input[@id='PO_reference3']";


	public static final String Orders_In_BinnerAssignment="(//*[contains(.,'1 of 1')])[16]";
	public static final String USERNAME=".//input[contains(@name,'username')]";
	public static final String PASSWORD=".//input[contains(@name,'password')]";
	public static final String LOGIN_BTN=".//button[contains(@type,'submit')]";
	public static final String DASHBOARD="Dashboard";
	public static final String ASSIGNPOD_MENU="Assign POD";
	public static final String BINNINGVQTN="//td[@id='BinnerDashboard_BalanceOrReceivedQuantity']/b";
	public static final String SEARCH_BOX=".//input[contains(@type,'search')]";
	public static final String ASSIGNPODSEARCH_BOX="//input[@id='DockIn_searchfield']";
	public static final String Validation_POD="(//*[contains(.,'0 of 0')])[16]";
	public static final String ROW1="(//tr[@id='row'])[1]";
	public static final String ASSIGNORDERAPI="//tr[@id='row']";
	public static final String CANCEL_PART_CHECKBOX1="(//input[@type='checkbox'])";
	//
	
	public final String SCANBTN="(//*[@id='BinnerDashboard_Scanbutton'])[1]";
	public final String NVSCAN="(//*[contains(.,'SCANNED')])[14]";
	public final String PICKSEARCH="//button[@id='Picking-searchbutton']";
	public final String PICKGENSEARCH="//button[@id='PickGen_searchButton']";

	public final String BINPLEASE="(//*[contains(.,'Please')])[14]";
	public final String PICKPLEASE="(//*[contains(.,'Please')])[16]";
	public final String CLEAR="//div[@class='Clear_field']";
	//public final String VEHICLE1="(//td[contains(@id,'vehNumber')])[1]";
	public final String VEHICLE1="//*[@id='DashboardPO_Table']/td[5]/span/img";
	public final String BINNINGENTER="(//*[contains(.,'Enter')])[14]";
	public final String BINSUCCESS="(//*[contains(.,'Successfully')])[14]";
	public final String NVNULINE=".//td[contains(@class,'tablerow')][4]";
	public final String CREATEREQ="(//*[contains(.,'CREATE REQUEST')])[13]";

	public static final String PICKINGVQTN="/html/body/div/div/div/main/div/div/div/div[3]/div/div/p/form/div[2]/div[2]/span[2]";
	public static final String LABELNO="/html/body/div[2]/div[2]/div/div[5]/button[2]/span[1]";
	public static final String ASSIGNPODSELECT="//input[@id='AssignPod_VehicleNumber_Checkbox 0']";
	//public static final String DOCKIN_BTN="//*[@id=\"root\"]/div/div/main/div/div/div[2]/div/div[1]/div[2]/div/div/div/div/div[2]";
	public static final String DOCKIN_BTN="//*[@id='PO_PopupAssignPOD']/button[2]/span[1]";
	public static final String CREATEREQUEST_BTN="//*[@id=\"root\"]/div/div/main/div/div/div/div[1]/div[2]/button[2]/span[1]";
	public static final String CUSTOMER_NAME=".//*[@id=\"customername\"]";
	//*[@id="doctytpe"]
	public static final String DOC_TYPE="//select[@id='PO_DocType']";
	public static final String DOC_NO="//*[@id='PO_DocNumber']";
	//public static final String DOC_SAVE=".//*[@id=\"simple-popper\"]/div/p/form/div[5]/button[1]";
	public static final String DOC_SAVE="//span[contains(text(),'SAVE')]";
	public static final String PO_CHOOSE_FILE="//*[@id='PO_fileupload']";
	//public static final String PO_CHOOSE_FILE="/html/body/div/div/div/main/div/div/div/form/div[4]/div/input";
	public static final String UPLOAD_PO=".//*[contains(text(),'UPLOAD PO')]";
	public static final String ASSIGNPODSEARCH="//input[@id='standard-search']";
	public static final String PICKERNAME="(//input[@name='AutomationTestUser'])";
	public static final String POD_OPTION=".//*[contains(text(),'Assign POD')]";
	public static final String DOCKIN_OPTION="//*[@id=\"root\"]/div/div/main/div/div/div[2]/div/div[1]/div[2]/div/div/div/div/div[2]";
	public static final String ASSIGN_POD_USER=".//*[contains(text(),'ASSIGN')]";
	//public static final String ASSIGN_POD_USER="/html/body/div[2]/div/div/main/div/div/div[2]/div/div[1]/table/tbody/tr[1]/td[6]/button/span[1]";
	public static final String POD_USER_CHECKBOX="//*[@id=\"simple-popper\"]/div[2]/p/div[2]/table/div/tbody/tr/td[2]/span/span[1]/input";
	public static final String ASSIGN_USER_POD_BTN=".//*[contains(text(),'ASSIGN USER-POD')]";
	public static final String POD_ASIGN_SUCES_CLOSE_BTN="/html/body/div/div/div/main/div/div/div[2]/div/div[2]/div[2]/div/div/div/div/div/div/span";
	//public static final String POD_ASIGN_SUCES_CLOSE_BTN="/html/body/div[2]/div/div/main/div/div/div[2]/div/div[2]/div[2]/div/div/div/div/div/div/span";
	public static final String PO_SUCES_POP_CLOSE_BTN="/html/body/div/div/div/main/div/div/div/div[3]/div/div[3]/div/div/div/div/div/div/span";                                            
	//    public static final String POD_DASHBOARD="//*[@id=\"root\"]/div/div/div/div/ul/li[1]/ul/li[4]/a/b";

	public static final String EXPECTED_QTY=".//input[contains(@id,'expectedpkg')]";
	public static final String RECEIVED_QTY=".//input[contains(@id,'received')]";
	public static final String DAMAGED_QTY=".//input[contains(@name,'damaged')]";
	public static final String POD_SAVE_BTN=".//button[contains(@id,'buttonSave')]";
	public static final String DAMAGE_CHECK_BOX=".//input[contains(@id,'damageCheck')]";
	public static final String POD_SUCCESS_POPUP="//*[@id=\"root\"]/div/div/main/div/div/div[3]/div/div/div/div/div/div";
	public static final String POD_CLOSE_BTN="//*[@id=\"root\"]/div/div/main/div/div/div[3]/div/div/div/div/div/div/span";
	public static final String DASHBOARD_MENU="(//b[contains(text(),'Dashboard')])[1]";
	public static final String FINISH_UPLOAD_BTN="//span[contains(text(),'COMPLETE')]";
	public static final String PROFILE_ICON="//*[@id=\"root\"]/div/header/ul/li[3]/a";
	public static final String LOGOUT_BTN=".//*[@id=\"root\"]/div/header/ul/li[3]/div/button";
	public static final String POD_APPROVAL_OPTION="#root > div > div > main > div > div > div:nth-child(5) > div > div > div:nth-child(3) > div > div:nth-child(3) > div > select";
	public static final String WHAPPROVAL_CLOSE_BTN="//*[@id=\"root\"]/div/div/main/div/div/div[3]/div/div[1]/div[2]/div/div/div/div/div/div/a";
	public static final String BINNERASSIGN_CHECK_BOX="(//input[@id='BinnerAssignment_Checkbox 0'])";
	public static final String ASSIGN_BINNER_BTH="//button[@id='BinnerAssignment_AssignBinning']";
	public static final String BINNER_USER_CHECK_BOX="#simple-popper > div.jss1224.jss1234.jss1225.jss1736 > p > table > div > tbody > tr:nth-child(1) > td:nth-child(2) > span > span.jss1220 > input";
	public static final String ASSIGN_BINNING_BTN="/html/body/div[6]/div[2]/p/div[3]/button/span[1]";
	public static final String BINNER_ASSIGNED_POPUP_CLOSE_BT="//*[@id=\"root\"]/div/div/main/div/div/div[2]/div/div[3]/div[3]/div/div/div/div/div/div/a";
	public static final String PRINT_LABEL_BTN="/html/body/div[1]/div/div/main/div/div/div[2]/button[2]/span[1]";
	public static final String ADD_LABEL_QTY_TXTBOX=".//input[contains(@id,'AddLabel_0')]";
	public static final String ADD_LABEL_PRINT_BTN=".//button[contains(@id,'PrintButton')]";
	public static final String BINNER_ASSIGNMENT_MEU="//button[@id='BinnerAssignment_AssignBinning']";
	public static final String UPLOAD_PO_AGAIN_BTN="/html/body/div/div/div/main/div/div/div/div[3]/div/div/div[2]/div/span[1]/button/span[1]";
	public static final String UPLOAD_AGAIN_PO_HEADER="//*[@id=\"root\"]/div/div/main/div/div/div[2]/div[1]/div[2]/div/div[2]/div/span[1]/h5";
	public static final String BACK_BT="/html/body/div[2]/div[2]/div/div[3]/button[1]/span[1]";
	// public static final String AWAITING_BINNING_MENU="//*[@id=\"root\"]/div/div/div/div/ul/li[1]/ul/li[6]/a/b";
	public static final String ASSIGNBINNER="//span[contains(text(),'ASSIGN BINNING USER')]";
	public static final String SCAN_BTN="//button[@id='BinnerDashboard_Scanbutton']";
	public static final String BIN_TEXT_BOX="//input[@id='BinnerDashboard_Lpn']";
	public static final String BINNING_SUBMIT_BTN="//span[contains(text(),'SUBMIT')]";
	public static final String AL_SAVE="//*[@id=\"buttonSave\"]";
	public static final String POD_ASSIGN_MENU="//*[@id=\"root\"]/div/div/div/div/ul/li[1]/ul/li[3]/a/b";
	public static final String VPART_QTY_TXT="//input[@id='BinnerDashboard_Qty']";
	public static final String BINNER_CHECKBOX="/html/body/div[6]/div[2]/p/div[3]/table/div/tbody/tr[2]/td[2]/span/span[1]/input";
	public static final String BINNER_USER2_CHECKBOX="/html/body/div[6]/div[2]/p/div[3]/table/div/tbody/tr[1]/td[2]/span/span[1]/input";
	public static final String PARTLEVEL_BINNERASSIGNMENT_BTN="/html/body/div[1]/div/div/main/div/div/div[2]/button[3]/span[1]";
	public static final String PARTLEVEL_SELECTALL_CHECKBOX="//*[@id=\"root\"]/div/div/main/div/div/div/div[2]/div/div[1]/table/thead/tr/th[1]/span/span[1]/input";
	public static final String SELECT_BINNER_DRPDOWN="//*[@id=\"binneruser\"]";
	public static final String PARTLEVEL_SUBMIT=".//*[contains(text(),'SUBMIT')]";
	public static final String PARTLEVEL_CANCEL=".//*[contains(text(),'CANCEL')]";
	public static final String EXCESS_PROCEED=".//*[contains(text(),'PROCEED')]";
	public static final String EXCESS_APPROVE_SELECT="//*[@id=\"root\"]/div/div/main/div/div/div[3]/div/div[1]/table/tbody/tr/td[8]/div/select";
	public static final String SHORT_BTN="//*[@id='ActionColumn']";
	public static final String DOCKIN_MENU="//*[@id=\"root\"]/div/div/div/div/ul/li[1]/ul/li[2]/a/b";
	public static final String INFO_BTN="//img[@alt='infoicon']";
	public static final String LPN_ROW1="/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr/td[1]";
	public static final String BINNING_CLOSE_BTN="//span[contains(text(),'×')]";
	public static final String INFO_CLOSEBTN="//SPAN[@class='close']";
	public static final String BINNING="//*[@id=\"root\"]/div/div/div/div/ul/li[1]/ul/li[1]/a/b";
	public static final String MISC_APPROVAL="//*[@id=\"root\"]/div/div/div/div/ul/li/a";
	//public static final String APPROVAL_MENU="//*[@id=\"root\"]/div/div/div/div/ul/li/ul/li/a/b";
	public static final String INBOUND_LABEL_MENU="//*[@id=\"root\"]/div/div/div/div/ul/li[1]/ul/li[7]/a/b";
	public static final String BIN_LABEL_DRP="//*[@id=\"root\"]/div/div/main/div/div/div[2]/div[2]/div/div/div[2]/div";
	public static final String INB_LABEL_CHOOSE="//*[@id=\"root\"]/div/div/main/div/div/div/select";
	public static final String VPART_LABEL_MENU="//*[@id=\"root\"]/div/div/div/div/ul/li[1]/ul/li[8]/a/b";
	public static final String OUTBOUND_MENU="//*[@id=\"root\"]/div/div/div/div/ul/li[2]/a";
	public static final String PICK_GEN_MENU="//*[@id=\"root\"]/div/div/div/div/ul/li[2]/ul/li[1]/a/b";
	public static final String PICKING_MENU="//*[@id=\"root\"]/div/div/div/div/ul/li[2]/ul/li[2]/a/b";
	public static final String OPTIMIZATION="//*[@id=\"root\"]/div/div/div/div/ul/li[2]/ul/li[3]/a/b";
	public static final String DISPATCH="//*[@id=\"root\"]/div/div/div/div/ul/li[2]/ul/li[4]/a/b";
	public static final String OUTBOUND_LABEL="//*[@id=\"root\"]/div/div/div/div/ul/li[2]/ul/li[5]/a/b";
	public static final String SCANCARTLABEL = "//*[@id='scanCartLbl']";
	public static final String SCANCART = "//b[contains(.,'Scan Cart Label')]";
	//public static final String MISC_MENU="//*[@id=\"root\"]/div/div/div/div/ul/li[3]/a";

	//    public static final String REPORTS_MAINMENU="//*[@id=\"root\"]/div/div/div/div/ul/li[4]/a";

	public static final String REPORTS_MAINMENU_DL="//*[@id=\"root\"]/div/div/div/div/ul/li[2]/a";
	//    public static final String REPORTS_SUBMENU="//*[@id=\"root\"]/div/div/div/div/ul/li[4]/ul/li/a/b";

	public static final String REPORTS_SUBMENU_DL="//*[@id=\"root\"]/div/div/div/div/ul/li[2]/ul/li/a";
	public static final String APPROVALSELECT= "//select[@placeholder='Choose']";

	public static final String REPORT_AC_MASTER_DRPDWN="//*[@id=\"Account Master_selection\"]";
	public static final String ACTOR_ID_DRPDWN="//*[@id=\"Actor Id_selection\"]";
	public static final String REPORT_OK_BTN="//*[@id=\"parameterDialogokButton\"]/input";
	public static final String REPORT_CLOSE_BTN="/html/body/div[2]/div[2]/div/div[3]/button";

	public static final String VPART_LABEL_QTY="/html/body/div/div/div/main/div/div/div[3]/div/div[1]/table/tbody/tr[1]/td[8]/input";
	public static final String VPART_LABELPRINT="//*[@id=\"buttonSave\"]/span[1]";
	public static final String CANCELLATION_DRP="//*[@id=\"root\"]/div/div/main/div/div/div/select";
	public static final String CANCEL_CHECKBOX="/html/body/div/div/div/main/div/div/div[2]/div/div/div[3]/div/div[1]/table/tbody/tr/td[1]/span/span[1]/input";
	public static final String CANCEL_REASON_DRP="//*[@id=\"root\"]/div/div/main/div/div/div[2]/div/div/div[3]/div/div[3]/div/div[3]/div/select";
	public static final String CANCEL_LPN_BTN="//*[@id=\"root\"]/div/div/main/div/div/div[2]/div/div/div[3]/div/div[3]/div/div[3]/div/button/span[1]";
	public static final String GateInIDColumn="//*[@id=\"root\"]/div/div/main/div/div/div[2]/div/div[2]/table/thead/tr/th[2]/span/b";

	public static final String POD_DASHBOARD="POD Dashboard";
	public static final String BINNING_MENU="Binning";
	public static final String INBOUND_MENU="(//b[contains(text(),'Inbound')])[1]";
	public static final String MISC_MENU="Misc";
	public static final String CARTON_HIS_MENU="Carton History";
	public static final String CANCELLATION_MENU="Cancellation";
	public static final String REPORTS_MAINMENU="Reports";
	public static final String SINGLE_BIN_TRANS_MENU="SGL.Bin Transfer";
	public static final String MULTI_BIN_TRANS_MENU="Multi.Bin Transfer";
	public static final String REPORTS_SUBMENU="Report";
	public static final String ADMIN_MAIN_MENU="Admin";
	public static final String STORAGE_SUBMENU="Storage";
	public static final String ADMIN_MENU_MASTERS="Admin";
	public static final String MASTERS_MENU="Masters";
	public static final String SITE_MASTER="Site";
	public static final String ACCOUNT_MASTER="Account";
	public static final String ENTITY_MASTER="Entity";
	public static final String GROUPACCOUNT_MASTER="Group Account";  
	public static final String USER_MASTER="User";
	public static final String CUSTOMER_SITE_MASTER="Customer Sites"; 
	public static final String TENANT_MASTER="Tenant";
	public static final String PART_MASTER="Part";
	public static final String BIN_MASTER="Bin";
	public static final String APPROVAL_MENU="Approve Request";
	public static final String BINNERASSIGNMENT_MENU="Binner Assignment";
	public static final String POD_DISCREPANCY_APPROVAL="//*[@id=\"root\"]/div/div/main/div/div/div[2]/div[1]/div/b/div/div[2]";
	public static final String BINNINGUSER_SEARCH="//*[@id=\"standard-search\"]";
	public static final String ASSIGN_PARTLEVEL_BINNER_BTH="//span[text()='PART']";
	public static final String BinnerAssignmentInfo="//*[@id='BinnerAssignment_customerReferance']/span/b/span/img";
	public static final String BA_VON="/html/body/div[2]/div/div/main/div/div/div[2]/div/div[1]/table/tbody/tr[1]/td[3]";
	public static final String PART_TYPE="/html/body/div[2]/div/div/main/div/div/div[3]/div/div[1]/table/tbody/tr/td[4]";
	public static final String SEARCH_BTN="//button[@id='BinnerAssignment_searchbutton']";
	public static final String APPROVAL_CHECKBOX_ROW1="#root > div > div > main > div > div > div:nth-child(5) > div > div > div:nth-child(1) > table > tbody > tr > td.jss407.jss409.jss413.tablerow > span > span.jss434 > input";
	public static final String APPROVAL_SUBMIT="#buttonSave";
	public static final String ASSIGNBINNING_USER_BTN="/html/body/div[3]/div[2]/div[2]/button/span[1]\r\n";
	public static final String BINNERASSIGNMENT_SUCCPOPUP_CLOSEBTN="//*[@id=\"root\"]/div/div/main/div/div/div[2]/div/div[5]/div/div/div/div/div/div/span";
	public static final String BINNERASSIGN_ROW1="//*[@id=\"row\"]/td[1]/span/span[1]/input";
	public static final String BA_VON_CELL=".//td[contains(@class,'jss268 jss270 jss272 tablerow')][2]";
	public static final String PARTTYPE_CELL="//td[@id='BinnerDashboard_PartType']";
	public static final String LPN_CELL_1="(//TD[@data-label='LPN'])[1]";
	public static final String PARTName_CELL="//td[@id='BinnerDashboard_PartNumber']";
	public static final String LPN_CELL_3="(//TD[@data-label='LPN'])[2]";
	//Pick Generation
	public static final String OUTBOUNDGRID="(//b[contains(.,'Outbound_OrderForm')])[1]";
	public static final String PICKGENERATIONGRID="//b[contains(.,'Pick Generation')]";
	public static final String CREATEREQUEST="//span[contains(.,'Create request')]";
	public static final String UPLOADBUTTON="//span[contains(.,'UPLOAD SO')]";
	public static final String SELECTCUSTOMERNAME="//select[@id='accountsMasterDTOList']";
	public static final String CHOOSEFILE="//input[@id='PickGen_file-upload']";
	public static final String UPLOADSO="//span[contains(.,'UPLOAD SO')]";
	public static final String SORECORDSGRID="//a[contains(.,'SO Records')]";
	public static final String CLICKRECORD="//td[@id='PickGen_SONumber']";
	public static final String CLICKBACK="//span[contains(.,'BACK')]";


	//Picking User
	public static final String SERIAL_ENTER="//*[@id='Picking_serail']";

	public static final String PICKINGGRID ="//b[contains(.,'Picking')]";
	public static final String PICKINGSEARCH="//input[contains(@id,'Picking-searchfield')]";
	public static final String PARTTYPE= "(//td[@data-label='PART TYPE'])[1]";
	public static final String PARTNAME= " (//td[contains(@id,'Picking_PartNumber')])";
	public static final String SERIALNOFIELD="//input[@id='Picking_serialLPN']";
	public static final String SUBMITBTN="//span[contains(.,'SUBMIT')]";
	public static final String ALERTBOXCANCEL="//span[@class='close']";
	public static final String VQUANTITY ="//*[@id='Picking_enterQty']";
	public static final String DISPATCH_REQNO="(//*[@id='Despatch_REQUESTNO'])[1]";

	//PickerAssign
	public static final String NORECORDS="//*[contains(text(),'No Records Found')]"; 
	public static final String DISPATCHQTY1="//*[@id=\"Despatch_enterQty1\"]";
	public static final String MASTERSEARCH="//input[@id='PickGen_searchfield']";
	public static final String SELECTRECORD="(//input[@type='checkbox'])[2]";
	public static final String PICKERCHECKBOX="(//input[contains(@type,'checkbox')])[1]";
	public static final String ASSIGNPICKERBTN="//span[contains(.,'ASSIGN PICKER')]";
	public static final String SELECTPART="(//input[@type='checkbox'])[1]";
	public static final String PICKERASSIGN="//span[contains(.,'ASSIGN PICKER')]";
	public static final String PICKERDROPDOWN="(//span[contains(.,'ASSIGN PICKER')])[2]";
	public static final String SELECTPICKER="(//input[@type='checkbox'])[8]";
	public static final String PICKERINPUT="//input[@placeholder=' Search']";
	public static final String PICKERSUBMIT="//span[contains(.,'ASSIGN PICKING USER')]";

	//Consolidation
	public static final String OPTISEARCH="//*[@id='root']/div/div/main/div/div/div[1]/div/div[1]/div[2]/button/span[1]";
	public static final String CONSOLIDATIONGRID="//b[contains(.,'Optimization')]";
	public static final String MASTERSEARCH1="//input[@id='standard-search']";
	public static final String ORDERCLICK="(//input[@type='checkbox'])[1]";
	public static final String CONSOLIDATIONSKIP="//span[contains(.,'SKIP')]";
	public static final String CONSOLIDATIONYES="/html/body/div[2]/div[2]/div/div[4]/button[1]";
	public static final String CONSOLIDATION="(//b[contains(.,'CONSOLIDATE>>')])[1]";
	public static final String NOOFBOX="//input[@id='Consolidate_noOfBoxes']";
	public static final String ADDBOX="//span[contains(.,'ADD LEVEL')]";
	public static final String SCANBOX="(//span[contains(.,'SCAN BOX')])[2]";
	public static final String ENTERLPN="//input[@id='Consolidate_enterLPN']";
	public static final String SUBMIT="//span[contains(.,'SUBMIT')]";
	public static final String NUNVQNTY="//input[@id='Consolidate_enterqty']";
	public static final String WEIGHT="//input[@id='Consolidate_weight']";
	public static final String UOM="//select[@name='doctytpe']";
	public static final String COMPLETE="//span[contains(.,'Completed')]";
	public static final String FINISH="//span[contains(text(),'FINISH')]";
	public static final String LABEL="//td[@id='Consolidate_PackageRefId']";
	public static final String SCAN_PACKING_SLIP="//input[@id='PO_ScanPackingSlipRadio']";
	public static final String PO_PACKING_SLIP="//input[@id='PO_Packingslip']";
	public static final String PO_PACKINGSLIPSCAN="//button[@id='PO_PackingslipScan']";
	public static final String PO_Upload=".//img[@class='buttonProgress']";	
	//Despatch
	
	public static final String DISPATCHGRID = "//b[contains(.,'Dispatch')]";
	public static final String DISPATCHSEARCH = "//input[@id='despatch-search']";
	public static final String DISPATCHBTN = "//span[contains(.,'DiSPATCH')]";
	public static final String DISPATCHMODE = "//select[@name='transportMode']";
	public static final String VEHICLENO = "//input[@id='vehicleNo']";
	public static final String SCANLPN = "//input[@id='Despatch_ScanCarton/LPN']";
	public static final String SCANBUTTON="//*[@id=\"popupform\"]/div[5]/div[2]/svg/path";
	public static final String CARRIER="//input[@name='Carrier']";
	public static final String CARRIERREF="//input[@name='CarrierRef']";
	public static final String VEHICLE="//*[@id='Despatch_entervehicle']";
	public static final String SCANTOBIN_RADIOBTN="//input[@id='BinnerDashboard_Radio']";
	public static final String SCANTOBIN_SUMBIT="//button[@id='BinnerDashboard_Submit']";
	public static final String OUTBOUND_NEWREQUEST="";
	public static final String SO_CHOOSE_FILE="//*[@id=\"file-upload\"]";
	public static final String PRINTCARRIER="//span[contains(text(),'Print Carrier Label')]";
	
	public static final String Cancel_Button="//span[contains(text(),'Cancel Order')]";
    public static final String Cancel_Button_Yes="//span[contains(text(),'YES')]";
    public static final String PROCESS="//span[contains(text(),'Process')]";
    public static final String Cancel_Button_close="//span[contains(text(),' × ')]";
    public static final String Partial_Finish="//span[contains(text(),'FINISH')]";
	//Outbound_OrderForm create new request xpaths------------------------------


	public static final String LOGINVALIDATION="//span[contains(text(),'Already logged in for the user id GDSA1')]";
	public static final String OUTBOUND = "//b[text()='Outbound']";
	public static final String PICKGENERATION = "//b[text()='Pick Generation']";
	public static final String CREATEREQUEST1 = "//span[contains(text(),'Create request')]";
	public static final String ORDERFORM = "//b[contains(text(),'Order Form')]";
	public static final String ACCOUNT = "//h5[contains(text(),'Account: ')]/../..//div[@class=' css-1kvcxg-control']";
	public static final String SERVICETYPE = "//h5[contains(text(),'Service Type :')]/../..//div[@class=' css-1kvcxg-control']";
	public static final String QUANTITY = "//input[@name='Quantity']";
	public static final String CUSTOMERLPN="//input[@name='customerLPN']";
	public static final String DELIVERYTYPE = "//h5[contains(text(),'Delivery Type :')]/../..//div[@class=' css-1kvcxg-control']";
	public static final String COUNTRYFILTER="//select[@name='country']";
	public static final String DESTINATIONADDRESS="//input[@name='Address']";
	public static final String GOOGLEADDRESSSEARCH="//input[@class='AutoComplete_map fieldmode_map pac-target-input']";
	public static final String COMPANY="//input[@id='company']";
	public static final String ADDRESSLINE1="//input[@id='address1']";
	public static final String ADDRESSLINE2="//input[@id='address2']";
	public static final String ADDRESSLINE3="//input[@id='address3']";
	public static final String ZIPCODE="//input[@class='form-control']";
	public static final String COUNTRY="//input[@name='country']";
	public static final String STATE="//input[@name='state']";
	public static final String CITY="//input[@name='city']";
	public static final String LATITUDE="//input[@name='lat']";
	public static final String LONGITUDE="//input[@name='lang']";
	public static final String DESTINATIONSUBMIT="//button[contains(text(),'Submit')]";
	public static final String DESTINATIONCODE = "//h5[contains(text(),'Destination Code :')]/../..//div[@id='PickGen_WebDestinationCode']";
	public static final String CUSTOMERREFERENCE = "//input[@name='CustomerRef']";
	public static final String REFERENCE1 = "//input[@name='Ref1']";
	public static final String REFERENCE2 = "//input[@name='Ref2']";
	public static final String PARTNO = "//h5[contains(text(),'Part No:')]/../..//div[@class=' css-1kvcxg-control']";
	public static final String STOCKTYPE = "//h5[contains(text(),'Stock Type:')]/../..//div[@class=' css-1kvcxg-control']";	
	public static final String PARTREFERENCE = "//input[@name='partReference']";
	public static final String ADD = "//span[text()='ADD']";
	public static final String PROCEED="//span[contains(text(),'PROCEED')]";
	public static final String CHOOSESITE="//input[@type='radio']";
	public static final String SAVE="//span[contains(text(),'SAVE')]";
	public static final String CLOSE="//span[@class='close']";
	public static final String OUTBOUNDPICKGENERATIONSEARCH="//input[@type='search']";
	public static final String OUTBOUNDPICKGENERATIONSEARCHBUTTON="//img[@alt='Search']";
	public static final String OUTBOUNDPICKGENERATIONSELECTALL="(//input[@type='checkbox'])[1]";
	public static final String OUTBOUNDPICKGENERATIONASSIGNPICKER="//span[contains(text(),'ASSIGN PICKER')]";
	public static final String OUTBOUNDPICKGENERATIONUSERPICKERALL="(//input[@type='checkbox'])[9]";
	public static final String OUTBOUNDPICKGENERATIONASSIGNPICKINGUSER="//span[contains(text(),'ASSIGN PICKING USER')]";
	public static final String OUTBOUNDPICKGENERATIONCANCEL="//span[contains(text(),'CANCEL')]";

	//AlternativeBin 
	public static final String Navigation="(//img[contains(@alt,'o')])[5]";
	public static final String LPNINFO="//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr[2]/td[4]/span";
	public static final String CANCEL="//span[contains(text(),' × ')]";
	public static final String DROPDOWN="//select[@id='Picking_actionType']";
	public static final String SUBMITBTN1="//span[contains(.,'SUBMIT')]";
	public static final String SERIALNOFIELD1="//input[contains(@id,'serial')]";
	public static final String VQUANTITY1 ="//input[@id='Picking_enterQty']";
	//RANDOMPICK
	public static final String PICKACTION="//select[@id='Picking_actionType']";
	public static final String RECORD="//*[@id='root']/div/div/main/div/div/div/div/div[4]/div/div[1]/table/tbody/tr/td";
	public static final String ALERT="(//SPAN[@class='close'])";
	public static final String ALERT2="(//SPAN[@class='close'][text()=' × '])[2]";
	public static final String OUT="//*[@id=\"root\"]/div/div/div/div/ul/li[2]/a/b";
	public static final String CHECK1="(//input[@type='checkbox'])[1]";
	public static final String RANDSEAR="//*[@id='standard-search']";
	public static final String PARTYPE="(//td[@data-label='PART TYPE'])[2]";
	public static final String SUB="//span[contains(text(),'SUBMIT')]";
	public static final String APPROVE="//*[@id=\"root\"]/div/div/main/div/div/div[3]/div/div/div[3]/div/div[3]/div/select/option[2]";
	public static final String CHOOSE="//select[@placeholder='Choose']";
	public static final String RAND="//*[@id='root']/div/div/main/div/div/div[2]/div[5]/div/b/div";
	public static final String APPROVEREQ="//*[@id=\"root\"]/div/div/div/div/ul/li[3]/ul/li[1]/a/b";
	public static final String INFO="//img[@alt='o']";
	public static final String LPN0="//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr[2]/td[4]/span";
	public static final String LPN1="//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr[3]/td[4]/span";
	public static final String CLOSE1="//*[@id='root']/div/div/main/div/div/div/div[6]/div/div/div/div/div/div/span";
	public static final String MISC="(//b[contains(.,'Misc')])[1]";
	public static  final String LPNCLOSE="//*[@id='simple-popper']/div[2]/p/div[1]/span";
	public static final String APPROVALPARTS="//*[@id='root']/div/div/main/div/div/div[3]/div/div/div[1]/table/tbody/tr/td[6]";
	public static final String REQPARTS="//*[@id='root']/div/div/main/div/div/div/div/div[4]/div/div[1]/table/tbody/tr/td[2]";

	//XPATH:PICKING USER
	public static final String DROPDOWN1="//*[@id=\"actionType\"]";
	public static final String LPNINFO1="(//img[contains(@alt,'o')])[5]";
	public static final String LPNSCAN="//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr[2]/td[4]/span";
	public static final String LPNCLOSE1="/html/body/div[2]/div[2]/p/div[1]/span";
	public static final String OUT1="//*[@id=\"root\"]/div/div/div/div/ul/li[2]/a/b";
	public static final String PICK="//*[@id=\"root\"]/div/div/div/div/ul/li[2]/ul/li[2]/a/b";
	public static final String PICKGEN_SEARCH="//*[@id='PickGen_searchButton']";

	// XPATH:MISC SCREEN    
	public static final String MISC1="//*[@id=\"root\"]/div/div/div/div/ul/li[3]";
	public static final String APPROVEREQ1="//b[contains(text(),'Approve Request')]";
	public static final String SHORTPIC="//*[contains(text(),'Short Pick')]";
	public static final String CHECK11="//*[@id=\"root\"]/div/div/main/div/div/div[3]/div/div/div[1]/table/tbody/tr[1]/td[1]/span/span[1]/input";
	public static final String CHECK2="//*[@id=\"root\"]/div/div/main/div/div/div[3]/div/div/div[1]/table/tbody/tr[2]/td[1]/span/span[1]/input";
	public static final String CHOOSE1="//*[@id=\"root\"]/div/div/main/div/div/div[3]/div/div/div[3]/div/div[3]/div/select";
	public static final String APPROVE1="//*[@id=\"root\"]/div/div/main/div/div/div[3]/div/div/div[3]/div/div[3]/div/select/option[2]";
	public static final String SUB1="//*[@id=\"buttonSave\"]/span[1]";
	public static final String MISCSEARCH="//input[contains(@id,'standard-search')]";
	public static final String CHECKBOXALL="(//input[@type='checkbox'])[1]";

	//Inbound Order Form
	public static final String INBOUNDDASHBOARD="(//b[contains(text(),'Dashboard')])[1]";
	public static final String INBOUNDSEARCH="//input[@type='search']";
	public static final String INBOUNDGATEIN="//tr[@id='row']";
	public static final String INBOUNDDOCKIN="//b[contains(text(),'Dock In')]";
	public static final String GATEIN="//td[@id='VehicleNumber_HARKAR0612']";
	public static final String DOCKIN="//span[contains(text(),'Dock In')]";
	public static final String DOCALREAYEXISTS="//span[contains(text(),'Doc Already Exists,Upload PO Now')]";
	public static final String INBOUNDCREATEREQUEST="//span[contains(text(),'CREATE REQUEST')]";
	public static final String INBOUNDCUSTOMERNAME="//select[@name = 'customername']";
	public static final String INBOUNDDOCTYPE="//select[@name='doctytpe']";
	public static final String INBOUNDDOCNUMBER="//input[@id='docnumber']";		
	public static final String INBOUNDORDERFORM="//input[contains(@id,'PO_OrderformRadio')]";
	public static final String INBOUNDDESTINATIONSITE="//input[@id='destinationvalue']";
	public static final String INBOUNDCUSTOMERREFERENCE="//input[@id='PO_custref']";
	public static final String INBOUNDINVOICENUMBER="//input[@id='PO_invoicenum']";
	public static final String INBOUNDREFERENCE1="//input[@id='PO_reference1orderform']";
	public static final String INBOUNDREFERENCE2="//input[@id='PO_reference2orderform']";
	public static final String INBOUNDSOURCETYPE="//div[@class=' css-yk16xz-control']";
	public static final String INBOUNDSOURCESITE="//div[@class=' css-enz8ck-control']";
	public static final String INBOUNDCOUNTRYFILTER="//select[@class='sourcecountry form-control']";
	public static final String INBOUNDADDRESS="//input[@class='formfieldAddress form-control']";
	public static final String INBOUNDPARTNUMBER="(//input[@placeholder='Enter'])[8]";
	public static final String INBOUNDSTOCKTYPE="(//div[@class=' css-1uccc91-singleValue'])[2]";
	public static final String INBOUNDQUANTITY="//input[@id='PO_quantityorder']";
	public static final String INBOUNDCUSTOMERLPN="//input[@id='PO_customerlpnorder']";
	public static final String INBOUNDBATCHNUMBER="//input[@id='PO_batchnumorder']";
	public static final String INBOUNDMANUFACTURINGDATE="(//input[@class='react-datetime-picker__inputGroup__input react-datetime-picker__inputGroup__day'])[1]";
	public static final String INBOUNDMANUFACTURINGMONTH="(//input[@class='react-datetime-picker__inputGroup__input react-datetime-picker__inputGroup__month'])[1]";
	public static final String INBOUNDMANUFACTURINGYEAR="(//input[@class='react-datetime-picker__inputGroup__input react-datetime-picker__inputGroup__year'])[1]";
	public static final String INBOUNDEXPIRYDATE="//b[contains(text(),'Expiry Date:')]";
	public static final String INBOUNDSERIALNUMBER="//input[@id='PO_serialnumorder']";
	public static final String INBOUNDVERSIONNUMBER="//input[@id='PO_versionnumorder']";
	public static final String INBOUNDPARTREFERENCE="//input[@id='PO_partrefrenceorder']";
	public static final String INBOUNDADDBUTTON="//span[contains(text(),'Add')]";
	public static final String INBOUNDSAVEBUTTON="//span[contains(text(),'SAVE')]";
	public static final String INBOUNDDOCCLOSEBUTTON="//span[@class='close']";
	public static final String INBOUNDUPLOADPOAGAIN="//span[contains(text(),'UPLOAD PO Again')]";
	public static final String INBOUNDFINISHUPLOAD="//span[contains(text(),'COMPLETE')]";
	public static final String GATEIN_1stROW="(//td[@id='DashboardPO_GateInNum'])[1]";
	public static final String NVNUPARTQTY="//input[@id='BinnerDashboard_Qty']";
	public static final String INBOUND_LABEL="Inbound Label";
	public static final String PRINTLABEL_DROPDOWN="//select[@id='Inboundlabel_chooseoption']";
	public static final String BOXLABLE_QTYTXTBOX="//input[@id='InboundLabel_quantityvalue']";
	public static final String REPRINT_RADIOBTN="(//input[@id='Inboundlabel_Radio'])[2]";
	public static final String REPRINT_1stROW="(//td[@id='Inboundlabel_boxlabel'])[1]";
	public static final String SCANTOBOX_RADIOBTN="//input[@value='scantobox']";
	public static final String REPRINT_2ndtROW="(//td[@id='Inboundlabel_boxlabel'])[2]";
	public static final String EXCESSBINNING_PROCEEDBTN="/html/body/div[3]/div/div/main/div/div/div[3]/div/div[5]/div[3]/div/div/div/div/div/div/div/div/button[2]/span[1]";
	public static final String BOXLABELPRINT="//button[@id='InboundLabel_buttonSave']";
	public static final String INFO_BTN1="//img[@alt='infoicon']";
	public static final String INFO_BTN2="(//img[contains(@id,'BinnerDashboard_Info')])[2]";			
	public static final String DISPATCHFINSH="//button[@id='Despatch_finish']";

	public static final String PICKINGLPN="/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[2]/td[4]";
	public static final String PICKINGBOXLPN="/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[2]/td[2]";

	public static final String PICKINGLPN1="/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[3]/td[3]";

	public static final String BINNER_ASSIGNMENT_MENU=".//*[contains(text(),'Binner Assignment')]";

	//Cancellation Screen-------------------------------

	public static final String MISCBUTTON="//b[contains(text(),'Misc')]";
	public static final String CANCELLATIONBUTTON="//b[contains(text(),'Cancellation')]";
	public static final String CANCELLATIONAT="//select[@placeholder='Choose']";
	public static final String CANCELLATIONSEARCHTEXTBOX="//input[@id='standard-search']";
	public static final String CANCELLATIONSEARCHBUTTON="(//img[contains(@src,'data:image/png;base64,')])[5]";
	public static final String CANCELLATIONSELECTALL="(//input[@type='checkbox'])[1]";
	public static final String CANCELLATIONREASON="(//select[@placeholder='Choose'])[2]";
	public static final String CANCELLATIONCANCELORDERBUTTON="//button[@name='cancelBtn']";
	public static final String CANCELLATIONPOPUPCLOSE="//span[@class='close']";
	public static final String CANCELLATIONPARTTYPESELECT="//b[contains(text(),'FIFOVTYPE')]/../..//input[@type='checkbox']";
	public static final String CANCELLATIONVPARTQUANITYCANCEL="//b[contains(text(),'V')]/../..//input[contains(text(),cancelQty)]";
	public static final String CANCELLATIONPOSOTEXTBOX="//input[@placeholder='PO/SO Reference']";
	public static final String CANCELLATIONPARTNUMTEXTBOX="//input[@placeholder='Part Number']";
	public static final String BINNINGNVINFO ="(//b[contains(text(),'NV')])[2]/../..//img[@alt='infoicon']"; 
	public static final String INBOUNDBINNINGNVLPN="//td[@data-label='LPN']";
	public static final String CANCELLATIONLPNTEXTBOX="//input[@placeholder='LPN/Customer LPN']";
	public static final String INBOUNDBINNINGLPNCLOSE="//span[@class='close']";
	public static final String CANCEL_LEVEL="//select[@class='form-control']";

	public static final String CANCEL_SOSEARCH="//input[@placeholder='PO/SO Reference']";
	public static final String CANCEL_PART_CHECKBOX="(//input[@type='checkbox'])[2]";
	public static final String CANCEL_REASON=" (//select[@placeholder='Choose'])[2]";
	public static final String CANCEL_BTN="//button[@name='cancelBtn']";
	public static final String CANCEL_QTY_ENTER="//input[@id='cancelQty_0']";
	public static final String CANCEL_PARTSEARCH="//input[@placeholder='Part Number']";
	public static final String CANCEL_LPNSEARCH="//input[@placeholder='LPN/Customer LPN']";
	public static final String NORECORD="//*[contains(text(),'No Records Found')]";
	public static final String OUTBOUNDMENU = "(//b[contains(.,'Outbound_OrderForm')])[1]";
	public static final String PICKGENERATIONMENU = "//b[contains(.,'Pick Generation')]";
	public static final String CREATEREQUESTMENU = "//span[contains(.,'Create request')]";
	public static final String CANCELNVNUPART = "//b[contains(text(),'PARTNVNUFIFO')]";
	public static final String CANCELNVNUPARTSELECT="//b[contains(text(),'PARTNVNUFIFO')]/../..//input[@type='checkbox']";
	public static final String CANCELNVPARTSELECT1="(//b[contains(text(),'PARTNVFIFO')]/../..//input[@type='checkbox'])[2]";
	public static final String CANCELNVPARTSELECT="(//b[contains(text(),'PARTNVFIFO')]/../..//input[@type='checkbox'])[1]";
	public final String BINSCAN="(//*[contains(.,'Scan')])[18]";

	public static final String CONSOLIDATIONSEARCH="//input[@id='standard-search']";
	public static final String LPNINFOC="//*[@id=\"root\"]/div/div/main/div/div/div/div[3]/div/div/p/form/div[6]/div/div/label/img";	
	public static final String FINESH="//*[@id=\"Despatch_finish\"]/span[1]";	
	public static final String CANCEL_VONSEARCH="//input[@placeholder='Visibility Order ID']";
	public static final String LPNSCEARCH="//*[@id=\"root\"]/div/div/main/div/div/div[2]/div/div/div[2]/div/div[6]/input";

	public static final String CONSOLIDATEENTERLPN="//input[contains(@id,'Consolidate_enterLPN')]";
	public static final String CANCEL_VONTEXT="(//td[@id='PickGen_VON'])[1]";
	public static final String PICKINGSOSEARCH="//input[@placeholder='SO No/Cust Ref']";
	public static final String BINNERASSGINMENT_CHECKBOX="//*[contains(@id,'BinnerAssignment_checkbox')]";

	public static final String LOGOUTYES="(//button[@id=\"buttonSave\"])[1]";
	public static final String ORDER="//td[@id='BinnerDashboard_Visibilityorderid']";
	public static final String BIN="//span[contains(text(),'GOOD')]";
	public static final String NAME1="//b[contains(text(),'AutomationTestUser')]";
	public static final String Assign_Order="//*[@id=\"PO_AssignorderRadio\"]";
	public static final String Assign_Order_Scearch_field="//*[@id=\"PO_AssignorderSearch\"]";
	public static final String Assign_Order_Check_Box="//*[@id=\"PO_CheckBoxId\"]";
	public static final String Dispatch1 = "//img[@id='Despatch_ACTION']";
	public static final String DISPATCHBUTTON="//span[contains(text(),'Dispatch')]";




	//label Reprint
	public static final String Box_Label_Print="//button[@id='Inboundlabel_print']";
	public static final String Outbound_Label_Choose2="(//select[@class='form-control'])[2]";
	public static final String Outbound_label_print="//*[@alt='print']";
	public static final String Inbound_Label_dropdown="//*[@id='Inboundlabel_chooseoption']";
	public static final String Cust_Label_Reprint_Scearch="//*[@id='standard-search' or @id='Inboundlabel_search_boxlabel']";

	public static final String Inbound_Label= "//*[@id=\"root\"]/div/div/div/div/ul/li[1]/ul/li[7]/a/b";
	public static final String Bin_Label="//*[@id=\"Inboundlabel_Binlabel\"]";
	public static final String Bin_Label_Scearch="//*[@id=\"standard-search\"]";
	public static final String Bin_Label_Scearch_Close="//*[@id=\"customized-dialog-title\"]/h2/div[1]/div[3]/button";
	public static final String Bin_Label_Print="//*[@id=\"Inboundlabel_print\"]/span[1]";

	public static final String Box_Label="//*[@id=\"InboundLabel_quantityvalue\"]";
	public static final String Box_Label_Reprint="/html/body/div/div/div/main/div/div/div[2]/div[1]/span[3]/span[1]/input";
	public static final String Box_Label_Reprint_Scearch="//*[@id=\"Inboundlabel_search_boxlabel\"]";
	public static final String Box_Label_Reprint1="//*[@id=\"Inboundlabel_print\"]";

	public static final String Customer_Label_Print="SEARCH BY CUSTOMER REFERENCE";
	public static final String Customer_Label_Print1="/html/body/div/div/div/main/div/div/div[2]/div[3]/div/div[1]/table/tbody/tr/td[4]/div/button/span[1]";



	public static final String LPN_Label_Print="(//*[@id='buttonSave'])[2]";




	//OUTBOUND LABEL

	public static final String OUTBOUND_Label="//*[@id=\"root\"]/div/div/div/div/ul/li[2]/ul/li[5]/a/b";
	public static final String OUTBOUND_Label_dropdown="//*[@id=\"root\"]/div/div/main/div/div/div[1]/select";
	public static final String OUTBOUND_Printlabel_Dropdown="//*[@id=\"root\"]/div/div/main/div/div/div[2]/div[3]/div/div[1]/table/tbody/tr/td[4]/div/select";
	public static final String OUTBOUND_Print="//*[@id=\"root\"]/div/div/main/div/div/div[2]/div[3]/div/div[1]/table/tbody/tr/td[4]/div/img";

	public static final String Picking_Von=	"//*[@id=\"PickGen_VON\"]";

	//Partial consolidate AND Cart Label
	public static final String OUTBOUND_LABEL_CHOOSE="//select[@class='form-control']";
	public static final String OUTBOUND_LABEL_SEARCH="//input[@id='standard-search']";
	public static final String CART_LABEL_CHOOSE="//*[@id='root']/div/div/main/div/div/div[2]/div[3]/div/div[1]/table/tbody/tr/td[4]/div/select";
	public static final String CART_LABEL_PRINT="//img [contains(@class,'Icon_Eye_print')]";
	public static final String DISPATCH_NVNU_QUNTY="//*[@id='Despatch_enterQty1']";
	public static final String CART_LABEL_QNTY="//input[@id='noflabels']";
	public static final String CART_LABEL_PRINT_BTN="//span[text()='PRINT']";
	public static final String SCAN_CART_LABEL_BTN="//div[@id='Picking_ScanLPN2']";
	public static final String CART_LABEL_XPATH="//input[@id='cartLabel']";
	public static final String CART_LABEL_SUBMIT="//button[@id='Picking_SubmitButton']";
	public static final String PickingBox="//table/tbody/tr[2]/td[2]";
	public static final String ACCOUNTSELECT="(//*[@id='PickGen_Custname'])";

	public static final String GRNMENU="//b[contains(text(),'GRN')]";
	public static final String GRNSEARCHBOX="//input[@id='Vpart_searchinput']";
	public static final String GRNACTIONCLICK="//img[@class='Icon_Edit_Table']";
	public static final String GRNPROCESS="//span[contains(text(),'Process')]";
	public static final String GRNEDIT="//span[contains(text(),'Edit')]";
	public static final String GRNBOXREQ="//select[@class='fieldmode form-control']";
	public static final String GRNSUBMIT="//button[contains(text(),'SUBMIT')]";
	public static final String GRNCHOOSEFILE="//input[@id='PO_fileupload']";
	public static final String GRNPROCESSYES="//span[contains(text(),'YES')]";

	public static final String Picking_Info="(//img[contains(@alt,'o')])[5]";
	public final String TEMPSEARCH="(//*[@id='standard-search'])[1]";
	public final	String TEMPCHECK="(//input[@type='checkbox'])[2]";
	public final	String TEMPSUCCESSMSG="(.//*[contains(.,'Successfully')])[17]";
	public final String TEMPSAVE="//*[@id='buttonSave']/span[1]";
	public final String TEMPCHOOSE="//*[@class='form-controls form-control']";
	public final String ACCOUNTSELECT1="(//*[@id='PickGen_Custname'])";
	public final String OUTBOUNDACCOUNTSELECT="(//*[@id='PickGen_WebAcc'])";
	public static final String PICKASBOX="//input[@id='pickAsBox']";
	//approval
	public static final String Temp_Sales_Order= "(//*[contains(.,'Temp Sales Order')])[14]";
	public static final String Binning_Short_excess="(//div/div/div/div/div[contains(.,'Binning Short')])[1]";
	public static final String POD_Discrepency="(//div/div/div/div/div[contains(.,'POD Discrepency')])[1]";
	public static final String Bin_Transfer_Approval="(//div/div/div/div/div[contains(.,'Bin Transfer Approval')])[1]";


}


