package com.aurg.webscrap;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class MyUiClass {

	public MyUiClass() {
		super();
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		// TODO Auto-generated constructor stub
		JFrame frame = new JFrame(MainUX.getVersion());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// Add content to the window.
		frame.add(new MainUX());

		// Display the window.
		frame.pack();
		// 화면 중앙으로 위치설정
		frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
		frame.setVisible(true);

		// System.out.println((screenSize.width-frame.getWidth())/2 +"," +
		// (screenSize.height-frame.getHeight())/2);
		// System.out.println(screenSize.width +"," + );

		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png")));
	}

}
