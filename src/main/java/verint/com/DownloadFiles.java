package verint.com;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * @Author : VIKAS YADAV	
 */
@SuppressWarnings("serial")
public class DownloadFiles extends JFrame implements ActionListener {

	JLabel lblselect = new JLabel("Select The Choice");
	JLabel lblFileNmae = new JLabel("Enter File Name ");
	JLabel lblKBNumber = new JLabel("Enter KB Number ");
	JLabel lblDownloadStatus = new JLabel("File Download Path");  
	String selectedChoice;
	String selectedFileName;
	String selectedKbNumber;
	List<String> strFiles;
	JLabel l10 = new JLabel(" ");
	String[] choice = { "Please Select Choice","File Name", "KB Number" };
	JComboBox<String> cmbChoice = new JComboBox<String>(choice);
	JTextField txtFileName = new JTextField();
	JTextField numKBNumber = new JTextField();
	JButton btnSubmit = new JButton("SUBMIT");
	JButton btnCancel = new JButton("CLEAR");
	JLabel lblFileName = new JLabel("File Paths");
	JTextArea textArea = new JTextArea("", 5, 50);
	JScrollPane taScroll = new JScrollPane(textArea);

	public DownloadFiles(){
		
		add(lblselect);
		add(cmbChoice);
		
		add(lblFileNmae);
		add(txtFileName);
		
		add(lblKBNumber);
		add(numKBNumber);
		
		add(btnSubmit);
		add(btnCancel);
		
		add(lblDownloadStatus);
		add(taScroll); 
		
		add(l10);
		
		lblselect.setBounds(20, 45, 260, 30);
		lblselect.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		cmbChoice.setBounds(285, 45, 450, 30);

		lblFileNmae.setBounds(20, 95, 260, 30);
		lblFileNmae.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		txtFileName.setBounds(285, 95, 450, 30);

		lblKBNumber.setBounds(20, 145, 260, 30);
		lblKBNumber.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		numKBNumber.setBounds(285, 145, 450, 30);
		
		btnSubmit.setBounds(285, 195, 90, 35);
		btnCancel.setBounds(385, 195, 90, 35);
		
		lblDownloadStatus.setBounds(20, 245, 260, 30);
		lblDownloadStatus.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		
		taScroll.setBounds(20, 295, 600, 100);
		textArea.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		taScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		taScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		btnSubmit.addActionListener(this);
		btnCancel.addActionListener(this);
		addWindowListener(new mwa());
		
		cmbChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				@SuppressWarnings("rawtypes")
				JComboBox comboBox = (JComboBox) event.getSource();
				Object selected = comboBox.getSelectedItem();
				selectedChoice=(String) selected;
				if(selectedChoice.toString().equalsIgnoreCase("File Name")) {
					lblKBNumber.setVisible(false);
					numKBNumber.setVisible(false);
					lblFileNmae.setVisible(true);
					txtFileName.setVisible(true);
				}else {	
					lblFileNmae.setVisible(false);
					txtFileName.setVisible(false);
					lblKBNumber.setVisible(true);
					numKBNumber.setVisible(true);
				}
				if(selectedChoice.toString().equalsIgnoreCase("Please Select Choice")) {
					lblFileNmae.setVisible(true);
					txtFileName.setVisible(true);
					lblKBNumber.setVisible(true);
					numKBNumber.setVisible(true);
				}
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			
			lblFileNmae.setVisible(true);
			txtFileName.setVisible(true);
			
			lblKBNumber.setVisible(true);
			numKBNumber.setVisible(true);
			
			txtFileName.setText(null);
			numKBNumber.setText(null);
			textArea.setText(null);
			cmbChoice.setSelectedIndex(0);
		} else {
		if (e.getSource() == btnSubmit) {
			selectedFileName=txtFileName.getText();
			selectedKbNumber=numKBNumber.getText();
			if(cmbChoice.getSelectedIndex()==0) {
				JOptionPane.showMessageDialog(null, "Please select you choise", "Failure",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if("File Name".equals(selectedChoice) && (selectedFileName==null || "".equals(selectedFileName))) {
				JOptionPane.showMessageDialog(null, "With File Name Choise, File Name field is Mandatory and Exact Match", "Failure",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if("KB Number".equals(selectedChoice) && (selectedKbNumber==null || "".equals(selectedKbNumber))) {
				
				JOptionPane.showMessageDialog(null, "With KB Number Choise, KB Number field is Mandatory and Exact Match", "Failure",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if("KB Number".equals(selectedChoice) && selectedKbNumber!=null && !"".equals(selectedKbNumber)) {
			try {
					Integer.parseInt(numKBNumber.getText());
				}catch(NumberFormatException ex) {
				
					JOptionPane.showMessageDialog(null, "KB Number should numeric", "Failure",
							JOptionPane.ERROR_MESSAGE);
					txtFileName.setText(null);
					numKBNumber.setText(null);
					
					return;
				}
			}
			
			System.out.println("AAYYYYYYYYYYY");
			SeleniumCode sel=new SeleniumCode();
			sel.invokeBrowser();
			
			if(selectedChoice.toString().equalsIgnoreCase("File Name")) {
				strFiles=sel.downloadFileWithFileName(selectedFileName);
				System.out.println(strFiles);
			}else {
				strFiles=sel.downloadFileWithKBNumber(selectedKbNumber);
				System.out.println(strFiles);
			}
			
			for(String str:strFiles) {
				if(textArea.getText()!=null && !"".equals(textArea.getText())) {
					textArea.setText(textArea.getText()+"\n");
				}
				textArea.setText(textArea.getText()+str);
			}

			setVisible(true);
			
			
			
		} else {}
		}
	}

	


	public static void main(String s[]) {
		DownloadFiles l = new DownloadFiles();
		l.setSize(new Dimension(850,850));
		l.setTitle("File Down Load");
		l.setLocationRelativeTo(null);
		l.setVisible(true);
		l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		l.setResizable(true);
	}
}

class mwa extends WindowAdapter {
	public mwa() {

	}

	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}


}