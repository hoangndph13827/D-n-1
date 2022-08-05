/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HELP;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.*;

/**
 *
 * @author Admin
 */
public  class ImgIO{
    public static ImageIcon readImg(String path,Dimension size){
        ImageIcon imageIcon = null;
        String url=path.indexOf("\\")>-1?path:"src/Img/"+path;
        try {
            BufferedImage img= ImageIO.read(new File(url));
            imageIcon= new ImageIcon(img.getScaledInstance(size.getSize().width, size.getSize().height, img.SCALE_SMOOTH));
            if(imageIcon==null){
                url= "MacDinh.jpg";
                imageIcon= new ImageIcon(img.getScaledInstance(size.getSize().width, size.getSize().height, img.SCALE_SMOOTH));
            }
            
        } catch (Exception e) {
            System.out.println(path);
            e.printStackTrace();
            
        }
        return imageIcon;
    }
    public static ImageIcon readImg(File file,JLabel lable){
        ImageIcon imageIcon = null;
        String url=  file.getAbsolutePath();
        try {
            BufferedImage img= ImageIO.read(new File(url));
            imageIcon= new ImageIcon(img.getScaledInstance(lable.getSize().width, lable.getSize().height, img.SCALE_SMOOTH));
        } catch (Exception e) {
        }
        return imageIcon;
    }
    public static ImageIcon readIcon(String path,int sizeW,int sizeH){
        ImageIcon imageIcon = null;
        String url="src/Img/"+path;
        try {
            BufferedImage img= ImageIO.read(new File(url));
            imageIcon= new ImageIcon(img.getScaledInstance(sizeW, sizeH, img.SCALE_SMOOTH));
        } catch (Exception e) {
        }
        return imageIcon;
    }
}
