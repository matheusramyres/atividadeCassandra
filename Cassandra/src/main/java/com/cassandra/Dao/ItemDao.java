/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cassandra.Dao;

import com.cassandra.Model.Item;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

/**
 *
 * @author Matheus
 */
public class ItemDao {
    
    public ItemDao(){
    }
    
    public boolean adiconar(Item item){
        
       Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

       Session session = cluster.connect("pedidos");
       
       PreparedStatement stmt = session.prepare("INSERT INTO pedidos"
               + "(id, quantidade, produto) VALUES (?,?,?)");
      
       BoundStatement bound = new BoundStatement(stmt);
       
       session.execute(bound.bind(proximoCodigo(), item.getQuantidade(), item.getProduto()));
       
       session.close();
       cluster.close();
        
        return true;
    }
    
    public String buscar(int id){
        
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        Session session = cluster.connect("pedidos");

        Statement select = QueryBuilder.select().all().from("pedidos").where(eq("id",id));

        ResultSet rs = session.execute(select);

        Row linha = rs.one();
        
        session.close();
        cluster.close();
        
        return linha.toString();
    }
    
    public boolean atualizar(int id, Item item){
        
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

       Session session = cluster.connect("pedidos");
       
       PreparedStatement stmt = session.prepare("UPDATE pedidos SET produto =?, quantidade=? WHERE id=? IF EXISTS");
      
       BoundStatement bound = new BoundStatement(stmt);
       
       session.execute(bound.bind(item.getProduto(),item.getQuantidade(), id));
       
       session.close();
       cluster.close();
        return true;
    }
    
    public boolean remover(int id){
        
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

       Session session = cluster.connect("pedidos");
       
       PreparedStatement stmt = session.prepare("DELETE FROM pedidos WHERE id=?");
      
       BoundStatement bound = new BoundStatement(stmt);
       
       session.execute(bound.bind(id));
       
       session.close();
       cluster.close();
        return true;
    }
    
    private int proximoCodigo(){
        
        
       Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
       
       Session session = cluster.connect("pedidos");

       Statement select = QueryBuilder.select().max("id").from("pedidos");
       
       ResultSet rs = session.execute(select);
       
       Row linha = rs.one();
        if(linha.getInt(0)==0){
            
            session.close();
            cluster.close();
            
            return 1;
        }
        else{
            int proximo = linha.getInt(0) +1;
            
            session.close();
            cluster.close();
            
            return proximo;
        }
        
    }
}
