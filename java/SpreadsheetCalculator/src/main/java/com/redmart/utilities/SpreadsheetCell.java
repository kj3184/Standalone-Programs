package com.redmart.utilities;

public class SpreadsheetCell{
	Double value;
	boolean IsCurrentEvaluation;
	boolean IsEvaluated;
	String cellContent;
	
	public SpreadsheetCell(String cellContent){
		
		this.cellContent = cellContent;
		this.IsCurrentEvaluation = false;
		this.IsEvaluated = false;
	}
}