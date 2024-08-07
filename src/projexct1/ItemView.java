/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projexct1;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Zelone
 */
public class ItemView extends JFrame {

    private JLabel ItemNameLabel;
    private JLabel ItemPriceLabel;
    private JLabel ItemDetailsImageLabel;
    private JLabel ItemLogoImageLabel;
    public ItemView() {
        generateView();

    }

    private void  generateView() {
        ItemNameLabel = new javax.swing.JLabel();
        ItemPriceLabel = new javax.swing.JLabel();
        ItemDetailsImageLabel = new javax.swing.JLabel();
        ItemLogoImageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        ItemNameLabel.setText("jLabel1");
        getContentPane().add(ItemNameLabel);
        ItemNameLabel.setBounds(117, 6, 480, 55);

        ItemPriceLabel.setText("jLabel1");
        getContentPane().add(ItemPriceLabel);
        ItemPriceLabel.setBounds(0, 150, 577, 127);

        ItemDetailsImageLabel.setText("jLabel1");
        getContentPane().add(ItemDetailsImageLabel);
        ItemDetailsImageLabel.setBounds(0, 0, 620, 260);

        ItemLogoImageLabel.setText("jLabel1");
        getContentPane().add(ItemLogoImageLabel);
        ItemLogoImageLabel.setBounds(7, 0, 90, 60);

        setSize(new java.awt.Dimension(632, 295));
        setLocationRelativeTo(null);
    }

}
