package com.bonanzapress.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class BingoFace {
	private static int nextID = 0;
	private int id;
	private int rows;
	private int columns;
	private int[][] face;
	private int[][] limits;  // limits[row][0] = low; limits[row][1]
	private SortedSet<String> winningPaths = new TreeSet<String>();
	private int[] winner;
	private int count = 0;
	
	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		int rows = 5;
		int columns = 3;
		BingoFace facePlant = new BingoFace( rows, columns );
//		int[][] endpoints = { { 1, 15 }, 
//							  { 16, 30 },
//							  { 31, 45 },
//							  { 46, 60 },
//							  { 61, 75 }
//							 };
//		BingoFace face = new BingoFace( rows, columns, endpoints );
//		System.out.println( "rows: " + face.getRows() );
//		System.out.println( "columns: " + face.getColumns() );
//		System.out.println( face.formattedCardFace() );
//		face.generateWinningPaths( face.face, 0 );
//		System.out.println( "count: " + face.count );
		
//		BingoFace face2 = new BingoFace( 10, 7, 225 );
//		System.out.println( "rows: " + face2.getRows() );
//		System.out.println( "columns: " + face2.getColumns() );
//		System.out.println( face2.formattedCardFace() );
//		System.out.println( "limits:\n" + face2.getLimitsString() );
	}
	
	/**
	 * Create a BingoFace using the number of rows, columns, and limits
	 * to each row. Should only be used if the customer wants the rows
	 * to have different size ranges.
	 * @param rows number of rows on the bingo face; first array index
	 * @param columns number of columns on the bingo face; second array index
	 * @param limits the high and low value of a given row
	 */
	public BingoFace( int rows, int columns, int[][] limits ) {
		if ( limits == null || limits.length != rows ) return;
		this.id = nextID++;
		this.rows = rows;
		this.columns = columns;
		this.limits = limits;
		this.winner = new int[rows];
		this.face = generateFace( this.rows, this.columns, this.limits );
	}
	
	/**
	 * Create a BingoFace using the number of rows, columns, and a uniform,
	 * ascending order with a step of 1.
	 * @param rows number of rows on the bingo face; first array index
	 * @param columns number of columns on the bingo face; second array index
	 * @param rowIncrement the size of the range of each row
	 */
	public BingoFace( int rows, int columns, int rowIncrement ) {
		// make sure that there are enough spaces per row to
		// account for the number expected
		if ( rowIncrement < columns ) return;
		this.id = nextID++;
		// initialize the limit array with the top and bottom
		// values based on the value of rowIncrement
		int[][] lims = new int[rows][2];
		for ( int i = 0; i < rows; i++ ) {
			lims[i][0] = ( i * rowIncrement ) + 1;
			lims[i][1] = rowIncrement * ( i + 1 );
		}
		this.rows = rows;
		this.columns = columns;
		this.limits = lims;
		this.face = generateFace( this.rows, this.columns, this.limits );
	}
	
	/**
	 * this constructor is for testing purposes
	 * @param rows
	 * @param columns
	 * @param limits
	 */
	public BingoFace( int rows, int columns ) {
		if ( rows != 5 || columns != 3 ) return;
		this.id = nextID++;
		this.rows = rows;
		this.columns = columns;
		this.limits = new int[this.rows][2];
		this.limits[0][0] = 1;
		this.limits[0][1] = 3;
		this.limits[1][0] = 4;
		this.limits[1][1] = 6;
		this.limits[2][0] = 7;
		this.limits[2][1] = 9;
		this.limits[3][0] = 10;
		this.limits[3][1] = 12;
		this.limits[4][0] = 13;
		this.limits[4][1] = 15;
		this.face = new int[5][3];
		int count = 1;
		for ( int i = 0; i < 5; i++ ) {
			for ( int j = 0; j < 3; j++ ) {
				face[i][j] = count++;
			}
		}
	}
	
	
	public void generateWinningPaths( int[][] bFace, int row ) {
		
	}
	
	/**
	 * Create a string that displays the face with columns 5 characters wide.
	 * @return string containing the face information
	 */
	public String formattedCardFace() {
		StringBuilder builder = new StringBuilder();
		String separator = System.getProperty( "line.separator" ); // \n doesn't always work for some reason
		for ( int i = 0; i < rows; i++ ) {
			for ( int j = 0; j < columns; j++ ) {
				builder.append( String.format( "%5s", Integer.toString( face[i][j] ) ) );
			}
			builder.append( separator );
		}
		return builder.substring( 0, builder.length() - separator.length() );
	}
	
	/**
	 * Creates the 2D array that holds the values for the numbers on the face.
	 * @param horizontal number of rows
	 * @param vertical number of columns
	 * @param ranges the bottom and top value for each row
	 * @return array of numbers associated with this face
	 */
	private int[][] generateFace( int horizontal, int vertical, int[][] ranges ) {
		int[][] bingoFace = new int[horizontal][vertical];
		for ( int i = 0; i < horizontal; i++ ) {
			bingoFace[i] = generateRandomRow( ranges[i][0], ranges[i][1] );
		}
		return bingoFace;
	}

	/**
	 * generate a row of numbers for the bingo card face
	 * 
	 * @param level
	 *            the row being generated
	 * @return array of numbers
	 */
	private int[] generateRandomRow( int low, int high ) {
		ArrayList<Integer> rowNumbers = new ArrayList<Integer>();
		for ( int i = 0; i < ( high - low ) + 1; i++ ) {
			rowNumbers.add( low + i );
		}
		Collections.shuffle( rowNumbers );
		int[] nums = new int[this.getColumns()];
		for ( int i = 0; i < nums.length; i++ ) {
			nums[i] = rowNumbers.get( i );
		}
		Arrays.sort( nums );
		return nums;
	}
	
	private String getLimitsString() {
		StringBuilder builder = new StringBuilder();
		String separator = System.getProperty( "line.separator" );
		for ( int i = 0; i < limits.length; i++ ) {
			builder.append( "[" + limits[i][0] + "," + limits[i][1] + "]" );
			builder.append( separator );
		}
		return builder.substring( 0, builder.length() - separator.length() );
	}
	
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String separator = System.getProperty( "line.separator" );
		builder.append( "Face ID: " + id );
		builder.append( separator );
		
		
		return builder.toString();
	}
}
