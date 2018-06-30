/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import Model.*;



public class Operation {
    
    public void CreateKey(Key k){
        
        SessionFactory Factory = NewHibernateUtil.getSessionFactory();
        Session currentsession = Factory.openSession();
        Transaction tx = currentsession.beginTransaction();
        currentsession.save(k);
        tx.commit();
        currentsession.close();
        JOptionPane.showMessageDialog(null , "Completed Transaction, created key"); 
       
    }
    
    
    
    
    
}
