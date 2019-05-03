/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibd.query;

import ibd.Table;
import ibd.Block;
import ibd.Record;

/**
 *
 * @author Sergio
 */
public class TableScan implements Operation {

    public long currentBlock;
    public int currentRecord;
    Table table;

    TableTuple nextTuple = null;

    public TableScan(Table table) {
        this.table = table;
    }

    @Override
    public void open() throws Exception {

        table.initLoad();
        currentBlock = 0;
        currentRecord = -1;
        nextTuple = null;
    }

    @Override
    public Tuple next() throws Exception {

        if (nextTuple != null) {
            TableTuple next_ = nextTuple;
            nextTuple = null;
            return next_;
        }

        Record rec = null;
        while (rec == null) {

            currentRecord++;
            if (currentRecord >= Block.RECORDS_AMOUNT) {
                currentRecord = 0;
                {
                    currentBlock++;
                    if (currentBlock == Table.BLOCKS_AMOUNT) {
                        throw new Exception("No records after this point");
                    }
                }
            }

            Block block = table.getBlock(currentBlock);
            rec = block.getRecord(currentRecord);
        }
        if (rec == null) {
            throw new Exception("No records after this point");
        }

        TableTuple tuple = new TableTuple();
        tuple.primaryKey = rec.getPrimaryKey();
        tuple.content = rec.getContent();

        return tuple;
    }

    @Override
    public boolean hasNext() throws Exception {

        if (nextTuple != null) {
            return true;
        }

        Record rec = null;
        while (rec == null) {

            currentRecord++;
            if (currentRecord >= Block.RECORDS_AMOUNT) {
                currentRecord = 0;
                {
                    currentBlock++;
                    if (currentBlock == Table.BLOCKS_AMOUNT) {
                        return false;
                    }
                }
            }

            Block block = table.getBlock(currentBlock);
            rec = block.getRecord(currentRecord);

        }
        if (rec != null) {
            nextTuple = new TableTuple();
            nextTuple.primaryKey = rec.getPrimaryKey();
            nextTuple.content = rec.getContent();
        }
        return (rec != null);
    }

    @Override
    public void close() {

    }

}
