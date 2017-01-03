/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CODIGOS;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 *
 * @author Ismael Ribeiro
 */
public class Tela extends javax.swing.JFrame {
    
    public static int y, x, contador,contador2, na, posicao_vertical_aleatoria_1, posicao_vertical_aleatoria_2, posicao_vertical_aleatoria_3,
    posicao_vertical_aleatoria_4, posicao_vertical_aleatoria_5, posicao_vertical_aleatoria_6, posicao_vertical_aleatoria_7, yatual, xatual,
    velocidade_do_tiro, velocidade_do_movimento, HORIZONTAL, VERTICAL, PORCENTAGEM_DO_EIXO_X, PORCENTAGEM_DO_EIXO_Y;
    
    public ArrayList<Controller> foundControllers;
    
/**************************************************************************************************************************************/
public void JoystickTest(){
    foundControllers = new ArrayList<>();
    searchForControllers();
    if(!foundControllers.isEmpty()){
        startShowingControllerData();
    }
    else{
        System.out.println("Nenhum Controle encontrado.");
    }
}
public void searchForControllers(){
    Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
    for(int i = 0; i < controllers.length; i++){
        Controller controller = controllers[i];
        if (controller.getType() == Controller.Type.STICK || controller.getType() == Controller.Type.GAMEPAD || 
            controller.getType() == Controller.Type.WHEEL || controller.getType() == Controller.Type.FINGERSTICK)
        {
            foundControllers.add(controller);
        }
    }
}
public void startShowingControllerData(){
//    while(true){
        int selectedControllerIndex = 0;
        Controller controller = foundControllers.get(selectedControllerIndex);
        if( !controller.poll() ){
        System.out.println("Controle desconectado.");
//        break;
        }
        PORCENTAGEM_DO_EIXO_X = 0;
        PORCENTAGEM_DO_EIXO_Y = 0;
        Component[] components = controller.getComponents();
        for(int i=0; i < components.length; i++){
                Component component = components[i];
                Component.Identifier componentIdentifier = component.getIdentifier();
                if(componentIdentifier.getName().matches("^[0-9]*$")){
                    boolean isItPressed = true;
                    if(component.getPollData() == 0.0f)
                    isItPressed = false;
                    String buttonIndex;
                    buttonIndex = component.getIdentifier().toString();
                    if(component.getPollData() == 1.0 && buttonIndex.equals("0")){
                        System.out.println("TRIANGULO");
                    }
                    if(component.getPollData() == 1.0 && buttonIndex.equals("1")){
                        System.out.println("BOLA");
                    }
                    if(component.getPollData() == 1.0 && buttonIndex.equals("2")){
                        System.out.println("XIS");
                    }
                    if(component.getPollData() == 1.0 && buttonIndex.equals("3")){
                        System.out.println("QUADRADO");
                    }
                    if(component.getPollData() == 1.0 && buttonIndex.equals("4")){
                        System.out.println("L1");
                    }
                    if(component.getPollData() == 1.0 && buttonIndex.equals("5")){
                        System.out.println("R1");
                    }
                    if(component.getPollData() == 1.0 && buttonIndex.equals("6")){
                        System.out.println("L2");
                    }
                    if(component.getPollData() == 1.0 && buttonIndex.equals("7")){
                        System.out.println("R2");
                    }
                    if(component.getPollData() == 1.0 && buttonIndex.equals("8")){
                        System.out.println("SELECT");
                    }
                    if(component.getPollData() == 1.0 && buttonIndex.equals("9")){
                        System.out.println("START");
                    }
                    //10 E 11 FUNCIONAM AO PRESSIONAR OS BOTOES ANALOGICOS PARA BAIXO
                    continue;
                }
                // Hat switch
                if(componentIdentifier == Component.Identifier.Axis.POV){
                    float hatSwitchPosition = component.getPollData();
//                    window.setHatSwitch(hatSwitchPosition);
                    
                    // We know that this component was hat switch so we can skip to next component.
                    continue;
                }
                // Axes
                if(component.isAnalog()){
                    float axisValue = component.getPollData();
                    int axisValueInPercentage = getAxisValueInPercentage(axisValue);
                    
                    // X axis
                    if(componentIdentifier == Component.Identifier.Axis.X){
                        PORCENTAGEM_DO_EIXO_X = axisValueInPercentage;
                        
                        continue; // Go to next component.
                    }
                    // Y axis
                    if(componentIdentifier == Component.Identifier.Axis.Y){
                        PORCENTAGEM_DO_EIXO_Y = axisValueInPercentage;
                        continue; // Go to next component.
                    }
                    if(PORCENTAGEM_DO_EIXO_X != 49 || PORCENTAGEM_DO_EIXO_Y != 49){
                        
                        try {
                            Robot bot = new Robot();
                            
                            HORIZONTAL = Integer.parseInt(""+MouseInfo.getPointerInfo().getLocation().x);
                            VERTICAL = Integer.parseInt(""+MouseInfo.getPointerInfo().getLocation().y);
                            
                            if(PORCENTAGEM_DO_EIXO_X > 49){
                                HORIZONTAL++;
                                bot.mouseMove(HORIZONTAL , VERTICAL);
                            }
                            if(PORCENTAGEM_DO_EIXO_X < 49){
                                HORIZONTAL--;
                                bot.mouseMove(HORIZONTAL , VERTICAL);
                            }
                            if(PORCENTAGEM_DO_EIXO_Y > 49){
                                VERTICAL++;
                                bot.mouseMove(HORIZONTAL , VERTICAL);
                            }
                            if(PORCENTAGEM_DO_EIXO_Y < 49){
                                VERTICAL--;
                                bot.mouseMove(HORIZONTAL , VERTICAL);
                            }
                        } catch (AWTException ex) {
                            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
        }
//        try {
//            Thread.sleep(10);//25
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }//end while
}
public int getAxisValueInPercentage(float axisValue){
    return (int)(((2 - (1 - axisValue)) * 100) / 2);
}
/**************************************************************************************************************************************/
    
    public int menor_posicao_x_1 = -46;//-46
    public int maior_posicao_x_1 = 1363;
    public int menor_posicao_y_1 = -49;
    public int maior_posicao_y_1 = 744;
        
    public int menor_posicao_x_2 = -46;//-46
    public int maior_posicao_x_2 = 1363;
    public int menor_posicao_y_2 = -49;
    public int maior_posicao_y_2 = 744;
        
    public int menor_posicao_x_3 = -46;//-46
    public int maior_posicao_x_3 = 1363;
    public int menor_posicao_y_3 = -49;
    public int maior_posicao_y_3 = 744;
        
    public int menor_posicao_x_4 = -46;//-46
    public int maior_posicao_x_4 = 1363;
    public int menor_posicao_y_4 = -49;
    public int maior_posicao_y_4 = 744;
        
    public int menor_posicao_x_5 = -46;//-46
    public int maior_posicao_x_5 = 1363;
    public int menor_posicao_y_5 = -49;
    public int maior_posicao_y_5 = 744;
        
    public int menor_posicao_x_6 = -46;//-46
    public int maior_posicao_x_6 = 1363;
    public int menor_posicao_y_6 = -49;
    public int maior_posicao_y_6 = 744;
        
    public int menor_posicao_x_7 = -46;//-46
    public int maior_posicao_x_7 = 1363;
    public int menor_posicao_y_7 = -49;
    public int maior_posicao_y_7 = 744;
    
    public Timer timer;
    public Random random;
    
    /**
     * Creates new form Tela
     */
    public Tela() {
        initComponents();
        
        random = new Random();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        inimigo1 = new javax.swing.JLabel();
        inimigo2 = new javax.swing.JLabel();
        inimigo3 = new javax.swing.JLabel();
        inimigo4 = new javax.swing.JLabel();
        inimigo5 = new javax.swing.JLabel();
        inimigo6 = new javax.swing.JLabel();
        inimigo7 = new javax.swing.JLabel();
        personagem = new javax.swing.JLabel();
        poder = new javax.swing.JLabel();
        PAPEL_DE_PAREDE = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Game");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jDesktopPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inimigo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CODIGOS/Imagens/Fantasma_Gif.gif"))); // NOI18N
        inimigo1.setFocusable(false);
        jDesktopPane1.add(inimigo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        inimigo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CODIGOS/Imagens/Fantasma_Gif.gif"))); // NOI18N
        inimigo2.setFocusable(false);
        jDesktopPane1.add(inimigo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, -1, -1));

        inimigo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CODIGOS/Imagens/Fantasma_Gif.gif"))); // NOI18N
        inimigo3.setFocusable(false);
        jDesktopPane1.add(inimigo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, -1, -1));

        inimigo4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CODIGOS/Imagens/Fantasma_Gif.gif"))); // NOI18N
        inimigo4.setFocusable(false);
        jDesktopPane1.add(inimigo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));

        inimigo5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CODIGOS/Imagens/Fantasma_Gif.gif"))); // NOI18N
        inimigo5.setFocusable(false);
        jDesktopPane1.add(inimigo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, -1, -1));

        inimigo6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CODIGOS/Imagens/Fantasma_Gif.gif"))); // NOI18N
        inimigo6.setFocusable(false);
        jDesktopPane1.add(inimigo6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, -1, -1));

        inimigo7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CODIGOS/Imagens/Fantasma_Gif.gif"))); // NOI18N
        inimigo7.setFocusable(false);
        jDesktopPane1.add(inimigo7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, -1, -1));

        personagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CODIGOS/Imagens/Giant_Jellyfish.gif"))); // NOI18N
        personagem.setFocusable(false);
        jDesktopPane1.add(personagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, -66, -1, -1));

        poder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CODIGOS/Imagens/star.gif"))); // NOI18N
        poder.setFocusable(false);
        jDesktopPane1.add(poder, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 300, -1, -1));

        PAPEL_DE_PAREDE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CODIGOS/Imagens/Castelo_Mal_Assombrado.jpg"))); // NOI18N
        PAPEL_DE_PAREDE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PAPEL_DE_PAREDEMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PAPEL_DE_PAREDEMouseEntered(evt);
            }
        });
        jDesktopPane1.add(PAPEL_DE_PAREDE, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
//        JoystickTest();
        
        velocidade_do_movimento = 10;
        
        inimigo1.setVisible(true);
        inimigo2.setVisible(false);
        inimigo3.setVisible(false);
        inimigo4.setVisible(false);
        inimigo5.setVisible(false);
        inimigo6.setVisible(false);
        inimigo7.setVisible(false);
        personagem.setVisible(true);
        poder.setVisible(false);
        
        this.setExtendedState(MAXIMIZED_BOTH);//DEFINE A JANELA PARA INICIAR MAXIMIZADA
        
        timer = new Timer(5, (ActionEvent e) -> {
            
            JoystickTest();
            
            contador = contador+5;
            
            //String posicao_do_mouse = "X: "+MouseInfo.getPointerInfo().getLocation().x+" Y: "+MouseInfo.getPointerInfo().getLocation().y;
            
            if(inimigo1.getX() < maior_posicao_x_1){
                
            inimigo1.setLocation(menor_posicao_x_1+contador, posicao_vertical_aleatoria_1);
            inimigo2.setLocation(menor_posicao_x_2+contador, posicao_vertical_aleatoria_2);
            inimigo3.setLocation(menor_posicao_x_3+contador, posicao_vertical_aleatoria_3);
            inimigo4.setLocation(menor_posicao_x_4+contador, posicao_vertical_aleatoria_4);
            inimigo5.setLocation(menor_posicao_x_5+contador, posicao_vertical_aleatoria_5);
            inimigo6.setLocation(menor_posicao_x_6+contador, posicao_vertical_aleatoria_6);
            inimigo7.setLocation(menor_posicao_x_7+contador, posicao_vertical_aleatoria_7);
            
            personagem.setLocation((MouseInfo.getPointerInfo().getLocation().x)-70, (MouseInfo.getPointerInfo().getLocation().y)-50);
                
            }else{
                
                contador = 0; 
                
                posicao_vertical_aleatoria_1 = random.nextInt(maior_posicao_y_1+1);
                posicao_vertical_aleatoria_2 = random.nextInt(maior_posicao_y_2+1);
                posicao_vertical_aleatoria_3 = random.nextInt(maior_posicao_y_3+1);
                posicao_vertical_aleatoria_4 = random.nextInt(maior_posicao_y_4+1);
                posicao_vertical_aleatoria_5 = random.nextInt(maior_posicao_y_5+1);
                posicao_vertical_aleatoria_6 = random.nextInt(maior_posicao_y_6+1);
                posicao_vertical_aleatoria_7 = random.nextInt(maior_posicao_y_7+1);
                
                int quantidade = random.nextInt(8);
                switch (quantidade) {
                    case 0:
                        inimigo1.setVisible(false);
                        inimigo2.setVisible(false);
                        inimigo3.setVisible(false);
                        inimigo4.setVisible(false);
                        inimigo5.setVisible(false);
                        inimigo6.setVisible(false);
                        inimigo7.setVisible(false);
                        break;
                    case 1:
                        inimigo1.setVisible(true);
                        inimigo2.setVisible(false);
                        inimigo3.setVisible(false);
                        inimigo4.setVisible(false);
                        inimigo5.setVisible(false);
                        inimigo6.setVisible(false);
                        inimigo7.setVisible(false);
                        break;
                    case 2:
                        inimigo1.setVisible(true);
                        inimigo2.setVisible(true);
                        inimigo3.setVisible(false);
                        inimigo4.setVisible(false);
                        inimigo5.setVisible(false);
                        inimigo6.setVisible(false);
                        inimigo7.setVisible(false);
                        break;
                    case 3:
                        inimigo1.setVisible(true);
                        inimigo2.setVisible(true);
                        inimigo3.setVisible(true);
                        inimigo4.setVisible(false);
                        inimigo5.setVisible(false);
                        inimigo6.setVisible(false);
                        inimigo7.setVisible(false);
                        break;
                    case 4:
                        inimigo1.setVisible(true);
                        inimigo2.setVisible(true);
                        inimigo3.setVisible(true);
                        inimigo4.setVisible(true);
                        inimigo5.setVisible(false);
                        inimigo6.setVisible(false);
                        inimigo7.setVisible(false);
                        break;
                    case 5:
                        inimigo1.setVisible(true);
                        inimigo2.setVisible(true);
                        inimigo3.setVisible(true);
                        inimigo4.setVisible(true);
                        inimigo5.setVisible(true);
                        inimigo6.setVisible(false);
                        inimigo7.setVisible(false);
                        break;
                    case 6:
                        inimigo1.setVisible(true);
                        inimigo2.setVisible(true);
                        inimigo3.setVisible(true);
                        inimigo4.setVisible(true);
                        inimigo5.setVisible(true);
                        inimigo6.setVisible(true);
                        inimigo7.setVisible(false);
                        break;
                    case 7:
                        inimigo1.setVisible(true);
                        inimigo2.setVisible(true);
                        inimigo3.setVisible(true);
                        inimigo4.setVisible(true);
                        inimigo5.setVisible(true);
                        inimigo6.setVisible(true);
                        inimigo7.setVisible(true);
                        break;
                    default:
                        break;
                }
                
            }
            
        });
        
        timer.start();
        
        /*y = personagem.getY();
        x = personagem.getX();
        
         KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher((KeyEvent event) -> {
                if(event.getID() == KeyEvent.KEY_PRESSED && event.getKeyCode() == 38){//cima
                    
                    y = y-velocidade_do_movimento;
                    personagem.setLocation(x, y);
                    
                    return true;
                }
                else
                    if(event.getID() == KeyEvent.KEY_PRESSED && event.getKeyCode() == 40){//baixo
                        
                        y = y+velocidade_do_movimento;
                        personagem.setLocation(x, y);
                        
                        return true;
                    }
                    else
                        if(event.getID() == KeyEvent.KEY_PRESSED && event.getKeyCode() == 39){//direita
                            
                            x = x+velocidade_do_movimento;
                            personagem.setLocation(x, y);
                            
                            return true;
                        }
                        else
                            if(event.getID() == KeyEvent.KEY_PRESSED && event.getKeyCode() == 37){//esquerda
                                
                                x = x-velocidade_do_movimento;
                                personagem.setLocation(x, y);
                                
                                return true;
                            }
                return false;
        });*/
        
    }//GEN-LAST:event_formWindowOpened

    private void PAPEL_DE_PAREDEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PAPEL_DE_PAREDEMouseClicked
        // TODO add your handling code here:
        
        yatual =  (MouseInfo.getPointerInfo().getLocation().y)-35;
        xatual =  (MouseInfo.getPointerInfo().getLocation().x)-100;
        
        velocidade_do_tiro = 10;
        
        if(poder.isVisible()){
            //poder.setVisible(false);
            //timer.stop();
        }else{
            poder.setVisible(true);
            poder.setLocation(xatual-contador2, yatual);
            //timer.start();
        }
        
        timer = new Timer(50, (ActionEvent e) -> {
            
        contador2 = contador2+10;
            
        //poder.setLocation((MouseInfo.getPointerInfo().getLocation().x)-100, (MouseInfo.getPointerInfo().getLocation().y)-35);
        
        //
           
        poder.setLocation(xatual-contador2, yatual);
            
        //
        
        if(poder.getX() < 1){
            
            poder.setLocation( xatual--, yatual);
            
            //yatual =  (MouseInfo.getPointerInfo().getLocation().y)-35;
            
            
            //poder.setLocation(xatual-contador2, yatual);
            
            //poder.setVisible(false);
            //timer.stop();
            //poder.setLocation((MouseInfo.getPointerInfo().getLocation().x)-100,  (MouseInfo.getPointerInfo().getLocation().y)-35);
        }
        
        });
        
        timer.start();
        
    }//GEN-LAST:event_PAPEL_DE_PAREDEMouseClicked

    private void PAPEL_DE_PAREDEMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PAPEL_DE_PAREDEMouseEntered
        // TODO add your handling code here:
        
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TRANSLUCENT);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0),"");
        PAPEL_DE_PAREDE.setCursor(blankCursor);
        
    }//GEN-LAST:event_PAPEL_DE_PAREDEMouseEntered

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tela().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PAPEL_DE_PAREDE;
    private javax.swing.JLabel inimigo1;
    private javax.swing.JLabel inimigo2;
    private javax.swing.JLabel inimigo3;
    private javax.swing.JLabel inimigo4;
    private javax.swing.JLabel inimigo5;
    private javax.swing.JLabel inimigo6;
    private javax.swing.JLabel inimigo7;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel personagem;
    private javax.swing.JLabel poder;
    // End of variables declaration//GEN-END:variables
}
