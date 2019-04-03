package br.com.lacqua.util;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import net.sf.jasperreports.engine.JRException;

public class TesteJPA {

	public static void main(String[] args) throws JRException, FileNotFoundException, SQLException, ParseException {

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		Integer dia = null;
		Integer ano = null;
		Integer mes = null;
		
		dia = cal.get(Calendar.DAY_OF_MONTH);
		mes = cal.get(Calendar.MONTH) + 1;
		ano = cal.get(Calendar.YEAR);
		
		System.out.println(dia + "/" + mes+ "/" + ano);
	}
}