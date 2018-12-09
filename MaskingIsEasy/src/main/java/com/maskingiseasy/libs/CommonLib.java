package com.maskingiseasy.libs;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;

public final class CommonLib {
	
	public static final NumberFormat trlFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("tr-TR"));
	public static final NumberFormat numberFormat =NumberFormat.getNumberInstance(Locale.forLanguageTag("tr-TR"));
	public static final NumberFormat percentFormat =NumberFormat.getPercentInstance(Locale.forLanguageTag("tr-TR"));
	public static final NumberFormat numberFormatNoDecimal =NumberFormat.getIntegerInstance(Locale.forLanguageTag("tr-TR"));
	public static final NumberFormat numberFormat4Decimal =NumberFormat.getIntegerInstance(Locale.forLanguageTag("tr-TR"));
	
	static {
		numberFormat4Decimal.setMinimumFractionDigits(4);
		numberFormat4Decimal.setMaximumFractionDigits(4);

	}
	
	//------------------------------------------------------------------------------------
	public static String nvl(String val, String valifnull) {
		if (val==null || val.length()==0) return valifnull;
		else return val;
	}
	
	
	
	//------------------------------------------------------------------------------------
	static public Connection getConn(String driver, String url, String username, String password) {
		
		/*
		System.out.println("Connecting to : ");
		System.out.println("driver     :["+driver+"]");
		System.out.println("connstr    :["+url+"]");
		System.out.println("user       :["+username+"]");
		System.out.println("pass       :["+"************]");	
		*/
		
		/*
		
		Connection conn=ConnectionPool.getInstance().lease(driver, url, username, password);
		
		String leasedConnInfourl="";
		try {
			leasedConnInfourl=conn.hashCode()+ " " +conn.getMetaData().getURL().toString();
		} catch (Exception e) {
			
		}
		
		 return conn;
		*/


         Connection conn=null;
			try {
				Class.forName(driver.replace("*",""));
				conn = DriverManager.getConnection(url, username, password);
				return conn;
			} catch (Exception e) {
				System.out.println("Exception@getconn : " + e.getMessage());
				e.printStackTrace();
				return null;
			}
			
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	public  static String makePhotoUrl(String photo) {
		try {
			if (photo.contains("http://portal.adaoto.com.tr/carianaliz")) return photo;
			return "http://portal.adaoto.com.tr/carianaliz/"+photo.substring(3)+"?x="+System.currentTimeMillis();
		} catch(Exception e) {
			return "http://portal.adaoto.com.tr/carianaliz/assets/img/profile/default.jpg";
		}
	}
	
	


	
	//--------------------------------------------------------------------------------------
	public static void closeConn(Connection conn) {
		
		/*
		String leasedConnInfourl="";
		try {
			leasedConnInfourl=conn.hashCode()+ " " +conn.getMetaData().getURL().toString();
		} catch (Exception e) {
			
		}

		 
		ConnectionPool.getInstance().release(conn);
		*/

		try {conn.close();} catch(Exception e) {}
	}
	//--------------------------------------------------------------------------------------

	static public final String DEFAULT_DATE_FORMAT="dd/MM/yyyy HH:mm:ss";
	static public final String DEFAULT_DATE_FORMAT_NO_TIME="dd/MM/yyyy";
	//---------------------------------------------------------------------------------------
	public static ArrayList<String[]> getDbArray(
			Connection conn, 
			String sql, 
			int limit,
			ArrayList<String[]> bindlist, 
			int timeout_insecond,
			ArrayList<String> colNameList,
			ArrayList<String> colTypeList) {
		
		
		
		
		ArrayList<String[]> ret1 = new ArrayList<String[]>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData md = null;

		
		int reccnt = 0;
		try {
			if (pstmt == null) 	{
				pstmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				try {pstmt.setFetchSize(1000);} catch(Exception e)   {e.printStackTrace();}

			}
			
			
			//------------------------------ end binding

			if (bindlist!=null) {
				for (int i = 1; i <= bindlist.size(); i++) {
					String[] a_bind = bindlist.get(i - 1);
					String bind_type = a_bind[0];
					String bind_val = a_bind[1];
					
	
					if (bind_type.equals("INTEGER")) {
						if (bind_val == null || bind_val.equals(""))
							pstmt.setNull(i, java.sql.Types.INTEGER);
						else
							pstmt.setInt(i, Integer.parseInt(bind_val));
					} else if (bind_type.equals("LONG")) {
						if (bind_val == null || bind_val.equals(""))
							pstmt.setNull(i, java.sql.Types.INTEGER);
						else
							pstmt.setLong(i, Long.parseLong(bind_val));
					} else if (bind_type.equals("DOUBLE")) {
						if (bind_val == null || bind_val.equals(""))
							pstmt.setNull(i, java.sql.Types.DOUBLE);
						else
							pstmt.setDouble(i, Double.parseDouble(bind_val));
					} else if (bind_type.equals("FLOAT")) {
						if (bind_val == null || bind_val.equals(""))
							pstmt.setNull(i, java.sql.Types.FLOAT);
						else
							pstmt.setFloat(i, Float.parseFloat(bind_val));
					}  else if (bind_type.equals("DATE")) {
						if (bind_val == null || bind_val.equals(""))
							pstmt.setNull(i, java.sql.Types.DATE);
						else {
							Date d = new SimpleDateFormat(DEFAULT_DATE_FORMAT)
									.parse(bind_val);
							java.sql.Date date = new java.sql.Date(d.getTime());
							pstmt.setDate(i, date);
						}
					} 
					else if (bind_type.equals("TIMESTAMP")) {
						if (bind_val == null || bind_val.equals(""))
							pstmt.setNull(i, java.sql.Types.TIMESTAMP);
						else {
							Timestamp ts=new Timestamp(System.currentTimeMillis());
							try {ts=new Timestamp(Long.parseLong(bind_val));} catch(Exception e) {e.printStackTrace();}
							pstmt.setTimestamp(i, ts);
						}
					}
					else {
						pstmt.setString(i, bind_val);
					}
				}
				//------------------------------ end binding
			}  // if bindlist 
			
			
			
			if (timeout_insecond>0 )
				pstmt.setQueryTimeout(timeout_insecond);
			
			if (rs == null) rs = pstmt.executeQuery();
			if (md == null) md = rs.getMetaData();
			
			int colcount = md.getColumnCount();
			
			if (colNameList!=null || colTypeList!=null) {
				if (colNameList!=null) colNameList.clear();
				if (colTypeList!=null) colTypeList.clear();
				
				for (int i=1;i<=colcount;i++) {
					String col_name=md.getColumnLabel(i);
					String col_type=md.getColumnTypeName(i);
					
					if (colNameList!=null)  colNameList.add(col_name);
					if (colTypeList!=null)  colTypeList.add(col_type);
				}
			}
			
			
			String a_field = "";
			
			

			while (rs.next()) {
				reccnt++;
				if (reccnt > limit) break;
				String[] row = new String[colcount];
				for (int i = 1; i <= colcount; i++) {
					try {
						a_field = rs.getString(i);
						
						if (a_field.equals("null")) a_field=""; 
						if (a_field.length()>10000) a_field=a_field.substring(0,10000);
						} 
					catch (Exception enull) {a_field = "";}
					row[i - 1] = a_field;
				}
				ret1.add(row);
			}
			
			
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("Exception@getDbArray : SQL       => " + sql);
			System.out.println("Exception@getDbArray : MSG       => " + sqle.getMessage());
			System.out.println("Exception@getDbArray : CODE      => " + sqle.getErrorCode());
			System.out.println("Exception@getDbArray : SQL STATE => " + sqle.getSQLState());
		}
		catch (Exception ignore) {
			ignore.printStackTrace();
			System.out.println("Exception@getDbArray : SQL => " + sql);
			System.out.println("Exception@getDbArray : MSG => " + ignore.getMessage());
		} finally {
			try {rs.close();} catch(Exception e) { e.printStackTrace();}
			try {pstmt.close();} catch(Exception e) {e.printStackTrace();}
			
		}
		return ret1;
	}
	
	//****************************************
	public static ArrayList<String[]> getDbArrayForBulk(
			Connection conn, 
			String base_sql, 
			String key_in_condition,
			ArrayList<String[]> sourceArr, 
			int keyIndex, 
			String bindTypeName) {
		
		ArrayList<String[]> bulkCollector=new ArrayList<String[]>();
		
		
		
		ArrayList<String[]> bindlist=new ArrayList<String[]>();
		StringBuilder sbSql=new StringBuilder();


		int counter=0;

		for (String[] sarr:sourceArr) {
			counter++;
			if (sbSql.length()>0) sbSql.append(",");
			sbSql.append("?");
			bindlist.add(new String[] {bindTypeName,sarr[keyIndex]});

			if (counter % 1500==0 || counter==(sourceArr.size())) {
				if (bindlist.size()>0)  {
					String sql=base_sql+ "  "+key_in_condition+" in ("+sbSql.toString()+") ";

					bulkCollector.addAll(CommonLib.getDbArray(conn, sql, Integer.MAX_VALUE, bindlist, 0, null, null));
					sbSql.setLength(0);
					bindlist.clear();
				}
				
			}
			
		}
		
		return bulkCollector;
	}
	//****************************************
	static public boolean execSingleUpdateSQL(
			Connection conn, 
			String sql,
			ArrayList<String[]> bindlist, 
			boolean commit_after, 
			int timeout_as_sec,
			StringBuilder sberr
			) {

		boolean ret1 = true;
		PreparedStatement pstmt = null;

		StringBuilder using = new StringBuilder();
		try {
			pstmt = conn.prepareStatement(sql);

			if (timeout_as_sec>0) 
				try { pstmt.setQueryTimeout(timeout_as_sec);  } catch(Exception e) {}
			if (bindlist!=null)
			for (int i = 1; i <= bindlist.size(); i++) {
				String[] a_bind = bindlist.get(i - 1);
				String bind_type = a_bind[0];
				String bind_val = a_bind[1];
				if (i > 1)
					using.append(", ");
				using.append("{" + bind_val + "}");

				if (bind_type.equals("INTEGER")) {
					if (bind_val == null || bind_val.equals(""))
						pstmt.setNull(i, java.sql.Types.INTEGER);
					else
						pstmt.setInt(i, Integer.parseInt(bind_val));
				} else if (bind_type.equals("LONG")) {
					if (bind_val == null || bind_val.equals(""))
						pstmt.setNull(i, java.sql.Types.INTEGER);
					else
						pstmt.setLong(i, Long.parseLong(bind_val));
				} else if (bind_type.equals("DOUBLE")) {
					if (bind_val == null || bind_val.equals(""))
						pstmt.setNull(i, java.sql.Types.DOUBLE);
					else
						pstmt.setDouble(i, Double.parseDouble(bind_val));
				} else if (bind_type.equals("FLOAT")) {
					if (bind_val == null || bind_val.equals(""))
						pstmt.setNull(i, java.sql.Types.FLOAT);
					else
						pstmt.setFloat(i, Float.parseFloat(bind_val));
				} else if (bind_type.equals("DATE")) {
					if (bind_val == null || bind_val.equals(""))
						pstmt.setNull(i, java.sql.Types.DATE);
					else {
						Date d = new SimpleDateFormat(DEFAULT_DATE_FORMAT)
								.parse(bind_val);
						java.sql.Date date = new java.sql.Date(d.getTime());
						pstmt.setDate(i, date);
					}
				} 
				else if (bind_type.equals("TIMESTAMP")) {
					if (bind_val == null || bind_val.equals(""))
						pstmt.setNull(i, java.sql.Types.TIMESTAMP);
					else {
						Timestamp ts=new Timestamp(System.currentTimeMillis());
						try {ts=new Timestamp(Long.parseLong(bind_val));} catch(Exception e) {e.printStackTrace();}
						pstmt.setTimestamp(i, ts);
					}
				}
				else {
					pstmt.setString(i, bind_val);
				}
			}



			pstmt.executeUpdate();
			//pstmt_execbind.execute();
			
			if (!conn.getAutoCommit() && commit_after) 	{
				conn.commit();
			}


		} catch (SQLException e) {
			System.out.println("SQLException@execSingleUpdateSQL : " + e.getErrorCode()+" " +e.getSQLState()+e.getSQLState());
			e.printStackTrace();
			if (sberr!=null) sberr.append("SQLException@execSingleUpdateSQL : " + e.getErrorCode()+" " +e.getSQLState()+" " +e.getMessage());
			ret1 = false;
		} catch (Exception e) {
			System.out.println("Exception@execSingleUpdateSQL : " + e.getMessage());
			e.printStackTrace();
			if (sberr!=null) sberr.append("Exception@execSingleUpdateSQL : " + e.getMessage());
			ret1 = false;
		} finally {
			try {
				pstmt.close();
				pstmt = null;
			} catch (Exception e) {
			}
		}

		return ret1;
	}
	
	
	//--------------------------------------------------------------
	public static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }



	public static String extractFileName(String path) {
		try {
			int pos=path.lastIndexOf("/");
			if (pos==-1) pos=path.lastIndexOf("\\");
			if (pos==-1) return path;
			return path.substring(pos+1, path.length());
		} catch(Exception e) {
			return path;
		}
	}


	
	
	//----------------------------------------------------------------------------------------------------------
	public static Date LocalDate2Date(LocalDate localDate) {
		if (localDate==null) return null;
		try{return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());} catch(Exception e) {
			return null;
		}
	}
	//----------------------------------------------------------------------------------------------------------
	public static LocalDate Date2LocalDate(Date date) {
		if (date==null) return null;
		try{
			Instant instant = date.toInstant();
			ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
			return zdt.toLocalDate();
			} catch(Exception e) {

			return null;
		}
	}
	//-------------------------------------------------------------------------------------------------------------
	public static double roundTo2Decimal(double in) {
		return  Math.round(in * 100.0) / 100.0;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	public static boolean checkRegex(String strtocheck, String regex) {
		try {
			Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(strtocheck);
			return m.find();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	//----------------------------------------------------------------------------------------------------------------
	public static String getSystemProperty(String propertyName) {
		try {
			 String ret1 = System.getProperty(propertyName);
			return ret1;
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}


	
	
	//-----------------------------------------------------------------------------------------------
	public static int getHttpResponseCode(String urlString) {
	    URL u =null;
	    
	    int ret1=0;
	    try {
	    	u =new URL(urlString); 
	    	HttpURLConnection huc =  (HttpURLConnection)  u.openConnection(); 
	 	    huc.setRequestMethod("GET"); 
	 	    huc.connect(); 
	 	   
	 	   ret1= huc.getResponseCode();
	 	   
	 	   return ret1;
	 	   
	    } catch(Exception e) {
	    	e.printStackTrace(); 
	    }
	    return ret1;
	   
	}






	//------------------------------------------------------------------
	public static String date2Str(Date date, String format) {
		SimpleDateFormat df=new SimpleDateFormat(format);
		try {
			return df.format(date);
		} catch (Exception e) {
			return "";
		}
	}
	//------------------------------------------------------------------
	public static Date str2Date(String date, String format) {
		SimpleDateFormat df=new SimpleDateFormat(format);
		try {
			return df.parse(date);
		} catch (Exception e) {
			return null;
		}
	}


	//------------------------------------------------------------------------------------------------



	public static String clearHtml(String content) {
		if (content==null) return "";
		return content.replaceAll("\\<[^>]*>","");
	}

	
	// ---------------------------------------------------
	static public TextField makeFilterFieldWithEvent(ValueChangeListener<String> listener) {
		TextField field=new TextField();
		field.addStyleName(ValoTheme.TEXTFIELD_TINY);
		field.setValueChangeMode(ValueChangeMode.LAZY);
		field.setWidth(100, Unit.PERCENTAGE);
		field.addValueChangeListener(listener);
		return field;
	}
	
	public static String getFilterFieldByCellId(Grid grid, String cellId) {
		String ret1="";
		int headerCount=grid.getHeaderRowCount();
		if (headerCount<2) return "";
		try {
			HeaderRow header=grid.getHeaderRow(1);
			TextField tf=(TextField) header.getCell(cellId).getComponent();
			return tf.getValue();
		} catch (Exception e) {
			
		}
		return ret1;
	}

	
	//---------------------------------------------------------------------------------
	public static void setStyle(UI current) {

		 current.getPage().getCurrent().getStyles().add(
                ".v-grid-column-header-content  {color: red; font-weight: bold;}"+
                ".v-grid-column-default-header-content  {color: red; font-weight: bold;}"+
                ".v-grid-cell  {color: black; font-weight: bold;}"+
                ".v-select-option  {color: black; font-weight: bold;}"+
                ".v-textfield  {color: black; font-weight: bold;}"+
                ".v-textfield-small  {color: black; font-weight: bold;}"+
                ".v-textfield-tiny  {color: black; font-weight: bold;}"+
                ".v-label  {color: blue; font-weight: bold;}"+
                ".v-label-small  {color: blue; font-weight: bold;}"+
                ".v-label-tiny  {color: blue; font-weight: bold;}"+
                ".v-datefield-textfield  {color: black; font-weight: bold;}"+
                ".v-filterselect  {color: black; font-weight: bold;}"+
                ".v-filterselect-input  {color: black; font-weight: bold;}"+
                ".v-button  {color: green; font-weight: bold;}"+
                ".v-button-small  {color: green; font-weight: bold;}"+
                ".v-button-tiny  {color: green; font-weight: bold;}"+
                ".v-caption  {font-weight: bold;}"+
                ""
                 
                

         );
		 
	}


	
}
