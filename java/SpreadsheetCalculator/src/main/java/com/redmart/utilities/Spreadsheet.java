package com.redmart.utilities;

import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 * @author Kunal
 *
 */
public class Spreadsheet {

	SpreadsheetCell spreadsheetCell;
	SpreadsheetCell[][] spreadsheetCellArr;
	private int sizeX;
	private int sizeY;

	/* 
	This method evaluates a spreadsheet cell recursively.
	Also, it detects cyclic dependencies using HashSet and return value when evaluation is completed.
	*/
	

	private Double evaluateCell(SpreadsheetCell sheetCell, Set<SpreadsheetCell> currentEvaluationStack) {

		if (currentEvaluationStack == null) {
			currentEvaluationStack = new LinkedHashSet<SpreadsheetCell>();
		}

		if (sheetCell.IsEvaluated) {
			// do nothing. Just return the value.
		} else if (!sheetCell.IsEvaluated && !currentEvaluationStack.contains(sheetCell)) {
			currentEvaluationStack.add(sheetCell);

			String[] fields = sheetCell.cellContent.split(" ");

			Stack<Double> operands = new Stack<Double>();

			for (int i = 0; i < fields.length; i++) {
				String s = fields[i];

				if (s.equals("+"))
					operands.push(operands.pop() + operands.pop());
				else if (s.equals("*"))
					operands.push(operands.pop() * operands.pop());
				else if (s.equals("/")) {

					double divisor = operands.pop();
					double dividend = operands.pop();

					operands.push(dividend / divisor);
				} else if (s.equals("-")) {
					double subtractor = operands.pop();
					double subtractee = operands.pop();

					operands.push(subtractee - subtractor);

				} else if (isNumber(s))
					operands.push(Double.parseDouble(s));
				else {
					SpreadsheetCell anotherCell = getCell(s);
					operands.push(evaluateCell(anotherCell, currentEvaluationStack));
				}
			}

			sheetCell.value = operands.pop();
			sheetCell.IsEvaluated = true;

		} else {
			System.out.println("Cycle Occurred while evaluating Cell Value " + sheetCell.cellContent);
			System.out.println("Loop trace:  ");
			for (SpreadsheetCell loopCell : currentEvaluationStack) {
				System.out.println(" cell with content : " + loopCell.cellContent + " ->");
			}
			System.exit(1);
		}

		return sheetCell.value;
	}

	/* 
	 * This is helper method to get cell content
	 */
	
	private SpreadsheetCell getCell(String cellContent) {

		try {
			int x = (int) cellContent.charAt(0) % 65;
			int y = Integer.parseInt(cellContent.substring(1, cellContent.length())) - 1;
			return spreadsheetCellArr[x][y];
		} catch (NumberFormatException e) {
			System.out.println("Data format error occurred while evaluating Cell" + cellContent);
			System.exit(1);
		}
		return null;

	}

	/* 
	 * This is helper method to check string is number
	 */
	private static boolean isNumber(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			// str is not numeric
			return false;
		}
	}

	/* 
	 * This method populates cell values into the SpreadSheet
	 * 
	 */
	
	private static void populateCellValues(Spreadsheet spreadSheet) {
		try {


			Scanner scanner = new Scanner(System.in);

			spreadSheet.spreadsheetCellArr = null;

			String[] fields = null;
			int[] size = verifyFirstLine(spreadSheet, scanner);

			int cellCount = constructCellArr(spreadSheet, scanner);

			if (cellCount != size[0] * size[1])
				throw new IllegalArgumentException("No of cells doesn't match the given size");
		} catch (Exception e) {
			System.out.println("Error occurred in while reading values");
			System.exit(1);
		}
	}

	private static int constructCellArr(Spreadsheet spreadSheet, Scanner scanner) {
		int rowIndex = 0, colIndex = 0, cellCount = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			spreadSheet.spreadsheetCell = new SpreadsheetCell(line);
			if (line.isEmpty())
				break;
			spreadSheet.spreadsheetCellArr[rowIndex][colIndex] = spreadSheet.spreadsheetCell;
			cellCount++;
			colIndex++;
			if (colIndex == spreadSheet.sizeY) {
				colIndex = 0;
				rowIndex++;
			}
		}
		return cellCount;
	}

	private static int[] verifyFirstLine(Spreadsheet spreadSheet, Scanner scanner) {
		String[] fields;
		int[] size = new int[2];
		if (scanner.hasNextLine()) {
			fields = scanner.nextLine().split(" ");

			if (fields.length != 2) {
				throw new IllegalArgumentException("Invalid Size");
			} else {
				for (int i = 0; i < fields.length; i++)
					size[i] = Integer.parseInt(fields[i]);
				spreadSheet.spreadsheetCellArr = new SpreadsheetCell[size[1]][size[0]];
				spreadSheet.sizeY = size[0];
				spreadSheet.sizeX = size[1];
			}

		}
		return size;
	}

	public static void main(String[] args) {

		try {

			final long startTime = System.currentTimeMillis();

			Spreadsheet spreadSheet = new Spreadsheet();

			populateCellValues(spreadSheet);

			for (int i = 0; i < spreadSheet.sizeX; i++) {
				for (int j = 0; j < spreadSheet.sizeY; j++) {
					spreadSheet.evaluateCell(spreadSheet.spreadsheetCellArr[i][j], null);
				}
			}
			System.out.println(spreadSheet.sizeY + " " + spreadSheet.sizeX);
			for (int i = 0; i < spreadSheet.sizeX; i++) {
				for (int j = 0; j < spreadSheet.sizeY; j++) {
//				System.out.println(spreadSheet.sheetCells[i][j].value);
					if (i == spreadSheet.sizeX - 1 && j == spreadSheet.sizeY - 1)
						System.out.printf("%.5f", spreadSheet.spreadsheetCellArr[i][j].value);
					else
						System.out.printf("%.5f%n", spreadSheet.spreadsheetCellArr[i][j].value);
				}
			}

			final long endTime = System.currentTimeMillis();

			System.out.println("Total execution time: " + (endTime - startTime));
		} catch (Exception e) {
			System.out.println("Error occurrend in main:" + e.getMessage());
		}
	}

}