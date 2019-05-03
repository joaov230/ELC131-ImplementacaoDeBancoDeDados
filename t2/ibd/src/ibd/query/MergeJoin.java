/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibd.query;

/**
 *
 * @author Joaov230
 */
public class MergeJoin implements Operation {

    Operation s1;
    Operation s2;
    
    TableTuple currentTuple1;
    TableTuple currentTuple2;

    TableTuple nextTuple1;
    TableTuple nextTuple2;
    
    public MergeJoin(Operation s1, Operation s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
    
    
    @Override
    public void open() throws Exception {
        s1.open();
        s2.open();
        
        currentTuple1 = (TableTuple)s1.next();
        currentTuple2 = (TableTuple)s2.next();
        
        while (s1.hasNext() || s2.hasNext()) {
                
            // CurrentTuple já está na posição certa 
            if (currentTuple1.primaryKey == currentTuple2.primaryKey) {
                // Calcula o próximo
                nextTuple1 = (TableTuple)s1.next();
                nextTuple2 = currentTuple2;
                    
                while (s1.hasNext() || s2.hasNext()) {
                    if (nextTuple1.primaryKey == nextTuple2.primaryKey) {
                        // nextTuple1 e 2 estão certas e existe próxima
                        break;
                    } else if (nextTuple1.primaryKey > nextTuple2.primaryKey) {
                        if (s2.hasNext()) {
                            nextTuple2 = (TableTuple)s2.next();
                        }
                    } else if (nextTuple1.primaryKey < nextTuple2.primaryKey) {
                        if (s1.hasNext()) {
                            nextTuple1 = (TableTuple)s1.next();
                        }
                    }
                }
                
                break;
            } else if (currentTuple1.primaryKey > currentTuple2.primaryKey) {
                // Incrementa o currentTuple2
                if (s2.hasNext()) {
                    currentTuple2 = (TableTuple)s2.next();
                }
            } else if (currentTuple1.primaryKey < currentTuple2.primaryKey) {
                // Incrementa o currentTuple1
                if (s1.hasNext()) {
                    currentTuple1 = (TableTuple)s1.next();
                }
            }
        }
    }
    

    @Override
    public Tuple next() throws Exception {
        // Salva currentTuple num rec para retorno
        TableTuple rec = new TableTuple();
        rec.primaryKey = currentTuple1.primaryKey;
        rec.content = currentTuple1.content + " " + currentTuple2.content;
        
        
        // currentTuple = nextTuple
        currentTuple1 = nextTuple1;
        currentTuple2 = nextTuple2;
        
        
        // Calcula a nextTuple1 e 2
        nextTuple1 = (TableTuple)s1.next();
        
        while (s1.hasNext() || s2.hasNext()) {
            if (nextTuple1.primaryKey == nextTuple2.primaryKey) {
                // nextTuple1 e 2 estão certas e existe próxima
                break;
            } else if (nextTuple1.primaryKey > nextTuple2.primaryKey) {
                if (s2.hasNext()) {
                    nextTuple2 = (TableTuple)s2.next();
                }
            } else if (nextTuple1.primaryKey < nextTuple2.primaryKey) {
                if (s1.hasNext()) {
                    nextTuple1 = (TableTuple)s1.next();
                }
            }
        }
        
        
        return rec;
    }
    
    // Fazer duplicatas das operações só pra verificar se tem próximo
    @Override
    public boolean hasNext() throws Exception {
        if (currentTuple1.primaryKey == currentTuple2.primaryKey) {
            return true;
        } else {
            return false;
        }
    }
    

    @Override
    public void close() throws Exception {
        
    }
    
}
