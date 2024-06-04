/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gmgmultiverso;

import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author geanina.foanta
 */
public class GmgMultiverso 
{
//
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException 
    {
        // TODO code application logic here
     // PrincipalPrueba pp = new PrincipalPrueba();
        //pp.setVisible(true);
        
        /*LoginAdmin l = new LoginAdmin();
        l.setVisible(true);*/
        
        /*
        UIManager.setLookAndFeel(new FlatCarbonIJTheme());
        UIManager.put("TextComponent.arc", 100);
        */
//        PrincipalCliente pc = new PrincipalCliente();
//        //SwingUtilities.updateComponentTreeUI(pc);
//        pc.setVisible(true);
        
        UIManager.setLookAndFeel(new FlatCyanLightIJTheme());
        UIManager.put("TextComponent.arc", 100);
        FramePrincipal frPrincip = new FramePrincipal();
        SwingUtilities.updateComponentTreeUI(frPrincip);
        frPrincip.setVisible(true);
    }
    
}