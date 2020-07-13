package main;

import java.util.ArrayList;
import java.util.List;

public class Solution99 {
	private static final int N = 3;
	private static final int M = 3;
	private static final int NM = N*M;
	private static int NBSE = (N*M)*(N*M)*(N*M);
	private static int NB = (N*M)*(N*M)*4;
	private static int[][] matrice = new int[NBSE][NB];
	private static final int nbelemse = 4;
	private static int[][] se = new int[NBSE+1][nbelemse+1];
	private static List<Integer> resultat = new ArrayList<Integer>();
	private static int compteur = 0;
	private static Cell racine;
	private static Cell[] debutligne = new Cell[NBSE+1];
	private static Cell[] debutcolonne = new Cell[NB+1];
	private static int[][] solution = new int[NM][NM];
	public static List<int[][]> allSolutions = new ArrayList<int[][]>();

	public static void main(String[] args) {
		entreeDesParties();
		matrice();
		reduire(Sudoku99.grille);
		chercher();
	}
	
	/**
	 * Get the number of solutions
	 * 
	 * @return compteur
	 */
	public static int getNumberOfSolutions() {
		return compteur;
	}
	
	/**
	 * Get all solutions in a ArrayList
	 * 
	 * @return allSolutions
	 */
	public static List<int[][]> getAllSolutions() {
		return allSolutions;
	}
	
	/**
	 * Resolve the sudoku 
	 */
	public static void resolve(int[][] grille) {
		entreeDesParties();
		matrice();
		reduire(grille);
		chercher();
	}
	
	/*
	 * Matrice
	 */
	static void entreeDesParties() {
		int i, j, k, ligne, col1, col2, col3, col4;
		for (i=0; i<N*M; i++) { //ligne
			for (j=0; j<N*M; j++) { //colonne
				for (k=0; k<N*M; k++) { //valeur
					ligne = i*(N*M)*(N*M) + j*(N*M) + k + 1;
					col1 = 0*(N*M)*(N*M) + i*(N*M) + j + 1;
					col2 = 1*(N*M)*(N*M) + i*(N*M) + k + 1;
					col3 = 2*(N*M)*(N*M) + j*(N*M) + k + 1;
					col4 = 3*(N*M)*(N*M) + ( (int) i/N + ((int) j/M)*M ) * (N*M) + k + 1;
					se[ligne][1] = col1;
					se[ligne][2] = col2;
					se[ligne][3] = col3;
					se[ligne][4] = col4;
				}
			}
		}
	}
	
	/*
	 * Entrée de la matrice avec des liens dansants
	 */
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
			for (j=1; j<=nbelemse; j++) {
				newcell = new Cell();
				colonne = se[i][j];
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
	 * Afficher matrice
	 */
	static void afficherMatrice() {
		for (int i=0; i<NBSE; i++) {
			System.out.format("%-3d", i);
			for (int j=0; j<NB; j++) {
				System.out.format("%2d", matrice[i][j]);
			}
			System.out.println();
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
	
	/**
	 * Cover une colonne
	 * 
	 * @param col
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
	
	/**
	 * Uncover une colonne
	 * 
	 * @param col
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
	
	/**
	 * Réduire
	 * @param grille de taille NM x NM (ici 4 x 4)
	 * 
	 */
	static void reduire(int[][] grille) {
		int i, j, ligne, col;
		Cell celll, cellc;
		for (i=0; i<NM; i++) {
			for (j=0; j<NM; j++) {
				// ajouter dans la matrice de solution
				solution[i][j]=grille[i][j];
				
				// si la case est remplie
				if (grille[i][j] != 0) {
					ligne = i*(NM)*(NM) + j*(NM) + grille[i][j];
					col = i*(NM) + j + 1;
					cellc = debutcolonne[col].b;
					while (cellc != debutcolonne[col]) {
						if (cellc.l == ligne) break;
						cellc = cellc.b;
					}
					cover(cellc.c);
					celll = cellc.d;
					while (celll!=cellc) {
						cover(celll.c);
						celll = celll.d;
					}
				}
			}
		}
	}
	
	/**
	 * Chercher des solutions
	 */
	static void chercher() {
		int colonne, i;
		Cell cellc, celll;
		
		if (racine.d == racine && racine.g == racine) {
			compteur++; System.out.println("solution " + compteur);
			for (i=0; i<resultat.size(); i++) {
				ajouterALaSolution(resultat.get(i));
				//System.out.println(resultat.get(i));
			}
			allSolutions.add(solution);
			afficherSolution();
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
	
	static void ajouterALaSolution(int ligne) {
		int l = ligne;
		int val = ( l % NM == 0 ) ? 9 : (l % NM);
		l = (l - val) / 9;
		int colSolution = l % NM;
		int ligneSolution = l / NM;
		solution[ligneSolution][colSolution] = val;
		
	}

	/**
	 * Afficher la solution
	 */
	static void afficherSolution() {
		for (int i=0; i<NM; i++) {
			for (int j=0; j<NM; j++) {
				System.out.format("%-2d", solution[i][j]);
			}
			System.out.println();
		}
	}

}
