/* @(#)JMovieControlPanel.java
 * Copyright © 2011 Werner Randelshofer, Switzerland. 
 * You may only use this software in accordance with the license terms.
 */
package ru.sbtqa.monte.media.gui;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.imageio.ImageIO;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import ru.sbtqa.monte.media.Movie;
import ru.sbtqa.monte.media.gui.border.ImageBevelBorder;
import ru.sbtqa.monte.media.image.Images;
import ru.sbtqa.monte.media.math.Rational;

/**
 * JMovieControlPanel.
 *
 * @author Werner Randelshofer
 * @version 1.0 2011-09-04 Created.
 */
public class JMovieControlPanel extends javax.swing.JPanel {
    private final static long serialVersionUID = 1L;

    private enum LabelMode {

        TIME, FRAME
    }
    private LabelMode labelMode = LabelMode.TIME;
  private class Handler implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            updateTimeLabel();
        }
        
    }
    private Handler handler=new Handler();
    /** Creates new form JMovieControlPanel */
    public JMovieControlPanel() {
        initComponents();
        putClientProperty("style", "textured");
        timelineEditor.putClientProperty("style", "textured");
        timeLabel.setOpaque(false);
        timeLabel.setMinimumSize(timeLabel.getPreferredSize());
        timeLabel.setPreferredSize(timeLabel.getPreferredSize());
    }

    @Override
    public void setTransferHandler(TransferHandler newHandler) {
        super.setTransferHandler(newHandler);
        timelineEditor.setTransferHandler(newHandler);
        timeLabel.setTransferHandler(newHandler);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        getBackgroundBorder().paintBorder(this, g, 0, 0, getWidth(), getHeight());
    }
    private Border backgroundBorder;

    protected Border getBackgroundBorder() {
        if (backgroundBorder == null) {
            backgroundBorder = readBorders(
                    "images/TimelineEditor.background.png", 1, false, new Insets(3, 3, 3, 3))[0];
        }
        return backgroundBorder;
    }

    protected Border[] readBorders(String resource, int count, boolean isHorizontal, Insets insets) {
        resource = resource.substring(0, resource.length() - 4) + getStyleSuffix() + ".png";
        try {
            BufferedImage[] imgs = Images.split(ImageIO.read(JTimelineEditor.class.getResource(resource)), count, false);
            Border[] borders = new Border[count];
            for (int i = 0; i < count; i++) {
                borders[i] = new ImageBevelBorder(imgs[i], new Insets(1, 3, 1, 3));
            }
            return borders;
        } catch (Throwable ex) {
            throw new InternalError("JMovieControlPanel image not found:" + resource);
        }
    }

    public Movie getMovie() {
        return timelineEditor.getMovie();
    }

    protected void updateTimeLabel() {
        Movie movie = getMovie();
        Rational time = movie == null ? new Rational(0,1) : movie.getInsertionPoint();
        int hours = (int) time.floor(1).getNumerator()/3600;
        int minutes = (int) (time.floor(1).getNumerator() / 60) % 60;
        int seconds = (int) (time.floor(1).getNumerator() % 60);
        int frame = movie == null ? 0 : (int) (movie.timeToSample(0, time) - movie.timeToSample(0, time.floor(1)));
        switch (labelMode) {
            case TIME:
                timeLabel.setText((hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes)
                        + ":" + (seconds < 10 ? "0" + seconds : seconds)
                        + "." + (frame < 10 ? "0" + frame : frame));

                break;
            case FRAME:
                timeLabel.setText(Long.toString(movie.timeToSample(0, movie.getInsertionPoint()) ));
                break;
        }
    }

    protected String getStyleSuffix() {
        String style = (String) getClientProperty("style");
        return style == null ? "" : "." + style;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        timeLabel = new javax.swing.JLabel();
        timelineEditor = new ru.sbtqa.monte.media.gui.JTimelineEditor();

        FormListener formListener = new FormListener();

        setLayout(new java.awt.GridBagLayout());

        timeLabel.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        timeLabel.setText("00:00:00.00");
        timeLabel.addMouseListener(formListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        add(timeLabel, gridBagConstraints);

        javax.swing.GroupLayout timelineEditorLayout = new javax.swing.GroupLayout(timelineEditor);
        timelineEditor.setLayout(timelineEditorLayout);
        timelineEditorLayout.setHorizontalGroup(
            timelineEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        timelineEditorLayout.setVerticalGroup(
            timelineEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(timelineEditor, gridBagConstraints);
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.MouseListener {
        FormListener() {}
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == timeLabel) {
                JMovieControlPanel.this.timeLabelMouseClicked(evt);
            }
        }

        public void mouseEntered(java.awt.event.MouseEvent evt) {
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
        }

        public void mousePressed(java.awt.event.MouseEvent evt) {
        }

        public void mouseReleased(java.awt.event.MouseEvent evt) {
        }
    }// </editor-fold>//GEN-END:initComponents

private void timeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timeLabelMouseClicked
    labelMode = labelMode == LabelMode.TIME ? LabelMode.FRAME : LabelMode.TIME;
    updateTimeLabel();
}//GEN-LAST:event_timeLabelMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel timeLabel;
    private ru.sbtqa.monte.media.gui.JTimelineEditor timelineEditor;
    // End of variables declaration//GEN-END:variables

    public void setMovie(Movie movie) {
       Movie oldValue=timelineEditor.getMovie();
       if (oldValue!=null)oldValue.removePropertyChangeListener(handler);
        
        timelineEditor.setMovie(movie);
        if (movie!=null)movie.addPropertyChangeListener(handler);
        updateTimeLabel();
    }
}
