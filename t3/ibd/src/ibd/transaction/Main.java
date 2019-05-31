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
        
        /*
        Transaction t1 = new Transaction();
        t1.addInstruction(new Instruction(table1, Instruction.READ, SimulatedIterations.getValue('A'), null));
        t1.addInstruction(new Instruction(table1, Instruction.WRITE, SimulatedIterations.getValue('B'), "bla"));
        
        
        Transaction t2 = new Transaction();
        t2.addInstruction(new Instruction(table1, Instruction.READ, SimulatedIterations.getValue('D'), null));
        t2.addInstruction(new Instruction(table1, Instruction.READ, SimulatedIterations.getValue('B'), null));
        t2.addInstruction(new Instruction(table1, Instruction.WRITE, SimulatedIterations.getValue('C'), "bla"));
        t2.addInstruction(new Instruction(table1, Instruction.READ, SimulatedIterations.getValue('H'), null));
        
        Transaction t3 = new Transaction();
        t3.addInstruction(new Instruction(table1, Instruction.WRITE, SimulatedIterations.getValue('D'), "bla"));
        t3.addInstruction(new Instruction(table1, Instruction.READ, SimulatedIterations.getValue('E'), null));
        t3.addInstruction(new Instruction(table1, Instruction.READ, SimulatedIterations.getValue('B'), null));
        
        Transaction t4 = new Transaction();
        t4.addInstruction(new Instruction(table1, Instruction.READ, SimulatedIterations.getValue('F'), null));
        t4.addInstruction(new Instruction(table1, Instruction.READ, SimulatedIterations.getValue('G'), null));
        t4.addInstruction(new Instruction(table1, Instruction.READ, SimulatedIterations.getValue('A'), null));
        
        Transaction t5 = new Transaction();
        t5.addInstruction(new Instruction(table1, Instruction.WRITE, SimulatedIterations.getValue('B'), "bla"));
        t5.addInstruction(new Instruction(table1, Instruction.WRITE, SimulatedIterations.getValue('F'), "bla"));
        t5.addInstruction(new Instruction(table1, Instruction.READ, SimulatedIterations.getValue('G'), null));
        
        
        SimulatedIterations simulation = new SimulatedIterations();
        simulation.addTransaction(t1);
        simulation.addTransaction(t2);
        simulation.addTransaction(t3);
        simulation.addTransaction(t4);
        simulation.addTransaction(t5);
        simulation.run();
        */
        
        /*
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
        */
        
        
        GrafoDoJoao grafo = new GrafoDoJoao();
        
        grafo.addVertice(1);
        grafo.addVertice(2);
        grafo.addVertice(3);
        grafo.addVertice(4);
        // Esse vertice já existe e não deve ser adicionado
        grafo.addVertice(3);
        
        grafo.linkVertices(2, 1);
        grafo.linkVertices(1, 3);
        grafo.linkVertices(3, 4);
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
