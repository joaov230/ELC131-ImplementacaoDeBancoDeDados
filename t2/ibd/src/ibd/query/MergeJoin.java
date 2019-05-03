/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibd.query;

/**
 *
 * @author Glenio
 */
public class MergeJoin implements Operation {

    Operation s1;
    Operation s2;
    
    TableTuple currentTuple1;
    TableTuple currentTuple2;
    TableTuple nextTuple;
    
    public MergeJoin(Operation s1, Operation s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
    
    
    @Override
    public void open() throws Exception {
        s1.open();
        s2.open();
        
        currentTuple1 = null;
        currentTuple2 = null;
        nextTuple = null;
    }
    

    @Override
    public Tuple next() throws Exception {
        if (currentTuple1 == null) { // Primeira execução
            TableTuple curTuple1 = (TableTuple)s1.next();
            TableTuple curTuple2 = (TableTuple)s2.next();
            

            TableTuple rec = new TableTuple();
            rec.primaryKey = curTuple1.primaryKey;
            rec.content = curTuple1.content + " " + curTuple2.content;
            
            currentTuple1 = curTuple1;
            currentTuple2 = curTuple2;
            return rec;
        } else {
            // Verifica hasNext() no s1
            // Pega o s1.next() e salva em currentTuple1
            // Verifica se fecha com a condição currentTuple1.primaryKey == currentTuple2.primaryKey
            // Se sim, dá merge e retorna o record gerado do merge
            // Se não, do { recTuple = s2.next() } while (currentTuple1 != recTuple.primaryKey)
            // Se chegou no final, retorna null
            // Senão, Atualiza o currentTuple2 = recTuple
                // Dá merge do currentTuple2 com currentTuple1
                // Retorna o record gerado do merge
        }
        return null;
    }
    
    // Fazer duplicatas das operações só pra verificar se tem próximo
    @Override
    public boolean hasNext() throws Exception {
        
        return false;
    }
    

    @Override
    public void close() throws Exception {
        
    }
    
}
