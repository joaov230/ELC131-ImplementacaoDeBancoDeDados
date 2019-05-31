/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibd.transaction;

import ibd.Table;
import static ibd.transaction.Instruction.WRITE;
import java.util.ArrayList;

/**
 *
 * @author pccli
 */
public class Item {

    public Table table;
    public long primaryKey;

    public LockTable lockTable;

    ArrayList<Lock> lockRequests = new ArrayList<>();
    
    GrafoDoJoao grafo = new GrafoDoJoao();

    public Item(LockTable lockTable, Table table, long pk) {
        this.table = table;
        primaryKey = pk;
        this.lockTable = lockTable;
    }

    public Transaction addToQueue(Transaction t, Instruction instruction) {

        if (!alreadyInQueue(t, instruction.getMode())) {
            Lock l = new Lock(t, instruction.getMode());
            lockRequests.add(l);
            instruction.setItem(this);
            
            //// O QUE FOI MODIFICADO ////

            grafo.addVertice(t.getId());
            
            Transaction t1 = t;
            Transaction t2 = null;
           
            for (int i = lockRequests.size()-1; i >= 0; i--) {
                if ((lockRequests.get(i).mode == WRITE) && (lockRequests.get(i).transaction.getCurrentInstruction().getItem() == instruction.getItem())) {
                    t2 = lockRequests.get(i).transaction;
                    break;
                }
            }
            
            if (t2 != null) {
                grafo.linkVertices(t2.getId(), t1.getId());
            }
            
            if (grafo.temCiclo()) {
                // Se t1 for mais nova que t2
                if (t1.getBenjaminButtonIdade() > t2.getBenjaminButtonIdade()) {
                    return t1;
                } else {
                    return t2;
                }
            }
            
            //////////////////////////////
        }
        
        
        return null;

    }

    private boolean alreadyInQueue(Transaction t, int mode){
        for (int i = 0; i < lockRequests.size(); i++) {
            Lock l = lockRequests.get(i);
            if (l.transaction.equals(t)) {
                if (mode == l.mode || l.mode==WRITE) {
                    return true;
                }
            }
        }
        return false;
    }
    
    

    public void removeTransaction(Transaction t) {

        for (int i = lockRequests.size() - 1; i >= 0; i--) {
            Lock lock = lockRequests.get(i);
            if (lock.transaction.equals(t)) {
                lockRequests.remove(i);
            }

        }

    }

    public void printLockRequests() {
        System.out.print("Item:" + SimulatedIterations.getChar((int) primaryKey) + "=>");
        for (int i = 0; i < lockRequests.size(); i++) {
            Lock lock = lockRequests.get(i);
            System.out.print(lock.transaction.getId() + ":" + Instruction.getModeType(lock.mode));

        }
        System.out.println("");

    }

    public boolean canBeLockedBy(Transaction t) {
        int currentMode = t.getCurrentInstruction().getMode();
        for (int i = 0; i < lockRequests.size(); i++) {
            Lock l = lockRequests.get(i);
            if (l.transaction.equals(t)) {
                return true;
            }
            if (l.mode == Instruction.WRITE) {
                return false;
            }
            if (currentMode == Instruction.WRITE) {
                return false;
            }
        }
        System.out.println("não deveria chegar aqui");
        return false;
    }

    

}
