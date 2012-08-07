package com.bonanzapress.utilities;


public class BingoMaster {
	static int[][] range = new int[5][2]; 

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		setRange();
		BingoFace gbc = new BingoFace( 5, 3, range );
	}

	public static void setRange() {
		range[0][0] = 1;
		range[0][1] = 15;
		range[1][0] = 16;
		range[1][1] = 30;
		range[2][0] = 31;
		range[2][1] = 45;
		range[3][0] = 46;
		range[3][1] = 60;
		range[4][0] = 61;
		range[4][1] = 75;
	}
	
	public static void generateBingoNumbers() {
		
	}
}
