/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Model.Promotion;
import java.util.ArrayList;



public interface IPromotionDAO extends GenericDAO<Promotion, ArrayList<Promotion>> {
    @Override
    ArrayList<Promotion> getAll();

    @Override
    Promotion getById(String id);

    @Override
    void insert(Promotion entity);

    @Override
    void update(Promotion entity);

    @Override
    void delete(String id);
}


  
