/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibd;

import static ibd.Utils.createTable;
import ibd.query.BlockNestedLoopJoin;
import ibd.query.BlockScan;
import java.util.logging.Level;
import java.util.logging.Logger;
import ibd.query.NestedLoopJoin;
import ibd.query.Operation;
import ibd.query.TableTuple;
import ibd.query.TableScan;

/**
 *
 * @author Sergio
 */
public class Main {

    
    public void testCreation() throws Exception {
        Table table;
        table = new Table("c:\\teste\\ibd", "table.ibd");
        table.createDatabase();
    }

    public void testLoad() throws Exception {
        Table table;
        table = new Table("c:\\teste\\ibd", "table.ibd");
        table.initLoad();
    }

    public void testInsertion() throws Exception {
        Table table;
        table = new Table("c:\\teste\\ibd", "table.ibd");
        table.initLoad();
        Record rec = table.addRecord(3, "lddldf fdçonfd faofhdofh odhfod ofsdf");
        table.flushDB();

    }

    public void testSearch() throws Exception {
        Table table;
        table = new Table("c:\\teste\\ibd", "table.ibd");
        table.initLoad();
        Record rec = table.getRecord(3L);
        if (rec == null) {
            System.out.println("não tem");
        } else {
            System.out.println(rec.getContent());
        }
    }

    public void testUpdate() throws Exception {
        Table table;
        table = new Table("c:\\teste\\ibd", "table.ibd");
        table.initLoad();
        Record rec = table.getRecord(3L);
        if (rec == null) {
            System.out.println("não tem");
        } else {
            rec.setContent("mudança de planos");
        }
        table.flushDB();
    }

    public void testMultipleInsertions() throws Exception {
        Table table;
        table = new Table("c:\\teste\\ibd", "table.ibd");
        table.initLoad();

        long[] array = new long[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        Utils.shuffleArray(array);

        for (int i = 4; i < array.length; i++) {
            table.addRecord(array[i], "Novo registros " + array[i]);
            System.out.println(i);
        }
        table.flushDB();
    }

    public void testMultipleSearches() throws Exception {
        Table table;
        table = new Table("c:\\teste\\ibd", "table.ibd");
        table.initLoad();

        long[] array = new long[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        for (int i = 0; i < array.length; i++) {
            Record rec = table.getRecord(array[i]);

            if (rec != null) {
                System.out.println(rec.getContent());
            }
        }
    }
    
    public void testSimpleQuery() throws Exception {
        Table table = createTable("c:\\teste\\ibd","t1.ibd",1000, true, 1);
        Operation scan = new TableScan(table);
        
        scan.open();
        while (scan.hasNext()){
            TableTuple r = (TableTuple)scan.next();
            System.out.println(r.primaryKey + " - "+r.content);
        }

    }
    
    
    public void testMultipleJoinsQuery() throws Exception {
        
        Table table1 = createTable("c:\\teste\\ibd","t1.ibd",1000, false, 1);
        Table table2 = createTable("c:\\teste\\ibd","t2.ibd",1000, false, 1);
        Table table3 = createTable("c:\\teste\\ibd","t3.ibd",1000, false, 50);
        
        Operation scan1 = new TableScan(table1);
        Operation scan2 = new TableScan(table2);
        Operation scan3 = new TableScan(table3);
        
        Operation join1 = new NestedLoopJoin(scan1, scan2);
        Operation join2 = new NestedLoopJoin(join1, scan3);
        
        join2.open();
        while (join2.hasNext()){
            TableTuple r = (TableTuple)join2.next();
            System.out.println(r.primaryKey + " - "+r.content);
        }

    }
    
    public void testNestedLoopJoinQuery() throws Exception {
        
        Table table1 = createTable("c:\\teste\\ibd","t1.ibd",1000, false, 1);
        Table table2 = createTable("c:\\teste\\ibd","t2.ibd",1000, false, 1);
        
        Operation scan1 = new TableScan(table1);
        Operation scan2 = new TableScan(table2);
        
        Operation join1 = new NestedLoopJoin(scan1, scan2);
        
        join1.open();
        while (join1.hasNext()){
            TableTuple r = (TableTuple)join1.next();
            System.out.println(r.primaryKey + " - "+r.content);
        }

    }
    
    public void testBlockNestedLoopJoinQuery() throws Exception {
        
        Table table1 = createTable("c:\\teste\\ibd","t1.ibd",1000, true, 1);
        Table table2 = createTable("c:\\teste\\ibd","t2.ibd",1000, true, 1);
        
        Operation scan1 = new BlockScan(table1);
        Operation scan2 = new BlockScan(table2);
        
        Operation join1 = new BlockNestedLoopJoin(scan1, scan2);
        
        join1.open();
        while (join1.hasNext()){
            TableTuple r = (TableTuple)join1.next();
            System.out.println(r.primaryKey + " - "+r.content);
        }

    }
    
    
    public static void main(String[] args) {
        try {
            Main m = new Main();
            
            //m.testSimpleQuery();
            //m.testOrderedQuery();
            //m.testNestedLoopJoinQuery();
            //m.testMultipleJoinsQuery();;
            //m.testBlockNestedLoopJoinQuery();
            
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
