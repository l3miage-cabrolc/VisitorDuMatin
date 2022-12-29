package edu.uga.miage.m1.polygons.gui.command;


public interface Command {
    
    static final long serialVersionUID = 1905122041950251207L;
    
    public void execute();


    public void cancel();

    @Override
    String toString();
    
}
