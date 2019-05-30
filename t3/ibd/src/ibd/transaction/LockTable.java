/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ibd.transaction;

import java.util.Hashtable;
import java.util.Iterator;

/**
 *
 * @author pccli
 */
public class LockTable {
    private Hashtable<String, Item> itens = new Hashtable<>();
    
    public Transaction queueTransaction(Instruction i) {
        Item item = getItem(i);
        return item.addToQueue(i.getTransaction(), i);
    }
    
    
    private Item getItem(Instruction i){
    String key = i.getTable().key + "(" + i.getPk() + ")";
        Item item = itens.get(key);
        if (item == null) {
            item = new Item(this, i.getTable(), i.getPk());
            itens.put(key, item);
        }
        return item;
    }
    
    public void removeTransaction(Transaction t){
    Iterator<Item> it = itens.values().iterator();
        while (it.hasNext()){
            Item item = it.next();
            item.removeTransaction(t);
        }
    }
    
    
      
    
      
      public void printLockRequests(){
          System.out.println("*****************************");
          Iterator<Item> it = itens.values().iterator();
        while (it.hasNext()){
            Item item = it.next();
            item.printLockRequests();
        }
        System.out.println("*****************************");
    }
    
}
