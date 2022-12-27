package edu.uga.miage.m1.polygons.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;

import javax.swing.*;

import edu.uga.miage.m1.polygons.gui.command.Compose;
import edu.uga.miage.m1.polygons.gui.command.DrawShape;
import edu.uga.miage.m1.polygons.gui.command.EraseShape;
import edu.uga.miage.m1.polygons.gui.command.Move;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Parser;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.CompositeShape;
import edu.uga.miage.m1.polygons.gui.shapes.ShapeFactory;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;


/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener {

    private enum Shapes {
        SQUARE, TRIANGLE, CIRCLE, COMPOSITE, SOLEIL
    }


    private ArrayList<SimpleShape> myShapes;
    
    private SimpleShape selectedShape;

    private ShapeFactory shapeFactory;


    private static final long serialVersionUID = 1L;

    private JToolBar mToolBar;

    private Shapes mSelected;

    private JPanel mPanel;

    private JLabel mLabel;

    private boolean isComposing = false;

    //commands 


    //ActionListeners 

    private ShapeActionListener mReusableActionListener = new ShapeActionListener();
    private SaveActionListener save = new SaveActionListener(this);
    private OpenFileListener openFile = new OpenFileListener(this);
    private ComposeListener composeShape = new ComposeListener(isComposing);
    
    
    // Visitors 
    private static JSonVisitor jsonVisitor = new JSonVisitor();
    private static XMLVisitor xmlVisitor = new XMLVisitor();
    /**
     * Tracks buttons to manage the background.
     */
    private EnumMap<Shapes, JButton> mButtons = new EnumMap<>(Shapes.class);

    private CompositeShape currentCompositeShape;

    /**
     * Default constructor that populates the main window.
     * @param frameName
     */
    public JDrawingFrame(String frameName) {
        super(frameName);


        //initiate the list of shapes 

        myShapes = new ArrayList<>();

        //Initiate a shape factory

        shapeFactory = new ShapeFactory();
    
        // Instantiates components
        mToolBar = new JToolBar("Toolbar");
        mPanel = new JPanel();
        mPanel.setBackground(Color.WHITE);
        mPanel.setLayout(null);
        mPanel.setMinimumSize(new Dimension(400, 400));
        mPanel.addMouseListener(this);
        mPanel.addMouseMotionListener(this);

        mPanel.addKeyListener(this);
        addKeyListener(this);
        mPanel.setFocusable(true);
        setFocusable(true);
        mPanel.setVisible(true);

        mLabel = new JLabel(" ", SwingConstants.LEFT);
        // Fills the panel
        setLayout(new BorderLayout());
        add(mToolBar, BorderLayout.NORTH);
        add(mPanel, BorderLayout.CENTER);
        add(mLabel, BorderLayout.SOUTH);
        
        
        
        
        // Add shapes in the menu
        addShape(Shapes.SQUARE, new ImageIcon(getClass().getResource("images/square.png")));
        addShape(Shapes.TRIANGLE, new ImageIcon(getClass().getResource("images/triangle.png")));
        addShape(Shapes.CIRCLE, new ImageIcon(getClass().getResource("images/circle.png")));
        addShape(Shapes.SOLEIL, new ImageIcon(getClass().getResource("images/soleil.png")));
        setPreferredSize(new Dimension(400, 400));
       
        //add persistence buttons 
        addImport();
        addExport();
        addCompose();

    
    }

    public void addCompose(){
        JButton compose = new JButton("Composition");
        compose.addActionListener(composeShape);
        mToolBar.add(compose);

        
    }

    public void addImport(){
        JButton open = new JButton("Import");
        open.addActionListener(openFile);
        mToolBar.add(open);
    }
    private void addExport(){
        JButton b = new JButton("Export");
        b.addActionListener(save);
        mToolBar.add(b);    
    }

    /**
     * Injects an available <tt>SimpleShape</tt> into the drawing frame.
     * @param name The name of the injected <tt>SimpleShape</tt>.
     * @param icon The icon associated with the injected <tt>SimpleShape</tt>.
     */
    private void addShape(Shapes shape, ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        mButtons.put(shape, button);
        button.setActionCommand(shape.toString());
        button.addActionListener(mReusableActionListener);
        if (mSelected == null) {
            button.doClick();
        }
        mToolBar.add(button);
        mToolBar.validate();
        repaint();
    }


    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_Z)) {
            mLabel.setText("Erase " + myShapes.get(myShapes.size()-1).getType());
            EraseShape eraseShape = new EraseShape(myShapes.get(myShapes.size()-1), myShapes);
            eraseShape.execute();
            redrawMyShapes();
        }
        if(myShapes.isEmpty()){
            mLabel.setText("There are no shapes to remove");
        }
        
       
    }

    public void keyReleased(KeyEvent e) {
        // implements key releaysed method
    }

    public void keyTyped(KeyEvent e) {
        // implements key typed method
    }
    /**
     * 
     *      * Implements method for the <tt>MouseListener</tt> interface to
     * draw the selected shape into the drawing canvas.
     * @param evt The associated mouse event.
     */
    public void mouseClicked(MouseEvent evt) {
        
        if(isSelectingShape(evt.getX(), evt.getY(), false)){
            mLabel.setText("Shape selected");
        }

        if(isSelectingShape(evt.getX(), evt.getY(), false) && isComposing){
            
            if(currentCompositeShape == null){

                currentCompositeShape = shapeFactory.getCompositeShape();

                Compose compose = new Compose(currentCompositeShape, myShapes);
                compose.setSimpleShape(selectedShape);
                compose.execute();

                mLabel.setText("Simple shape added to a new composite shape");
                
            }else{
                mLabel.setText("Simple shape added to existing composite shape");

                currentCompositeShape.addShape(selectedShape);
            }

            
            new EraseShape(selectedShape, myShapes).execute();
        }
        
        if (mPanel.contains(evt.getX(), evt.getY()) && !isSelectingShape(evt.getX(), evt.getY(), false)) {

            SimpleShape shape = shapeFactory.getShape(mSelected.toString().toLowerCase(), evt.getX(), evt.getY());

            new DrawShape(shape, myShapes).execute();

            // shape.accept(jsonVisitor);
            // shape.accept(xmlVisitor);

            redrawMyShapes();

            mLabel.setText("New " + shape.getType());
        }
       
    }

    private boolean isSelectingShape(int x, int y, boolean toMove){
        boolean res = false;
        for(SimpleShape s : myShapes){
            if(s.isSelected(x, y)){
                res = true;
                if(toMove){
                    selectedShape = s;
                }
            }
        }

        return res;
    }


    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     * @param evt The associated mouse event.
     */
    public void mouseEntered(MouseEvent evt) {
        //Implements an empty method for the <tt>MouseListener</tt> interface.
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     * @param evt The associated mouse event.
     */
    public void mouseExited(MouseEvent evt) {
        mLabel.setText(" ");
        mLabel.repaint();
    }
    /**
     * Implements method for the <tt>MouseListener</tt> interface to initiate
     * shape dragging.
     * @param evt The associated mouse event.
     */
    public void mousePressed(MouseEvent evt) {
        //Implements method for the <tt>MouseListener</tt> interface to initiate
        //shape dragging.
        isSelectingShape(evt.getX(), evt.getY(), true);

    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete
     * shape dragging.
     * @param evt The associated mouse event.
     */
    public void mouseReleased(MouseEvent evt) {
        //Implements method for the <tt>MouseListener</tt> interface to complete
        //shape dragging.

        requestFocus();
       
    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     * @param evt The associated mouse event.
     */
    public void mouseDragged(MouseEvent evt) {
        //Implements method for the <tt>MouseMotionListener</tt> interface to
        //move a dragged shape.
        if(selectedShape != null){
            mLabel.setText("Moving " + selectedShape.getType() + " to (" + evt.getX() + "," + evt.getY() + ")");
            new Move(selectedShape, evt.getX(), evt.getY()).execute();
            redrawMyShapes();
        }
       
    }

    private void redrawMyShapes(){
        repaint();

        Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
       
        SwingUtilities.invokeLater(() -> {
            for(SimpleShape s : myShapes){
                s.draw(g2);   
            }
        });
        
    }

    /**
     * Implements an empty method for the <tt>MouseMotionListener</tt>
     * interface.
     * @param evt The associated mouse event.
     */
    public void mouseMoved(MouseEvent evt) {
        modifyLabel(evt);
    }

    private void modifyLabel(MouseEvent evt) {
        mLabel.setText("(" + evt.getX() + "," + evt.getY() + ")");
    }

    
    /**
     * Simple action listener for shape tool bar buttons that sets
     * the drawing frame's currently selected shape when receiving
     * an action event.
     */
    private class ShapeActionListener implements ActionListener, Serializable {

        public void actionPerformed(ActionEvent evt) {
            // ItÃ¨re sur tous les boutons
            Iterator<Shapes> keys = mButtons.keySet().iterator();
            while (keys.hasNext()) {
                Shapes shape = keys.next();
                JButton btn = mButtons.get(shape);
                if (evt.getActionCommand().equals(shape.toString())) {
                    btn.setBorderPainted(true);
                    mSelected = shape;
                } else {
                    btn.setBorderPainted(false);
                }
                btn.repaint();
            }
        }
    }



    private class SaveActionListener implements ActionListener, Serializable {

        JDrawingFrame frame;

        public SaveActionListener(JDrawingFrame frame){
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser("/Users/begimaykonushbaeva/Desktop/M1/PC/Persistence/git");
            int option = fileChooser.showSaveDialog(frame);
            if(option == JFileChooser.APPROVE_OPTION){
               File file = fileChooser.getSelectedFile();
               mLabel.setText("File Selected: " + file.getName());
               if(Parser.getFileType(file.getName()).equals("xml")){
                for(SimpleShape s : myShapes){
                    s.accept(xmlVisitor);
                }
                xmlVisitor.save(file.getPath());
               }
               if(Parser.getFileType(file.getName()).equals("json")){
                for(SimpleShape s : myShapes){
                    s.accept(jsonVisitor);
                }
                jsonVisitor.save(file.getPath());
               }
               
            }else{
               mLabel.setText("Open command canceled");
            }
        }
    }


    private class OpenFileListener implements ActionListener, Serializable{

        JDrawingFrame frame;

        public OpenFileListener(JDrawingFrame frame){
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            JFileChooser fileChooser = new JFileChooser("/Users/begimaykonushbaeva/Desktop/M1/PC/Persistence/git");
            int option = fileChooser.showOpenDialog(frame);
            if(option == JFileChooser.APPROVE_OPTION){
               File file = fileChooser.getSelectedFile();
               mLabel.setText("File Selected: " + file.getName());
               myShapes.addAll(Parser.importFrom(file.getName()));
               redrawMyShapes();
                
            }else{
               mLabel.setText("Open command canceled");
            }

            
        }
    }

    private class ComposeListener implements ActionListener, Serializable{

        public ComposeListener(boolean isComposing){
           
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!isComposing){
                isComposing = true;
                mLabel.setText("Composition on");
            }else{
                isComposing = false;
                mLabel.setText("Composition off");
                currentCompositeShape.accept(xmlVisitor);
            }
        }
    }

}