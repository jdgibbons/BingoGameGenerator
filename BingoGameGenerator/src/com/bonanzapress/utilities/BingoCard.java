package com.bonanzapress.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class BingoCard {
	private static int id = 0;
	private String cardId;
	private int[][] numbers = null;
	private SortedSet<String> winners = new TreeSet<String>();

	private final boolean DEBUG = false;

	public static void main( String[] args ) {
		String[] nums = new String[15];
		for ( int i = 0; i < 15; i++ ) {
			nums[i] = Integer.toString( i );
		}

		BingoCard bingo = new BingoCard( nums );
		bingo.createSortedSet();
		bingo.printWinners();
		System.out.println( "HashSet.size() = " + bingo.numberOfWinners() );
		System.out.println( "01-05-07-10-13.contained: "
				+ bingo.containsWinningPath( "01-05-07-10-13" ) );
		System.out.println( "02-04-05-07-24.contained: "
				+ bingo.containsWinningPath( "02-04-05-07-24" ) );
		System.out.println( "CardID: " + bingo.getCardId() );
		bingo.printCard();
	}

	/**
	 * create a new bingo face from an array of strings containing 15 numbers
	 * 
	 * @param numbers
	 *            array of numbers as strings
	 */
	public BingoCard( String[] numbers ) {
		if ( numbers == null || numbers.length != 15 )
			return;
		this.cardId = getNextId();
		this.numbers = new int[5][3];
		int position = 0;
		for ( int i = 0; i < 5; i++ ) {
			for ( int j = 0; j < 3; j++ ) {
				this.numbers[i][j] = Integer.parseInt( numbers[position++] );
			}
		}
	}

	/**
	 * make a new card with random numbers first row: 1-15 second row: 16-30
	 * third row: 31-45 fourth row: 46-60 fifth row: 61-75
	 */
	public BingoCard() {
		this.numbers = new int[5][3];
		this.cardId = getNextId();
		for ( int level = 0; level < 5; level++ ) {
			numbers[level] = generateRandomRow( level );
		}
	}

	/**
	 * generate a row of numbers for the bingo card face
	 * 
	 * @param level
	 *            the row being generated
	 * @return array of numbers
	 */
	private int[] generateRandomRow( int level ) {
		ArrayList<Integer> fifteen = new ArrayList<Integer>();
		for ( int i = 1; i < 16; i++ ) {
			fifteen.add( i );
		}
		Collections.shuffle( fifteen );
		int[] nums = { fifteen.get( 0 ) + ( level * 15 ),
				fifteen.get( 1 ) + ( level * 15 ),
				fifteen.get( 2 ) + ( level * 15 ) };
		Arrays.sort( nums );
		return nums;
	}

	/**
	 * Send the card face to System.out
	 */
	public void printCard() {
		for ( int i = 0; i < 5; i++ ) {
			for ( int j = 0; j < 3; j++ ) {
				System.out.print( numbers[i][j] + " " );
			}
			System.out.println();
		}
	}

	/**
	 * return a formatted string with the face numbers
	 * 
	 * @return
	 */
	public String formattedCardFace() {
		StringBuilder builder = new StringBuilder();
		for ( int i = 0; i < 5; i++ ) {
			for ( int j = 0; j < 3; j++ ) {
				builder.append( String.format( "%5s",
						Integer.toString( numbers[i][j] ) ) );
			}
			builder.append( "\n" );
		}
		return builder.toString();
	}

	/**
	 * return a formatted string of 1 row of the face
	 * 
	 * @param level
	 *            the row to be return
	 * @return formatted row of numbers
	 */
	public String formattedCardFaceLine( int level ) {
		StringBuilder builder = new StringBuilder();
		for ( int i = 0; i < 3; i++ ) {
			builder.append( String.format( "%5s",
					Integer.toString( numbers[level][i] ) ) );
		}
		return builder.toString();
	}

	public String CSVCardFaceLine( int level ) {
		StringBuilder builder = new StringBuilder();
		for ( int i = 0; i < 3; i++ ) {
			builder.append( Integer.toString( numbers[level][i] ) + " " );
		}
		return builder.toString().trim().replace( ' ', ',' );
	}
	
	/**
	 * Cycle through the five rows and columns to generate all of the winning
	 * paths on the card face. Path begins at the top and works its way down.
	 * The next path is found by moving to the next location on the bottom
	 * row, then bumping the row above it when it reaches the end
	 */
	public void createSortedSet() {
		StringBuilder builder = new StringBuilder();
		int count = 0;
		for ( int i = 0; i < 3; i++ ) {                  //  00 00 00
			for ( int j = 0; j < 3; j++ ) {              //  00 00 00
				for ( int k = 0; k < 3; k++ ) {          //  00 00 00
					for ( int l = 0; l < 3; l++ ) {      //  00 00 00
						for ( int m = 0; m < 3; m++ ) {  //  00 00 00
							builder.append( String.format( "%02d",
									numbers[0][i] ) );
							builder.append( "-" );
							builder.append( String.format( "%02d",
									numbers[1][j] ) );
							builder.append( "-" );
							builder.append( String.format( "%02d",
									numbers[2][k] ) );
							builder.append( "-" );
							builder.append( String.format( "%02d",
									numbers[3][l] ) );
							builder.append( "-" );
							builder.append( String.format( "%02d",
									numbers[4][m] ) );
							winners.add( builder.toString() );
							builder.setLength( 0 );
						}
					}
				}
			}
		}
		if ( DEBUG )
			System.out.println( "Count = " + count );
	}

	/**
	 * check whether a particular path is on the card face
	 * 
	 * @param winningPath
	 *            the path in 00-00-00-00-00 format
	 * @return true if path exists on face
	 */
	public boolean containsWinningPath( String winningPath ) {
		return winners.contains( winningPath );
	}

	/**
	 * print all of the winners from a face. For debug purposes.
	 */
	public void printWinners() {
		Iterator<String> iterator = winners.iterator();
		while ( iterator.hasNext() ) {
			System.out.println( iterator.next() );
		}
	}

	/**
	 * return the number of winners--should always be 243
	 * 
	 * @return int number of winning paths
	 */
	public int numberOfWinners() {
		return winners.size();
	}

	/**
	 * retrieve the next id for new face
	 * 
	 * @return new id
	 */
	private String getNextId() {
		int tempId = id++;
		return String.format( "%08d", tempId );
	}

	/**
	 * return the unique id for this
	 * 
	 * @return id
	 */
	public String getCardId() {
		return cardId;
	}

	/**
	 * return the set of the face's winning paths
	 * 
	 * @return set of winning paths
	 */
	public SortedSet<String> getWinners() {
		return winners;
	}
}
