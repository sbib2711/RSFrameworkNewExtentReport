package com.test.automation.selenium.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Utility {

	public static long startTime	= 0;
	public static long endTime 		= 0;
	
	public static long RandomNumber(int min, int max) {
		StringBuffer outputBuffer = new StringBuffer(min);
		StringBuffer MinValue = new StringBuffer(min);
		for (int i = 0; i < min; i++){
			outputBuffer.append("2");
			if (i == 0) {
				MinValue.append("1");
			}
			else {
				MinValue.append("0");
			}	
		}
		int Value1= Integer.valueOf(outputBuffer.toString());
		Random random = new Random();
		int RandomNumber = random.nextInt(Value1);
		if (RandomNumber < Integer.valueOf(MinValue.toString())) {
			return RandomNumber+ Integer.valueOf(MinValue.toString());
		}
		else {
			return RandomNumber;
		}
	}
	
	public static void SleepForWhile (int SleepTime) {
			try {
    		Thread.sleep(SleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
    		//Handle exception
		}
	}
	//PAN - 4056384578344748
	public static void SleepForWhileInSecs (int SleepTime) {
		try {
    		TimeUnit.SECONDS.sleep(SleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
    		//Handle exception
		}
	}
	public static String GetTimeStampForScreenShot() {
		String timestamp = new Timestamp(System.currentTimeMillis()).toString();
		timestamp = timestamp.substring(0, timestamp.length()-4).replaceAll("-|:| |","");
		return timestamp;
	}
	public static boolean isThisCardNumber(String CardNumber){
		if (isNumber(CardNumber) && CardNumber.length() ==16 ) 
			return true;
		else 
			return false;
	}
	
	public static boolean isNumber(String n) { 
		try { 
		@SuppressWarnings("unused")
		double d = Double.valueOf(n).doubleValue(); 
		return true; 
		} 
		catch (NumberFormatException e) { 
			e.printStackTrace();
		//e.printStackTrace(); 
		return false; 
		} 
	}
	public static String ChangeDBDateToMMDDYYY(String date) {
		date = date.substring(4, 6) + "/" + date.substring(6, 8) + "/" + date.substring(0, 4);
		return date;
	}
	
	
	public static String GetCurrentTimeStampInSec(){
	    //getTime() returns current time in milliseconds
		Date dNow = new Date();
		long time = dNow.getTime();
		return String.valueOf(time/1000L);
	}
	
	public static void startTimer(){
	    //getTime() returns current time in milliseconds
		Date dNow = new Date();
		long time = dNow.getTime();
		startTime = (time/1000L);
	}
	
	public static long getTimeElapse() {
	    //getTime() returns current time in milliseconds
		Date dNow = new Date();
		long time = dNow.getTime();
		endTime = (time/1000L);
		return endTime-startTime;
	}
	
	
	private static final String CHAR_LIST =  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String generateRandomString(int str_len) {
        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<str_len; i++){
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }
	
	private static int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
	}
	
	public static String GetRowBySearchStringInCsvFile(String fileToParse, String strToSearch, String delimiter, int col){
			
		BufferedReader fileReader = null;                 
		try  {            
			String line = "";           
			//Create the file reader            
			fileReader = new BufferedReader(new FileReader(fileToParse));                         
			//Read the file line by line            
			while ((line = fileReader.readLine()) != null)             
			{                //Get all tokens available in line  
				String[] tokens = line.split(delimiter);
				if (tokens[col].startsWith(strToSearch)) {	
				return line;  
				}
			}        
		}  catch (Exception e) {           
				e.printStackTrace();        
		}         
		finally        
		{            
			try {                
				fileReader.close();            
			} catch (IOException e)
				{                
					e.printStackTrace();            
				}        
		} 
		return null;
	}
	
	public static StringBuilder readFile(String path) 
	 {       
        File file = new File(path);
        if (!file.exists()) {
           return null;
        }	       
        StringBuilder builder = new StringBuilder();
	      BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                builder.append(line);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }

	       return builder;
	    }

	public static String GetLastPartialStringAsPerLength(String Value, int Rule) {
		switch (Rule) {
			case  4: Value = Value.substring(Value.length() - 4); break;
			case  6: Value = Value.substring(Value.length() - 6); break; 
			case  7: Value = Value.substring(Value.length() - 7); break;
		}
	return Value;
	}
	
	public static String GetFormattedMonth(String monthInNumber) {
		String Month = "";
		switch (monthInNumber) {
			case  "01": Month ="01 - January";
					break;
					
			case  "02": Month ="02 - February";
					break;	
					
			case  "03": Month ="03 - March";
					break;
		
			case  "04": Month ="04 - April";
					break;
		
			case  "05": Month ="05 - May";
					break;
		
			case  "06": Month ="06 - June";
					break;
		
			case  "07": Month ="07 - July";
					break;
		
			case  "08": Month ="08 - August";
					break;
		
			case  "09": Month ="09 - September";
					break;
		
			case  "10": Month ="10 - October";
					break;
		
			case  "11": Month ="11 - November";
					break;
					
			case  "12": Month ="12 - December";
					break;
		}
		return Month;
	}
	public static void writeToCommandLine(String info)  {
		System.out.println(info);
	}
}



