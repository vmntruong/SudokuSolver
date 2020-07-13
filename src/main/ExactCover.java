package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExactCover {
	
	private static int NB = 5;
	private static int NBSE = 8;
	private static int[] nbelemse = new int[NBSE];
	private static List<List<Integer>> se = new ArrayList<List<Integer>>();
	private static int[] used = new int[NB];
	private static int[] pred = new int[NB];
	private static int number = 0;

	public static void main(String[] args) {
		entreeDesParties();
		
		for (int i=0; i<NBSE; i++) {
			for (int j=0; j<NB; j++) {
				used[j] = 0;
			}
			arbre(i, 1);
			System.out.println("------");
		}
	}
	
	static void entreeDesParties() {
		List<Integer> li;
		li = new ArrayList<Integer>(Arrays.asList(1, 5)); 		se.add(0, li);
		li = new ArrayList<Integer>(Arrays.asList(2, 4)); 		se.add(1, li);		
		li = new ArrayList<Integer>(Arrays.asList(2, 3)); 		se.add(2, li);
		li = new ArrayList<Integer>(Arrays.asList(3)); 			se.add(3, li);
		li = new ArrayList<Integer>(Arrays.asList(1, 4, 5)); 	se.add(4, li);
		li = new ArrayList<Integer>(Arrays.asList(1, 3, 5)); 	se.add(5, li);
		li = new ArrayList<Integer>(Arrays.asList(2, 5)); 		se.add(6, li);
		li = new ArrayList<Integer>(Arrays.asList(1, 4)); 		se.add(7, li);
		
		for (int i = 0; i < NBSE; i++) { nbelemse[i] = se.get(i).size(); }
	}
	
	static void arbre(int i, int etage) {
		int j, k, e, allused, flag;
		for ( j = 0; j < nbelemse[i]; j++) { 
			used[se.get(i).get(j)-1] = etage; 
		}
		allused = 1;
		for ( j = 0 ; j < NB; j++) 
			if( used[j] == 0 ) { 
				allused = 0; break; 
			}
		if ( allused == 1 ) {
			number++; System.out.println("Solution n°" + number);
			for ( k = 0; k < NB; k++ ) { System.out.println(used[k]); }
			//for( k = 2; k <= etage; k++) System.out.println(pred[k]);
			//System.out.println(i);
			
		}
		else {
			for ( e = i+1; e < NBSE; e++ ) //chaque partie de numéro > i
			{
				for ( j = 0; j < NB; j++ ) 
					if( used[j] > etage ) 
						used[j] = 0; // actualisation
				flag = 0; /* si flag = 1, on ne prend pas la partie */
				for ( j = 0; j < nbelemse[e]; j++ ) 
					if( used[se.get(e).get(j)-1] != 0 ) { 
						flag = 1; break; 
					}
				if ( flag == 0 ) { 
					pred[etage] = i+1; 
					arbre(e, etage+1); 
				}
			}
		}
	}

}
