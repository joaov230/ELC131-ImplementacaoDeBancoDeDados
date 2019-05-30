/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibd.transaction;

import ibd.Record;

/**
 *
 * @author pccli
 */
public class Manager {

    LockTable lockTable = new LockTable();

    static int cont = 0;
    
    public Record processNextInstruction(Transaction t) throws Exception {
        
        
        if (!t.waitingLockRelease()) {
            Transaction toAbort = lockTable.queueTransaction(t.getCurrentInstruction());
            if (toAbort!=null)
            {
                
                abort(toAbort);
                return null;
            }
        }

        if (t.canLockCurrentInstruction()) {
            return t.processCurrentInstruction();
        }
        
        return null;

    }


    public void commit(Transaction t){
        t.commit();
        lockTable.removeTransaction(t);
    }
    
    public void abort(Transaction t){
        //System.out.println("Aborting "+t);
        System.out.println(SimulatedIterations.getTab(t.getId()-1)+t.getId()+" Abort");
        t.abort();
        lockTable.removeTransaction(t);
        
        
    }
    
    
    
    

    
}
