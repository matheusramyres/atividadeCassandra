/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cassandra.Model;

import com.cassandra.Dao.ItemDao;

/**
 *
 * @author Matheus
 */
public class App {
   public static void main(String[]args){
      
      Item item = new Item("pizza",13);
      ItemDao dao = new ItemDao();
      
      //dao.adiconar(item);
      //System.out.println(dao.buscar(1));
      //dao.atualizar(1, item);
      dao.remover(1);
   } 
   
  
}