package fontviewer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Font viewer useful for browsing through any and all installed fonts
 * 
 * @author Darragh Maguire Kane
 */

public class FontViewer
{
	private int[] fontSizes = {36, 30, 24, 21, 18, 16, 14, 12, 11, 10, 9, 8, 7, 6}; //in pixels
	private final int FONT_SIZE = 40;
	
	private JFrame frame;
	private ImageIcon icon;
	private JTextPane tpExampleText;
	private JList<String> fontList;
	private JPanel panelMain;
	private JScrollPane scrollFontList, scrollTextPane;

	public static void main(String[] args)
	{
		new FontViewer();
	}
	
	public FontViewer()
	{
		setLookAndFeel();
		initialiseGuiComponents();
		frame.setVisible(true);
	}
	
	private void setLookAndFeel()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void initialiseGuiComponents()
	{		
		fontList = new JList<String>();
		tpExampleText = new JTextPane();
		scrollFontList = new JScrollPane();
		scrollTextPane = new JScrollPane();
		panelMain = new JPanel();	
		frame = new JFrame();
		
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		fontList.setListData(fontNames);
		fontList.addListSelectionListener(fontListSelectionListener);
		fontList.setSelectedIndex(0);
		fontList.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		
		tpExampleText.setContentType("text/html");
		tpExampleText.setEditable(false);
		tpExampleText.setFocusable(false);
		tpExampleText.setBorder(new EmptyBorder(0, 20, 10, 20));		
		tpExampleText.setFont(new Font(fontList.getSelectedValue(), Font.PLAIN, FONT_SIZE));
		fontChangeOccurred();
		
		scrollFontList.setViewportView(fontList);
		
		scrollTextPane.setViewportView(tpExampleText);
			
		panelMain.setLayout(new BorderLayout());
		panelMain.add(scrollFontList, BorderLayout.WEST);
		panelMain.add(scrollTextPane, BorderLayout.CENTER);
		panelMain.setBackground(Color.WHITE);

		icon = new ImageIcon(this.getClass().getResource("/fontviewer/resources/font_viewer_icon.png"));

		frame.add(panelMain);
		frame.setTitle("Font Viewer");
		frame.setIconImage(icon.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setLocationRelativeTo(null);
	}
	
	private ListSelectionListener fontListSelectionListener = new ListSelectionListener() 
	{		
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			fontChangeOccurred();	
		}
	};
	
	private void fontChangeOccurred()
	{		
		String exampleText = "";
		
		for(int i = 0; i < fontSizes.length; i++)
		{
			exampleText += "<p style='font-family:" + fontList.getSelectedValue() + "; font-size:" + (fontSizes[i]/16.0f)*100 + "%'>(" + fontSizes[i] + "px) The quick brown fox jumps over the lazy dog</p>";
		}
		
		tpExampleText.setText(exampleText);
		tpExampleText.setCaretPosition(0);
	}
}