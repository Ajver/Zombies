package jslEngine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class jslManager {
    private jslEngine engine;
    private boolean autoRender = true, autoUpdate = true, autoClearScreen = true;
    protected boolean isTranslating = true;
    private float translateX = 0, translateY = 0;
    private jslObject clickedOb = null;
    private ArrayList<jslLabel> renderOrder = new ArrayList<>();
    private ArrayList<jslObject> objects = new ArrayList<>();
    private ArrayList<jslKeyInput> keyInputs = new ArrayList<>();
    private ArrayList<jslObject> mouseInputs = new ArrayList<>();
    public jslManager(jslEngine engine) { this.engine = engine; }
    public void setAutoRender(boolean flag) { this.autoRender = flag; }
    public void setAutoUpdate(boolean flag) { this.autoUpdate = flag; }
    public void setAutoClearScreen(boolean flag) { this.autoClearScreen = flag; }
    public void translate(float tx, float ty) {
        translateX(tx);
        translateY(ty);
    }
    public void translateX(float tx) { this.translateX += tx; }
    public void translateY(float ty) { this.translateY += ty; }
    public void setTranslate(float tx, float ty) {
        setTranslateX(tx);
        setTranslateY(ty);
    }
    public void setTranslateX(float tx) { this.translateX = tx; }
    public void setTranslateY(float ty) { this.translateY = ty; }
    public float getTranslateX() { return (isTranslating ? translateX : 0); }
    public float getTranslateY() { return (isTranslating ? translateY : 0); }
    public void setIsTranslating(boolean flag) { this.isTranslating = flag; }
    public boolean getIsTranslating(boolean flag) { return this.isTranslating; }
    public void update(float et) {
        if(autoUpdate) {
            for(int i=objects.size()-1; i>=0; i--) {
                objects.get(i).update(et);
            }
        }
    }
    public void render(Graphics g) {
        if(autoRender) {
            if(autoClearScreen) {
                g.setColor(new Color(30, 30, 30));
                g.fillRect(0, 0, engine.WW(), engine.WH());
            }
            if(isTranslating) {
                g.translate((int) translateX, (int) translateY);
            }
            for(int i=0; i<objects.size(); i++) {
                jslObject o = objects.get(i);
                o.render(g);
            }
            if(isTranslating) {
                g.translate(-(int) translateX, -(int) translateY);
            }
        }
    }
    public void mouseMoved(MouseEvent e) {
        for(int i=mouseInputs.size()-1; i>=0; i--) {
            jslObject o = mouseInputs.get(i);
            if (o.isPointIn(e.getX()-getTranslateX(), e.getY()-getTranslateY())) {
                o.onMove(e);
                if (!o.hover) {
                    o.hover = true;
                    o.onEnter(e);
                }
                for(i=i-1; i>=0; i--) {
                    o = mouseInputs.get(i);
                    if(o.hover) {
                        o.hover = false;
                        o.onLeave(e);
                    }
                }
                return;
            } else if (o.hover) {
                o.hover = false;
                o.onLeave(e);
            }
        }
    }
    public void mouseDragged(MouseEvent e) {
        if(clickedOb != null) {
            clickedOb.onDrag(e);
        }
    }
    public void mousePressed(MouseEvent e) {
        for(int i=mouseInputs.size()-1; i>=0; i--) {
            jslObject o = mouseInputs.get(i);
            if(o.isPointIn(e.getX()-getTranslateX(), e.getY()-getTranslateY())) {
                clickedOb = o;
                o.onPress(e);
                return;
            }
        }
    }
    public void mouseReleased(MouseEvent e) {
        if(clickedOb != null) {
            if (clickedOb.isPointIn(e.getX()-getTranslateX(), e.getY()-getTranslateY())) {
                clickedOb.onRelease(e);
            }
            clickedOb = null;
        }
    }
    public void add(jslObject o) { objects.add(o); sortObjects(); }
    public ArrayList<jslObject> getObjects() { return objects; }
    public jslObject getObject(int i) { return objects.get(i); }
    public jslObject getObject(jslLabel l) {
        for(jslObject o : objects) {
            if(o.is(l)) {
                return o;
            }
        }
        return null;
    }
    public void removeAllObjects() { objects.clear(); }
    public void removeObject(int i) { objects.remove(i); }
    public void removeObject(jslObject o) {
        for(int i=objects.size()-1; i>=0; i--) {
            if(getObject(i) == o) {
                removeObject(i);
            }
        }
    }
    public void keyPressed(KeyEvent e) {
        for(jslKeyInput k : keyInputs) {
            k.onPress(e);
        }
    }
    public void keyReleased(KeyEvent e) {
        for(jslKeyInput k : keyInputs) {
            k.onRelease(e);
        }
    }
    public void keyTyped(KeyEvent e) {
        for(jslKeyInput k : keyInputs) {
            k.onType(e);
        }
    }
    public void add(jslKeyInput k) { keyInputs.add(k); }
    public ArrayList<jslKeyInput> getKeyInputs() { return keyInputs; }
    public jslKeyInput getKeyInput(int i) { return keyInputs.get(i); }
    public void removeAllKeyInputs() { keyInputs.clear(); }
    public void removeKeyInput(int i) { keyInputs.remove(i); }
    public void removeKeyInput(jslKeyInput k) {
        for(int i=keyInputs.size()-1; i>=0; i--) {
            if(getKeyInput(i) == k) {
                removeKeyInput(i);
            }
        }
    }
    public void addMouseInput(jslObject o) { mouseInputs.add(o); sortObjects(); }
    public ArrayList<jslObject> getMouseInputs() { return mouseInputs; }
    public jslObject getMouseInput(int i) { return mouseInputs.get(i); }
    public void removeAllMouseInputs() { mouseInputs.clear(); }
    public void removeMouseInput(int i) { mouseInputs.remove(i); }
    public void removeMouseInput(jslObject o) {
        for(int i=mouseInputs.size()-1; i>=0; i--) {
            if(getMouseInput(i) == o) {
                removeKeyInput(i);
            }
        }
    }
    public void addToRenderOrder(jslLabel l) {
        for(int i=renderOrder.size()-1; i>=0; i--) {
            if(renderOrder.get(i) == l) {
                renderOrder.remove(i);
            }
        }
        renderOrder.add(l);
        sortObjects();
    }
    public void setRenderOrder(ArrayList<jslLabel> labels) {
        renderOrder = labels;
        sortObjects();
    }
    public void setRenderOrder(jslLabel... labels) {
        renderOrder.clear();
        for(int i=0; i<labels.length; i++) {
            renderOrder.add(labels[i]);
        }
        sortObjects();
    }
    private void sortObjects() {
        ArrayList<jslObject> sortedOb = new ArrayList<>();
        ArrayList<jslObject> sortedMI = new ArrayList<>();
        for(int j=0; j<renderOrder.size(); j++) {
            // Sort objects to render
            for(int i=objects.size()-1; i>=0; i--) {
                jslObject o = objects.get(i);
                if(o.is(renderOrder.get(j))) {
                    sortedOb.add(o);
                    objects.remove(i);
                }else if(!renderOrder.contains(o.getLabel())) {
                    renderOrder.add(o.getLabel());
                }
            }
            // Sort objects to mouse input
            for(int i=mouseInputs.size()-1; i>=0; i--) {
                jslObject o = mouseInputs.get(i);
                if(o.is(renderOrder.get(j))) {
                    sortedMI.add(o);
                    mouseInputs.remove(i);
                }else if(!renderOrder.contains(o.getLabel())) {
                    renderOrder.add(o.getLabel());
                }
            }
        }
        objects = sortedOb;
        mouseInputs = sortedMI;
    }
    public ArrayList<jslLabel> getRenderOrder() { return this.renderOrder; }
}