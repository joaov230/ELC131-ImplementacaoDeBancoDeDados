/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibd.query;


import static ibd.Block.RECORDS_AMOUNT;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Joaov230
 */
public class OrderedScan implements Operation {
    public int currentRecord;
    TableScan tableScan;
    
    TableTuple nextTuple = null;
    ArrayList<TableTuple> ordScan;
    
    public OrderedScan(Operation ts) {
        this.tableScan = (TableScan)ts;
        ordScan = new ArrayList<TableTuple>();
    }
    
    
    @Override
    public void open() throws Exception {
        tableScan.open();
        while (tableScan.hasNext()){
            TableTuple tuple = (TableTuple)tableScan.next();
            ordScan.add(tuple);
        }
        
        Collections.sort(ordScan, (TableTuple tuple1, TableTuple tuple2) ->
                tuple1.primaryKey > tuple2.primaryKey ? 1 : tuple1.primaryKey < tuple2.primaryKey ? -1 : 0);
        
        currentRecord = 0;
        nextTuple = ordScan.get(currentRecord);
    }
    
    
    
    @Override
    public Tuple next() throws Exception {
        if (nextTuple != null) {
            TableTuple r = nextTuple;
            currentRecord++;
            if (currentRecord < ordScan.size()) {
                nextTuple = ordScan.get(currentRecord);
            } else {
                nextTuple = null;
            }
            return r;
        }
        return null;
    }
    
    

    @Override
    public boolean hasNext() throws Exception {
        if (nextTuple != null) {
            return true;
        } else {
            return false;
        }
    }
    
    

    @Override
    public void close() throws Exception {
        
    }
    
}
