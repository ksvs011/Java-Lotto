import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.json.simple.JSONObject;

@SuppressWarnings("serial")
public class Lotto extends JFrame implements MouseListener, KeyListener {
	MyButton[] mbt = new MyButton[7];
	int[] arr = new int[7];
	JTextField[] mnum = new JTextField[6]; 

	JButton checkBtn = new JButton("해당회차로");
	JTextField turnTxt = new JTextField(""); 
	JLabel tutleLbl = new JLabel("로또 번호 조회");
	JLabel turnLbl = new JLabel(""); 
	JLabel tuturnLbl = new JLabel(""); 
	JLabel numberLbl = new JLabel("내 번호 조회"); 
	JLabel dateLbl = new JLabel(""); 
	JLabel rankLbl = new JLabel(""); 
	JButton okBtn = new JButton("확인");
	JButton clearBtn = new JButton("초기화");
	JLabel plusLbl = new JLabel(new ImageIcon(new ImageIcon("image/plus.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
	
	public void init() {
		getContentPane().setLayout(null);
		for (int i = 0; i < mbt.length - 1; i++) {
			int wm = 80;
			mbt[i] = new MyButton("");
			mbt[i].setBounds(20 + wm * i, 50, 65, 65);
			getContentPane().add(mbt[i]);
		}
		mbt[6] = new MyButton("");
		mbt[6].setBounds(560, 50, 65, 65);
		getContentPane().add(mbt[6]);
		
		int w = 10;
		for (int i = 0; i < mnum.length; i++) {
			mnum[i] = new JTextField();
			mnum[i].setBounds(10 + w, 300, 46, 46);
			w = w + 70;
			getContentPane().add(mnum[i]);
		}

		plusLbl.setBounds(500, 60, 50, 50); 
		getContentPane().add(plusLbl);
		tutleLbl.setBounds(20, 10, 150, 30); 
		getContentPane().add(tutleLbl);
		tuturnLbl.setBounds(150, 10, 150, 30); 
		getContentPane().add(tuturnLbl);
		turnLbl.setBounds(350, 170, 600, 50); 
		getContentPane().add(turnLbl);
		turnTxt.setBounds(100, 170, 100, 50); 
		getContentPane().add(turnTxt);
		checkBtn.setBounds(220, 170, 120, 50); 
		getContentPane().add(checkBtn);
		numberLbl.setBounds(20, 240, 100, 50);
		getContentPane().add(numberLbl);
		dateLbl.setBounds(240, 10, 150, 30); 
		getContentPane().add(dateLbl);
		rankLbl.setBounds(20, 350, 600, 50); 
		getContentPane().add(rankLbl);
		okBtn.setBounds(435, 297, 80, 50); 
		getContentPane().add(okBtn);
		clearBtn.setBounds(530, 297, 100, 50); 
		getContentPane().add(clearBtn);
	}

	public void event() {
		checkBtn.addMouseListener(this);
		turnTxt.addKeyListener(this);
		okBtn.addMouseListener(this);
		clearBtn.addMouseListener(this);
	}

	public void showResult() {
		String turnNum = turnTxt.getText();
		System.out.println(turnNum);
		try {
			int a = Integer.parseInt(turnNum);
			if (a < 0 || a > 10000) {
				turnLbl.setText("번호다시 입력해 주세요.");
				return;
			}
		} catch (Exception e) {
			turnLbl.setText("번호다시 입력해 주세요.");
			turnTxt.setText("");
			return;
		}

		try {
			JsonReader jr = new JsonReader();
			JSONObject jo = jr.connectionUrlToJSON(turnTxt.getText());
			if (jo == null) {
				turnLbl.setText("접속에 실패하였습니다.");
				return;
			}
			if (String.valueOf(jo.get("retrunValue")).equals("fail")) {
				turnLbl.setText("회차정보가 없습니다.");
				clearNumber();
				return;
			}
			for (int i = 0; i < arr.length - 1; i++) {
				arr[i] = Integer.parseInt(String.valueOf(jo.get("drwtNo" + (i + 1))));
				if (arr[i] > 40) {
					mbt[i].setBgColor(Color.red);
					mbt[i].setTxtColor(Color.white);
				} else if (arr[i] > 30) {
					mbt[i].setBgColor(Color.orange);
					mbt[i].setTxtColor(Color.white);
				} else if (arr[i] > 20) {
					mbt[i].setBgColor(Color.magenta);
					mbt[i].setTxtColor(Color.white);
				} else if (arr[i] > 10) {
					mbt[i].setBgColor(Color.pink);
					mbt[i].setTxtColor(Color.white);
				} else if (arr[i] > 1) {
					mbt[i].setBgColor(Color.gray);
					mbt[i].setTxtColor(Color.white);
				}
				mbt[6].setText(String.valueOf(jo.get("bnusNo")));
			}
			for (int i = 0; i < mbt.length - 1; i++) {
				mbt[i].setText(String.valueOf(jo.get("drwtNo" + (i + 1))));
				System.out.println(jo.get("drwtNo" + (i + 1)));
			}
			tuturnLbl.setText(turnTxt.getText() + "회차");
			dateLbl.setText(String.valueOf(jo.get("drwNoDate")));
			clearTurnTxt();
			clearTurnLbl();
		} catch (Exception e) {
			e.printStackTrace();
			turnLbl.setText("예기치 못한 오류가 발생했습니다.");
			return;
		}
	}
	
	public void compare() {
		int cn = 0;
		int bn = 0;
		rankLbl.setText("");

		for(int i = 0; i< mbt.length - 1; i++) {
			for(int j=0;  j < 6; j++) {
				if(mnum[j].getText().equals(mbt[i].getText())) {
					rankLbl.setText(rankLbl.getText()+" "+ mbt[i].getText()+", ");
					cn++;
				}
			}
		}
		
		for(int i=0; i<mnum.length; i++) {
			if(mnum[i].getText().equals(mbt[6].getText())) {
				rankLbl.setText(rankLbl.getText()+" 보너스 번호 "+ mnum[i].getText());
				bn = 1;
			}
		}
		
		if(cn==6) {
			rankLbl.setText(rankLbl.getText() + "축하합니다! 1등 입니다.");
		} else if (cn==5 && bn == 1) {
			rankLbl.setText(rankLbl.getText() + "	축하합니다! 2등 입니다.");
		} else if (cn==5) {
			rankLbl.setText(rankLbl.getText() + "	축하합니다! 3등 입니다."); 
		} else if (cn==4) {
			rankLbl.setText(rankLbl.getText() + "	축하합니다! 4등 입니다.");
		} else if (cn==3) {
			rankLbl.setText(rankLbl.getText() + "	축하합니다! 5등 입니다.");
		} else {
			rankLbl.setText(rankLbl.getText() + "	꽝입니다.");
		}
	}
	
	public void reset() {
		for(int i = 0; i < 7; i++) {
			mnum[i].setText("");		
			clearNumber();
		}
	}

	private void clearNumber() {
		clearTurnLbl();
		clearTurnTxt();
		rankLbl.setText("");
	}
	
	void clearTurnLbl() {
		turnLbl.setText("");
	}
	void clearTurnTxt() {
		turnTxt.setText("");
	}
	void clearButton() {
		clearBtn.setText("");
	}


	public static void setUIFont(javax.swing.plaf.FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource)
				UIManager.put(key, f);
		}
	}

	Lotto() {
		super("로또번호 조회");
		init();
		event();

		setResizable(false);
		setPreferredSize(new Dimension(700, 600 / 12 * 9));
		setSize(700, 600 / 12 * 9);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}

	public static void main(String[] args) throws Exception {
		String fType = "NotoSansKR-Bold.otf";
		Font f1 = Font.createFont(Font.TRUETYPE_FONT, new File(fType));
		setUIFont(new FontUIResource(f1.deriveFont(18f)));
		new Lotto();
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			showResult();
		}
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			clearNumber();
			clearTurnLbl();
			clearTurnTxt();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Clicked");
		if (e.getSource() == checkBtn) {
			showResult();
		}
		
		if (e.getSource() == okBtn) {
			compare();
		}
		
		if (e.getSource() == clearBtn) {
			reset();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}


