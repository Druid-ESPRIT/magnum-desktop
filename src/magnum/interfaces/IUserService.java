/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magnum.interfaces;

import magnum.models.User;

/**
 *
 * @author Litai
 */
public interface IUserService {
    
    public User getUser(int id);
    
}
