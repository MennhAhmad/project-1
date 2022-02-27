package com.sig.invoices;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class InvoiceFrame extends JFrame {

//	private JSplitPane splitPane;
	JPanel leftPanel, rightPanel, invoiceItemsPanel, inputPanel;

	private JLabel name;
    private JTextField tname;
    private JLabel mno;
    private JTextField tmno;
    
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem loadFileMenuItem, saveFileMenuItem, exitMenuItem;

	private JButton newButton, deleteButton, saveButton, cancelButton;

	private JTable invoicesTable, invoiceItems;
	private String[] invoicesCols = { "No.", "Date", "Customer", "Total" };
//	private String[][] invoicesRows = { { "1", "Date", "AAA", "125" }, { "2", "Date", "BBB", "150" } };
	private String[][] invoices = loadInvoices();
	private String[][] invoicesRows = loadInvoices();
	private String[] itemsCols = { "No.", "Item Name", "ItemPrice", "Count", "Item Total" };
//	private String[][] itemsRows = { { "1", "Date", "AAA", "125", "" }, { "2", "Date", "BBB", "150", "" } };
//	private String[] itemsCols = { "Item Name", "ItemPrice", "Count"};
	private String[][] invoicesItems = loadInvoicesItems();
	private String[] selectedInvoice = invoices[0];
	private String[][] selectedInvoiceItems = getInvoiceItems(selectedInvoice[0]);
	private int selectedInvoiceTotal = getInvoiceTotal(selectedInvoiceItems);
	private JLabel invoiceNumber, invoiceTotal;
	private JTextField invoiceDate, customerName;

	public InvoiceFrame() {
		super("Sales Invoice Genrator");
//		setLayout(new FlowLayout());
//		setLayout(new GridLayout(1, 2));
		setLayout(new GridLayout());

		leftPanel = new JPanel();
		rightPanel = new JPanel();
//        leftPanel.setBackground(Color.orange);
//        rightPanel.setBackground(Color.green);
		add(leftPanel);
		add(rightPanel);

//        splitPane = new JSplitPane();
//        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
//        splitPane.setResizeWeight(0.5);
//        splitPane.setContinuousLayout(true);
//                splitPane.setDividerLocation(getWidth() / 2);

//        getContentPane().setLayout(new GridLayout()); 
//        getContentPane().add(splitPane);
//        setContentPane(splitPane);
//        add(splitPane, BorderLayout.CENTER);

//		
		addMenuBar();
//		
//		leftPanel.setLayout(new GridLayout());
		JLabel lblLeft = new JLabel("Invoices Table");
		leftPanel.add(lblLeft);
		invoicesTable = new JTable(invoicesRows, invoicesCols);
		leftPanel.add(new JScrollPane(invoicesTable));

//		JPanel inputPanel = new JPanel();
		newButton = new JButton("Create New Invoice");
		leftPanel.add(newButton);
		deleteButton = new JButton("Delete Invoice");
		leftPanel.add(deleteButton);
//		leftPanel.add(inputPanel);
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(0, 2));
        inputPanel.add(new JLabel("Invoice Number "));
		invoiceNumber = new JLabel(selectedInvoice[0]);
		inputPanel.add(invoiceNumber);
		rightPanel.add(inputPanel);

		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(0, 2));
		inputPanel.add(new JLabel("Invoice Date "));
		invoiceDate = new JTextField(selectedInvoice[1], 15);
		inputPanel.add(invoiceDate);
		rightPanel.add(inputPanel);
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(0, 2));
		inputPanel.add(new JLabel("Customer Name "));
		customerName = new JTextField(selectedInvoice[2], 15);
		inputPanel.add(customerName);
		rightPanel.add(inputPanel);
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(0, 2));		
		inputPanel.add(new JLabel("Invoice Total "));
		invoiceTotal = new JLabel(Integer.toString(selectedInvoiceTotal));
		inputPanel.add(invoiceTotal);
		rightPanel.add(inputPanel);
		
		invoiceItemsPanel = new JPanel();
		invoiceItemsPanel.setBorder(BorderFactory.createTitledBorder("Invoice Items"));
		invoiceItems = new JTable(selectedInvoiceItems, itemsCols);
		invoiceItemsPanel.add(new JScrollPane(invoiceItems));
		rightPanel.add(invoiceItemsPanel);

		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(0, 2));	
		saveButton = new JButton("Save");
		inputPanel.add(saveButton);
		cancelButton = new JButton("Cancel");
		inputPanel.add(cancelButton);
		rightPanel.add(inputPanel);

		setSize(900, 500);
		setLocation(200, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void addMenuBar() {
		menuBar = new JMenuBar();
//		
		loadFileMenuItem = new JMenuItem("Load File", 'L');
		loadFileMenuItem.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.ALT_DOWN_MASK));
		saveFileMenuItem = new JMenuItem("Save File", 'S');
		saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.ALT_DOWN_MASK));
		exitMenuItem = new JMenuItem("Exit", 'X');
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke('X', KeyEvent.ALT_DOWN_MASK));

		fileMenu = new JMenu("File");
		fileMenu.add(loadFileMenuItem);
		fileMenu.add(saveFileMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);

		setJMenuBar(menuBar);
	}

//	public static String[][] loadInvoices1() {
//
//		String[][] data = new String[50][];
//		String fileName = "./data/InvoiceHeader.csv";
//
//		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));) {
//
//			String line;
//
//			int i = 0;
//			while ((line = br.readLine()) != null) {
//
//				System.out.println(line);
//				String[] header = line.split(",");
//				data[i++] = header;
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		System.out.println();
//		System.out.println("data: " + data[0][0]);
//		return data;
//	}

	public String[][] loadInvoices() {
		String fileName = "./data/InvoiceHeader.csv";

		String[] lines = loadFile(fileName);
		String[][] records = new String[lines.length][];
		int i = 0;
		for (String line : lines) {
			records[i++] = line.split(",");
		}
		return records;
	}

	public String[][] loadInvoicesItems() {
		String fileName = "./data/InvoiceLine.csv";

		String[] lines = loadFile(fileName);
		String[][] records = new String[lines.length][];
		int i = 0;
		for (String line : lines) {
			records[i++] = line.split(",");
		}
		return records;
	}

	public String[][] getInvoiceItems(String invoiceNo) {

		List<String[]> items = new ArrayList<>();

		int n = 1;
		for (int i = 0; i < invoicesItems.length; i++) {
			String[] item = invoicesItems[i];
			if (item[0].equals(invoiceNo)) {
				int total = Integer.valueOf(item[2]) * Integer.valueOf(item[3]);
				String[] itemDetails = { String.valueOf(n++), item[1], item[2], item[3], Integer.toString(total) };
				items.add(itemDetails);
			}
		}
		String[][] itemsArr = new String[items.size()][];
		int i = 0;
		for (String[] item : items) {
			itemsArr[i++] = item;
		}
		return itemsArr;
	}

	public int getInvoiceTotal(String[][] items) {

		int total = 0;
		for (int i = 0; i < items.length; i++) {
			total += Integer.valueOf(items[i][4]);
		}
		return total;
	}

	public String[] loadFile(String fileName) {
		List<String> lines = new ArrayList<String>();
		FileInputStream fis = null;
		BufferedReader reader = null;
		try {
//		BufferedReader in = new BufferedReader(new FileReader(fileName));
			fis = new FileInputStream(fileName);
			reader = new BufferedReader(new InputStreamReader(fis));

			String line = reader.readLine();

			while (line != null) {
				System.out.println(line);
				lines.add(line);
				line = reader.readLine();
			}
		} catch (FileNotFoundException ex) {
			System.out.println("File NotFoundException");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("IOException");
			ex.printStackTrace();
		} finally {
			try {
				reader.close();
				fis.close();
			} catch (IOException ex) {
				System.out.println("IOException");
				ex.printStackTrace();
			}
		}

		String[] output = lines.toArray(new String[lines.size()]);
		System.out.println(" " + Arrays.toString(output));
		return output;
	}

	public static void main(String[] args) {
		new InvoiceFrame();
//		loadInvoices();
	}

}
