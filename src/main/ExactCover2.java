package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExactCover2 {
	private static int NB = 5;
	private static int NBSE = 8;
	private static int[] nbelemse = new int[NBSE+1];
	private static List<List<Integer>> se = new ArrayList<List<Integer>>();
	private static List<Integer> resultat = new ArrayList<Integer>();
	private static int compteur = 0;
	private static Cell racine;
	private static Cell[] debutligne = new Cell[NBSE+1];
	private static Cell[] debutcolonne = new Cell[NB+1];
	
	public static void main(String[] args) {
		entreeDesParties();
		matrice();
		chercher();
	}
	
	/*
	 * Entrée des parties
	 */
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
		
		for (int i = 1; i <= NBSE; i++) { nbelemse[i] = se.get(i-1).size(); }
	}
	
	public static void matrice() {
		Cell oldcell, newcell;
		Cell[] oldcellcol = new Cell[NB+1];
		int i, j, colonne ;
		
		/* Création de la racine */
		racine = new Cell();	
		racine.l = 0;
		racine.c = 0;
		racine.b = racine;
		racine.h = racine;
		racine.d = racine;
		racine.g = racine;
		
		
		/* Bordure gauche verticale */
		oldcell = racine;
		for (i=1; i<=NBSE; i++) {
			debutligne[i] = new Cell();
			debutligne[i].l = i;
			debutligne[i].c = 0;
			oldcell.b = debutligne[i]; racine.h = debutligne[i];
			debutligne[i].b = racine; debutligne[i].h = oldcell;
			oldcell = debutligne[i];
		}
		
		/* Bordure haute horizontale */
		oldcell = racine;
		for (j=1; j<=NB; j++) {
			debutcolonne[j] = new Cell();
			debutcolonne[j].l = 0;
			debutcolonne[j].c = j;
			oldcell.d = debutcolonne[j]; racine.g = debutcolonne[j];
			debutcolonne[j].d = racine; debutcolonne[j].g = oldcell;
			oldcell = debutcolonne[j];
		}
		
		/* Construction des lignes comme listes chaînées, ainsi que les colonnes */
		for (j=1; j<=NB; j++) {
			oldcellcol[j] = debutcolonne[j];
		}
		for (i=1; i<=NBSE; i++) {
			oldcell = debutligne[i];
			for (j=1; j<=nbelemse[i]; j++) {
				newcell = new Cell();
				colonne = se.get(i-1).get(j-1);
				newcell.l = i; newcell.c = colonne;
				// liens droit-gauche
				newcell.d = debutligne[i]; newcell.g = oldcell;
				oldcell.d = newcell; debutligne[i].g = newcell;
				oldcell = newcell;
				// liens haut-bas
				newcell.b = debutcolonne[colonne]; newcell.h = oldcellcol[colonne];
				oldcellcol[colonne].b = newcell; debutcolonne[colonne].h = newcell;
				oldcellcol[colonne] = newcell;
				
			}
		}
		
		/* Supprimer les butoirs de lignes */
		for (i=1; i<=NBSE; i++) {
			debutligne[i].g.d = debutligne[i].d;
			debutligne[i].d.g = debutligne[i].g;
		}
	}

	/*
	 * Afficher une colonne
	 */
	static void afficherColonne(int j) {
		Cell cell;
		System.out.println(j);
		cell = debutcolonne[j].b;
		do {
			System.out.println(cell.l);
			cell = cell.b;
		} while(cell!=debutcolonne[j]);
	}
	
	/*
	 * Afficher toutes les colonnes
	 */
	static void afficherLesColonnes() {
		Cell cell;
		cell  = racine.d;
		while (cell!=racine) {
			afficherColonne(cell.c);
			cell = cell.d;
		}
	}
	
	/*
	 * Cover une colonne
	 */
	static void cover(int col) {
		Cell cellc, celll;
		debutcolonne[col].g.d = debutcolonne[col].d;
		debutcolonne[col].d.g = debutcolonne[col].g;
		cellc = debutcolonne[col].b;
		while (cellc!=debutcolonne[col]) {
			celll = cellc.d;
			while (celll!=cellc) {
				celll.h.b = celll.b;
				celll.b.h = celll.h;
				celll = celll.d;
			}
			cellc = cellc.b;
		}
	}
	
	/*
	 * Uncover une colonne
	 */
	static void uncover(int col) {
		Cell cellc, celll;
		cellc = debutcolonne[col].h;
		while (cellc!=debutcolonne[col]) {
			celll = cellc.g;
			while (celll!=cellc) {
				celll.h.b = celll;
				celll.b.h = celll;
				celll = celll.g;
			}
			cellc = cellc.h;
		}
		debutcolonne[col].g.d = debutcolonne[col];
		debutcolonne[col].d.g = debutcolonne[col];
	}
	
	/*
	 * Chercher des solutions
	 */
	static void chercher() {
		int colonne, i;
		Cell cellc, celll;
		
		if (racine.d == racine && racine.g == racine) {
			compteur++; System.out.println("solution " + compteur);
			for (i=0; i<resultat.size(); i++) {
				System.out.println(resultat.get(i));
			}
		}
		else {
			colonne = racine.d.c;
			cover(colonne);
			cellc = debutcolonne[colonne].b;
			while (cellc!=debutcolonne[colonne]) {
				resultat.add(cellc.l);
				celll = cellc.d;
				while (celll!=cellc) {
					cover(celll.c);
					celll = celll.d;
				}
				chercher();
				celll = cellc.g;
				while (celll!=cellc) {
					uncover(celll.c);
					celll = celll.g;
				}
				cellc = cellc.b;
				resultat.remove(resultat.size()-1);
			}
			uncover(colonne);
		}
	}
}
