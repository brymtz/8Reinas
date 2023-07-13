/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8reinas;

/**
 *
 * @author ALEXDELL
 */
public class Tablero {
    static int FILAS = 8;
    static int COLUMNAS = 8;
    public String[][] TABLERO;

    public Tablero() {
        TABLERO = new String[FILAS][COLUMNAS];
    }

    public void iniciar_tablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                TABLERO[i][j] = "0";
            }
        }
    }

    public void colocar_individuo(Gen individuo) {
        individuo.tablero.iniciar_tablero();
        int posicion;
        for (int i = 0; i < individuo.posicion_reinas.length; i++) {
            posicion = individuo.posicion_reinas[i];
            individuo.tablero.TABLERO[8-posicion][i] = "1";
        }
    }

    public void imprimir_tablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print("  " + TABLERO[i][j]);
            }
            System.out.println(" ");
        }
        System.out.println("");
    }

    public int ataques_horizontales() {
        int total = 0;
        for (int i = 0; i < FILAS; i++) {
            total += buscar_ataques_horizontales(i);
        }
        return total;
    }

    public int buscar_ataques_horizontales(int fila_reina) {
        int contador = 0;
        for (int i = 0; i < FILAS; i++) {
            if (TABLERO[fila_reina][i].equals("1")) {
                contador++;
            }
        }
        return contador <= 1 ? 0 : contador;
    }

    public int ataques_verticales() {
        int total = 0;
        for (int i = 0; i < FILAS; i++) {
            total += buscar_ataques_verticales(i);
        }
        return total;
    }

    public int buscar_ataques_verticales(int columna_reina) {
        int contador = 0;
        for (int i = 0; i < COLUMNAS; i++) {
            if (TABLERO[i][columna_reina].equals("1")) {
                contador++;
            }
        }
        return contador <= 1 ? 0 : contador;
    }
    
    public int ataques_diagonal_derecha() {
        int total = 0, f = 0, n;
        for (int i = 0; i < COLUMNAS; i++) {
            n = buscar_cruce_diagonal_derecha(f,i);
            total += n;
        }
        for (int i = 1; i < FILAS; i++) {
            total += buscar_cruce_diagonal_derecha(i, 0);
        }
        return total;
    }

    public int buscar_cruce_diagonal_derecha(int fila, int columna) {
        int f = fila, contador = 0;
        for (int i = columna; i < COLUMNAS; i++) {
            if (f < FILAS) {
                if (TABLERO[f][i].equals("1")) {
                    contador++;
                }
                f++;
            }
        }
        return contador <= 1 ? 0 : contador;
    }

    public int buscar_cruce_diagonal_izquierda1(int fila,int columna){
        int f=fila,contador=0;
        for (int i = columna; i >= 0; i--) {
            if(f<FILAS){
                if(TABLERO[f*-1][i].equals("1")){
                    contador++;
                }
                f--;
            }
        }
        
        return contador<=1 ? 0:contador;
    }
    
    public int buscar_cruce_diagonal_izquierda2(int fila,int columna){
        int f=fila,contador=0;
        for (int i = columna; i > 0; i--) {
            if(f<FILAS){
                if(TABLERO[f][i].equals("1")){
                    contador++;
                }
                f++;
            }
        }
        return contador<=1 ? 0:contador;
    }
    
    public int ataques_diagonal_izquierda(){
        int total=0;
        for (int i = 0; i < COLUMNAS; i++) {
            total+=buscar_cruce_diagonal_izquierda1(0, i);
        }
        for (int i = 1; i < FILAS; i++) {
            total+=buscar_cruce_diagonal_izquierda2(i,7);
        }
        return total;
    }
    
    public int cantidad_ataques(){
        return ataques_diagonal_izquierda()+ataques_diagonal_derecha()+ataques_horizontales()+ataques_verticales();
    }
}
