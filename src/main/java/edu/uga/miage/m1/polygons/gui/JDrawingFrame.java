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
import java.util.List;

import javax.swing.*;

import edu.uga.miage.m1.polygons.gui.command.Command;
import edu.uga.miage.m1.polygons.gui.command.DrawShape;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
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
        SQUARE, TRIANGLE, CIRCLE
    }


    private ArrayList<SimpleShape> myShapes;
    
    private SimpleShape selectedShape;

    private ShapeFactory shapeFactory;


    private static final long serialVersionUID = 1L;

    private JToolBar mToolBar;

    private Shapes mSelected;

    private JPanel mPanel;

    private JLabel mLabel;

    //commands 
    protected List<Command> commands = new ArrayList<>();

    public boolean addCommand(Command command) {
        return commands.add(command);
    }

    public void play() {
        for (Command command : commands) {
            command.execute(selectedShape, myShapes);
        }
    }

    public void reset() {
        commands.clear();
    }


    //AvtionListeners 

    private ShapeActionListener mReusableActionListener = new ShapeActionListener();
    private JsonSaveActionListener saveJson = new JsonSaveActionListener(this);
    private XMLSaveActionListener saveXML = new XMLSaveActionListener(this);
    private OpenFileListener openFile = new OpenFileListener(this);
    private static JSonVisitor jsonVisitor = new JSonVisitor();
    private static XMLVisitor xmlVisitor = new XMLVisitor();


    /**
     * Tracks buttons to manage the background.
     */
    private EnumMap<Shapes, JButton> mButtons = new EnumMap<>(Shapes.class);

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
        addKeyListener(null);
        mPanel.setFocusable(true);
        
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
        setPreferredSize(new Dimension(400, 400));
       
        //add persistence buttons 
        addOpenFile();
        addSave();


        //commands 
        commands = new ArrayList<>();

    
    }


    public void addOpenFile(){
        JButton open = new JButton("Open file to import");
        open.addActionListener(openFile);
        mToolBar.add(open);


    }


    private void addSave(){
        JButton b1 = new JButton("save to Json");
        JButton b2 = new JButton("save to XML");
        b1.addActionListener(saveJson);
        b2.addActionListener(saveXML);
        mToolBar.add(b1);
        mToolBar.add(b2);
       
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
            System.out.println("woot!");
        }

        System.out.println("key pressed");
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("woot!");
    }

    public void keyTyped(KeyEvent e) {
        
    }
    /**
     * 
     *      * Implements method for the <tt>MouseListener</tt> interface to
     * draw the selected shape into the drawing canvas.
     * @param evt The associated mouse event.
     */
    public void mouseClicked(MouseEvent evt) {
        if (mPanel.contains(evt.getX(), evt.getY())) {
            Graphics2D g2 = (Graphics2D) mPanel.getGraphics();

            SimpleShape shape = shapeFactory.getShape(mSelected.toString().toLowerCase(), evt.getX(), evt.getY());
            //add to commands 
            commands.add(new DrawShape(g2).execute(shape, myShapes));

            shape.accept(jsonVisitor);
            shape.accept(xmlVisitor);

        }
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
        for(SimpleShape s : myShapes){
            if(((evt.getX()-25> s.getX() && evt.getX()-25 < s.getX() + 50)||(evt.getX()-25 > s.getX()- 50 && evt.getX()-25 <  s.getX())) && ((evt.getY()-25> s.getY() && evt.getY()-25 < s.getY() + 50)||(evt.getY()-25 > s.getY()- 50 && evt.getY()-25 <  s.getY()))){
                s.setSelected(true);
                this.selectedShape = s;
            }
        }

    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete
     * shape dragging.
     * @param evt The associated mouse event.
     */
    public void mouseReleased(MouseEvent evt) {
        //Implements method for the <tt>MouseListener</tt> interface to complete
        //shape dragging.
       
    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     * @param evt The associated mouse event.
     */
    public void mouseDragged(MouseEvent evt) {
        //Implements method for the <tt>MouseMotionListener</tt> interface to
        //move a dragged shape.
        if(selectedShape!= null){
            selectedShape.move(evt.getX(), evt.getY());
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
    private class JsonSaveActionListener implements ActionListener, Serializable {

        JDrawingFrame frame;

        public JsonSaveActionListener(JDrawingFrame frame){
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            saveFile(frame, jsonVisitor);
        }
    }
    private class XMLSaveActionListener implements ActionListener, Serializable{

        JDrawingFrame frame;

        public XMLSaveActionListener(JDrawingFrame frame){
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            saveFile(frame, xmlVisitor);
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
            }else{
               mLabel.setText("Open command canceled");
            }
        }
    }


    private void saveFile(JDrawingFrame frame, Visitor visitor){
        JFileChooser fileChooser = new JFileChooser("/Users/begimaykonushbaeva/Desktop/M1/PC/Persistence/git");
            int option = fileChooser.showSaveDialog(frame);
            if(option == JFileChooser.APPROVE_OPTION){
               File file = fileChooser.getSelectedFile();
               mLabel.setText("File Selected: " + file.getName());
               visitor.save(file.getPath());
            }else{
               mLabel.setText("Open command canceled");
            }
    }
}