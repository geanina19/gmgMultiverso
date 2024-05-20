/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso;

import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author geanina.foanta
 */
public class EjecutaAdmin {
    
    public static void main(String[] args) throws UnsupportedLookAndFeelException 
    {
        // TODO code application logic here
        
        /* 
        PrincipalPrueba pp = new PrincipalPrueba();
        pp.setVisible(true);
        */
        
        //UIManager.setLookAndFeel(new FlatCarbonIJTheme());
        UIManager.put("TextComponent.arc", 100);
        LoginAdmin la = new LoginAdmin();
        SwingUtilities.updateComponentTreeUI(la);
        
        la.setVisible(true);
        
    }
    
}
