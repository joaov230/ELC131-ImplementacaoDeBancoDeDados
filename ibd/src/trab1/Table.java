/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trab1;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Table {

    public final static Long BLOCKS_AMOUNT = 64L;

    //header
    public final static Long INDEX_LEN = Long.BYTES + 3 * BLOCKS_AMOUNT * Block.RECORDS_AMOUNT * Long.BYTES;
    public final static Long FREE_BLOCKS_LEN = Long.BYTES + BLOCKS_AMOUNT * Long.BYTES;
    public final static Long HEADER_LEN = INDEX_LEN + FREE_BLOCKS_LEN;

    public final static Long BD_LEN = HEADER_LEN + BLOCKS_AMOUNT * Block.BLOCK_LEN;

    protected DataOrganizer organizer = new DataOrganizer();
    protected DatabaseIO databaseIO = null;
    protected BufferManager bufferManager = new BufferManager();
    Index index = new Index();

    public Table(String folder, String name) throws Exception {
        databaseIO = new DatabaseIO(folder, name);

    }

    public void initLoad() throws Exception {

        index.clear();
        databaseIO.loadIndex(index);

        bufferManager.clear();
        organizer.clear();
        databaseIO.loadFreeBlocks(organizer);

    }

    public Record getRecord(Long primaryKey) throws Exception {

        IndexRecord index_rec = index.getEntry(primaryKey);
        if (index_rec == null) {
            return null;
        }

        Block block = bufferManager.getBlock(index_rec.getBlockId(), databaseIO);

        //now the block contains the record
        return (Record) block.getRecord((int) (long) index_rec.getRecordId());
    }

    public boolean isFull() {
        return (organizer.getFreeBlocksCount() == 0);
    }

    public Record addRecord(long primaryKey, String content) throws Exception {

        if (index.getEntry(primaryKey) != null) {
            throw new Exception("ID already exists");
        }

        if (isFull()) {
            throw new Exception("No Space");
        }

        Record rec = new CreatedRecord(primaryKey);
        rec.setContent(content);

        Long free_block_id = selectBlock(primaryKey);
        Block block = bufferManager.getBlock(free_block_id, databaseIO);

        addRecord(block, rec);

        return rec;

    }

    private void addRecord(Block block, Record rec) throws Exception {
    
    block.addRecord(rec, -1L);

        if (block.isFull()) {
            organizer.removeFreeBlock(block.block_id);
        }

        index.addEntry(block.block_id, rec.getRecordId(), rec.getPrimaryKey());
    }
    
    
    ///////////////////////
    // MÉTODOS IMPLEMENTADOS PARA O TRABALHO
    
    // Pega o maior registro dentro de um bloco
    /*private Record getMaiorRegistro(Long blockId) {
        Long maior = (long)-1;
        Record recTemp = null;
        long index = 0;
        
        try {
            Block block = bufferManager.getBlock(blockId, databaseIO);
            if (block.isFull()) {
                return null;
            }
            
            for (long i = 0; i < Block.RECORDS_AMOUNT; i++) {
                if (block.getRecord((int) i).getPrimaryKey() > maior) {
                    maior = block.getRecord((int)i).getPrimaryKey();
                    recTemp = block.getRecord((int)i);
                }
            }
            return recTemp;

        } catch (Exception ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    */
    
    private Long selectBlock(long primaryKey) {
        
        for (long i = 0; i < BLOCKS_AMOUNT; i++) {
            try {
                
                Block block = bufferManager.getBlock(i, databaseIO);
                //if (primaryKey < getMaiorRegistro(i).getPrimaryKey()) {
                if (!block.isFull()) {
                    if (primaryKey < block.getMaiorRegistro()) {
                        return i;
                    } else {
                        //continue;
                    }
                } else {
                    if (primaryKey < block.getMaiorRegistro()) {
                        //  Senão, salva o maior num Registro temporário,
                        //  retira ele do bloco, manda ele ser inserido nos blocos seguintes
                        //  e retorna o id do bloco que será inserido o Registro atual
                        long reg = block.getMaiorRegistro();
                        Record registro = getRecord(reg);
                        removeRecord(registro);
                        // Chama a função que vai passando reg pros blocos seguintes:
                        insereOrdenado(reg, block.block_id);
                        return i;
                    }
                }
                /*if (primaryKey < block.getMaiorRegistro() || block.isEmpty()) {
                    // Se não estiver cheio, insere aqui
                    if (!block.isFull()) {
                        return i;
                    } else {
                        //  Senão, salva o maior num Registro temporário,
                        //  retira ele do bloco, manda ele ser inserido nos blocos seguintes
                        //  e retorna o id do bloco que será inserido o Registro atual
                        long reg = block.getMaiorRegistro();
                        Record registro = getRecord(reg);
                        removeRecord(registro);
                        // Chama a função que vai passando reg pros blocos seguintes:
                        insereOrdenado(reg, block.block_id);
                        return i;
                    }
                } else {
                    continue;
                }*/
                
            } catch (Exception ex) {
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    // Passa o registro a diante nos blocos seguintes
    private void insereOrdenado(Long primaryKey, Long blockId) {
        
        if (blockId > BLOCKS_AMOUNT) {
            return;
        }
        
        try {
            
            Block block = bufferManager.getBlock(blockId, databaseIO);
            
            if (!block.isFull()) {
                if (primaryKey < block.getMaiorRegistro()) {
                    addRecord(block, getRecord(primaryKey));
                } else {
                    insereOrdenado(primaryKey, blockId+1);
                    return;
                }
            } else {
                if (primaryKey < block.getMaiorRegistro()) {
                    long reg = block.getMaiorRegistro();
                    Record registro = getRecord(reg);
                    removeRecord(registro);
                    addRecord(block, getRecord(primaryKey));
                    insereOrdenado(reg, blockId+1);
                    return;
                } else {
                    insereOrdenado(primaryKey, blockId+1);
                    return;
                }
            }
            
            /*if (primaryKey < block.getMaiorRegistro() || block.isEmpty()) {
                if (!block.isFull()) {
                    addRecord(block, getRecord(primaryKey));
                } else {
                    long reg = block.getMaiorRegistro();
                    Record registro = getRecord(reg);
                    removeRecord(registro);
                    addRecord(block, getRecord(primaryKey));
                    insereOrdenado(reg, blockId+1);
                    return;
                }
            } else {
                insereOrdenado(primaryKey, blockId+1);
                return;
            }*/
            
        } catch (Exception ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return;
    }
    
    
    
    ///////////////////////
    
    
    public void removeRecord(Record record) throws Exception {

        Block block = record.getBlock();
        boolean wasFull = block.isFull();

        block.removeRecord(record);

        if (wasFull) {
            organizer.addFreeBlock(block.block_id);
        }

        index.removeEntry(record.getPrimaryKey());

    }

    public void flushDB() throws Exception {

        databaseIO.flushIndex(index.index);

        databaseIO.flushFreeBlocks(organizer.getFreeBlocksIds());

        databaseIO.flushBlocks(bufferManager.getBufferedBlocks());

    }

    public void createDatabase() throws Exception {
        databaseIO.createDatabase();
    }

}
