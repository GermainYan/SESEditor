/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlr.ses.seseditor;

import com.google.common.graph.Graph;
import com.google.errorprone.annotations.Var;
import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <h1>GraphCellPopUp</h1>
 * <p>
 * This class implements right click action of the mouse on the node in the
 * graphical editor. It initiates all the mouse actions such as add variable,
 * delete variable, rename, delete node etc which are used in the graphical
 * editor for modifying SES nodes.
 * </p>
 *
 * @author Bikash Chandra Karmokar
 * @version 1.0
 */
class GraphCellPopUp extends JPopupMenu {

    public GraphCellPopUp(int x, int y, Object pos) {
        JMenuItem itemVar = new JMenuItem("Add Variable");
        JMenuItem itemVarDel = new JMenuItem("Delete Variable");
        JMenuItem itemVarDelAll = new JMenuItem("Delete All Variables");
        JMenuItem itemRename = new JMenuItem("Rename");
        JMenuItem itemDel = new JMenuItem("Delete");
        JMenuItem itemAddModule = new JMenuItem("Add Module");
        JMenuItem itemSaveModule = new JMenuItem("Save Module");
        JMenuItem itemConstraint = new JMenuItem("Add Constraint");
        JMenuItem itemConstraintDelAll = new JMenuItem("Delete All Constraint");
        JMenuItem itemDelEdge = new JMenuItem("Delete Edge");
        
        
        
        //Additional part for Fold and Unfold the cells
    
        JMenuItem itemFoldCells = new JMenuItem("Fold the Node");
        JMenuItem itemUnfoldCells = new JMenuItem("Unfold the Node");
        boolean State=true;
        //Check state of Cells
        State=SESEditor.jtreeTograph.CheckObject(pos);
        System.out.print("State of Edge:"+State);
        //Check the number of the edges 
        int number =SESEditor.jtreeTograph.getChildCount(pos);
        //System.out.print("Number of Edges"+number);
        String test=SESEditor.jtreeTograph.CheckStyle(pos);
        String val="Entity";
        if(number<=1) {
       	
        itemFoldCells.setEnabled(false);
        itemUnfoldCells.setEnabled(false);
        
        }else {
        	
        }
            if (!test.equals(val)) {
        	            	
            	itemFoldCells.setEnabled(false);
                
            }else {
            	itemUnfoldCells.setEnabled(false);
            	}
            
            
      
        itemUnfoldCells.addActionListener(new ActionListener() {
        	boolean collapse=true;
            public void actionPerformed(ActionEvent ae) {
            	 
                	SESEditor.jtreeTograph.toggleSubtree(pos,collapse); 
                	SESEditor.jtreeTograph.reset(pos);
              
            }
        });
        
        
        itemFoldCells.addActionListener(new ActionListener() {
        	boolean collapse=true;
        	boolean recurse=false;
            public void actionPerformed(ActionEvent ae) {
            	
                	SESEditor.jtreeTograph.FoldCells(pos,collapse, recurse); 
                	mxCell cell = (mxCell) pos;
                	 //Implementation of the Element after the collapse
                	String style=cell.getStyle();
                	String style1;
                	style1=style+mxConstants.STYLE_STROKECOLOR+"=RED";
                	cell.setStyle(style1);
                	//cell.setCellStyles(mxConstants.STYLE_STROKECOLOR, "red", cell);
            }
        });

        
        itemVar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                SESEditor.jtreeTograph.addVariableFromGraphPopup(pos);

            }
        });

        itemVarDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                SESEditor.jtreeTograph.deleteVariableFromGraphPopup(pos);

            }
        });

        itemVarDelAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                SESEditor.jtreeTograph.deleteAllVariablesFromGraphPopup(pos);

            }
        });

        itemRename.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                SESEditor.jtreeTograph.renameCell(pos);

            }
        });

        itemDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                SESEditor.jtreeTograph.deleteNodeFromGraphPopup(pos);

            }
        });

        itemDelEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SESEditor.jtreeTograph.deleteEdgeFromGraphPopup(pos);

            }
        });

        itemAddModule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                SESEditor.jtreeTograph.addModuleFromOtherModelAsXML(pos);

            }
        });

        itemSaveModule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                SESEditor.jtreeTograph.writeSaveModuleToFileAsXML(pos);

            }
        });

        itemConstraint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                SESEditor.jtreeTograph.addConstraintFromGraphPopup(pos);

            }
        });

        itemConstraintDelAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                SESEditor.jtreeTograph.deleteAllConstraintFromGraphPopup(pos);

            }
        });

        mxCell cell = (mxCell) pos;
        String cellName = (String) cell.getValue();
        boolean connected = SESEditor.jtreeTograph.isConnectedToRoot(cell);
        JtreeToGraph.connectedToRoot = false; // have to assign false because isConnectedToRoot() function
        // assign true during calling

        if (cell.isVertex()) {
            if (cell.getId().startsWith("uniformity") && connected) {
                if (cell.getId().endsWith("RefNode")) {
                    add(itemDel);
                } else {
                    // nothing
                }

            } else {
                add(itemVar);
                add(new JSeparator());
                add(itemRename);
                add(new JSeparator());
                add(itemVarDel);
                add(new JSeparator());
                add(itemVarDelAll);
                add(new JSeparator());
                add(itemDel);
                add(new JSeparator());
                add(itemAddModule);
                add(new JSeparator());
                add(itemSaveModule);
                add(new JSeparator());
                add(itemFoldCells);
                add(new JSeparator());
                add(itemUnfoldCells);
                
                
                                
                if (cellName.endsWith("Dec")) {
                    add(new JSeparator());
                    add(itemConstraint);
                    add(new JSeparator());
                    add(itemConstraintDelAll);
                    itemUnfoldCells.setEnabled(false); 
                }
                if ((cellName.endsWith("Spec"))||(cellName.endsWith("MAsp"))) {
                	itemUnfoldCells.setEnabled(false);
                }
                
            }
        } else {
            add(itemDelEdge);
        }

    }
}
