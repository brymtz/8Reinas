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
public class Gen {
    public int[] posicion_reinas;
    public int ataques;
    public double probabilidad;
    public Tablero tablero;

    public Gen(int[] posicion_reinas) {
        this.posicion_reinas = posicion_reinas;
        tablero=new Tablero();
    }

    public void imprimirGen(int indice){
        String gen="";
        for (int i = 0; i < posicion_reinas.length; i++) {
            gen=gen+"|"+posicion_reinas[i];
        }
        System.out.println("Gen número " + (indice+1) + " de la población");
        System.out.println("Gen: ["+ gen + "]");
        System.out.println("Número de ataques: " + tablero.cantidad_ataques());
        System.out.println("Probabilidad de cruce = (Ataques del gen actual * 100) / número total de ataques de la población");
        System.out.println("Probabilidad de cruce actual: " + probabilidad + "\n");
    }
    
    public Gen(){
        
    }

    public double getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(double probabilidad) {
        this.probabilidad = probabilidad;
    }
}
