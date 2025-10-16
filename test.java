package swingTest;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class test implements ActionListener {

	private JFrame frame;
	private JTextField txtNo;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtTel;
	private JTable table;
	public JButton btnTotal, btnAdd, btnDel, btnSearch, btnCancel;
	String driver = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "system";
	String password = "123456";
	Connection conn = null;
	PreparedStatement pstmtin, pstmtse, pstmtup, pstmtde;
	Statement stmt = null;
	ResultSet rs = null;
	
	private static final int NONE = 0;
	private static final int ADD = 1;
	private static final int DELETE = 2;
	private static final int SEARCH = 3;
	private static final int TOTAL = 4;
	
	int cmd = NONE;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
		dbconn();
		init();
	}
	
	private void init() {
		txtNo.setText("");
		txtName.setText("");
		txtEmail.setText("");
		txtTel.setText("");
		txtNo.setEditable(false);
		txtName.setEditable(false);
		txtEmail.setEditable(false);
		txtTel.setEditable(false);
	}
	
	private void dbconn() {
		try {
			
			Class.forName(driver); // 1단계
			conn = DriverManager.getConnection(url, user, password); // 연결
			System.out.println("DB 연결 성공");
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("고객관리");
		frame.setBounds(100, 100, 817, 507);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("번호");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		lblNewLabel.setBounds(52, 68, 57, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("이름");
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		lblNewLabel_1.setBounds(52, 153, 57, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("이메일");
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		lblNewLabel_2.setBounds(52, 238, 57, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("전화번호");
		lblNewLabel_3.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		lblNewLabel_3.setBounds(52, 323, 57, 15);
		frame.getContentPane().add(lblNewLabel_3);
		
		txtNo = new JTextField();
		txtNo.setBounds(120, 67, 116, 21);
		frame.getContentPane().add(txtNo);
		txtNo.setColumns(10);
		
		txtName = new JTextField();
		txtName.setBounds(121, 152, 116, 21);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(120, 237, 116, 21);
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		txtTel = new JTextField();
		txtTel.setBounds(120, 322, 116, 21);
		frame.getContentPane().add(txtTel);
		txtTel.setColumns(10);
		
		btnTotal = new JButton("전체보기");
		btnTotal.setBounds(52, 401, 97, 23);
		frame.getContentPane().add(btnTotal);
		
		btnAdd = new JButton("추가");
		btnAdd.setBounds(201, 401, 97, 23);
		frame.getContentPane().add(btnAdd);
		
		btnDel = new JButton("삭제");
		btnDel.setBounds(350, 401, 97, 23);
		frame.getContentPane().add(btnDel);
		
		btnSearch = new JButton("검색");
		btnSearch.setBounds(499, 401, 97, 23);
		frame.getContentPane().add(btnSearch);
		
		btnCancel = new JButton("취소");
		btnCancel.setBounds(648, 401, 97, 23);
		frame.getContentPane().add(btnCancel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(271, 68, 474, 275);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnTotal.addActionListener(this);
		btnAdd.addActionListener(this);
		btnDel.addActionListener(this);
		btnSearch.addActionListener(this);
		btnCancel.addActionListener(this);
		
	}

	// DB 추가 부분 -------------------------------------------------------------
	public void add() {
		try {
			String sql = "insert into customer(code, name, email, tel) values(?, ?, ?, ?)";
			String strNo = txtNo.getText();
			String strName = txtName.getText();
			String strEmail = txtEmail.getText();
			String strTel = txtTel.getText();
			
			if(strNo.length() < 1 || strName.length() < 1) {
				JOptionPane.showMessageDialog(null, "번호와 이름은 필수입니다.");
				return;
			}
			
			pstmtin = conn.prepareStatement(sql);
			pstmtin.setString(1, strNo);
			pstmtin.setString(2, strName);
			pstmtin.setString(3, strEmail);
			pstmtin.setString(4, strTel);
			int r = pstmtin.executeUpdate();
			
			if(r >= 1) JOptionPane.showMessageDialog(null, "성공");
			else JOptionPane.showMessageDialog(null, "실패");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// DB 삭제 부분 -------------------------------------------------------------
	public void del() {
		System.out.println("DB 삭제");
	}
	
	public void total() {
		System.out.println("DB 전체보기");
	}
	
	public void search() {
		System.out.println("DB 검색");
	}
	
	public void setText(int command) {
		switch (command) {
		case ADD:
			txtNo.setEditable(true);
			txtName.setEditable(true);
			txtEmail.setEditable(true);
			txtTel.setEditable(true);
			break;
		case DELETE:
			txtNo.setEditable(true);
			txtName.setEditable(false);
			txtEmail.setEditable(false);
			txtTel.setEditable(false);
			break;
		case SEARCH:
			txtNo.setEditable(false);
			txtName.setEditable(true);
			txtEmail.setEditable(false);
			txtTel.setEditable(false);
			break;
		case NONE:
//			txtNo.setEditable(true);
//			txtName.setEditable(true);
//			txtEmail.setEditable(true);
//			txtTel.setEditable(true);
			break;
		}
		setBtn(command);
	}
	
	public void setBtn(int command) {
		btnTotal.setEnabled(false);
		btnAdd.setEnabled(false);
		btnDel.setEnabled(false);
		btnSearch.setEnabled(false);
		
		switch(command) {
		case ADD:
			btnAdd.setEnabled(true);
			cmd = ADD;
			break;
		case DELETE:
			btnDel.setEnabled(true);
			cmd = DELETE;
			break;
		case SEARCH:
			btnSearch.setEnabled(true);
			cmd = SEARCH;
			break;
		case TOTAL:
			btnTotal.setEnabled(true);
			cmd = TOTAL;
			break;
		case NONE:
			btnTotal.setEnabled(true);
			btnAdd.setEnabled(true);
			btnDel.setEnabled(true);
			btnSearch.setEnabled(true);
			cmd = NONE;
			break;
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnTotal) {
			setText(TOTAL);
			frame.setTitle("전체내용 보기");
			total();
			System.out.println("전체보기 버튼");
		}
		
		else if(e.getSource() == btnAdd) {
			if(cmd != ADD) {
				frame.setTitle("데이터추가");
				setText(ADD);
				return;
			}
			add(); // DB 추가
			frame.setTitle("데이터 추가되었음");
			System.out.println("추가 버튼");
		}
			
		else if(e.getSource() == btnDel) {
			if(cmd != DELETE) {
				frame.setTitle("데이터삭제");
				setText(DELETE);
				return;
			}
			del(); // DB 삭제
			frame.setTitle("데이터 삭제되었음");
			System.out.println("삭제 버튼");
		}
		
		else if(e.getSource() == btnSearch) {
			if(cmd != SEARCH) {
				frame.setTitle("데이터검색");
				setText(SEARCH);
				return;
			}
			search(); // DB 검색
			System.out.println("검색 버튼");
		}
		
		setText(NONE);
	}
}
