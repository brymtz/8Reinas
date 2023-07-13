/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8reinas;

import static java.lang.System.exit;
import java.util.Random;

/**
 *
 * @author ALEXDELL
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Gen[] poblacion = new Gen[4];
        Gen[] poblacion_temporal = new Gen[4];
        Gen gen = new Gen(iniciar());
        Gen gen1 = new Gen(iniciar());
        Gen gen2 = new Gen(iniciar());
        Gen gen3 = new Gen(iniciar());
        poblacion[0] = gen;
        poblacion[1] = gen1;
        poblacion[2] = gen2;
        poblacion[3] = gen3;
        int ataques_totales = 0;
        int generacion = 0;
        System.out.println("Javier Jiménez - Bryan Martínez\n");
        System.out.println("***** \tPOBLACIÓN   *****");
        while (generacion <= 100000) {
            for (int i = 0; i < poblacion.length; i++) {
                poblacion[i].tablero.colocar_individuo(poblacion[i]);
                 System.out.println("\n************************************************************************************************\n");
                poblacion[i].imprimirGen(i);
                poblacion[i].tablero.imprimir_tablero();
                ataques_totales += poblacion[i].tablero.cantidad_ataques();
            }
            for (int i = 0; i < poblacion.length; i++) {
                if (poblacion[i].tablero.cantidad_ataques() <= 0) {
                    System.out.println(" **** SOLUCIÓN ****");
                    poblacion[i].imprimirGen(i);
                    poblacion[i].tablero.imprimir_tablero();
                    exit(0);
                }
            }
            System.out.println("\n************************************************************************************************\n");
            calcular_probabilidad(poblacion, ataques_totales);
            int[] vector_probabilidades = new int[100];
            int total_probabilidad = 0;
            for (int i = 0; i < poblacion.length; i++) {
                int individuo_probabilidad = (int) poblacion[i].probabilidad;
                llenar_vector_probabilidad(vector_probabilidades, total_probabilidad, i);
                total_probabilidad += individuo_probabilidad;
            }

            int[] padres = new int[2];
            padres = seleccion_padres(vector_probabilidades);

            poblacion_temporal[0] = poblacion[padres[0]];
            poblacion_temporal[1] = poblacion[padres[1]];
            for (int i = 0; i < 2; i++) {
                poblacion_temporal[i].imprimirGen(i);
            }
            cruce(poblacion_temporal);
            System.out.println("**** GENERACIÓN " + (generacion + 1) + " ***");
            ataques_totales = 0;
            poblacion[padres[0]] = poblacion_temporal[2];
            poblacion[padres[1]] = poblacion_temporal[3];
            generacion++;
        }
    }

    static int[] iniciar() {
        int[] orden_reinas = new int[8];
        Random random = new Random();
        int valor;
        for (int i = 0; i < orden_reinas.length; i++) {
            valor = random.nextInt(8) + 1;
            orden_reinas[i] = valor;
        }
        return orden_reinas;
    }

    static void calcular_probabilidad(Gen[] poblacion, double ataques) {
        double probabilidad;
        for (Gen poblacion1 : poblacion) {
            probabilidad = (poblacion1.tablero.cantidad_ataques() * 100) / ataques;
            poblacion1.setProbabilidad(probabilidad);
        }

        for (int i = 0; i < poblacion.length; i++) {
            poblacion[i].imprimirGen(i);
        }
    }

    static void llenar_vector_probabilidad(int[] vector_probabilidades, int partida, int indice_individuo) {
        for (int i = partida; i < vector_probabilidades.length; i++) {
            vector_probabilidades[i] = indice_individuo;
        }
    }

    static int[] seleccion_padres(int[] vector_probabilidades) {
        System.out.println("\n************************************************************************************************\n");
        System.out.println("**** SELECCIÓN DE PADRES ****\n");
        Random random = new Random();
        int primer_padre;
        int segundo_padre;
        int[] padres = new int[2];
        do {
            primer_padre = vector_probabilidades[random.nextInt(99)];
            segundo_padre = vector_probabilidades[random.nextInt(99)];
        } while (primer_padre == segundo_padre);
        padres[0] = primer_padre;
        padres[1] = segundo_padre;
        return padres;
    }

    static Gen[] cruce(Gen[] poblacion_temporal) {
        System.out.println("\n************************************************************************************************\n");
        System.out.println("**** CRUCE ****");
        int primer_cromosoma = 3;
        int segundo_cromosoma = 8;
        //cromosomas
        int[] primer_cromosoma_individuo1 = new int[3];
        int[] segundo_cromosoma_individuo1 = new int[5];
        int[] primer_cromosoma_individuo2 = new int[3];
        int[] segundo_cromosoma_individuo2 = new int[5];
        //nuevos individuos
        int[] genNuevo1 = new int[8];
        int[] genNuevo2 = new int[8];

        for (int j = 0; j < primer_cromosoma; j++) {
            primer_cromosoma_individuo1[j] = poblacion_temporal[0].posicion_reinas[j];
            primer_cromosoma_individuo2[j] = poblacion_temporal[1].posicion_reinas[j];
        }

        int k = 0;
        for (int i = primer_cromosoma; i < segundo_cromosoma; i++) {
            segundo_cromosoma_individuo1[k] = poblacion_temporal[0].posicion_reinas[i];
            segundo_cromosoma_individuo2[k] = poblacion_temporal[1].posicion_reinas[i];
            k++;
        }

        //formación primer gen
        System.arraycopy(primer_cromosoma_individuo1, 0, genNuevo1, 0, primer_cromosoma_individuo1.length);
        System.arraycopy(segundo_cromosoma_individuo2, 0, genNuevo1, primer_cromosoma_individuo1.length, segundo_cromosoma_individuo2.length);
        

        //formación segundo gen
        System.arraycopy(primer_cromosoma_individuo2, 0, genNuevo2, 0, primer_cromosoma_individuo2.length);
        System.arraycopy(segundo_cromosoma_individuo1, 0, genNuevo2, primer_cromosoma_individuo2.length, segundo_cromosoma_individuo1.length);
        
        
        Gen nuevo1 = new Gen(genNuevo1);
        Gen nuevo2 = new Gen(genNuevo2);
        System.out.println("*****************");
        nuevo1.tablero.colocar_individuo(nuevo1);
        nuevo2.tablero.colocar_individuo(nuevo2);
        nuevo1.imprimirGen(0);
        nuevo2.imprimirGen(1);
        System.out.println("\n************************************************************************************************\n");
        System.out.println("**** MUTACIÓN ****\n");
        mutacion(nuevo1);
        mutacion(nuevo2);
        System.out.println("\n************************************************************************************************\n");
        nuevo1.tablero.colocar_individuo(nuevo1);
        nuevo2.tablero.colocar_individuo(nuevo2);
        nuevo1.imprimirGen(0);
        nuevo2.imprimirGen(1);

        poblacion_temporal[2] = nuevo1;
        poblacion_temporal[3] = nuevo2;

        return poblacion_temporal;

    }

    static void mutacion(Gen individuo_mutado) {
        Random random = new Random();
        //int probabilidad_mutacion = random.nextInt(100) + 1;
        int probabilidad_mutacion = 50;
        if (probabilidad_mutacion <= 50) {
            int cromosoma_mutado = random.nextInt(7) + 1;
            int posicion_reina = random.nextInt(7) + 1;
            individuo_mutado.posicion_reinas[cromosoma_mutado] = posicion_reina;
        } else {
            System.out.println("Individuo no mutado");
        }
    }
    
}
