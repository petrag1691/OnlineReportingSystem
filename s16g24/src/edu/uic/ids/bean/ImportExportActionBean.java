package edu.uic.ids.bean;

import java.io.LineNumberReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import edu.uic.ids.constants.FinalConstants;
@ManagedBean
@ViewScoped
public class ImportExportActionBean implements Serializable,Cloneable{
	private static final long serialVersionUID = 7525697971994837497L;
	private DatabaseAccessBean dbbean;
	@ManagedProperty(value="#{importExportBean}")
	private ImportExportBean importExportBean;
	@ManagedProperty(value="#{errorMessageBean}")
	private ErrorMessageBean errorMessageBean;
	private boolean renderDisplay;
	private boolean renderExportFields;
	private boolean renderImportFields;
	private boolean renderImportResult;
	//private boolean renderImportConfirmation;
	private boolean renderQueryResults;
	private boolean renderDataTable;
	private boolean renderErrorMessages;
	private int rowCount;
	private int columnsCount;
	private String executionQuery;
	private ResultSet resultSet;
	private Result result;
	private ResultSetMetaData rsmd;
	private String status;
	private String fileName;
	private String filePath;
	private FileOutputStream fos;
	private FileInputStream fis;
	private File exportFile;
	private File tempDirectory;
	@PostConstruct
	public void init(){
		Map <String, Object> m =  FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		dbbean = (DatabaseAccessBean) m.get("databaseAccessBean");
		filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/temp");
		renderDisplay = false;
		renderExportFields = false;
		renderImportFields = false;
		renderImportResult = false;
		renderQueryResults = false;
		renderDataTable = false;
		renderErrorMessages = false;
		executionQuery = "";
	}

	public DatabaseAccessBean getDbbean() {
		return dbbean;
	}

	public void setDbbean(DatabaseAccessBean dbbean) {
		this.dbbean = dbbean;
	}

	public ImportExportBean getImportExportBean() {
		return importExportBean;
	}

	public void setImportExportBean(ImportExportBean importExportBean) {
		this.importExportBean = importExportBean;
	}

	public ErrorMessageBean getErrorMessageBean() {
		return errorMessageBean;
	}

	public void setErrorMessageBean(ErrorMessageBean errorMessageBean) {
		this.errorMessageBean = errorMessageBean;
	}

	public boolean isRenderDisplay() {
		return renderDisplay;
	}

	public boolean isRenderExportFields() {
		return renderExportFields;
	}

	public boolean isRenderImportFields() {
		return renderImportFields;
	}

	public boolean isRenderImportResult() {
		return renderImportResult;
	}

	/*public boolean isRenderImportConfirmation() {
		return renderImportConfirmation;
	}*/

	public boolean isRenderQueryResults() {
		return renderQueryResults;
	}

	public boolean isRenderDataTable() {
		return renderDataTable;
	}

	public boolean isRenderErrorMessages() {
		return renderErrorMessages;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnsCount() {
		return columnsCount;
	}

	public String getExecutionQuery() {
		return executionQuery;
	}

	public Result getResult() {
		return result;
	}
	public String displayImportFields(){
		if(!importExportBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onChangeSchema().equalsIgnoreCase("SUCCESS")){
				displayImportFields();
			}else{
				setErrorMessage("");
			}
		}else{
			if(importExportBean.getSelectedDatabase().equalsIgnoreCase("world")){
				setErrorMessage("Import feature is not available on World schema");
			}else{
				resetDisplay();
				if(dbbean.getTablesFromDatabase().equalsIgnoreCase("SUCCESS")){
					if(dbbean.getTables()!=null && dbbean.getTables().size()>0){
						if(importExportBean.getTables()!=null){
							importExportBean.getTables().clear();
						}
						importExportBean.setTables(dbbean.getTables());
						renderDisplay = true;
						renderImportFields = true;
					}else{
						setErrorMessage("No tables present in the selected database");
					}
				}else{
					setErrorMessage("");
				}
			}
		}
		return "SUCCESS";
	}
	public String displayExportFields(){
		if(!importExportBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onChangeSchema().equalsIgnoreCase("SUCCESS")){
				displayExportFields();
			}else{
				setErrorMessage("");
			}
		}else{
			resetDisplay();
			if(dbbean.getTablesFromDatabase().equalsIgnoreCase("SUCCESS")){
				if(dbbean.getTables()!=null && dbbean.getTables().size()>0){
					if(importExportBean.getTables()!=null){
						importExportBean.getTables().clear();
					}
					importExportBean.setTables(dbbean.getTables());
					renderDisplay = true;
					renderExportFields = true;
				}else{
					setErrorMessage("No tables present in the selected database");
				}
			}else{
				setErrorMessage("");
			}
		}
		return "SUCCESS";
	}
	public String importTable(){
		if(!importExportBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onChangeSchema().equalsIgnoreCase("SUCCESS")){
				resetDisplay();
			}else{
				setErrorMessage("");
			}
		}else{
			status = "FAIL";
			// assumes temp exists in WebContent
			exportFile = null;
			fos = null;
			// set codes, counts
			try {
				if(importExportBean.getUploadedFile()!=null){
					fileName = importExportBean.getUploadedFile().getName();
					if(fileName.contains("csv")|| fileName.contains("xml")){
						//fileSize = importExportBean.getUploadedFile().getSize();
						//fileContentType = importExportBean.getUploadedFile().getContentType();
						//System.out.println("ImportExportActionBean.importTable() file props "+fileName+" "+fileSize+" "+fileContentType);
						filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/imports");
						tempDirectory = new File(filePath);
						if(!tempDirectory.exists()){
							tempDirectory.mkdir();
						}
						exportFile = new File(filePath + "/" + fileName);
						if(exportFile.exists()){
							exportFile.delete();
						}
						fos = new FileOutputStream(exportFile);
						// next line if want file uploaded to disk
						fos.write(importExportBean.getUploadedFile().getBytes());
						fos.close();
						status = "SUCCESS";
						if(importExportBean.getSelectedTable()!=null && !importExportBean.getSelectedTable().trim().isEmpty()){
							if(dbbean.getTableColumns(importExportBean.getSelectedTable()).equalsIgnoreCase("SUCCESS")){
								if(dbbean.getColumns()!=null && dbbean.getColumns().size()>0){
									if(fileName.contains("csv")){
										//System.out.println("ImportExportActionBean.importTable() file opened is a csv file");
										insertIntoCSV();
									}else if(fileName.contains("xml")){
										//System.out.println("ImportExportActionBean.importTable() import successful");
										insertIntoXML();
									}
								}else{
									setErrorMessage("No columns exist for the selected table");
								}
							}else{
								setErrorMessage("");
							}
						}else{
							setErrorMessage("Select a table to import records into");
						}
					}else{
						setErrorMessage("selected file/format is not supported");
					}

				}else{
					setErrorMessage("No file selected.Please select a file to upload");
				}
			}catch (IOException e) {
				setErrorMessage(e.getMessage());
			}catch (Exception e) {
				setErrorMessage(e.getMessage());
			} // end catch
		}
		return status;
	}

	public String exportTable(){
		if(!importExportBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onChangeSchema().equalsIgnoreCase("SUCCESS")){
				resetDisplay();
			}else{
				setErrorMessage("");
			}
		}else{
			if(importExportBean.getSelectedTable()!=null && !importExportBean.getSelectedTable().isEmpty()){
				if(importExportBean.getExportOption()!=null && !importExportBean.getExportOption().isEmpty()){
					executionQuery = "SELECT * FROM "+importExportBean.getSelectedTable();
					if(dbbean.processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
						this.resultSet = dbbean.getResultSet();
						if(generateDownload(importExportBean.getExportOption().toLowerCase()).equalsIgnoreCase("SUCCESS")){
							setErrorMessage(importExportBean.getSelectedTable()+" has been successfully exported in ."+importExportBean.getExportOption().toLowerCase()+" format");
						}else{
							setErrorMessage("Unable to generate "+importExportBean.getExportOption()+" format file");
						}
					}else{
						setErrorMessage("");
					}
				}else{
					setErrorMessage("Select a format to export the table");
				}
			}else{
				setErrorMessage("Select a table to export");
			}
		}
		return null;
	}
	public String displayTable(){
		if(!importExportBean.getSelectedDatabase().equalsIgnoreCase(dbbean.getUserBean().getDb_schema())){
			if(onChangeSchema().equalsIgnoreCase("SUCCESS")){
				resetDisplay();
			}else{
				setErrorMessage("");
			}
		}else{
			if(importExportBean.getSelectedTable()!=null && !importExportBean.getSelectedTable().trim().equalsIgnoreCase("")){
				if(dbbean.getTableColumns(importExportBean.getSelectedTable()).equalsIgnoreCase("SUCCESS")){
					if(importExportBean.getTableColumns()!=null){
						importExportBean.getTableColumns().clear();
					}
					importExportBean.setTableColumns(dbbean.getColumns());
					executionQuery ="SELECT * FROM "+importExportBean.getSelectedTable();
					String status = dbbean.processQuery(executionQuery);
					if(status.equalsIgnoreCase("SUCCESS")){
						this.resultSet = dbbean.getResultSet();
						generateMetaData();
						renderDataTable = true;
						renderErrorMessages = false;
					}else{
						setErrorMessage("");
					}
				}else{
					setErrorMessage("");
				}
			}else{
				setErrorMessage("No table selected.Please select table");
			}
		}
		return "SUCCESS";
	}
	private String onChangeSchema(){
		status = "FAIL";
		//System.out.println("ImportExportActionBean.onChangeSchema() "+importExportBean.getSelectedDatabase());
		//System.out.println("ImportExportActionBean.onChangeSchema() "+event.getNewValue().toString());
		dbbean.getUserBean().setDb_schema(importExportBean.getSelectedDatabase());
		this.dbbean.closeDBResources();
		try{
			if(this.dbbean.getConnection()!=null){
				if(dbbean.connect().equalsIgnoreCase("SUCCESS")){
					status = "SUCCESS";
					resetDisplay();
				}else{
					//connection failed
					setErrorMessage("");
				}
			}else{
				setErrorMessage("Connection Expired");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;

	}
	public String onDatabaseChange(ValueChangeEvent event){
		status = "FAIL";
		//System.out.println("ImportExportActionBean.onChangeSchema() "+importExportBean.getSelectedDatabase());
		//System.out.println("ImportExportActionBean.onChangeSchema() "+event.getNewValue().toString());
		dbbean.getUserBean().setDb_schema(event.getNewValue().toString());
		this.dbbean.closeDBResources();
		try{
			if(this.dbbean.getConnection()!=null){
				if(dbbean.connect().equalsIgnoreCase("SUCCESS")){
					status = "SUCCESS";
					resetTables();
					resetDisplay();
					
				}else{
					//connection failed
					setErrorMessage("");
				}
			}else{
				setErrorMessage("Connection Expired");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	private String generateDownload(String format) {
		status = "FAIL";
		//System.out.println("ImportExportActionBean.generateDownload() format is "+format);
		if(this.resultSet!=null){
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			fos = null;
			filePath = ec.getRealPath("/exports");
			fileName = filePath + "/" +dbbean.getUserBean().getUsername()+ "_" + importExportBean.getSelectedTable()+"_"+FinalConstants.RANDOM_PRECISION*Math.random()+"."+format;
			//System.out.println("ImportExportActionBean.generateCSV() filePath is "+filePath);
			//System.out.println("ImportExportActionBean.generateCSV() filename is "+fileName);
			tempDirectory = new File(filePath);
			if(!tempDirectory.exists()){
				tempDirectory.mkdir();
			}
			exportFile = new File(fileName);
			if(exportFile.exists()){
				exportFile.delete();
			}
			try {
				exportFile.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			result = ResultSupport.toResult(resultSet);
			Object [][] sData = result.getRowsByIndex();
			//System.out.println("ImportExportActionBean.generateDownload() sData length is "+sData.length);
			String columnNames [] = result.getColumnNames();
			//System.out.println("ImportExportActionBean.generateDownload() columnnames lenght is  "+columnNames.length);
			StringBuffer sb = new StringBuffer();
			try {
				fos = new FileOutputStream(exportFile);
				switch (format) {
				case "csv":
					for(int i=0; i<columnNames.length; i++) {
						sb.append(columnNames[i].toString() + ",");
					}
					sb.append("\n");
					fos.write(sb.toString().getBytes());
					for(int i = 0; i < sData.length; i++) {
						sb = new StringBuffer();
						//System.out.println("ImportExportActionBean.generateDownload() col name  "+sData[i].length);
						for(int j=0; j<columnNames.length; j++) {
							//System.out.println("ImportExportActionBean.generateCSV() coltype is "+columnNames[j]+" data is "+sData[i][j]);
							if(sData[i][j]!=null){
								//System.out.println("ImportExportActionBean.generateCSV() data is "+sData[i][j].toString());
								String data = sData[i][j].toString();
								if(data.contains(",")){
									//System.out.println("ImportExportActionBean.generateDownload() data includes comma removing it");
									data = data.replaceAll(",", " ");
									//System.out.println("ImportExportActionBean.generateDownload() data after removing");
								}
								data = data.replaceAll("[^a-zA-Z\\d\\s,.]", "");
								////System.out.println("ImportExportActionBean.generateCSV() data is "+data);
								sb.append(data + ",");
							}else{
								if(dbbean.getResultSetMetaData()!=null){
									//System.out.println("ImportExportActionBean.generateDownload() column type "+dbbean.getResultSetMetaData().getColumnTypeName(j)+" columnname is "+columnNames[j]+" value is "+sData[i][j]);
									switch(dbbean.getResultSetMetaData().getColumnTypeName(j).toLowerCase()){
									case "int":
										sb.append(0+ ",");
										break;
									case "smallint":
										sb.append(0+ ",");
										break;
									case "float":
										sb.append(0+ ",");
										break;
									case "long":
										sb.append(0+ ",");
										break;
									case "double":
										sb.append(0+ ",");
										break;
									case "char":
										sb.append("NULL"+ ",");
										break;
									case "varchar":
										sb.append("NULL"+ ",");
										break;
									case "date":
										SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
										java.util.Date date = new Date();
										sb.append(df.format(date)+ ",");
										break;
									case "datetime":
										SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
										java.util.Date datetime = new Date();
										sb.append(tf.format(datetime)+ ",");
										break;
									case "enum":
										sb.append("NULL");
										break;
									default:
										sb.append("NULL");
									}
									
									
								}else{
									sb.append("NULL"+",");
								}
								/*String info = sData[0][0].toString();
								info = info.replaceAll("[^a-zA-Z\\d\\s,.]", "");*/
								//sb.append(info + ",");
								//sb.append("NULL" + ",");
							}
						}
						sb.append("\n");
						fos.write(sb.toString().getBytes());
					}
					break;
				case "xml":
					String columns="";
					for(int k=0;k<columnNames.length;k++){
						if(k<columnNames.length-1){
							columns = columns+columnNames[k]+",";
						}else{
							columns = columns+columnNames[k];
						}
					}
					//System.out.println("ImportExportActionBean.generateDownload() columns is "+columns);
					sb.append("<database name=\""+importExportBean.getSelectedDatabase()+"\">");
					sb.append("\n\t");
					sb.append("<table name=\""+importExportBean.getSelectedTable()+"\">");
					sb.append("\n\t\t");
					sb.append("<rows columns=\""+columns+"\">");
					sb.append("\n\t\t\t");
					for(int i = 0; i < sData.length; i++) {
						sb.append("<row id=\""+i+1+"\">");
						for(int j=0;j<columnNames.length;j++){
							sb.append("\n\t\t\t\t");
							if(sData[i][j]!=null){
								String data = sData[i][j].toString();
								data = data.replaceAll("[^a-zA-Z\\d\\s,.]", "");
								sb.append("<column name=\""+columnNames[j]+"\" value=\""+data+"\"/>");
							}else{
								String nullValue = "NULL";
								if(dbbean.getResultSetMetaData()!=null){
									//System.out.println("ImportExportActionBean.generateDownload() column type "+dbbean.getResultSetMetaData().getColumnTypeName(j));
									switch(dbbean.getResultSetMetaData().getColumnTypeName(j).toLowerCase()){
									case "int":
										sb.append("<column name=\""+columnNames[j]+"\" value=\""+0+"\"/>"+ ",");
										break;
									case "smallint":
										sb.append("<column name=\""+columnNames[j]+"\" value=\""+0+"\"/>"+ ",");
										break;
									case "float":
										sb.append("<column name=\""+columnNames[j]+"\" value=\""+0+"\"/>"+ ",");
										break;
									case "long":
										sb.append("<column name=\""+columnNames[j]+"\" value=\""+0+"\"/>"+ ",");
										break;
									case "double":
										sb.append("<column name=\""+columnNames[j]+"\" value=\""+0+"\"/>"+ ",");
										break;
									case "char":
										sb.append("<column name=\""+columnNames[j]+"\" value=\""+nullValue+"\"/>"+ ",");
										break;
									case "varchar":
										sb.append("<column name=\""+columnNames[j]+"\" value=\""+nullValue+"\"/>"+ ",");
										break;
									case "date":
										SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
										java.util.Date date = new Date();
										sb.append("<column name=\""+columnNames[j]+"\" value=\""+df.format(date)+"\"/>"+ ",");
										break;
									case "datetime":
										SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
										java.util.Date datetime = new Date();
										sb.append("<column name=\""+columnNames[j]+"\" value=\""+tf.format(datetime)+"\"/>"+ ",");
										break;
									case "enum":
										sb.append("<column name=\""+columnNames[j]+"\" value=\""+nullValue+"\"/>"+ ",");
										break;
									default:
										sb.append("<column name=\""+columnNames[j]+"\" value=\""+nullValue+"\"/>"+ ",");
									}
								}else{
									sb.append("<column name=\""+columnNames[j]+"\" value=\""+nullValue+"\"/>"+ ",");
								}
								/*String info = sData[0][0].toString();
								info = info.replaceAll("[^a-zA-Z\\d\\s,.]", "");
								sb.append("<column name=\""+columnNames[j]+"\" value=\""+info+"\"/>");*/
								//sb.append("NULL" + ",");
							}
						}
						sb.append("\n\t\t\t");
						sb.append("</row>");
						if(i<sData.length-1){
							sb.append("\n\t\t\t");
						}
					}
					sb.append("\n\t\t");
					sb.append("</rows>");
					sb.append("\n\t");
					sb.append("</table>");
					sb.append("\n");
					sb.append("</database>");
					fos.write(sb.toString().getBytes());
					break;
				default:
					break;
				}
				fos.flush();
				fos.close();
			}catch (FileNotFoundException e){
				setErrorMessage(e.getMessage());
			} catch (IOException e) {
				setErrorMessage(e.getMessage());
			}catch(Exception e){
				setErrorMessage(e.getMessage());
			}
			String mimeType = ec.getMimeType(fileName);
			fis = null;
			byte b;
			ec.responseReset();
			ec.setResponseContentType(mimeType);
			ec.setResponseContentLength((int) exportFile.length());
			ec.setResponseHeader("Content-Disposition","attachment; filename=\"" +dbbean.getUserBean().getUsername()+ "_" + importExportBean.getSelectedTable()+"."+format + "\"");
			try {
				fis = new FileInputStream(exportFile);
				OutputStream output = ec.getResponseOutputStream();
				while(true) {
					b = (byte) fis.read();
					if(b < 0)
						break;
					output.write(b);
				}
			}catch (IOException e) {
				setErrorMessage(e.getMessage());
			}finally {
				try{
					fis.close();
				}catch (IOException e){
					setErrorMessage(e.getMessage());
				}
				fc.responseComplete();
				status = "SUCCESS";
			}
		}
		return status;
	}
	
	private String insertIntoCSV(){
		rowCount = 0;
		boolean isErrorSet = false;
		try (LineNumberReader br = new LineNumberReader(new FileReader(exportFile))) {
		    String line;
		    String[] columns={};
		    String[] columnsType = {};
		    while ((line = br.readLine()) != null) {
		    	//System.out.println("ImportExportActionBean.importTable() line is "+line);
		    	if(line.trim().equalsIgnoreCase("")){
		    		setErrorMessage("Empty line in the file on line number: "+br.getLineNumber());
		    		continue;
		    	}
		    	if(br.getLineNumber()!=1){
		    		line = line.substring(0,line.length()-1);
		    	}
	    		//System.out.println("ImportExportActionBean.importTable() updated line is "+line);
	    		String[] rows = line.split(",");
	    		//System.out.println("ImportExportActionBean.importTable() columns length is "+rows.length);
		    	if(br.getLineNumber()==1){
		    		columns = rows;
		    		columnsType = new String[columns.length];
		    		for(int p=0;p<columns.length;p++){
		    			if(dbbean.getColumns()!=null && dbbean.getColumns().size()>0){
		    				if(dbbean.getColumns().contains(columns[p])){
			    				columnsType[p] = getColumnType(columns[p]); 
			    				status = "SUCCESS";
			    			}else{
			    				status = "FAIL";
			    				setErrorMessage("column "+columns[p]+" doesn't exist in the table: "+importExportBean.getSelectedTable());
			    				isErrorSet = true;
			    				break;
			    			}
		    			}
		    		}
		    		if(status.equalsIgnoreCase("FAIL")){
		    			isErrorSet = true;
		    			break;
		    		}else{
		    			executionQuery = "DELETE FROM "+importExportBean.getSelectedTable();
		    			if(dbbean.processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
		    				//System.out.println("ImportExportActionBean.importTable() deleted all rows from the selected table");
		    			}else{
		    				status = "FAIL";
		    				setErrorMessage("");
		    				isErrorSet = true;
		    				break;
		    			}
		    		}
		    	}else{
		    		executionQuery = "INSERT INTO "+importExportBean.getSelectedTable()+"("+getColumnNames(columns)+") VALUES(";
					for(int l=0;l<columns.length;l++){
						if(l<columns.length-1){
							executionQuery = executionQuery+"?,";
						}else{
							executionQuery = executionQuery+"?)";
						}
					}
					//System.out.println("ImportExportActionBean.insertIntoCSV() query is "+executionQuery);
					if(dbbean.getPrepStatement(executionQuery).equalsIgnoreCase("SUCCESS")){
						for(int m=0;m<columns.length;m++){
							//System.out.println("ImportExportActionBean.insertIntoCSV() columntype is "+columnsType[m]+" columnname is "+columns[m]+" columnvalue is "+rows[m]);
							switch(columnsType[m].toLowerCase()){
							case "int":
								dbbean.getPreparedStatement().setInt(m+1,Integer.parseInt(rows[m]));
								break;
							case "smallint":
								dbbean.getPreparedStatement().setInt(m+1,Integer.parseInt(rows[m]));
								break;
							case "float":
								dbbean.getPreparedStatement().setFloat(m+1,Float.parseFloat(rows[m]));
								break;
							case "double":
								dbbean.getPreparedStatement().setDouble(m+1,Double.parseDouble(rows[m]));
								break;
							case "date":
								SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
								java.util.Date date = df.parse(rows[m]) ;
								java.sql.Date sqldate = new java.sql.Date(date.getTime());
								dbbean.getPreparedStatement().setDate(m+1,sqldate);
								break;
							case "datetime":
								SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
								java.util.Date datetime = tf.parse(rows[m]) ;
								java.sql.Date sqldatetime = new java.sql.Date(datetime.getTime());
								dbbean.getPreparedStatement().setDate(m+1,sqldatetime);
								break;
							case "char":
								String resultValue = removeSpecialChars(rows[m]);
								dbbean.getPreparedStatement().setString(m+1,resultValue);
								break;
							case "varchar":
								String rValue = removeSpecialChars(rows[m]);
								dbbean.getPreparedStatement().setString(m+1,rValue);
								break;
							default:
								String resValue = removeSpecialChars(rows[m]);
								dbbean.getPreparedStatement().setString(m+1,resValue);
								break;
							}
						}
						if(dbbean.processQueryPreparedStatement(executionQuery).equalsIgnoreCase("SUCCESS")){
							rowCount++;
							status = "SUCCESS";
						}else{
							setErrorMessage("Unable to insert Row: "+line);
							status = "FAIL";
							isErrorSet = true;
							//break;
						}
					}else{
						setErrorMessage("");
						status = "FAIL";
						isErrorSet = true;
						break;
					}
		    	}
		    }
		    //System.out.println("ImportExportActionBean.insertIntoCSV() file imported successfully rowcount is "+rowCount);
		    if(!isErrorSet){
		    	setErrorMessage("File Upload Successful. Check your output directory");
			    renderImportResult = true;
			    if(br!=null){
			    	br.close();
			    }
		    }else{
		    	renderImportResult = false;
		    }
		}catch (IOException e) {
			renderImportResult = false;
			e.printStackTrace();
			setErrorMessage(e.getMessage());
		}catch (Exception e) {
			setErrorMessage("Unable to read the file uploaded");
			renderImportResult = false;
			e.printStackTrace();
		}
		return status;
	}
	private String insertIntoXML() {
		status = "FAIL";
		rowCount = 0;
		//System.out.println("ImportExportActionBean.insertIntoXML() inserting into database");
		boolean isErrorSet = false;
		List<String>rowValues = new ArrayList<>();
		try (LineNumberReader br = new LineNumberReader(new FileReader(exportFile))) {
		    String line;
		    String[] columns={};
		    String[] columnsType = {};
		    while ((line = br.readLine()) != null) {
		    	//System.out.println("ImportExportActionBean.importTable() line is "+line);
		    	if(line.contains("columns")){
		    		columns = getXMLColumns(line,"columns");
		    		columnsType = new String[columns.length];
		    		for(int p=0;p<columns.length;p++){
		    			if(dbbean.getColumns().contains(columns[p])){
		    				columnsType[p] = getColumnType(columns[p]); 
		    				status = "SUCCESS";
		    			}else{
		    				status = "FAIL";
		    				setErrorMessage("column "+columns[p]+" doesn't exist in the table: "+importExportBean.getSelectedTable());
		    				isErrorSet =true;
		    				break;
		    			}
		    		}
		    		if(status.equalsIgnoreCase("FAIL")){
		    			break;
		    		}else{
		    			executionQuery = "DELETE FROM "+importExportBean.getSelectedTable();
		    			if(dbbean.processQuery(executionQuery).equalsIgnoreCase("SUCCESS")){
		    				//System.out.println("ImportExportActionBean.importTable() deleted all rows from the selected table");
		    			}else{
		    				status = "FAIL";
		    				setErrorMessage("");
		    				isErrorSet = true;
		    				break;
		    			}
		    		}
		    		continue;
		    	}
		    	if(line.contains("row ")){
		    		//System.out.println("ImportExportActionBean.insertIntoXML() start of row");
		    		continue;
		    	}
		    	if(line.contains("column ")){
		    		String[] temp = line.split("value=");
		    		//System.out.println("ImportExportActionBean.insertIntoXML() insert value is "+getStringInQuotes(temp[1]));
		    		rowValues.add(getStringInQuotes(temp[1]));
		    		continue;
		    	}
		    	if(line.contains("/row>")){
		    		executionQuery = "INSERT INTO "+importExportBean.getSelectedTable()+"("+getColumnNames(columns)+") VALUES(";
					for(int l=0;l<columns.length;l++){
						if(l<columns.length-1){
							executionQuery = executionQuery+"?,";
						}else{
							executionQuery = executionQuery+"?)";
						}
					}
					if(dbbean.getPrepStatement(executionQuery).equalsIgnoreCase("SUCCESS")){
						for(int m=0;m<columns.length;m++){
							//System.out.println("ImportExportActionBean.insertIntoXML() columntype is "+columnsType[m]);
							switch(columnsType[m].toLowerCase()){
							case "int":
								dbbean.getPreparedStatement().setInt(m+1,Integer.parseInt(rowValues.get(m)));
								break;
							case "smallint":
								dbbean.getPreparedStatement().setInt(m+1,Integer.parseInt(rowValues.get(m)));
								break;
							case "float":
								dbbean.getPreparedStatement().setFloat(m+1,Float.parseFloat(rowValues.get(m)));
								break;
							case "double":
								dbbean.getPreparedStatement().setDouble(m+1,Double.parseDouble(rowValues.get(m)));
								break;
							case "date":
								SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
								java.util.Date date = df.parse(rowValues.get(m)) ;
								java.sql.Date sqldate = new java.sql.Date(date.getTime());
								dbbean.getPreparedStatement().setDate(m+1,sqldate);
								break;
							case "datetime":
								SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
								java.util.Date datetime = tf.parse(rowValues.get(m)) ;
								java.sql.Date sqldatetime = new java.sql.Date(datetime.getTime());
								dbbean.getPreparedStatement().setDate(m+1,sqldatetime);
								break;
							case "char":
								String resultValue = removeSpecialChars(rowValues.get(m));
								dbbean.getPreparedStatement().setString(m+1,resultValue);
								break;
							case "varchar":
								String rValue = removeSpecialChars(rowValues.get(m));
								dbbean.getPreparedStatement().setString(m+1,rValue);
								break;
							default:
								String resValue = removeSpecialChars(rowValues.get(m));
								dbbean.getPreparedStatement().setString(m+1,resValue);
								break;
							}
						}
						if(dbbean.processQueryPreparedStatement(executionQuery).equalsIgnoreCase("SUCCESS")){
							rowCount++;
							status = "SUCCESS";
						}else{
							//System.out.println("ImportExportActionBean.insertIntoXML() printing error2");
							setErrorMessage("Unable to insert Row: "+line);
							status = "FAIL";
							isErrorSet = true;
							//break;
						}
					}else{
						//System.out.println("ImportExportActionBean.insertIntoXML() printing error1");
						setErrorMessage("");
						status = "FAIL";
						isErrorSet = true;
						//break;
					}
					if(rowValues!=null && rowValues.size()>0){
						rowValues.clear();
					}
		    	}
		    }
		    //System.out.println("ImportExportActionBean.insertIntoXML() isErrorSet is "+isErrorSet);
		    if(!isErrorSet){
		    	renderImportResult = true;
				setErrorMessage("File Upload Successful");
		    }else{
		    	renderImportResult = false;
		    }
		    if(br!=null){
		    	br.close();
		    }
		}catch (IOException e) {
			//System.out.println("ImportExportActionBean.insertIntoXML() printing error3");
			e.printStackTrace();
			renderImportResult = false;
			setErrorMessage(e.getMessage());
		}catch (Exception e) {
			//System.out.println("ImportExportActionBean.insertIntoXML() printing error3");
			e.printStackTrace();
			renderImportResult = false;
			setErrorMessage(e.getMessage());
		}
		return status;
	}
	private String[] getXMLColumns(String line,String attribute) {
		String[]values={};
		String[] textarr = line.split("columns=");
		String quoteValue = getStringInQuotes(textarr[1]);
		if(quoteValue!=null){
			//System.out.println("ImportExportActionBean.getXMLColumns() quoteValue exists");
			values= quoteValue.split(",");
			//System.out.println("ImportExportActionBean.getXMLColumns() values is "+values);
		}
		return values;
	}
	
	private String getStringInQuotes(String quote){
		Pattern p = Pattern.compile(".*\\\"(.*)\\\".*");
		Matcher m = p.matcher(quote);
		if(m.find()){
			//System.out.println(m.group(1));
			return m.group(1);
		}
		return null;
	}
	private void resetTables(){
		if(importExportBean.getSelectedTable()!=null && !importExportBean.getSelectedTable().isEmpty()){
			importExportBean.setSelectedTable("");
		}
		if(importExportBean.getTables()!=null && importExportBean.getTables().size()>0){
			importExportBean.getTables().clear();
		}
	}
	private void resetDisplay() {
		renderDisplay = false;
		renderExportFields = false;
		renderImportFields = false;
		renderImportResult = false;
		renderDataTable = false;
		renderErrorMessages = false;
		renderQueryResults = false;
		rowCount = 0;
		columnsCount = 0;
	}
	private void setErrorMessage(String message){
		if(message.equalsIgnoreCase("")){
			this.errorMessageBean.setErrorMessage(dbbean.getErrorMessage());
		}else{
			this.errorMessageBean.setErrorMessage(message);
		}
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(this.errorMessageBean.getErrorMessage()));
		renderErrorMessages = true;
	}
	private String getColumnNames(String[] columns) {
		String column="";
		if(columns!=null && columns.length>0){
			for(int i=0;i<columns.length;i++){
				if(i<columns.length-1){
					column = column+columns[i]+",";
				}else{
					column = column+columns[i];
				}
			}
		}
		//System.out.println("ImportExportActionBean.getColumnNames() comma separated column is "+column);
		return column;
	}
	private String getColumnType(String column) {
		// TODO Auto-generated method stub
		String colType = "";
		if(dbbean.getColumns()!=null && dbbean.getColumns().size()>0){
			for(int i=0;i<dbbean.getColumns().size();i++){
				if(column.equalsIgnoreCase(dbbean.getColumns().get(i))){
					colType = dbbean.getColumnsType().get(i);
					break;
				}
			}
		}
				
		return colType;
	}
	private String removeSpecialChars(String input){
		StringBuilder sb = new StringBuilder();
		if(input!=null && input.length()>0){
			for(int i=0;i<input.length();i++){
				char ch = input.charAt(i);
			    if (!Character.isHighSurrogate(ch) && !Character.isLowSurrogate(ch)) {
			        sb.append(ch);
			    }
			}
		}
		return sb.toString();
	}
	private void generateMetaData(){
		if(dbbean.getResultSet()!=null){
			rsmd = dbbean.getResultSetMetaData();
			result = ResultSupport.toResult(resultSet);
			rowCount = result.getRowCount();
			try {
				columnsCount = rsmd.getColumnCount();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			renderDataTable = true;
		}
	}
}
