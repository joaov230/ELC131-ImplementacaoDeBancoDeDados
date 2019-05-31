/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ibd.transaction;

import ibd.Table;
import ibd.Utils;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pccli
 */
public class Main {
    
    
    
    
    public void test1() throws Exception{
        Table table1 = Utils.createTable("c:\\teste\\ibd","t1.ibd",1000, true, 1);
        
        Transaction t1 = new Transaction();
        t1.addInstruction(new Instruction(table1, Instruction.READ, SimulatedIterations.getValue('A'), null));
        t1.addInstruction(new Instruction(table1, Instruction.WRITE, SimulatedIterations.getValue('B'), "xx"));

        Transaction t2 = new Transaction();
        t2.addInstruction(new Instruction(table1, Instruction.READ, SimulatedIterations.getValue('B'), null));
        t2.addInstruction(new Instruction(table1, Instruction.WRITE, SimulatedIterations.getValue('A'), "yy"));

        SimulatedIterations simulation = new SimulatedIterations();
        simulation.addTransaction(t1);
        simulation.addTransaction(t2);
        simulation.run();
        
        
        /*
        //////////////////////
        // Teste do Grafo
        GrafoDoJoao grafo = new GrafoDoJoao();
        
        grafo.addVertice(1);
        grafo.addVertice(2);
        grafo.addVertice(3);
        grafo.addVertice(4);
        grafo.addVertice(5);
        // Esse vertice já existe e não deve ser adicionado
        grafo.addVertice(3);
        
        grafo.linkVertices(2, 1);
        grafo.linkVertices(1, 3);
        grafo.linkVertices(3, 4);
        grafo.linkVertices(3, 5);
        grafo.linkVertices(5, 4);
        // Gera um ciclo
        grafo.linkVertices(4, 2);
        // É repetido
        grafo.linkVertices(2, 1);
        
        if (grafo.temCiclo()) {
            System.out.println("Tem ciclo");
        } else {
            System.out.println("Não tem ciclo");
        }
        
        grafo.printGrafo();
        System.out.println();
        
        grafo.unlinkVertice(4);
        
        if (grafo.temCiclo()) {
            System.out.println("Tem ciclo");
        } else {
            System.out.println("Não tem ciclo");
        }

        System.out.println();
        grafo.printGrafo();
        */
    }
    
    
    
    
    public static void main(String[] args) {
     Main m = new Main();
        try {
            m.test1();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
