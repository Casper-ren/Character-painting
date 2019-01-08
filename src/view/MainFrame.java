package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import utils.ImageChange;
import utils.ImageConfirm;
import view.MyPanel;


public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MyPanel contentPane;
	private JFileChooser chooser;
	/**
	 * Launch the application.
	 */
	int a,b,c,d;
	JPopupMenu popupMenu;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		chooser=new JFileChooser();
		setUndecorated(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode()==0) {
					System.exit(0);
				}
			}
		});
		setTitle("MagicChange");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new MyPanel();
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(c+(e.getXOnScreen()-a),d+(e.getYOnScreen()-b));
			}
		});
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				a=e.getXOnScreen();
				b=e.getYOnScreen();
				c=getX();
				d=getY();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				popupMenu.show(MainFrame.this, e.getX(), e.getY());
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnMin = new JButton("-");
		btnMin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});
		btnMin.setBounds(279, 0, 59, 23);
		contentPane.add(btnMin);
		
		JButton btnMax = new JButton("□");
		btnMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnMax.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(getExtendedState()==JFrame.MAXIMIZED_BOTH) {
					setExtendedState(JFrame.NORMAL);
				}else {
					setExtendedState(JFrame.MAXIMIZED_BOTH);
				}
			}
		});
		btnMax.setBounds(334, 0, 59, 23);
		contentPane.add(btnMax);
		
		JButton btnExit = new JButton("×");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int value =JOptionPane.showConfirmDialog(MainFrame.this,"确定退出吗?");
				if(value==JOptionPane.YES_OPTION) {
					System.exit(0);
			}
			}
		});
		btnExit.setBounds(391, 0, 59, 23);
		contentPane.add(btnExit);
		
		
		
		popupMenu=new JPopupMenu();
		JMenuItem a=new JMenuItem("请不要乱点");
		popupMenu.add(a);

		JTextPane textPane = new JTextPane();
		textPane.setEnabled(false);
		//Copy path to text field 实现拖拉文件至文本框后读取文件路径
        textPane.setTransferHandler(new TransferHandler() 
        {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@Override
            public boolean importData(JComponent comp, Transferable t) {
                try {
                    Object o = t.getTransferData(DataFlavor.javaFileListFlavor);
 
                    String filepath = o.toString();
                    if (filepath.startsWith("[")) {
                        filepath = filepath.substring(1);
                    }
                    if (filepath.endsWith("]")) {
                        filepath = filepath.substring(0, filepath.length() - 1);
                    }
                    
                    //验证拖入的文件是为图片
                    textPane.setText(filepath);
                    if(ImageConfirm.isimage(filepath) == false) {
                    	textPane.setText("该文件不是图片，请重选");
                    }
                    
                    return true;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
            @Override
			public boolean canImport(JComponent comp, DataFlavor[] flavors) {
				for (int i = 0; i < flavors.length; i++) {
					if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
						return true;
					}
				}
				return false;
			}
		});
        
        
		//选择图片
		JButton btnFileOpen = new JButton("\u9009\u62E9\u56FE\u7247");
		btnFileOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter("image","jpg","png");
				chooser.setFileFilter(filter);
				int value=chooser.showOpenDialog(null);
				if(value==JFileChooser.APPROVE_OPTION) {
					File file=chooser.getSelectedFile();
					textPane.setText(file.getAbsolutePath());
					try {

						BufferedImage image = ImageIO.read(file);
						JFrame frame2 = new JFrame();
					    JLabel label = new JLabel(new ImageIcon(image));
					    frame2.getContentPane().add(label, BorderLayout.CENTER);
					    frame2.pack();
					    frame2.setVisible(true); 

					     
					 	}catch (IOException e1) {
					 		// TODO Auto-generated catch block
					 		e1.printStackTrace();
					}
				}
			}
		});
		
		//开始转换
		JButton btnNewButton = new JButton("\u5F00\u59CB\u8F6C\u6362"); 
		btnNewButton.addMouseListener(new MouseAdapter() {
			 @Override
			public void mouseClicked(MouseEvent e) {
				String path=textPane.getText();
				BufferedImage oriImg = null;
				try {
					oriImg = ImageIO.read(new File(path));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int width;
				int height;
				int rgb;

				width = oriImg.getWidth();
				height = oriImg.getHeight();
//				BufferedImage BinaryImage;
//				BinaryImage = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
				//字符由简单到复杂，用来表示不同灰度
				String str="@#$%*o!(^.";
				int r,g,b,index;
				int grey;
				//字符画路径
				String txtDir="字符化"+".txt";
				//写入文本文件
				try(FileWriter fw = new FileWriter(txtDir)){
				for(int i=0;i<height;i+=8) {
					for(int j=0;j<width;j+=5) {
						rgb = oriImg.getRGB(j, i);
						r = (rgb & 0xff0000) >>> 16;
						g = (rgb & 0xff00) >>> 8;
						b = (rgb & 0xff);
						grey =(int)(0.299*r+0.587*g+0.114*b);
						
						index = Math.round((grey*str.length())/255);
						if(index>=str.length()) {
							fw.write(" ");
						}
						else fw.write(str.charAt(index));
						
					}
					fw.write("\r\n");

					
				}} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println("................................已生成字符画......................");
				//打开字符画文件
				Runtime rt = Runtime.getRuntime();
				try {
					rt.exec("cmd.exe  /c "+txtDir);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 				 
				 
			}
		});
		
		
		//黑白
		JButton btnNewButton_1 = new JButton("\u9ED1\u767D");
		btnNewButton_1.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		btnNewButton_1.setForeground(new Color(255, 140, 0));
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ImageIcon imageIcon = new ImageIcon(textPane.getText());
				ImageChange.filter(imageIcon,1);
			}
		});
		
		//底片
		JButton btnNewButton_2 = new JButton("\u5E95\u7247");
		btnNewButton_2.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		btnNewButton_2.setForeground(new Color(255, 140, 0));
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String path=textPane.getText();
				ImageIcon imageIcon = new ImageIcon(path);
				ImageChange.filter(imageIcon,2);
			}
		});
		
		//灰度
		JButton btnNewButton_3 = new JButton("\u7070\u5EA6");
		btnNewButton_3.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		btnNewButton_3.setForeground(new Color(255, 140, 0));
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String path=textPane.getText();
				ImageIcon imageIcon = new ImageIcon(path);
				ImageChange.filter(imageIcon,3);
			}
		});
		
		JLabel label = new JLabel("\u53EF\u9009\u6EE4\u955C\uFF1A");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label.setForeground(new Color(255, 140, 0));
		
		JLabel label_1 = new JLabel("\u6587\u4EF6\u8DEF\u5F84\uFF1A");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_1.setForeground(new Color(165, 42, 42));
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(label_1)
								.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnMin, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnMax)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
								.addComponent(btnFileOpen, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
								.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(label_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnExit)
								.addComponent(btnMax)
								.addComponent(btnMin))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnFileOpen, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
					.addGap(70)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1)
						.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_3)))
		);
		contentPane.setLayout(gl_contentPane);
	}
}

