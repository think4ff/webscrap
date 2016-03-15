/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package com.aurg.webscrap;

/*
 * TextSamplerDemo.java requires the following files:
 *   TextSamplerDemoHelp.html (which references images/dukeWaveRed.gif)
 *   images/Pig.gif
 *   images/sound.gif
 */

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aurg.webscrap.io.HttpIO;
import com.aurg.webscrap.io.InputCSV;

import java.awt.*;              //for layout managers and more
import java.awt.event.*;        //for action events
import java.io.File;
//import java.net.InetAddress;
import java.util.ArrayList;


public class MainUX extends JPanel
                             implements ActionListener {
    /**
	 * 
	 */
	public static String version = "대법원사이트 자동 조회 시스템 (v1.6 2015-01-09)";  
	private static final long serialVersionUID = 1L;
	protected static final String strSoeIDField = "SOE ID";
    protected static final String strPasswordField = "Password";
    protected static final String strBtnFileOpen = "File Open";
    protected static final String strBtnRun = "Run";
    //TODO:적용시 수정
//    protected static final String mode ="live"; 
    protected static final String mode ="dev";

    protected static JLabel labelProgressInfo;
    protected static JProgressBar progress1;
    protected JButton btnRun;

	public static ArrayList<Object> InputList;
	//public static ArrayList<Object> OutputList;   // 읽기성공한 출력후보
	public static ArrayList<Object> FailList;     // 읽기 실패한 리스트
	public static Log log;
	static JTextArea textareaLog ;
	
	static JFileChooser fc;
	static JPanel panelLeft;
	JTextField textSoeID;
	JPasswordField textPassword;
	
	//protected static Logger logger = Logger.getLogger( MainUX.class.getName());
	protected static Logger logger = LoggerFactory.getLogger( MainUX.class);
	
    public MainUX() {
    	
        // Init variables... 
        // ArrayList 생성
        InputList = new ArrayList<Object>();
        // OutputList = new ArrayList<Object>();
        FailList = new ArrayList<Object>();
        
        setLayout(new BorderLayout());

        //Create a regular text field.
        textSoeID = new JTextField(10);
//        textField.setActionCommand(strSoeIDField);
//        textField.addActionListener(this);

        //Create a password field.
        textPassword = new JPasswordField(10);
        
        if( isDevMode() )
        {
        	textSoeID.setText("sj10706");
        	textPassword.setText("Citibank02");
        }
        
//        passwordField.setActionCommand(strPasswordField);
//        passwordField.addActionListener(this);

        //Create a File Open Button
        JButton btnFileOpen = new JButton("File Open");
        btnFileOpen.setActionCommand(strBtnFileOpen);
        btnFileOpen.addActionListener(this);
        
        //Create a Run Button
        btnRun = new JButton("Run");
        btnRun.setActionCommand(strBtnRun);
        btnRun.addActionListener(this);  
        btnRun.setEnabled(false);

        //Create some labels for the fields.
        JLabel labelSoeID = new JLabel(strSoeIDField + ": ");
        labelSoeID.setLabelFor(textSoeID);
        JLabel labelPassword = new JLabel(strPasswordField + ": ");
        labelPassword.setLabelFor(textPassword);
        JLabel labelBtnFileOpen = new JLabel(strBtnFileOpen + ": ");
        labelBtnFileOpen.setLabelFor(btnFileOpen);
        
        JLabel labelBtnRun = new JLabel(strBtnRun + ": ");
        labelBtnRun.setLabelFor(btnRun);        
        
        //Create a label to put messages during an action event.
        labelProgressInfo = new JLabel("0/0");
        labelProgressInfo.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        // Progress Bar
        progress1 = new JProgressBar(0,100);
        progress1.setStringPainted(true);
        
        //Lay out the text controls and the labels.
        JPanel panelUpper = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        panelUpper.setLayout(gridbag);

        JLabel[] labels = {labelBtnFileOpen, labelBtnRun, labelSoeID, labelPassword};
        JComponent[] textFields = {btnFileOpen, btnRun, textSoeID, textPassword };
		addLabelTextRows(labels, textFields, gridbag, panelUpper);

        c.gridwidth = GridBagConstraints.REMAINDER; //last
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1.0;
        panelUpper.add(labelProgressInfo, c);
        panelUpper.add(progress1, c);
        panelUpper.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder(""),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
        
        //Create a text area.
        textareaLog = new JTextArea( 15, 35 );
        textareaLog.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
        textareaLog.setLineWrap(true);
        textareaLog.setWrapStyleWord(true);
        JScrollPane panelLower = new JScrollPane(textareaLog);
        panelLower.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //panelLower.setPreferredSize(new Dimension(00, 200));
        panelLower.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Log"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                panelLower.getBorder()));

        //Create a text pane.
/*        JTextPane textPane = new JTextPane();  //createTextPane();
        JScrollPane paneScrollPane = new JScrollPane(textPane);
        paneScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        paneScrollPane.setPreferredSize(new Dimension(400, 10));
        paneScrollPane.setMinimumSize(new Dimension(10, 10));
*/

        //Put everything together.
        panelLeft = new JPanel(new BorderLayout());
        panelLeft.add(panelUpper, 
                     BorderLayout.PAGE_START);
        panelLeft.add(panelLower,
                     BorderLayout.CENTER);

        add(panelLeft, BorderLayout.LINE_START);
        //add(rightPane, BorderLayout.LINE_END);
        
        // Create a file chooser
        fc = new JFileChooser();      
        // Create Log
        log = new Log(textareaLog);           
    }

    private void addLabelTextRows(JLabel[] labels,
    		                      JComponent[] textFields,
                                  GridBagLayout gridbag,
                                  Container container) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        int numLabels = labels.length;

        for (int i = 0; i < numLabels; i++) {
            c.gridwidth = GridBagConstraints.RELATIVE;// .RELATIVE; //next-to-last
            c.fill = GridBagConstraints.NONE;      //reset to default
            c.weightx = 0.0;                       //reset to default
            container.add(labels[i], c);

            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
            c.fill = GridBagConstraints.HORIZONTAL;//HORIZONTAL;
            c.weightx = 1.0;
            container.add(textFields[i], c);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (strBtnFileOpen.equals(e.getActionCommand())) {
			 fc.setCurrentDirectory(new File(".\\"));   // 마지막 Open 했던 경로를 기억했다가 읽어오도록 수정 필요.
			 int returnVal = fc.showOpenDialog(panelLeft);			 
	         if (returnVal == JFileChooser.APPROVE_OPTION) {
	             	             
	             InputCSV inputCSV = new InputCSV(fc.getSelectedFile().getPath());
	             
	             // Input list를 초기화를 한다.
	             InputList.clear();
	             if( inputCSV.ReadFile( InputList ) )
	             {       
	            	 if( InputList.size() != 0 )
                    {
	            		 log.out("파일읽기 완료 : " + fc.getSelectedFile().getPath() );
	                     log.out( InputList.size() + "개의 데이타를 정상적으로 읽었습니다." );
		                 btnRun.setEnabled(true);
                    }
	             }
	             else 
	             {
	            	 log.out("파일읽기 실패 : " + fc.getSelectedFile().getPath() );            	 
	             }
	             
	         } else {
	        	 // System.out.println("Open command cancelled by user.");
	         }		

        } else if (strBtnRun.equals(e.getActionCommand())) {
 			// List Initialize 
        	String password = (new String(textPassword.getPassword())).trim();
        	String SoeID = textSoeID.getText().trim();
        	
        	if( SoeID.length() == 0 ||  password.length() == 0)
        	{
            	JOptionPane.showMessageDialog(panelLeft,
            		    "SOE ID 또는 Password가 입력되지 않았습니다.",
            		    "SOE ID, Password Error!!",
            		    JOptionPane.ERROR_MESSAGE);
        		
            	return;
        	}
        	
			FailList.clear();
			//OutputList.clear(); 			
			btnRun.setEnabled(false);
			
			log.out( "=============== 조회 시작 ===============" );
			// Http IO Start
			HttpIO httpIO = new HttpIO(SoeID, password); 
			httpIO.start();        	
        }
        
    }
    
    public static boolean isDevMode()
    {
    	
    	if( mode.toLowerCase().equals("dev") )
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    public static String getVersion()
    {
    	if( isDevMode() )
    	{
    		return "DEV:" + version; 
    	}
    	
    	return version;
    }

    // UX 정보를 업데이트 한다.
    public static void updateUx( String info, int value )
    {
    	logger.info( "info : " + info + ",value : " + value );
    	
     	progress1.setValue( value );                                     // 프로그래스바에 값 설정
     	labelProgressInfo.setText( " :: Progress Info. :" + info );
     	progress1.setToolTipText( info);            
     	progress1.repaint();                                             // Refresh graphics
    }
    
    public static void main(String[] args) {
    	
    	logger.info( "start.. : " + version );
    	
    	// output 폴더가 없으면 생성한다. 
    	File folder_ck = new File("./output/"); 
    	if( folder_ck.exists() == false )
    	{
    		logger.info( "Output folder Make Success : " + folder_ck.mkdir() );
    	}
    	
        //Schedule a job for the event dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
        
        	public void run() {
	            //Turn off metal's use of bold fonts
			    // UIManager.put("swing.boldMetal", Boolean.FALSE);
	
		        try {
					//InetAddress inetAddress = InetAddress.getByName("www.google.com");
		        	//InetAddress inetAddress = InetAddress.getByName("legalprocess.citibankkorea.citigroup.net");
					//logger.info( "legalprocess.citibankkorea.citigroup.net IP : " + inetAddress.getHostAddress() );
		        	
		            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		            	
		            	logger.info(info.getName());
		            	
		                if ("Nimbus".equals(info.getName())) {
		                    UIManager.setLookAndFeel(info.getClassName());
		                    
		    		        logger.info( "set Nimbus LookAndFeel" );
		                    break;
		                }
		            }
			        
			        /**
			         * Create the GUI and show it.  For thread safety,
			         * this method should be invoked from the
			         * event dispatch thread.
			         */
		            //Create and set up the window.
			        logger.info( "new MyUiClass()" );
		        	new MyUiClass();
		        	
		        } catch (Exception e) {
		            // If Nimbus is not available, you can set the GUI to another look and feel.
		        	e.printStackTrace();
		        }


            }
        	
        });
        
    }
}

