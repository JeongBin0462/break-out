import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

class Jump extends JFrame {
	private int userX = 230;
	private int userY = 400;
	private int ballX = 230;
	private int ballY = 120;
	private int ySpeed = 5;
	private int xSpeed = 3;
	private List<JLabel> brickLabels;
	private int score = 0;

	public Jump() {
		JPanel pnl = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.BLACK);
				g.drawRect(10, 10, getWidth() - 20, getHeight() - 20);
			}
		};
		pnl.setLayout(null);

		JLabel lbl = new JLabel("ㅁㅁㅁㅁㅁㅁㅁ");
		lbl.setBounds(userX, userY, 100, 20);

		JLabel lbl2 = new JLabel("ㅇ");
		Font font2 = new Font("Serif", Font.BOLD, 20);
		lbl2.setFont(font2);
		lbl2.setBounds(ballX, ballY, 30, 30);
		
		JLabel lbl3 = new JLabel("현재 점수는: " + score);
		lbl3.setBounds(300, 460, 200, 20);

		JLabel lbl4 = new JLabel("GAME OVER");
		lbl4.setBounds(120, 200, 300, 40);
		Font font = new Font("Serif", Font.BOLD, 40);
		lbl4.setFont(font);

		JLabel lbl5 = new JLabel("GAME CLEAR");
		lbl5.setBounds(120, 200, 300, 40);
		lbl5.setFont(font);

		initializeBricks(pnl);

		Timer timer = new Timer(24, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (lbl2.getBounds().intersects(lbl.getBounds())) {
					if (lbl.getX() + (lbl.getWidth() / 2) > lbl2.getX()) { // 오른쪽 부분에 닿였을 때
						if (xSpeed > 0) { // 공이 왼쪽으로
							xSpeed = -xSpeed; // 공 반대로
						}
					} else {
						if (xSpeed < 0) {
							xSpeed = -xSpeed;
						}
					}
					ySpeed = -ySpeed;
				}

				if (ballY < 20)
					ySpeed = -ySpeed;

				if (ballX > 410) {
					xSpeed = -xSpeed;
					ballX = 410;
				}

				if (ballX < 20) {
					xSpeed = -xSpeed;
					ballX = 20;
				}

				if (ballY == 530) {
					pnl.add(lbl4);
					pnl.remove(lbl2);
				}

				if (score == 800) {
					xSpeed = 0;
					ySpeed = 0;
					pnl.add(lbl5);
					pnl.remove(lbl2);
				}

				for (int i = 0; i < brickLabels.size(); i++) {
				    JLabel brickLabel = brickLabels.get(i);
				    if (brickLabel.getBounds().intersects(lbl2.getBounds())) {
				        pnl.remove(brickLabel);
				        brickLabels.remove(i);
				        i--;
				        score += 50;
				        lbl3.setText("현재 점수는: " + score);
				        ySpeed = -ySpeed;
				    }
				}
				ballY += ySpeed;
				ballX += xSpeed;

				lbl2.setLocation(ballX, ballY);

				pnl.repaint();
			}
		});
		timer.start();

		// 포커스가 가능하게 설정
		pnl.setFocusable(true);
		// 키 이벤트 생성
		pnl.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					userX -= 10;
					if (userX < 20) {
						userX = 20;
					}
					lbl.setLocation(userX, userY);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					userX += 10;
					if (userX > 380) {
						userX = 380;
					}
					lbl.setLocation(userX, userY);
				}
			}
		});

		pnl.add(lbl);
		pnl.add(lbl2);
		pnl.add(lbl3);
		add(pnl);

		setSize(500, 530);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void initializeBricks(JPanel panel) {
	    String[] labelBlock = { "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ",
	        "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ", "ㅍㅍㅍㅍㅍㅍㅍ", };
	    
	    brickLabels = new ArrayList<JLabel>();
	    int num1 = 0;
	    int num2 = 0;

	    for (int i = 0; i < labelBlock.length; i++) {
	        JLabel brickLabel = new JLabel(labelBlock[i]);
	        brickLabel.setBounds(43 + (num1 * 100), 40 + num2, 100, 20);
	        num1 += 1;
	        if (num1 % 4 == 0) {
	            num2 += 20;
	            num1 = 0;
	        }

	        brickLabels.add(brickLabel);
	        panel.add(brickLabel);
	    }
	}
}


public class BreakOut {
	public static void main(String[] args) {
		new Jump();
	}
}
